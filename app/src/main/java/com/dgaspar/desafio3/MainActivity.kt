package com.dgaspar.desafio3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom

class MainActivity : AppCompatActivity() {
    //var idAux : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get list layout id
        var listItemsLayout : LinearLayout = findViewById(R.id.listItemsLayout)

        // list items
        listItems(listItemsLayout)
    }

    fun newItem(view : View){
        // go to NewItem activity
        var intent = Intent(this, NewItem::class.java)
        startActivity(intent)
    }

    fun listItems(layout: LinearLayout){

        // list items
        for (i in 1..10){
            // add item
            createListItem(
                    layout,
                    i,
                    "nome do produtoAAA",
                    10.05,
                    10
            )

            // add horizontal line
            createHorizontalLine(layout)
        }

        /*createListItem(
            layout,
            1,
            "nome do produto",
            10.05,
            4
        )*/
    }

    fun createListItem (
        layout : LinearLayout,
        id : Int,
        name : String,
        price : Double,
        quantity : Int
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
                    "Preço unitário: " + price.toString() + "\n" +
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

    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

}