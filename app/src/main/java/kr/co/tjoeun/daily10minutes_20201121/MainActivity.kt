package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signInBtn.setOnClickListener {

//            1. 입력된 아이디 / 비번 받아오기

            val inputEmail = emailEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            2. 서버에 맞는 회원인지 확인 => 로그인 API 호출 => 서버 응답 분석 => UI 반영

            ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    로그인 API에 다녀오면 실행할 내용
//                    다녀온다 : 서버가 Response를 준다
//                    그 응답 내용 : json 변수에 담겨있다.

                    Log.d("화면:서버다녀옴", json.toString())

                }

            })

        }

    }

    override fun setValues() {

    }

}