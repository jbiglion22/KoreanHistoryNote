package com.jbiglion22.koreanhistorynote

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.jbiglion22.koreanhistorynote.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    val LOG_HEAD ="KOREAHISTORYNOTE-QustionActivity"

    private lateinit var binding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        var curPos:Int = intent.getIntExtra("curPos", 0)
        val titlename: ContentDataItem = contentdataList.get(curPos)

        var actionBar = supportActionBar
        actionBar?.title = "퀴즈"

        var quesrtiondataAnaysis= QuestionDataAnalysis(this, titlename.cont, binding)
        binding.tvTitle.text = titlename.name
        binding.tvContent.text = quesrtiondataAnaysis.span
        binding.tvContent.movementMethod = LinkMovementMethod()

        setBannerAds()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 액션바 뒤로가기 버튼 클릭 -> 닫기
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBannerAds() {

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


        binding.adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d("ads log", "배너광고가 로드됨")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ads log", "배너광고가 로드 실패. ${adError.responseInfo}")
            }

            override fun onAdOpened() {
                Log.d("ads log", "배너광고 열었음")
            }

            override fun onAdClicked() {
                Log.d("ads log", "배너광고 클릭")
            }

            override fun onAdClosed() {
                Log.d("ads log", "배너광고 닫음")
            }
        }
    }
}


// 컨텐츠를 출력을 위해 분해한 조각(아이템)
class QuestionSliceItem {
    var strText=""
    var intStyle=0
    var intStart=0
    var intEnd=0

    var isQuestion=false // 문제인가?
    var strAnswer=""     // 답변
    var isCorrect = false // 정답인가?

    constructor(text:String) {
        strText = text
        intStyle = 0
    }
    constructor(text:String, sty:Int, start:Int, end:Int, question:Boolean, answer:String) {
        strText = text
        intStyle = sty
        intStart = start
        intEnd = end
        isQuestion=question
        strAnswer = answer
    }
}


// 강의별 컨텐츠를 퀴즈로만들어 분해하여는 여러 작을을 담고있음
// (ContentDataItem -> ContentSliceItem)
class QuestionDataAnalysis {
    val LOG_HEAD ="QuestionDataAnalysis"
    var questionslideList = mutableListOf<QuestionSliceItem>()
    lateinit var span : SpannableStringBuilder

    constructor(conAct : Context, fulltext: String, binding: ActivityQuestionBinding) {

        var cnt: Int =0 // questionslideList에 넣는 순서번호
        var loc0: Int =0
        var loc1: Int = 0
        var str : String =""

        var range = (1..2)

        // 컨텐츠 내용을 분해함  ------------->>>>>
        while(fulltext.indexOf("[[",loc0)>0 ) {
            loc1=fulltext.indexOf("[[",loc0)
            questionslideList.add(cnt, QuestionSliceItem(fulltext.substring(loc0,loc1)))
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            loc0 = loc1+2
            loc1 = fulltext.indexOf("]]",loc0)
            str = fulltext.substring(loc0,loc1)
            if (str.indexOf("||",0)>0 ) {
                var str_arr = str.split("||")
                if (range.random()==1) {
                    var str = "[__클릭__]"
                    questionslideList.add(
                        cnt,
                        QuestionSliceItem(
                            str,
                            str_arr[1].toInt(),
                            cntNowLength(),
                            cntNowLength() + str.length,
                            true,
                            str_arr[0]
                        )
                    )
                } else {
                    questionslideList.add(
                        cnt,
                        QuestionSliceItem(
                            str_arr[0],
                            str_arr[1].toInt(),
                            cntNowLength(),
                            cntNowLength() + str_arr[0].length,
                            false,
                            str_arr[0]
                        )
                    )
                }
            } else {
                questionslideList.add(cnt, QuestionSliceItem(str))
            }
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            loc0=loc1+2
        }
        loc1=fulltext.length
        questionslideList.add(cnt,QuestionSliceItem(fulltext.substring(loc0,loc1)))
        Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")

        Log.d(LOGTAG,"[${LOG_HEAD}] list.size=${questionslideList.size}")

        // 컨텐츠 내용을 분해함  -------------<<<<<<<<<<

        remake(conAct, binding)

    }

    fun remake(conAct : Context, binding: ActivityQuestionBinding) {
        // 분해된 내용을 재조합함  ------------->>>>>>>>>>>>>>>>

        // SpannableStringBuilder로 속성을 입힌다.
        var fullString_question=""
        for (i in questionslideList.indices) {
            if (questionslideList[i].intStyle == 0) {
                fullString_question = fullString_question + questionslideList[i].strText
            } else {
                fullString_question = fullString_question + questionslideList[i].strText
//                for (i in 1..questionslideList[i].strText.length)
//                    fullString_question += "_"
            }

        }
        span = SpannableStringBuilder(fullString_question)
        for (i in questionslideList.indices) {
            if (questionslideList[i].intStyle==1 && questionslideList[i].isQuestion) {

                //클릭
                val ccs= object: ClickableSpan(){
                    override fun onClick(view: View) {
                        Toast.makeText(conAct, "내용: ${questionslideList[i].strText}", Toast.LENGTH_LONG).show()

                        // 답변 다이얼로그 출력 ----------------------->>>>
                        var builder= AlertDialog.Builder(conAct)
                        builder.setTitle("답변?")
                        builder.setIcon(R.mipmap.ic_launcher_khn_round)

                        var layoutInflater: LayoutInflater = conAct.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
                        val v1 = layoutInflater.inflate(R.layout.dialog_answer, null)
                        builder.setView(v1)
                        var listener = object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {

                            }
                        }
                        val dia = builder.create()
                        dia.show()
                        var btnAnswer = v1.findViewById<Button>(R.id.btn_answer)
                        btnAnswer.setOnClickListener{
                            dia.dismiss()
                            var edAnswer = v1.findViewById<EditText>(R.id.ed_answer)
                            //Toast.makeText(conAct, "답변: ${edAnswer.text}", Toast.LENGTH_SHORT).show()

                            if(edAnswer.text.toString() == questionslideList[i].strAnswer ) {
                                questionslideList[i].isCorrect = true        // 정답
                                //Toast.makeText(conAct, "맞았습니다.", Toast.LENGTH_LONG).show()
                            } else {
                                questionslideList[i].isCorrect = false        // 오답
                                //Toast.makeText(conAct, "틀렸습니다.", Toast.LENGTH_LONG).show()
                            }

                            // 정답을 원래에 넣고
                            questionslideList[i].strText = questionslideList[i].strAnswer
                            questionslideList[i].intStyle = 2
                            // 이후를 이동함
                            questionslideList[i].intEnd += questionslideList[i].strText.length-"[__클릭__]".length
                            for (j in i+1 until questionslideList.size) {
                                questionslideList[j].intStart += questionslideList[i].strText.length-"[__클릭__]".length
                                questionslideList[j].intEnd += questionslideList[i].strText.length-"[__클릭__]".length
                            }
                            remake(conAct, binding)
                            binding.tvContent.text = span
                        }
                        // 답변 다이얼로그 출력 -----------------------<<<<
                    }
                }
                span.setSpan(ccs, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 볼드
                val boldSpan = StyleSpan(Typeface.BOLD)
                span.setSpan(boldSpan, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 크기
                val sizeBigSpan = RelativeSizeSpan(1f)
                span.setSpan(sizeBigSpan, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 색상
                val colorBlueSpan = ForegroundColorSpan(Color.BLUE)
                span.setSpan(colorBlueSpan, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            } else if (questionslideList[i].intStyle==2) {
                // 볼드
                val boldSpan = StyleSpan(Typeface.BOLD)
                span.setSpan(boldSpan, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 크기
                val sizeBigSpan = RelativeSizeSpan(1.0f)
                span.setSpan(sizeBigSpan, questionslideList[i].intStart, questionslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                if (questionslideList[i].isCorrect) {
                    // 색상
                    val colorBlueSpan = ForegroundColorSpan(Color.BLUE)
                    span.setSpan(
                        colorBlueSpan,
                        questionslideList[i].intStart,
                        questionslideList[i].intEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    // 색상
                    val colorBlueSpan = ForegroundColorSpan(Color.RED)
                    span.setSpan(
                        colorBlueSpan,
                        questionslideList[i].intStart,
                        questionslideList[i].intEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        // 분해된 내용을 재조합함  -------------<<<<<<<<


    }

    // 현재까지 분해한것을 조합했을경우 길이를 리턴함
    fun cntNowLength(): Int{
        var len=0
        for (i in questionslideList.indices) {
            len += questionslideList[i].strText.length
        }
        return len
    }

}