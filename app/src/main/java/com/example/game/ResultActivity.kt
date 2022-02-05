package com.example.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.game.databinding.ActivityResultBinding

lateinit var bindingclass3: ActivityResultBinding
class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingclass3 = ActivityResultBinding.inflate(layoutInflater)
        setContentView(bindingclass3.root)


        val userName = intent.getStringExtra(Constants.USER_NAME)
        bindingclass3.tvName.text = userName

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        bindingclass3.tvScore.text = "${getString(R.string.ysc)} $correctAnswers ${getString(R.string.outf)} $totalQuestions."

        bindingclass3.btnFinish.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }
    }
}