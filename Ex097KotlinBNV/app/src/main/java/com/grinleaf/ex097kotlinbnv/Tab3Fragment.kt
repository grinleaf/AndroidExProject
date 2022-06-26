package com.grinleaf.ex097kotlinbnv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab1Binding
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab2Binding
import com.grinleaf.ex097kotlinbnv.databinding.FragmentTab3Binding

class Tab3Fragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTab3Binding.inflate(inflater,container,false)
        return binding.root
    }

    //바인딩 클래스
    lateinit var binding: FragmentTab3Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text="세번째 Tab"
    }
}