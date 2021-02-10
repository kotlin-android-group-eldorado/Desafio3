package com.dgaspar.desafio3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class NewItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
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
            "pathImage" + "\n"
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