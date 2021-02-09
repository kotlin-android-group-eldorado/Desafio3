package com.dgaspar.desafio3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class NewItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)

        // name editText
        var nameEditText : EditText = findViewById(R.id.name)

        // name editText
        var valueEditText : EditText = findViewById(R.id.value)

        // name editText
        var quantityEditText : EditText = findViewById(R.id.quantity)
    }

    fun saveItem(view : View) {
        println("SALVA PRODUTO")

    }
}