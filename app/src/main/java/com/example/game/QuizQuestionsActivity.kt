package com.example.game

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.game.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var bindingclass2: ActivityQuizQuestionsBinding
        private var mCurrentPosition: Int = 1 // Позиция первого вопроса в списке
    private var mQuestionsList: ArrayList<Question>? = null

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingclass2 = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(bindingclass2.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList = Constants.getQuestions()

        setQuestion()
        bindingclass2.tvOptionOne.setOnClickListener(this)
        bindingclass2.tvOptionTwo.setOnClickListener(this)
        bindingclass2.tvOptionThree.setOnClickListener(this)
        bindingclass2.tvOptionFour.setOnClickListener(this)
        bindingclass2.btnSubmit.setOnClickListener(this)
    }
    /**
     * Обработчик нажатий на UI компоненты
     */
    override fun onClick(v: View) {

        when (v.id) {

            R.id.tv_option_one -> {

                selectedOptionView(bindingclass2.tvOptionOne, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(bindingclass2.tvOptionTwo, 2)
            }

            R.id.tv_option_three -> {

                selectedOptionView(bindingclass2.tvOptionThree, 3)
            }

            R.id.tv_option_four -> {

                selectedOptionView(bindingclass2.tvOptionFour, 4)
            }

            R.id.btn_submit -> {

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {

                            // Перенос данных в ResultActivity  и сам переход
                            val intent =
                                    Intent(this@QuizQuestionsActivity, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // Если ответ не правильный
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else {
                        mCorrectAnswers++
                    }

                    // Если ответ правильный
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        bindingclass2.btnSubmit.text = getString(R.string.fz)
                    } else {
                        bindingclass2.btnSubmit.text = getString(R.string.nextques)
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    /**
     * Функция для назначения вопросов UI компонентам
     */
    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question =
            mQuestionsList!![mCurrentPosition - 1] // Получение вопроса по списку

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            bindingclass2.btnSubmit.text = getString(R.string.fz)
        } else {
            bindingclass2.btnSubmit.text = getString(R.string.subbut)
        }

        bindingclass2.progressBar.progress = mCurrentPosition
        bindingclass2.tvProgress.text = "$mCurrentPosition" + "/" + bindingclass2.progressBar.max

        bindingclass2.tvQuestion.text = question.question
        bindingclass2.ivImage.setImageResource(question.image)
        bindingclass2.tvOptionOne.text = question.optionOne
        bindingclass2.tvOptionTwo.text = question.optionTwo
        bindingclass2.tvOptionThree.text = question.optionThree
        bindingclass2.tvOptionFour.text = question.optionFour
    }

    /**
     Изменение вида Textview при выборе
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.selected_option_border_bg
        )
    }

    /**
    Изменение вида Textview на начальное
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, bindingclass2.tvOptionOne)
        options.add(1, bindingclass2.tvOptionTwo)
        options.add(2, bindingclass2.tvOptionThree)
        options.add(3, bindingclass2.tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    R.drawable.default_option_border_bg
            )
        }
    }

    /**
     Выделение ответа: Правильный или не правильный
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                bindingclass2.tvOptionOne.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            2 -> {
                bindingclass2.tvOptionTwo.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            3 -> {
                bindingclass2.tvOptionThree.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            4 -> {
                bindingclass2.tvOptionFour.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
        }
    }
}