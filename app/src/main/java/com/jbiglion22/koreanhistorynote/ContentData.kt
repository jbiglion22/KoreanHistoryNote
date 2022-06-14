package com.jbiglion22.koreanhistorynote

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.getSystemService
import kotlin.system.exitProcess

val titlenameList = mutableListOf<TitleName>(

    // --------------- 1 ------------------//
    TitleName(1,
        "1. 선사 문화와 여러나라의 성장",
    """
* 구석기: 이동생활 → [[동굴||1]], [[땐석기||1]], [[주먹도끼||1]]
* 신석기: 농경 → 움집, 간석기, 빗살무늬토기, 가락바퀴
* 청동기: 계급 → 고인돌, 비파형동검, 반달칼, 민무늬토기
        """.trimIndent(),
        """
선사시대는 구석기, 신석기, 청동기 시대가 중요합니다. 
각 시기에 주요 단어를 암기해야합니다.
        """.trimIndent()
    ),


    // --------------- 2 ------------------//
    TitleName(2,"2. 삼국과 가야의 발전",
        """
[고구려]
* 소수림왕: 율령, 불교, 태학
* 장수왕: 평양, 백제한성, 계루왕, 충주고구려비

[백제]
* 지증왕: 신라, 왕, 우산국
* 법흥왕: 율령, 불교/이차돈, 금관가야, 건원
* 진흥왕 : 화랑도, 한강, 대가야, 적성비, 순수비

[신라]
* 성왕 : 사비, 남부여, 관산성전투

[그외사건]
* 살수대첩, 안시성싸움
* 나당연합 : 고구려,백제 멸망
* 나당전쟁 : 매소성, 기벌포 → 삼국통일의 완성(676년)
        """.trimIndent(),
        ""),


    // --------------- 3 ------------------//
    TitleName(3,"3. 통일 신라와 발해의 발전",
        """
▷통일신라
* 신문왕 : 국학, 관료전지금, 녹읍폐지
* 9주5소경, 9서당 10정

▷발해
* 선왕: 해동성국
* 3성6부, 5경 15부 62주 
* 주자감(유학)   
        """.trimIndent(),
        ""),

    // --------------- 4 ------------------//
    TitleName(4,"4. 고려의 발전과 변화",
        """
▷ 고려전기
* 태조 : 사심관, 기인제도, 흑장(빈민구휼제도), 서경중시, 훈요10조
* 광종 : 노비안검법, 과거제, 쌍기
* 성종 : 최승로(시부28조), 지방관 파견

* 중앙정치조직 : 2성6부
* 지방행정조직 : 5도 양계
* 중앙군 : 2군 6위

▷ 고려 침략기

* 거란의 침입(11C)
- 1차 : 소손녕 - 서희 - 강동6주
- 2차 : 양규
- 3차 : 강감찬 - 귀주대첩

* 여진의 침입(12C)
- 별무반 : 윤관 → 동북 9성 확보

* 이자겸의 난, 묘청의 서경 천도 운동


* 무신정변(1170) - 정중부

* 최씨 무신정권 - 최충헌: 교정도감, 도방, 최우: 정방, 삼별초


* 몽골과의 항쟁(13C)

- 최우-강화도, 배중손-삼별초

​

▷ 고려 후기

* 공민왕 : 쌍성총관부, 전민변정도감 신돈, 반원자주정책

    """.trimIndent(),
        ""),
    TitleName(5, "5. 고려의 경제, 사회와 문화",
        """
* 전시과 – 토지제도

* 할구(은병)

* 벽란도

* 소 - 수공업

* 경시서 - 상업활동 관장

 

* 문화: 청자, 승려-의천(천태종), 지눌(조계종, 결사운동)

        """.trimIndent(),
        ""),
    TitleName(6,"6. 조선의 건국과 통치체제의 정비",
        """
* 태종: 6조직계제, 사병혁파, 호폐법

* 세종: 의정부 서사제, 집현전, 4군6진, 대마도정벌

훈민정음, 농사직설, 칠정산(달력)

전분6등법, 연분9등법

측우기, 해시계 물시계

* 세조: 6조직계제, 집현전 경연 폐지, 직전법

* 성종; 홍문관, 경국대전

        """.trimIndent(),
        ""),
    TitleName(7,"7. 조선의 대외 관계와 양 난의 극복",
        """
▷ 왜란

* 임진왜란- 관군:이순신, 김시민(진주대첩), 권율(행주대첩), 의병: 곽재우, 조헌

* 정유재란 - 명랑해전

​

▷ 호란

* 정묘호란 : 친명배금 → 인조 강화도피신 → 화의

* 병자호란 : 군신관계요구 → 인조 남한산성 → 산전도의 굴욕
       
    """.trimIndent(),
        ""),
    TitleName(8,"8. 조선후기의 정치 변동과 제도 개편",
        """
* 통치 체제: 비변사, 훈련도감, 5군영, 속오군

 

▷ 수취체제

* 영정법 : 인조, 토지1결당 4~6두

* 대동법 : 광해군, 방납의 폐단(특산물→쌀), 1결당 12두, 공인, 상품화폐경제발달

* 균역법 : 영조, 군역의 폐단, 결작(토지세금), 선무군관포

 

* 영조 : 탕평정치, 속대전, 서원정리, 균역법

* 정조 : 수원화성, 규장각, 장용영, 대전통편, 신해통공(금난전권 폐지)
            
        """.trimIndent(),
        ""),
    TitleName(9,"9. 조선후기의 사회의 변동과 문화의 새경향",
        """
* 양반수 증가

* 문화 : 진경산수화(정선-인왕제색도), 풍속화(김홍도-씨름, 서당, 신윤복-단오풍경)

* 실학 : 정약용-배다지, 수원화성-거중기, 여전론(마을토지공유), 박제가-북학의, 소비론

        """.trimIndent(),
        ""),
    TitleName(10,"10. 흥선대원군의 정치와 개화정책의 추진",
        """
* 흥선대원군 : 경복궁중건(원납전, 당백전), 삼정의 문란(호포제, 사창제), 서원철폐, 비변사축소, 대전회통

* 병인양요 : 병인박해, 정족산성 양헌수 → 외규장각 도서 약탈

* 신미양요 : 제너럴셔먼호사건, 광성보 어제현 → 척화비

* 개화정책: 통리기무아문

* 임오군란 : 별기군, 제물포조약(외국군주둔), 조청상민수륙무역장전

* 갑신정변: 급진개화파, 우정통국, 한성조약, 텐진조약
     
        """.trimIndent(),
        ""),
    TitleName(11,"11. 구국 운동과 근대 국가 수립 운동의 전개","""
▷ 동학농민운동 

* 고부농민봉기 : 조병갑, 전봉준 봉기

* 1차봉기:황토현, 황룡촌 관군승리, 전주성 점령

* 전주 화약 체결 : 집강소

* 2차봉기 : 청일전쟁, 공주우금치 전투

 

▷ 갑오,을미개혁

* 1차 갑오개혁: 군국기무처 설치, 신분제 철폐, 재가허용, 과거제 폐지, 

* 2차 갑오개혁 : 군국기무처 폐지, 홍범14조, 재판소설치

* 3차 갑오개혁(을미개혁) : 을미사변(명성황후시해), 단발령, 아관파천

 

* 독립협회 : 만민 공동회, 의회설립운동, 관민공동회

* 대한제국: 광무개혁-원수부, 지계발금

    """.trimIndent(),
        ""),
    TitleName(12,"12. 일제의 국권 침탈과 구국 수호 운동",
        """
* 한일의정서

* 제1차한일 협약 : 고문정치

* 제2차 한일협약(을사늑약) : 외교권강탈, 통감파견

* 헤이그 특사 → 고종퇴위

* 정미7조약 : 군대해산, 일본인 차관

* 경술국치(1910, 8, 29)

* 대한자강회 : 고종퇴위반대운동

* 보안회 : 일본의 황무지 개간권 요구 반대 운동

* 신민회 : 안창호, 비밀결사조직, 독립운동기지 건설, 105인 사건

* 을미의병: 을미사변, 단발령

* 을사의병: 을사늑약, 신돌석

* 정미의병 : 의병전쟁 13도창의군, 서울진공작전

        """.trimIndent(),
        ""),
    TitleName(13,"13. 개항이후의 경제와 사회문화의 변화",
        """
* 국채보상운동 : 대구, 서상돈, 대한매일신보

        """.trimIndent(),
        ""),
    TitleName(14,"14. 일제의 식민통치와 경제수탈",
        """
* 1910년 무단통치 : 헌병경찰, 태형령, 토지조사사업, 회사령

* 1920년 문화통치 : 보통경찰, 조선일보, 산미증식계획, 회사령폐지, 관세폐지

* 1930년 말살통치 : 황국신민암송, 창씨개명, 국가총동원법, 공출배급, 남면북양, 농촌진흥운동, 황국식민화, 위안부, 조선어학회사건(1942)

        """.trimIndent(),
        ""),
    TitleName(15,"15. 3.1운동과 대한민국 임시정부의 활동",
        """
* 비밀결사단체: 독립의군부(복벽주의 – 옛왕조회복), 대한광복회(공화정)

* 3.1운동 : 고종서거, 제암리 학살사건, 대한민국임시정부 수립

* 대한민국임시정부 : 연통제, 교통국, 3권분립(국무원, 임시의정원, 법원)

    """.trimIndent(),
        ""),
    TitleName(16,"16. 3.1 운동 이후 국내 민족운동과 사회문화변화",
        """
* 물산장려운동(평양), 민립대학설립운동, 문맹퇴치운동

* 신간회 : 6.10만세운동(순종서거), 정우회, 민족유일당운동, 민족주의 사회주의 연합, 광주학생항일운동

            """,
        ""),
    TitleName(17,"17. 국외민족운동과 건국주비활동",
        """
▷ 1920년대 무장투쟁 : 봉오동전투-홍범도, 청산리대첩-김좌진, 홍범도, 백운평, 어랑총

▷ 1930년대 무장독립운동

* 한중연합(만주) 

- 한국독립군: 지청천, 쌍성보, 대전자령

- 조선혁명군: 양세봉, 영릉가, 홍경성

* 중국관내

   - 민족혁명당 : 김원봉, 지청천, 조선의용대(중국관내에 결성된, 최초의 한인독립군 부대)

 

▷ 의열투쟁

* 의열단(1920년대) : 김원봉,  신채호 “조선혁명선언”

* 한인애국단(1930년대) : 김구, 이봉창-일왕암살, 윤봉길-홍거우 공원

 

▷ 1940년대 대한민국 임시정부

* 한국독립당 : 김구, 충칭, 삼균주의(정치, 경제, 교육 평등)

* 한국광복군 : 지청천, 김원봉, 미얀마, OSS(미국전략정보국) 국내진공작전, 대일선전포고

        """.trimIndent(),
        ""),
    TitleName(18,"18. 대한민국 정부수립과 6.25 전쟁",
        """
* 모스크바 3국 외상회의 : 미소공동위원회

* 5.10 총선거 : 보통선거, 정부수립

* 6.25 전쟁 : 인천상륙전쟁, 흥남철수, 1.4후퇴

        """.trimIndent(),
        ""),
    TitleName(19,"19. 자유 민주주의의 시련과 발전",
        """
* 4.19혁명 : 3.15부정선거, 김주열, 이승만하야, 장면내각

* 5.18 민주화운동 : 신군부, 유신철폐, 계엄령 철폐, 광주, 시민군, 계엄군

* 6월 민주항쟁 : 5.13 호헌조치, 박종철, 이한열, 6.29 선언, 대통령직선제, 호헌철폐, 독재타도

        """.trimIndent(),
        ""),
    TitleName(20,"20. 경제 발전과 사회변화, 통일을 위한 노력",
        """
* 7.4남북공동성명 : 박정희, 통일 3대원칙(자주, 평화, 민족대단결), 남북조절위원회, 이후락

* 남북기본합의서 : 노태우,남북UN가입 → 남북기본합의서 → 한반도 비핵화선언, 남북불가침

* 6.15남북공동선언: 김대중, 최초의 남북정상회담, 개성공단, 경의선, 이산가족, 금강산관광, 낮은단계의 연방제
 
        """.trimIndent(),
        ""),
)

class ContentItem {
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

class ContentData {
    val LOG_HEAD ="ContentData"
    var fulltext = ""
    var list = mutableListOf<ContentItem>()
    lateinit var span : SpannableStringBuilder
    lateinit var span_question : SpannableStringBuilder

    constructor(conAct : Context, fulltext: String) {

        var l: Int =0
        var loc0: Int =0
        var loc1: Int
        var sstr : String

        while(fulltext.indexOf("[[",loc0)>0 ) {
            loc1=fulltext.indexOf("[[",loc0)
            list.add(l, ContentItem(fulltext.substring(loc0,loc1)))
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            l++

            loc0 = loc1+2
            loc1 = fulltext.indexOf("]]",loc0)
            sstr = fulltext.substring(loc0,loc1)
            if (sstr.indexOf("||",0)>0) {
                var str_arr = sstr.split("||")
                list.add(l, ContentItem(str_arr[0], str_arr[1].toInt(), cntNowLength(), cntNowLength()+str_arr[0].length))
            } else {
                list.add(l, ContentItem(sstr))
            }
            Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")
            l++

            loc0=loc1+2
        }
        loc1=fulltext.length
        list.add(l,ContentItem(fulltext.substring(loc0,loc1)))
        Log.d(LOGTAG,"[${LOG_HEAD}] sub=${fulltext.substring(loc0,loc1)}")


        Log.d(LOGTAG,"[${LOG_HEAD}] list.size=${list.size}")


        var fullString=""
        for (i in list.indices) {
            fullString = fullString+list[i].strText
        }
        span = SpannableStringBuilder(fullString)
        for (i in list.indices) {
            if (list[i].intStyle==1) {

                //클릭
                val ccs= object: ClickableSpan(){
                    override fun onClick(view: View) {
                        Toast.makeText(conAct, "내용: ${list[i].strText}", Toast.LENGTH_LONG).show()


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

                            if(edAnswer.text.toString() == list[i].strText ) {
                                Toast.makeText(conAct, "맞았습니다.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(conAct, "틀렸습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                        // 답변 다이얼로그 출력 -----------------------<<<<






                    }
                }
                span.setSpan(ccs, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 볼드
                val boldSpan = StyleSpan(Typeface.BOLD)
                span.setSpan(boldSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 크기
                val sizeBigSpan = RelativeSizeSpan(1.0f)
                span.setSpan(sizeBigSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 색상
                val colorBlueSpan = ForegroundColorSpan(Color.RED)
                span.setSpan(colorBlueSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

        }




        var fullString_question=""
        for (i in list.indices) {
            if (list[i].intStyle == 0) {
                fullString_question = fullString_question + list[i].strText
            } else {
                for (i in 1..list[i].strText.length)
                    fullString_question += "_"
            }

        }
        span_question = SpannableStringBuilder(fullString_question)
        for (i in list.indices) {
            if (list[i].intStyle==1) {

                //클릭
                val ccs= object: ClickableSpan(){
                    override fun onClick(view: View) {
                        Toast.makeText(conAct, "내용: ${list[i].strText}", Toast.LENGTH_LONG).show()

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

                            if(edAnswer.text.toString() == list[i].strText ) {
                                Toast.makeText(conAct, "맞았습니다.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(conAct, "틀렸습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                        // 답변 다이얼로그 출력 -----------------------<<<<

                    }
                }
                span_question.setSpan(ccs, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 볼드
                val boldSpan = StyleSpan(Typeface.BOLD)
                span_question.setSpan(boldSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 크기
                val sizeBigSpan = RelativeSizeSpan(1.2f)
                span_question.setSpan(sizeBigSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 색상
                val colorBlueSpan = ForegroundColorSpan(Color.BLUE)
                span_question.setSpan(colorBlueSpan, list[i].intStart, list[i].intEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            }

        }

    }

    fun cntNowLength(): Int{
        var k=0
        for (i in list.indices) {
            k=k+list[i].strText.length
        }
        return k
    }

}