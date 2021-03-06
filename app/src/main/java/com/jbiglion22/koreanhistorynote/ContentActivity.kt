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
            // ????????? ???????????? ?????? ?????? -> ??????
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
                Log.d("ads log", "??????????????? ?????????")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ads log", "??????????????? ?????? ??????. ${adError.responseInfo}")
            }

            override fun onAdOpened() {
                Log.d("ads log", "???????????? ?????????")
            }

            override fun onAdClicked() {
                Log.d("ads log", "???????????? ??????")
            }

            override fun onAdClosed() {
                Log.d("ads log", "???????????? ??????")
            }
        }
    }
}

// ???????????? ????????? ?????? ????????? ??????(?????????)
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

// ????????? ???????????? ??????????????? ?????? ????????? ????????????
// (ContentDataItem -> ContentSliceItem)
class ContentDataAnalysis {
    val LOG_HEAD ="ContentData"
    var contentslideList = mutableListOf<ContentSliceItem>()
    var span : SpannableStringBuilder

    constructor(conAct : Context, fulltext: String) {
        var cnt: Int =0 // contentslideList??? ?????? ????????????
        var loc0: Int =0
        var loc1: Int = 0
        var str : String = ""

        // ????????? ????????? ?????????  ------------->>>>>
        while(fulltext.indexOf("[[",loc0)>0 ) {

            // ???????????? "[["?????? ??????(add)???
            loc1=fulltext.indexOf("[[",loc0)
            contentslideList.add(cnt, ContentSliceItem(fulltext.substring(loc0,loc1)))
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            // "[[" ?????? "]]" ?????? ??????(add)???
            loc0 = loc1+2
            loc1 = fulltext.indexOf("]]",loc0)
            str = fulltext.substring(loc0,loc1)
            // ????????? ????????? ??????????????? ??????
            if (str.indexOf("||",0)>0) {
                // ?????? ??????
                var str_arr = str.split("||")
                contentslideList.add(cnt, ContentSliceItem(str_arr[0], str_arr[1].toInt(), cntNowLength(), cntNowLength()+str_arr[0].length))
            } else {
                contentslideList.add(cnt, ContentSliceItem(str))
            }
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            cnt++

            loc0=loc1+2
        }
        // ????????? ????????? ?????? ??????(add)
        loc1=fulltext.length
        contentslideList.add(cnt,ContentSliceItem(fulltext.substring(loc0,loc1)))
        Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")

        Log.d(LOGTAG,"[${LOG_HEAD}] list.size=${contentslideList.size}")

        // ????????? ????????? ?????????  -------------<<<<<<<<<<



        // ????????? ????????? ????????????  ------------->>>>>>>>>>>>>>>>

        // SpannableStringBuilder??? ????????? ?????????.
        var fullString=""
        for (i in contentslideList.indices) {
            fullString = fullString+contentslideList[i].strText
        }
        span = SpannableStringBuilder(fullString)
        for (i in contentslideList.indices) {
            if (contentslideList[i].intStyle==1) {

                //??????
                // ?????? ???????????? -- ????????? ????????? ????????? ???????????? ?????? ????????????
                /*
                val ccs= object: ClickableSpan(){
                    override fun onClick(view: View) {
                        Toast.makeText(conAct, "??????: ${contentslideList[i].strText}", Toast.LENGTH_LONG).show()

                        // ?????? ??????????????? ?????? ----------------------->>>>
                        var builder= AlertDialog.Builder(conAct)
                        builder.setTitle("???????")
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
                            Toast.makeText(conAct, "??????: ${edAnswer.text}", Toast.LENGTH_SHORT).show()

                            if(edAnswer.text.toString() == contentslideList[i].strText ) {
                                Toast.makeText(conAct, "???????????????.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(conAct, "???????????????.", Toast.LENGTH_LONG).show()
                            }
                        }
                        // ?????? ??????????????? ?????? -----------------------<<<<

                    }
                }
                span.setSpan(ccs, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                */

                // ??????
                val boldSpan = StyleSpan(Typeface.BOLD)
                span.setSpan(boldSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // ??????
                val sizeBigSpan = RelativeSizeSpan(1.0f)
                span.setSpan(sizeBigSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // ??????
                val colorBlueSpan = ForegroundColorSpan(Color.RED)
                span.setSpan(colorBlueSpan, contentslideList[i].intStart, contentslideList[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

        }
        // ????????? ????????? ????????????  -------------<<<<<<<<

    }

    // ???????????? ??????????????? ?????????????????? ????????? ?????????
    fun cntNowLength(): Int{
        var len=0
        for (i in contentslideList.indices) {
            len += contentslideList[i].strText.length
        }
        return len
    }

}