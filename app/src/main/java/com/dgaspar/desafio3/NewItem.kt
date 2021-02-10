package com.dgaspar.desafio3

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class NewItem : AppCompatActivity() {
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri : Uri? = null
    var PATH : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
    }

    // ###############################################################################

    fun takePhoto(view : View){
        println("TIRA FOTO")

        //var file : File = File(getExternalFilesDir(""), FILE_NAME)

        var values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // camera intent
        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            var imageView : ImageView = findViewById(R.id.imageView)
            imageView.setImageURI(image_uri)

            // get path
            var file : File = File(getRealPathFromURI(image_uri))
            println("CCCCCCCCCCCCCCC: " + file.absolutePath)
            PATH = file.absolutePath
        }
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (contentResolver != null) {
            val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    // ###############################################################################

    fun saveItem(view : View) {
        println("SALVA PRODUTO")

        // id
        var lastLine : String = getLastLine(FILE_NAME).split("\t")[0]
        var id : Int
        if (lastLine == "id"){
            id = 0
        } else {
            id = lastLine.toInt() + 1
        }

        // name editText
        var nameEditText : EditText = findViewById(R.id.name)

        // name editText
        var valueEditText : EditText = findViewById(R.id.value)

        // name editText
        var quantityEditText : EditText = findViewById(R.id.quantity)

        // append file
        var file : File = File(getExternalFilesDir(""), FILE_NAME)
        var fileWriter : FileWriter = FileWriter(file, true)
        fileWriter.append(
            id.toString() + "\t" +
            nameEditText.text.toString() + "\t" +
            valueEditText.text.toString() + "\t" +
            quantityEditText.text.toString() + "\t" +
            PATH + "\n"
        )
        fileWriter.close()

        // toast
        Toast.makeText(this, "Produto salvo!", Toast.LENGTH_SHORT).show()

        // clear editTexts
        nameEditText.text.clear()
        valueEditText.text.clear()
        quantityEditText.text.clear()
    }

    // ###############################################################################

    fun getLastLine(
        fileName : String
    ) : String {
        var file : File = File(getExternalFilesDir(""), fileName)
        var fileReader : BufferedReader = BufferedReader(FileReader(file))
        var line : String
        var lastLine : String = ""

        try{
            line = fileReader.readLine()
            while(line != null){
                lastLine = line

                line = fileReader.readLine()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return lastLine
    }
}