package com.jbiglion22.koreanhistorynote

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.jbiglion22.koreanhistorynote.databinding.ActivityContentBinding


class ContentActivity : AppCompatActivity() {
    val LOG_HEAD ="KOREAHISTORYNOTE-ContentActivity"

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        var curPos:Int = intent.getIntExtra("curPos", 0)
        val titlename: ContentDataItem = contentdataList.get(curPos)

        var actionBar = supportActionBar
        actionBar?.title = titlename.name

        var contentdataAnalysis= ContentDataAnalysis(this, titlename.cont)
        binding.tvContent.text = contentdataAnalysis.span
        binding.tvContent.movementMethod = LinkMovementMethod()

        if (titlename.explain.length>0) {
            binding.tvExplain.visibility = View.VISIBLE
            binding.tvTitleexplain.visibility = View.VISIBLE
            binding.tvExplain.text = titlename.explain
        } else {
            binding.tvExplain.visibility = View.INVISIBLE
            binding.tvTitleexplain.visibility = View.INVISIBLE
        }

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
class ContentSliceItem {
    var strText=""
    var intStyle=0
    var intStart=0
    var intEnd=0

    constructor(text:String) {
        strText = text
        intStyle = 0
    }
    constructor(text:String, sty:Int, start:Int, end:Int) {
        strText = text
        intStyle = sty
        intStart = start
        intEnd = end
    }
}

// 강의별 컨텐츠를 분해하여는 여러 작을을 담고있음
// (ContentDataItem -> ContentSliceItem)
class ContentDataAnalysis {
    val LOG_HEAD ="ContentData"
    var contentslideList = mutableListOf<ContentSliceItem>()
    var span : SpannableStringBuilder

    constructor(conAct : Context, fulltext: String) {
        var cnt: Int =0 // contentslideList에 넣는 순서번호
        var loc0: Int =0
        var loc1: Int = 0
        var str : String = ""

        // 컨텐츠 내용을 분해함  ------------->>>>>
        while(fulltext.indexOf("[[",loc0)>0 ) {

            // 앞쪽부터 "[["까지 추가(add)함
            loc1=fulltext.indexOf("[[",loc0)
            contentslideList.add(cnt, ContentSliceItem(fulltext.substring(loc0,loc1)))
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            // "[[" 부터 "]]" 까지 추가(add)함
            loc0 = loc1+2
            loc1 = fulltext.indexOf("]]",loc0)
            str = fulltext.substring(loc0,loc1)
            // 중간에 속성이 들어있는지 확인
            if (str.indexOf("||",0)>0) {
                // 속성 입력
                var str_arr = str.split("||")
                contentslideList.add(cnt, ContentSliceItem(str_arr[0], str_arr[1].toInt(), cntNowLength(), cntNowLength()+str_arr[0].length))
            } else {
                contentslideList.add(cnt, ContentSliceItem(str))
            }
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            loc0=loc1+2
        }
        // 마지막 나머지 부분 추가(add)
        loc1=fulltext.length
        contentslideList.add(cnt,ContentSliceItem(fulltext.substring(loc0,loc1)))
        Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")

        Log.d(LOGTAG,"[${LOG_HEAD}] list.size=${contentslideList.size}")

        // 컨텐츠 내용을 분해함  -------------<<<<<<<<<<



        // 분해된 내용을 재조합함  ------------->>>>>>>>>>>>>>>>

        // SpannableStringBuilder로 속성을 입힌다.
        var fullString=""
        for (i in contentslideList.indices) {
            fullString = fullString+contentslideList[i].strText
        }
        span = SpannableStringBuilder(fullString)
        for (i in contentslideList.indices) {
            if (contentslideList[i].intStyle==1) {

                //클릭
                val ccs= object: ClickableSpan(){
                    override fun onClick(view: View) {
                        Toast.makeText(conAct, "내용: ${contentslideList[i].strText}", Toast.LENGTH_LONG).show()

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
                            Toast.makeText(conAct, "답변: ${edAnswer.text}", Toast.LENGTH_SHORT).show()

                            if(edAnswer.text.toString() == contentslideList[i].strText ) {
                                Toast.makeText(conAct, "맞았습니다.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(conAct, "틀렸습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                        // 답변 다이얼로그 출력 -----------------------<<<<

                    }
                }
                span.setSpan(ccs, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 볼드
                val boldSpan = StyleSpan(Typeface.BOLD)
                span.setSpan(boldSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 크기
                val sizeBigSpan = RelativeSizeSpan(1.0f)
                span.setSpan(sizeBigSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 색상
                val colorBlueSpan = ForegroundColorSpan(Color.RED)
                span.setSpan(colorBlueSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

        }
        // 분해된 내용을 재조합함  -------------<<<<<<<<

    }

    // 현재까지 분해한것을 조합했을경우 길이를 리턴함
    fun cntNowLength(): Int{
        var len=0
        for (i in contentslideList.indices) {
            len += contentslideList[i].strText.length
        }
        return len
    }

}