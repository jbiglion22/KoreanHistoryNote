package com.jbiglion22.koreanhistorynote

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbiglion22.koreanhistorynote.databinding.ActivityMainBinding

val LOGTAG = "KOREAHISTORYNOTE"



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.rvTitlename.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTitlename.setHasFixedSize(true)

        binding.rvTitlename.adapter = TitleNameAdapter(titlenameList)



        val intent = Intent(this, IntroActivity::class.java)
        intent.putExtra("curPos", 1)
        startActivity(intent)


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

}