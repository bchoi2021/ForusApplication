package com.example.qa

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.qa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityMainBinding

    private var currentPosition: Int = 1 //질문 위치
    private var selectedOption: Int = 0 //선택 답변 값
    private var score: Int = 0 // 점수

    private lateinit var questionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //질문 리스트 가져오기
        questionList = QuestionData.getQuestion()

        //화면 셋팅
        getQuestionData()

        binding.option1Text.setOnClickListener(this)
        binding.option2Text.setOnClickListener(this)
        binding.option3Text.setOnClickListener(this)
        binding.option4Text.setOnClickListener(this)

        //답변 체크 이벤트
        binding.submitBtn.setOnClickListener {

            if(selectedOption != 0){

                val question = questionList[currentPosition-1]

                //정답 체크(선택 답변과 정답을 비교)
                if(selectedOption != question.correct_answer) { //오답

                    setColor(selectedOption, R.drawable.wrong_option_background)

                    callDialog("오답", "정답 ${question.correct_answer}")
                }else{
                    score++
                }
                setColor(question.correct_answer, R.drawable.correct_option_background)

                if(currentPosition == questionList.size){
                    binding.submitBtn.text = getString(R.string.submit, "끝")
                }else{
                    binding.submitBtn.text = getString(R.string.submit, "다음")
                }

            }else{
                //위치값 상승
                currentPosition++
                when{
                    //전체 문제 숫자가 현재 위치보다 크면 다음 문제로 셋팅
                    currentPosition <= questionList.size -> {
                        //다음 문제 셋팅
                        getQuestionData()
                    }

                    else ->{
                        //결과 액티비티로 넘어가는 코드
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("score", score)
                        intent.putExtra("totalSize", questionList.size)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            //선택값 초기화
            selectedOption = 0
        }//submitBtn

    } //onCreate

    /**
     * 답변 배경색상 변경
     */
    private fun setColor(opt: Int, color: Int){
        when(opt){
            1 -> binding.option1Text.background = ContextCompat.getDrawable(this, color)
            2 -> binding.option2Text.background = ContextCompat.getDrawable(this, color)
            3 -> binding.option3Text.background = ContextCompat.getDrawable(this, color)
            4 -> binding.option4Text.background = ContextCompat.getDrawable(this, color)
        }
    }

    /**
     * 정답 확인 다이얼로그
     */
    private fun callDialog(alertTitle: String, correctAnswer: String){

        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("정답: $correctAnswer")
            .setPositiveButton("OK"){
                    dialogInterface, i ->
                dialogInterface.dismiss() //창 닫기
            }
            .setCancelable(false)
            .show()
    }


    /**
     * 문제 셋팅
     */
    private fun getQuestionData(){

        //답변 설정 초기화
        setOptionStyle()

        //질문 변수에 담기
        val question = questionList[currentPosition-1]

        //상태바 위치
        binding.progressBar.progress = currentPosition

        //상태바 최대값
        binding.progressBar.max = questionList.size

        //현재 위치 표시
        binding.progressText.text = getString(R.string.count_label, currentPosition, questionList.size)

        //질문 표시
        binding.questionText.text = question.question

        //답변 표시
        binding.option1Text.text = question.option_one
        binding.option2Text.text = question.option_two
        binding.option3Text.text = question.option_three
        binding.option4Text.text = question.option_four

        setSubmitBtn("제출")
    }

    //제출 버튼 텍스트 설정
    private fun setSubmitBtn(name: String){

        binding.submitBtn.text = getString(R.string.submit, name)
    }

    /**
     * 답변 스타일 설정
     */
    private fun setOptionStyle(){

        var optionList: ArrayList<TextView> = arrayListOf()
        optionList.add(binding.option1Text)
        optionList.add(binding.option2Text)
        optionList.add(binding.option3Text)
        optionList.add(binding.option4Text)

        //답변 텍스트뷰 설정
        for(op in optionList){
            op.setTextColor(Color.parseColor("#555151"))
            op.background = ContextCompat.getDrawable(this, R.drawable.option_background)
            op.typeface = Typeface.DEFAULT
        }
    }

    /**
     * 답변 선택 이벤트
     */
    private fun selectedOptionStyle(view: TextView, opt: Int){

        //옵션 초기화
        setOptionStyle()

        //위치 담기
        selectedOption = opt

        view.setTextColor((Color.parseColor("#5F00FF")))
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_option_background)
        view.typeface = Typeface.DEFAULT_BOLD
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.option1_text -> selectedOptionStyle(binding.option1Text, 1)
            R.id.option2_text -> selectedOptionStyle(binding.option2Text, 2)
            R.id.option3_text -> selectedOptionStyle(binding.option3Text, 3)
            R.id.option4_text -> selectedOptionStyle(binding.option4Text, 4)
        }
    }
}