package com.example.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

class ContentsRVAdapter(val context : Context,
                        val items : ArrayList<ContentModel>,
                        val keyList : ArrayList<String>,
                        val bookmarkIdList : MutableList<String>) : RecyclerView.Adapter<ContentsRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRVAdapter.ViewHolder {

        val v  = LayoutInflater.from(parent?.context).inflate(R.layout.contents_rv_item, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ContentsRVAdapter.ViewHolder, position: Int) {

        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel, key: String) {

            itemView.setOnClickListener {
                Toast.makeText(context, item.title, Toast.LENGTH_LONG).show()
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }

            val title = itemView.findViewById<TextView>(R.id.textArea)
            title.text = item.title
            val image = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkBtn = itemView.findViewById<ImageView>(R.id.bookmarkBtn)

            if(bookmarkIdList.contains(key)){
                bookmarkBtn.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkBtn.setImageResource(R.drawable.bookmark_white)
            }

            bookmarkBtn.setOnClickListener {
                Toast.makeText(context, key, Toast.LENGTH_LONG).show()

                //북마크가 있을 떄
                if(bookmarkIdList.contains(key)) {
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                } else {
                    //북마크가 없을 때
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))
                }
            }

            Glide.with(context)
                .load(item.imageUrl)
                .into(image)
        }
    }
}