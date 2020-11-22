package kr.co.tjoeun.daily10minutes_20201121.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    화면(액티비티)의 입장에서, 서버에 다녀왔을때 어떤 행동을 실행할지
//    행동 지침을 담아주기 위한 인터페이스(가이드북)를 직접 정의

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

//    ServerUtil.함수() 처럼, 클래스이름.만 해도 기능을 사용하게 도와주는 코드
//    JAVA - static 개념에 대응되는 개념.

    companion object {

//        서버 호스트 주소를 쉽게 입력하기 위한 변수
        val BASE_URL = "http://15.164.153.174"

//        로그인 기능 수행 함수

        fun postRequestLogin(context: Context, id: String, pw: String, handler: JsonResponseHandler?) {

//            클라이언트의 역할을 수행해주는 변수 (라이브러리 활용)
            val client = OkHttpClient()

//            실제 기능 수행 주소 : ex.로그인 - http://15.164.153.174/user
//            BASE_URL/user => 최종 접근 주소 (url)

            val urlString = "${BASE_URL}/user"

//            POST메쏘드 - 보통 formBody(OkHttp 라이브러리가 제공)에 데이터 첨부
//            AlertDialog => Builder 메세지/타이틀 등등 추가해서 완성 => 최종 마무리 .build() 등
//            Key / Value 쌍 적는다 => myIntent.putExtra("이름표-키", 실제데이터-밸류)
            val formData = FormBody.Builder()
                    .add("email", id)
                    .add("password", pw)
                    .build()

//            myIntent 변수를 만들듯이, API Request정보를 담는 변수도 만들어야함.
//            요청에 대한 모든 정보를 종합하는 역할.
//            1) 어디로? url
//            2) 어떤 방식? method (POST)
//            3) 갈때 어떤 데이터? formData에 담아둠
//            + header 데이터 요청이 있다면 여기서 추가.

            val request = Request.Builder()
                    .url(urlString)
                    .post(formData)
//                    .header("이름표", "실제값") // 서버가 헤더데이터를 요구하면 주석 해제
                    .build()

//            startActivity처럼 실제로 Request를 실행시키는 함수.
//            클라이언트로 동작하는 행위 (Request 호출)
//            OkHttp 라이브러리 => client 변수 활용

//            newCall => 실제 API 호출 실행
//            enqueue : Callback => 서버에 다녀와서 (Callback) 할 일을 등록하자
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우.
//                    인터넷 단선, 서버 터짐 등의 사유로 연결 자체를 실패.
//                    로그인 실패, 회원가입 실패 등은 우선 연결은 성공. (결과가 나오면 연결은 성공)
//                    토스트 : 서버에 문제가 있습니다. 서버관리자에게 문의해주세요 등.

                    Toast.makeText(context, "서버에 문제가 있습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {

//                    서버의 응답을 받아내는데 성공한 경우.
//                    서버가 내려준 응답에 뭐라고 적혀있는지 확인.
//                    응답(Response) - 본문(body) + 부가정보들.. => body만 추출해보자.
//                    String 형태로 변환해서 저장. (로그로 확인) => toString() X, string() 으로 실행.

                    val bodyString = response.body!!.string()

//                    bodyString은 JSON 양식으로 가공되어 내려옴.
//                    그냥 바로 로그로 찍으면 => JSON으로 인코딩 된 상태. (영어 OK, 한글 - 못알아봄)
//                    앱에서도 JSON을 다룰 수 있도록, bodyString을 => JSON으로 변환.
//                    Log.d("서버응답본문", bodyString)

                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답본문", jsonObj.toString())

//                    이 함수를 사용하는 화면에서,
//                    서버에 다녀오면 어떻게 대처할지 적어두었다면
//                    그 내용을 실행하도록 해주는 코드

                    handler?.onResponse(jsonObj)

                }

            })



        }

//        회원가입 기능 수행 함수

        fun putRequestSignUp(context: Context, id: String, pw: String, nickName:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .add("nick_name", nickName)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
//                    .header("이름표", "실제값") // 서버가 헤더데이터를 요구하면 주석 해제
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                   Toast.makeText(context, "서버에 문제가 있습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)

                }

            })



        }


    }


}