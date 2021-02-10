package com.dgaspar.desafio3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.fotoapparat.Fotoapparat
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.view.CameraView
import java.io.File

class NewItem : AppCompatActivity() {

    var fotoapparat: Fotoapparat? = null
    val filename = "test.png"
    val sd = Environment.getExternalStorageDirectory()
    val dest = File(sd, filename)
    var fotoapparatState : FotoapparatState? = null
    var cameraStatus : CameraState? = null
    var flashState: FlashState? = null

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

    /*override fun onResume() {
        super.onResume()
        if(!hasNoPermissions() && fotoapparatState == FotoapparatState.OFF){
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/

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

