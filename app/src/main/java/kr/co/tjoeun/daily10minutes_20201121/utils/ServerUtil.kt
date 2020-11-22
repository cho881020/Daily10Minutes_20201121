package kr.co.tjoeun.daily10minutes_20201121.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ServerUtil {

//    ServerUtil.함수() 처럼, 클래스이름.만 해도 기능을 사용하게 도와주는 코드
//    JAVA - static 개념에 대응되는 개념.
    companion object {

//        서버 호스트 주소를 쉽게 입력하기 위한 변수
        val BASE_URL = "http://15.164.153.174"

//        로그인 기능 수행 함수

        fun postRequestLogin(id: String, pw: String) {

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

            client.newCall(request)



        }

    }


}