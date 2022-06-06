package com.jbiglion22.koreanhistorynote

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbiglion22.koreanhistorynote.databinding.ActivityMainBinding

// 콤 수정함(2022.6.7)

val LOGTAG = "KOREAHISTORYNOTE"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTitlename.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTitlename.setHasFixedSize(true)

        binding.rvTitlename.adapter = TitleNameAdapter(titlenameList)


        if (savedInstanceState != null) {
            // 회전시 초기화되는 내용을 처리하기위해 사용

        } else {
            // 첫 실행일 경우만 실행
            val intent = Intent(this, IntroActivity::class.java)
            intent.putExtra("curPos", 1)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Item1 -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }


    // 회전시 데이터 저장을 위해
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}