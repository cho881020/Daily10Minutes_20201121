package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.tjoeun.daily10minutes_20201121.datas.Project
import kr.co.tjoeun.daily10minutes_20201121.datas.User
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil
import org.json.JSONObject

class ViewProjectUserListActivity : BaseActivity() {

    lateinit var mProject : Project

    val mUserList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_user_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mProject = intent.getSerializableExtra("project") as Project

//        서버에서 사용자목록 가져오는 함수 별도 실행
        getUserListFromServer()

    }

    fun getUserListFromServer() {

//        실제로 서버에 접근 (ServerUtil) => 사용자 목록 받아오기. => 내용 분석 + mUserList에 담아주기

        ServerUtil.getRequestProjectUserList(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val projectObj = dataObj.getJSONObject("project")

//                project {} 내부에 있는, ongoing_users [] 를 가져다 분석하자.
                val ongoingUsersArr = projectObj.getJSONArray("ongoing_users")

//                for문 이용 - 0 ~ 들어있는 사용자 수 직전까지 돌자.
//                14명 : 0 ~ 13까지 돌자. (현재 몇바퀴째인지? i로 표현하자)
//                몇명이 들어있는지? ongoingUsersArr 의 기능중, length() 를 실행하면 알려줌.

                for (i in   0 until ongoingUsersArr.length()) {

                }



            }

        })

    }

}