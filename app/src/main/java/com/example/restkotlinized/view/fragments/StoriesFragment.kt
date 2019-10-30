package com.example.restkotlinized.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restkotlinized.R

//object StoriesFragment : Fragment() {
//    fun getStFrag(context: Context): StoriesFragment = StoriesFragment
//}
class StoriesFragment(context: Context) : Fragment() {
    val ctx = context
    companion object Factory {
        fun create(context: Context): StoriesFragment =
            StoriesFragment(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_stories, container, false)
}