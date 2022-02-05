package com.example.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.game.databinding.ActivityMainBinding


lateinit var bindingclass: ActivityMainBinding
class MainActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Создание цикла жизни OnCreate
        super.onCreate(savedInstanceState)
        // Подключение bindingView вместо FindviewbyId
        bindingclass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingclass.root)

        //Обработка нажатия на кнопку через bindingview
        bindingclass.btnStart.setOnClickListener {

            if (bindingclass.etName.text.toString().isEmpty()) {

                Toast.makeText(this@MainActivity, "Please enter your name", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val intent = Intent(this@MainActivity, QuizQuestionsActivity::class.java)
                // Передача Имени на следующие активити и переход на него
                intent.putExtra(Constants.USER_NAME, bindingclass.etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}