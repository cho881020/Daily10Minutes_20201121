package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.tjoeun.daily10minutes_20201121.datas.Project

class ViewProjectUserListActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_user_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mProject = intent.getSerializableExtra("project") as Project

    }

}