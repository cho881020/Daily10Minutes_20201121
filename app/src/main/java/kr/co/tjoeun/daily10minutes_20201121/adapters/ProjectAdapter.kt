package kr.co.tjoeun.daily10minutes_20201121.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20201121.R
import kr.co.tjoeun.daily10minutes_20201121.datas.Project

class ProjectAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<Project>) : ArrayAdapter<Project>(mContext, resId, mList) {

//    mContext, mList => m으로 시작 : 멤버변수임을 명시
//    멤버변수 : 변수중, 클래스 내부의 어느곳(=어떤 함수)에서든 사용가능한 변수.
//    resId => 그냥 시작  : 멤버변수가 아님.
//    다른 함수 내에서는 가져다 사용할 수 없다. (임시로 사용하기 위함) : 클래스 첫 중괄호 까지만 OK

    val mInf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        리스트뷰의 재사용성 활용 : convertView 변수 활용
//        convertView는 내용 변경 불가 => "var" tempRow를 활용
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInf.inflate(R.layout.project_list_item, null)
        }

        val row = tempRow!!

        val projectBackgroundImg = row.findViewById<ImageView>(R.id.projectBackgroundImg)
        val projectTitleTxt = row.findViewById<TextView>(R.id.projectTitleTxt)
        val projectDescriptionTxt = row.findViewById<TextView>(R.id.projectDescriptionTxt)

        val projectData = mList[position]

        projectTitleTxt.text = projectData.title
        projectDescriptionTxt.text = projectData.description

        Glide.with(mContext).load(projectData.imageURL).into(projectBackgroundImg)

        return row

    }

}