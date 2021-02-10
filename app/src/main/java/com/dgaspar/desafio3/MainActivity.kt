package com.dgaspar.desafio3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*

var FILE_NAME : String = "data.tsv"
var TSV_HEADER : String = "id\tname\tprice\tquantity\tpathImage"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get list layout id
        var listItemsLayout : LinearLayout = findViewById(R.id.listItemsLayout)

        // check permissions
        if (hasNoPermissions()){
            requestPermission()
            println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")
        }

        // check if file exists
        //var file : File = File(Environment.getExternalStorageDirectory(), FILE_NAME)
        var file : File = File(getExternalFilesDir(""), FILE_NAME)
        //if (file.exists()) file.delete() // uncomment for tests
        if (!file.exists()){
            var fileWriter : FileWriter = FileWriter(file)
            fileWriter.append(TSV_HEADER + "\n")
            fileWriter.close()
        }

        // fileReader
        if(file.exists()){
            println("READ FILE")
            var fileReader : BufferedReader = BufferedReader(FileReader(file))
            var line : String
            var i : Int = 1

            // cycle through lines
            try {
                // header
                line = fileReader.readLine()

                // line 1
                line = fileReader.readLine()

                // next lines
                while (line != null){
                    println("READLINE: " + line)
                    // parse line (TSV)
                    var tokens : List<String> = line.split("\t")

                    // add item
                    createListItem(
                        listItemsLayout,
                        i,
                        tokens[1],
                        tokens[2],
                        tokens[3]
                    )

                    // add horizontal line
                    createHorizontalLine(listItemsLayout)

                    line = fileReader.readLine()
                    i++
                }
            } catch (e: Exception) {
                println("Reading TSV Error!")
                e.printStackTrace()
            } finally {
                try {
                    if (fileReader != null) fileReader.close()
                } catch (e: Exception) {
                    println("closing TSV Error!")
                    e.printStackTrace()
                }
            }
        }
    }

    // ###############################################################################

    fun newItem(view : View){
        // go to NewItem activity
        var intent = Intent(this, NewItem::class.java)
        startActivity(intent)
    }

    // ###############################################################################

    fun createListItem (
        layout : LinearLayout,
        id : Int,
        name : String,
        price : String,
        quantity : String
    ) {

        // item layout params
        var layoutParams : LayoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 50, 0, 50)

        // item layout
        var itemLayout : LinearLayout = LinearLayout(this)
        itemLayout.layoutParams = layoutParams
        itemLayout.orientation = LinearLayout.HORIZONTAL

        // textView
        var nameTextView : TextView =  createTextView(
            itemLayout,
            id,
            "Produto: " + name + "\n" +
                    "Preço unitário: " + price + "\n" +
                    "Quantidade: " + quantity
        )

        layout.addView(itemLayout)
    }

    fun createTextView (
        layout : LinearLayout,
        id : Int,
        text : String,
        textSize : Float = 20f
    ) : TextView {
        var textView : TextView = TextView(this)
        textView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        textView.textSize = textSize
        textView.id = id
        textView.text = text
        layout.addView(textView)

        return textView
    }

    fun createHorizontalLine (
        layout : LinearLayout,
        height : Int = 2
    ){
        var horLine : View = View(this)
        horLine.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                height
        )
        horLine.setBackgroundColor(Color.parseColor("#c0c0c0"))

        // add horizontal line
        layout.addView(horLine)
    }

    // ###############################################################################

    // request permissions

    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    fun hasNoPermissions() : Boolean{
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

    // ###############################################################################

}