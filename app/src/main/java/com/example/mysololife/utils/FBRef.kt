package com.example.mysololife.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object {
        val database = Firebase.database


        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")
        val bookmarkRef = database.getReference("bookmark_list")
    }
}