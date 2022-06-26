package com.grinleaf.ex097kotlinbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab1Binding
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab2Binding

class Tab2Fragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    //바인딩 클래스
    val binding:FragmentTab2Binding by lazy { FragmentTab2Binding.inflate(layoutInflater) }
}