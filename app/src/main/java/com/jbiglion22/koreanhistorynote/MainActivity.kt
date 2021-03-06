package com.jbiglion22.koreanhistorynote

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.jbiglion22.koreanhistorynote.databinding.ActivityMainBinding
import kotlin.system.exitProcess

//import androidx.ui.material.AlertDialog

val LOGTAG = "KOREAHISTORYNOTE"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var adLoader: AdLoader? = null

    var mInterstitialAd : InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // EntraneActivity 실행
        if (savedInstanceState != null) {
            // 회전시 초기화되는 내용을 처리하기위해 사용

        } else {
            // 첫 실행일 경우만 실행
            val intent = Intent(this, EntraneActivity::class.java)
            intent.putExtra("curPos", 1)
            startActivity(intent)
        }
                
        setContentView(binding.root)

        setBannerAds()
        /*
        binding.btnAdd.setOnClickListener {

            if(mInterstitialAd !=null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d("ads log", "정면광고 닫음")

                        val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d("ads log", "정면광고  실패")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d("ads log", "정면광고  성고")
                        mInterstitialAd = null
                    }
                }

                mInterstitialAd!!.show(this@MainActivity)
            } else {
                Log.d("ads log", "정면광고 로딩안됨")
                Toast.makeText(this@MainActivity, "잠시후다시하라", Toast.LENGTH_LONG).show()
            }
        }
        */


        // Recycler View 실행
        binding.rvTitlename.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTitlename.setHasFixedSize(true)

        binding.rvTitlename.adapter = TitleNameAdapter(contentdataList)



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_introduce -> {
                val intent = Intent(this, IntroduceActivity::class.java)
                intent.putExtra("curPos", 1)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "뒤로가기",Toast.LENGTH_LONG).show()

//        super.onBackPressed()


        // 종료 다이얼로그 출력 ----------------------->>>>
        var builder= AlertDialog.Builder(this)
        builder.setTitle("종료하시겠습니까?")
        builder.setIcon(R.mipmap.ic_launcher_khn_round)

        val v1 = layoutInflater.inflate(R.layout.dialog_leave, null)
        builder.setView(v1)
        var listener = object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                var alert = p0 as AlertDialog
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }


/*
        builder.setPositiveButton("", listener)
            .setPositiveButtonIcon(getDrawable(R.drawable.btn_close))

        builder.setNegativeButton("", null)
            .setNegativeButtonIcon(getDrawable(R.drawable.intro_title))
 //       builder.show()

 */
        val dia = builder.create()
        dia.show()

        //       val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //       val view=inflater.inflate(R.layout.end_dialog, null)
        var btnClose = v1.findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener{
//            Toast.makeText(this, "close", Toast.LENGTH_LONG).show()
            dia.dismiss()
            finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
//        v1.btnCancel
        var btnClancel = v1.findViewById<Button>(R.id.btn_cancel)
        btnClancel.setOnClickListener{
            dia.dismiss()
        }

        // 종료 다이얼로그 출력 -----------------------<<<<

        /// 종료 네이티브 광고 ----->>>

        createAd()
        adLoader?.loadAd(AdRequest.Builder().build())

        ////<<<<


    }

    fun createAd() {
        MobileAds.initialize(this)
        // 테스트용
        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
        // 진짜용
//        adLoader = AdLoader.Builder(this, "ca-app-pub-3401120384384866/2828616736")
            .forNativeAd { ad : NativeAd ->

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {

                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                .build())
            .build()
    }


    // 회전시 데이터 저장을 위해
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
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