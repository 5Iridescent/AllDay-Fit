package com.example.alldayfit.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.alldayfit.databinding.SettingNotifiFragmentBinding

class SettingAppNotifiFragment : Fragment() {
    private var _binding: SettingNotifiFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingNotifiFragmentBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}