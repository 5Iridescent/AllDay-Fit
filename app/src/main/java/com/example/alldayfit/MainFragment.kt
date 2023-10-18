package com.example.alldayfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alldayfit.databinding.FragmentMainBinding
import com.example.alldayfit.databinding.MainActivityBinding


class MainFragment : Fragment() {
   private lateinit var mBinding : FragmentMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}