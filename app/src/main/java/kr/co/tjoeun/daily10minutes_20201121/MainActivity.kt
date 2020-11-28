package kr.co.tjoeun.daily10minutes_20201121

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

//        서버에 => 어떤 프로젝트들이 있는지 API 호출 => 그 결과 (JSON) 파싱해서, ArrayList에 대입
        getProjectsFromServer()

        mAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mAdapter

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
                    val project = Project()

//                    기본데이터들을 => 서버가 주는 값으로 교체
                    project.id = projectObj.getInt("id")
                    project.title = projectObj.getString("title")
                    project.imageURL = projectObj.getString("img_url")
                    project.description = projectObj.getString("description")
                    project.proofMethod = projectObj.getString("proof_method")
                    project.completeDays = projectObj.getInt("complete_days")
                    project.onGoingUsersCount = projectObj.getInt("ongoing_users_count")

//                    완성된 project를 => mProjectList에 추가.
                    mProjectList.add(project)

                }

            }

        })

    }

}