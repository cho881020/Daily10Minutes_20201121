package kr.co.tjoeun.daily10minutes_20201121.utils

import android.content.Context

class ContextUtil {

    companion object {

//        메모장 이름을 오타내지 않기 위해 변수로 저장.

        val prefName = "Daily10MintuesPref"

//        로그인한사용자의 토큰을 저장한다는 의미의 항목명 변수화.

        val LOGIN_USER_TOKEN = "LOGIN_USER_TOKEN"

//        사용자 토큰 저장 / 불러내기 함수 2개

//        저장 - setter

        fun setLoginUserToken(context: Context, token: String) {
//            메모장 파일을 (파일이름:변수 활용, 모드:우리 앱에서만 활용) 열어서 => pref변수에 저장.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//            열린 메모장 (pref) 에 token 값 (LOGIN_USER_TOKEN 항목에) 저장. => apply() 로 최종 SAVE
            pref.edit().putString(LOGIN_USER_TOKEN, token).apply()
        }

//        불러내기 - getter

        fun getLoginUserToken(context: Context) : String {
//            메모장을 열어서(SAVE때와 동일) => LOGIN_USER_TOKEN 항목에 저장된 String을 결과로 지정.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            return pref.getString(LOGIN_USER_TOKEN, "")!!

        }

    }

}