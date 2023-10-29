package com.example.alldayfit.settings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.R
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

    /* setting page design, data 초기 설정 */
    private fun initView() = with(binding) {
        appNotifiSetting.setOnClickListener { showDialog(R.id.action_settingMainFragment_to_settingNoticeFragment) }
        settingGuide.setOnClickListener { showDialog(R.id.action_settingMainFragment_to_settingGuideFragment) }
        settingNotice.setOnClickListener { showDialog(R.id.action_settingMainFragment_to_settingAppNotifiFragment) }
        modUserInfo.setOnClickListener { showDialog(R.id.action_settingMainFragment_to_settingModUserFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /* main_graph의 action을 활용해서 dialog 띄우기 */
    private fun showDialog(action: Int) {
        findNavController().navigate(action)
    }

    companion object{
        fun newInstance() = SettingMainFragment()
    }
}