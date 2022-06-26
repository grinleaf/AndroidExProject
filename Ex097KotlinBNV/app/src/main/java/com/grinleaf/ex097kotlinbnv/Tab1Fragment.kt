package com.grinleaf.ex097kotlinbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab1Binding

class Tab1Fragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    //바인딩 클래스
    val binding:FragmentTab1Binding by lazy { FragmentTab1Binding.inflate(layoutInflater) }
}