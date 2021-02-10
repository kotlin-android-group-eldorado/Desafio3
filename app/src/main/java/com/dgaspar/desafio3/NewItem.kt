package com.dgaspar.desafio3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import io.fotoapparat.Fotoapparat
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.view.CameraView
import java.io.File

class NewItem : AppCompatActivity() {

    private var fotoapparat: Fotoapparat? = null
    private val filename = "test.png"
    val sd = Environment.getExternalStorageDirectory()
    private val dest = File(sd, filename)
    private var fotoapparatState : FotoapparatState? = null
    private var cameraStatus : CameraState? = null
    private var flashState: FlashState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF
    }

    fun saveItem(view : View) {
        println("SALVA PRODUTO")
    }

    fun takePhoto(view : View) {
        println("Entrou takePhotoButton")
        takePhoto()
    }

    private fun createFotoapparat(){
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        fotoapparat = Fotoapparat(
            context = this,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
            logger = loggers(
                logcat()
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )
    }

    private fun takePhoto() {
        println("Entrou no takePhoto")
         fotoapparat
           ?.takePicture()
           ?.saveToFile(dest)
    }

    override fun onStart() {
        super.onStart()
        fotoapparat?.start()
        fotoapparatState = FotoapparatState.ON
    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onResume() {
        super.onResume()
        if(fotoapparatState == FotoapparatState.OFF){
            val intent = Intent(baseContext, NewItem::class.java)
            startActivity(intent)
            finish()
        }
    }

}

enum class CameraState{
    FRONT, BACK
}

enum class FlashState{
    TORCH, OFF
}

enum class FotoapparatState{
    ON, OFF
}

