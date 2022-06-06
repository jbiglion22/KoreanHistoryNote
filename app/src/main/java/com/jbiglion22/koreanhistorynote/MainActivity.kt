package com.jbiglion22.koreanhistorynote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbiglion22.koreanhistorynote.databinding.ActivityMainBinding

val LOGTAG = "KOREAHISTORYNOTE"

val titlenameList = arrayListOf(
    TitleName(1, "1. 선사 문화와 여러나라의 성장",
        "* 구석기: 이동생활 → [[동굴]], [[땐석기]], [[주먹도끼]]\n" +
             "* 신석기: 농경 → 움집, 간석기, 빗살무늬토기, 가락바퀴\n" +
             "* 청동기: 계급 → 고인돌, 비파형동검, 반달칼, 민무늬토기",
      "선사시대는 구석기, 신석기, 청동기 시대가 중요합니다. \n"+
             "각 시기에 주요 단어를 암기해야합니다."),

    TitleName(2,"2. 삼국과 가야의 발전",
        "[고구려]\n" +
            "* 소수림왕: 율령, 불교, 태학\n" +
            "* 장수왕: 평양, 백제한성, 계루왕, 충주고구려비\n" +
            "[백제]\n" +
            "* 지증왕: 신라, 왕, 우산국\n" +
            "* 법흥왕: 율령, 불교/이차돈, 금관가야, 건원\n" +
            "* 진흥왕 : 화랑도, 한강, 대가야, 적성비, 순수비\n" +
            "[신라]\n" +
            "* 성왕 : 사비, 남부여, 관산성전투\n" +
            "[그외사건]\n" +
           "* 살수대첩, 안시성싸움\n" +
           "* 나당연합 : 고구려,백제 멸망\n" +
          "* 나당전쟁 : 매소성, 기벌포 → 삼국통일의 완성(676)\n",
        ""),

    TitleName(3,"3. 통일 신라와 발해의 발전","",
        ""),
    TitleName(4,"4. 고려의 발전과 변화","",
        ""),
    TitleName(5, "5. 고려의 경제, 사회와 문화","",
        ""),
    TitleName(6,"6. 조선의 건국과 통치체제의 정비","",
        ""),
    TitleName(7,"7. 조선의 대외 관계와 양 난의 극복","",
        ""),
    TitleName(8,"8. 조선후기의 정치 변동과 제도 개편","",
        ""),
    TitleName(9,"9. 조선후기의 사회의 변동과 문화의 새경향","",
        ""),
    TitleName(10,"10. 흥선대원군의 정치와 개화정책의 추진","",
        ""),
    TitleName(11,"11. 구국 운동과 근대 국가 수립 운동의 전개","",
        ""),
    TitleName(12,"12. 일제의 국권 침탈과 구국 수호 운동","",
        ""),
    TitleName(13,"13. 개항이후의 경제와 사회문화의 변화","",
        ""),
    TitleName(14,"14. 일제의 식민통치와 경제수탈","",
        ""),
    TitleName(15,"15. 3.1운동과 대한민국 임시정부의 활동","",
        ""),
    TitleName(16,"16. 3.1 운동 이후 국내 민족운동과 사회문화변화","",
        ""),
    TitleName(17,"17. 국외민족운동과 건국주비활동","",
        ""),
    TitleName(18,"18. 대한민국 정부수립과 6.25 전쟁","",
        ""),
    TitleName(19,"19. 자유 민주주의의 시련과 발전","",
        ""),
    TitleName(20,"20. 경제 발전과 사회변화, 통일을 위한 노력","",
        ""),
)

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
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 액션바 커스터마이징을 허용
        supportActionBar?.setDisplayShowCustomEnabled(true)
        // 기존 액션바의 요소들을 숨김
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        // 새로운 커스텀 액션바로 변경
        var actionView = layoutInflater.inflate(R.layout.custom_actionbar, null)
        supportActionBar?.customView = actionView

        return true
    }

 */
}