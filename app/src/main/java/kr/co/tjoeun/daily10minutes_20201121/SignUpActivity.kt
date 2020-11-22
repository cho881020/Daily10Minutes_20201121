package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        이메일 입력칸의 내용이 변경될때

        idEdt.addTextChangedListener {

//            한글자라도 내용이 변경되면 실행되는 부분.
            Log.d("아이디입력값", idEdt.text.toString())

//            내용이 변경되면 => 무조건 검사 결과를 원상태로 복귀.
            checkResultTxt.text = "중복 확인을 해주세요."

        }

        emailCheckBtn.setOnClickListener {

//            1. 입력한 이메일 값 확인
            val inputEmail = idEdt.text.toString()
//            2. 서버에 중복인지 물어봄 => API 호출 (서버 문서 확인) + ServerUtil에 기능 추가
            ServerUtil.getRequestEmailCheck(mContext, inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    3. 검사결과를 텍스트뷰에 반영
//                    서버가 내려준 검사 결과 메세지를 그대로 텍스트뷰에 반영

                    val message = json.getString("message")

                    runOnUiThread {
                        checkResultTxt.text = message
                    }

                }

            })


        }

        okBtn.setOnClickListener {
//            1. 입력한 아이디 / 비번 / 닉네임을 파악
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()
            val inputNickName = nickNameEdt.text.toString()
            
//            2. ServerUtil 활용 => 회원가입 API 호출
//            회원가입 기능을 ServerUtil에 우선 추가 => 화면에서 활용
            ServerUtil.putRequestSignUp(mContext, inputId, inputPw, inputNickName, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    3. 돌아와서 어떡할지? 코딩
//                     code 값이 200이냐 / 아니냐로 구별

                    val code = json.getInt("code")

                    if (code == 200) {
//                        가입 성공 : 가입한사람의 닉네임으로 환영 토스트
//                        테스터201122님 환영합니다! 등
//                        로그인 화면으로 복귀

//                        json {} => data {} : JSONObject => user {} => nick_name String

                        val dataObj = json.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickName = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nickName}님 환영합니다!", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }
                    else {
//                        가입실패 : message 적힌 가입 실패 사유를 받아서 출력
                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })
            

        }

    }

    override fun setValues() {

    }

}