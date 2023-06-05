package org.techtown.firebase_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ButtonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_list)
        var button1 : Button = findViewById<Button>(R.id.button1)
        var button2 : Button = findViewById<Button>(R.id.button2)
        var button3 : Button = findViewById<Button>(R.id.button3)

//        button1.setOnClickListener {
//            val intent = Intent(this, MypgActivity::class.java)
//            startActivity(intent)
//            Toast
//                .makeText(this, "상품구매", Toast.LENGTH_SHORT)
//                .show()
//        }
//        button2.setOnClickListener {
//            val intent = Intent(this, MappgActivity::class.java)
//            startActivity(intent)
//            Toast
//                .makeText(this, "지도 페이지", Toast.LENGTH_SHORT)
//                .show()
//        }
        button3.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            Toast
                .makeText(this, "소개 페이지", Toast.LENGTH_SHORT)
                .show()
        }
    }
}