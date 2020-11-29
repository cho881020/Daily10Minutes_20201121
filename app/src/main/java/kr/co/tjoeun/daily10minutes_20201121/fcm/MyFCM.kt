package kr.co.tjoeun.daily10minutes_20201121.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

//       (라이브러리가 알아서) 새 토큰을 발급받앗을때 (자동으로) 실행되는 함수.
//        토큰값 임시 확인 테스트용

        Log.d("새로발급된기기토큰", p0)

    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
//        FCM서버가 => 우리폰으로 푸시알림을 줬을때 실행되는 함수.
//        푸시알림의 내용 : p0 변수에 들어있다.
//        실제 사용자에게 알림을 띄우는 코드를 작성하는 곳
    }

}