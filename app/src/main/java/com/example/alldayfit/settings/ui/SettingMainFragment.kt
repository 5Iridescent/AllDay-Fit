package com.example.alldayfit.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.databinding.SettingMainFragmentBinding

class SettingMainFragment : Fragment() {
    private var _binding: SettingMainFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingMainFragmentBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        appNotifiSetting.setOnClickListener { showDialog(SettingMainFragmentDirections.actionSettingMainFragmentToSettingNoticeFragment()) }
        settingGuide.setOnClickListener { showDialog(SettingMainFragmentDirections.actionSettingMainFragmentToSettingAppNotifiFragment()) }
        settingNotice.setOnClickListener { showDialog(SettingMainFragmentDirections.actionSettingMainFragmentToSettingGuideFragment()) }
        modUserInfo.setOnClickListener { showDialog(SettingMainFragmentDirections.actionSettingMainFragmentToSettingModUserFragment()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(action: NavDirections) {
        findNavController().navigate(action)
    }


}