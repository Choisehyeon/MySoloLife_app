package com.example.mysololife.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth

class BoardLVadapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        view= LayoutInflater.from(parent?.context).inflate(R.layout.board_lv_item, parent, false)


        val title = view!!.findViewById<TextView>(R.id.titleArea)
        title.text = boardList[position].title
        val content = view!!.findViewById<TextView>(R.id.contentArea)
        content.text = boardList[position].content
        val time = view!!.findViewById<TextView>(R.id.timeArea)
        time.text = boardList[position].time
        val itemView = view!!.findViewById<LinearLayout>(R.id.itemView)

        if(boardList[position].uid.equals(FBAuth.getUid())) {
            itemView.setBackgroundColor(Color.parseColor("#ffd400"))
        } else {
            itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        return view!!

    }
}