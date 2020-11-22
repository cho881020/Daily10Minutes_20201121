package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



                }

            })
            

        }

    }

    override fun setValues() {

    }

}