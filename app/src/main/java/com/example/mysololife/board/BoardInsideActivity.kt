package com.example.mysololife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardInsideBinding
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName
    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        //첫번째 방법
       /* val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")

        binding.titleArea.text = title
        binding.timeArea.text = time
        binding.contentArea.text = content*/

        //두번째 방법
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }
    }
    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.updateBtn)!!.setOnClickListener {

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.removeBtn)!!.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            finish()
        }
    }

    private fun getBoardData(key : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    Log.d(TAG, dataSnapshot.toString())


                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                    binding.titleArea.text = dataModel!!.title
                    binding.timeArea.text = dataModel.time
                    binding.contentArea.text = dataModel.content

                } catch (e : Exception) {
                    Log.d(TAG, "삭제완료")
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ContentListActivity", "loadPost: ", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    private fun getImageData(key : String) {
        val storageReference = Firebase.storage.reference.child(key + ".png")

// ImageView in your Activity
        val imageView = binding.getImageArea

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageView)

            } else {

            }
        })
    }
}