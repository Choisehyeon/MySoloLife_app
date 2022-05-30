package com.example.mysololife.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        auth = Firebase.auth
        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.joinId.text.toString()
            val password1 = binding.joinPwd.text.toString()
            val password2 = binding.joinPwdCheck.text.toString()

            if(email.isEmpty()) {
                Toast.makeText(baseContext, "이메일 입력해주세요.",
                    Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(password1.isEmpty()) {
                Toast.makeText(baseContext, "password1을 입력해주세요.",
                    Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            if(password2.isEmpty()) {
                Toast.makeText(baseContext, "password2를 입력해주세요.",
                    Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }

            if(password1.length < 6) {
                Toast.makeText(baseContext, "비밀번호 6자리 이상 입력해주세요.",
                    Toast.LENGTH_SHORT).show()
                isGoToJoin = false
            }
            if(isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }
                    }
            }


        }


    }
}