package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_project_detail.*
import kr.co.tjoeun.daily10minutes_20201121.datas.Project
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil

class ViewProjectDetailActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        applyBtn.setOnClickListener {

//            ServerUtil.post

        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

//        mProject 활용 => 실제 데이터 화면에 뿌려주기

        projectTitleTxt.text = mProject.title
        Glide.with(mContext).load(mProject.imageURL).into(projectImg)
        projectDescriptionTxt.text = mProject.description
        projectProofMethodTxt.text = mProject.proofMethod
        
//        서버가 주는 데이터 : 5 등의 숫자. => 지금 n명 참여중! => String 가공
        onGoingUsersCountTxt.text = "지금 ${mProject.onGoingUsersCount}명 참여중!"

    }

}