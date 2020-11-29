package kr.co.tjoeun.daily10minutes_20201121

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d("모든화면", "공통기능자동실행")

//        이 모든 화면이 공통적으로 실행하는 함수에서 => 액션바 설정
//        모든 화면에 커스텀 액션바 자동 반영
//        상황을 봐가면서 괜찮을때만 액션바 자동 반영

        supportActionBar?.let {
//            supportActionBar 가 null 이 아닐때 실행시켜줄 코드. : let의 역할

            setCustomActionBar()
        }


    }

    val mContext = this

    abstract fun setupEvents()
    abstract fun setValues()

    //    액션바를 직접 그리기 위한 함수

    fun setCustomActionBar() {

//        1. 액션바가 어떻게 보이게 하고 싶은지? 모양 (layout)을 그려야함. xml 작성

//        기존 액션바를 불러내서 => 속성(ex. 커스텀액션바모드)들 변경 => xml 반영

//        기존 액션바 불러내기 (무조건 존재한다고 우기기)
        val defaultActionBar = supportActionBar!!

//        커스텀 액션바를 보여주게 모드 변경
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

//        실제로 보여질 커스텀 화면 연결
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

//        액션바(보라색) > 툴바(여백) > 커스텀뷰(검정배경)
//        여백을 없애려면? => 액션바-툴바의 속성 변경 => 내부공간 0으로 설정.

//        액션바.커스텀뷰.부모 -> 툴바로 변신
//        androidx 가 주는 툴바 사용
        val toolBar = defaultActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0,0)


    }

}