package com.jbiglion22.koreanhistorynote

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jbiglion22.koreanhistorynote.databinding.ActivityContentBinding


class ContentActivity : AppCompatActivity() {
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