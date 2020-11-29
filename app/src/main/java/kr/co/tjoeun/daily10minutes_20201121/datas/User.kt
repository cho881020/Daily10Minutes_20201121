package kr.co.tjoeun.daily10minutes_20201121.datas

import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class User {

    var id = 0 // id가 Int라고 명시
    var email = "" // email은 String
    var nickName = ""

//    응용
//    1. 가입일시 (created_at) : 서버는 String으로 알려줌 -> 앱에서는 Calendar 형태로 변환해서 사용.

//    Calendar를 만들때는, 생성자 사용 X => getInstance() 함수를 대신 사용.
//    createdAt에는 기본값 ?년?월?일? => 현재시간정보가 기본값으로 들어감.
    val createdAt = Calendar.getInstance()

//    2. 프로필 사진"들" : 한명의 User가 -> 여러장의 사진URL (String) 을 갖고 있음을 어떻게 표현?

//    한명의 사용자 : 프사 URL 목록을 갖는다고 명시. (ArrayList를 갖는다)
    val profileImgList = ArrayList<String>()

//    JSON 넣으면 => User로 변환해주는 기능 제작

    companion object {

        fun getUserFromJSON(json: JSONObject) : User {
            
            val u = User()
            
//            u의 변수들을 => json이 가진 값을 활용해서 변경

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")

//            프사 목록 파싱 => u가 가진 profileImgList에 add 해주자.

            val profileImagesArr = json.getJSONArray("profile_images")

            for (i in   0 until profileImagesArr.length()) {
                val pf = profileImagesArr.getJSONObject(i)
                val imgURL = pf.getString("img_url")
                u.profileImgList.add(imgURL)
            }

//            사용자의 회원가입 일자를 2000-01-01 00:00:00 으로 세팅하고싶다.

//            1) 항목을 지정하고, 거기에 들어갈 값 명시
            u.createdAt.set(Calendar.YEAR, 2000) // 년도 자리에 2000 대입.
            u.createdAt.set(Calendar.MONTH, Calendar.JANUARY) // 0~11월 까지 있다. Calendar.월이름 으로 넣는게 정확함.
            u.createdAt.set(Calendar.DAY_OF_MONTH, 1) // (해당 월의) 일자 자리에 1일로 기록.

//            2) 여러 항목을 한꺼번에 입력하기
            u.createdAt.set(2000, Calendar.JANUARY, 1, 0, 0, 0)

            return u
        }

    }

}