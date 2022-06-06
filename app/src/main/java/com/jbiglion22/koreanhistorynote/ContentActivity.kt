package com.jbiglion22.koreanhistorynote

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.jbiglion22.koreanhistorynote.databinding.ActivityContentBinding


class ContentActivity : AppCompatActivity() {
    val LOG_HEAD ="ContentActivity"

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var curPos:Int = intent.getIntExtra("curPos", 0)
        val titlename: TitleName = titlenameList.get(curPos)

        var actionBar = supportActionBar
        actionBar?.title = titlename.name
        binding.tvTitlename.text = ""


        /* 내용을 자른다 */
        var loc: Int =0
        var loc2: Int =0
        var sstr:String = ""
        Log.d(LOGTAG,"[${LOG_HEAD}] sub=${sstr}")

        while(titlename.cont.indexOf("[[",loc)>0 ) {
            loc = titlename.cont.indexOf("[[",loc)
            loc = loc+2
            loc2 = titlename.cont.indexOf("]]",loc)
            sstr = titlename.cont.substring(loc,loc2)

            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${sstr}")
        }



        if (titlename.cont.length>0) {
            var span = SpannableStringBuilder(titlename.cont)
            span.setSpan(ForegroundColorSpan(Color.RED), 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvContent.text = span
        } else {
            binding.tvContent.text =""
        }

        if (titlename.explain.length>0) {
            binding.tvExplain.visibility = View.VISIBLE
            binding.tvTitleexplain.visibility = View.VISIBLE
            binding.tvExplain.text = titlename.explain
        } else {
            binding.tvExplain.visibility = View.INVISIBLE
            binding.tvTitleexplain.visibility = View.INVISIBLE
        }

    }
}