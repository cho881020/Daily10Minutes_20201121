package kr.co.tjoeun.daily10minutes_20201121

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20201121.adapters.ProjectAdapter
import kr.co.tjoeun.daily10minutes_20201121.datas.Project
import kr.co.tjoeun.daily10minutes_20201121.utils.ContextUtil
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjectList = ArrayList<Project>()
    lateinit var mAdapter : ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        projectListView.setOnItemClickListener { parent, view, position, id ->

            val clickedProject = mProjectList[position]

//            상세화면으로 이동 => 클릭된 프로젝트를 통째로 넘기자.
            val myIntent = Intent(mContext, ViewProjectDetailActivity::class.java)
            myIntent.putExtra("project", clickedProject)
            startActivity(myIntent)

        }

        logoutBtn.setOnClickListener {
//            로그인 : 서버에 아이디 비번이 맞는지? 물어보는 (API 통신) => 맞으면 토큰을 기기에 저장
//            로그아웃 : 저장된 토큰을 날려주는 행위 => 저장된 토큰값을 "" 로 변경

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
//                저장 토큰 날려주기
                ContextUtil.setLoginUserToken(mContext, "")

//                메인화면 종료, Splash화면으로 넘어가기
                val myIntent = Intent(mContext, SplashActivity::class.java)
                startActivity(myIntent)
                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

//        액션바의 제목을 바꾸는 제일 기본적인 방법
//        title = "메인화면"  // 자주 사용되지는 않는다.

//        액션바 자체를 커스텀으로 그리려면?
        setCustomActionBar()

//        서버에 => 어떤 프로젝트들이 있는지 API 호출 => 그 결과 (JSON) 파싱해서, ArrayList에 대입
        getProjectsFromServer()

        mAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mAdapter

    }

//    액션바를 직접 그리기 위한 함수

    fun setCustomActionBar() {

//        1. 액션바가 어떻게 보이게 하고 싶은지? 모양 (layout)을 그려야함. xml 작성

//        기존 액션바를 불러내서 => 속성(ex. 커스텀액션바모드)들 변경 => xml 반영

//        기존 액션바 불러내기 (무조건 존재한다고 우기기)
        val defaultActionBar = supportActionBar!!

//        커스텀 액션바를 보여주게 모드 변경
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

//        실제로 보여질 커스텀 화면 연결
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

//        액션바(보라색) > 툴바(여백) > 커스텀뷰(검정배경)
//        여백을 없애려면? => 액션바-툴바의 속성 변경 => 내부공간 0으로 설정.

//        액션바.커스텀뷰.부모 -> 툴바로 변신
//        androidx 가 주는 툴바 사용
        val toolBar = defaultActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0,0)


    }

//    서버에 프로젝트 목록 요청/분석 기능 함수

    fun getProjectsFromServer() {

//        실제 서버 호출 등 작업
        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")

//                [  ] 로 구성된 JSONArray 추출 코드
                val projectsArr = dataObj.getJSONArray("projects")

//                ex. 10개 프로젝트 : 0~9(10 직전)번째 까지 분석
//                반복분석 => for 활용 : JSONArray 분석은 대부분 for문과 연계됨.
//                JSONArray의 내용물 갯수 : arr변수.length() 기능 활용

                for (i in     0 until projectsArr.length()) {

//                    projectsArr 에서 자리에 맞는 (i번째) {  } JSONObject 추출

                    val projectObj = projectsArr.getJSONObject(i)

//                    projectObj는 JSONObject => Project 형태로 변환 : ArrayList에 삽입 가능.

//                    기본데이터만 들어있는 Project 객체 하나 생성
                    val project = Project.getProjectFromJSON(projectObj)

//                    완성된 project를 => mProjectList에 추가.
                    mProjectList.add(project)

                }

//                서버를 다녀오는 행위이므로 => 어댑터 연결보다, 우선 작성되어도 실제로는 늦게 끝날수 있다.
//                리스트뷰의 내용 변경을 유발하는 행위 => 변경사항 반영 실행
//                리스트뷰 내용 변경 반영 => UI 영향 미침. => UI쓰레드 안에서 실행

                runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }



            }

        })

    }

}