package com.example.alldayfit.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.community.adapter.CommunityViewPagerAdapter
import com.example.alldayfit.databinding.CommunityMainFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class CommunityMainFragment : Fragment() {
    private var _binding: CommunityMainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CommunityViewModel


    private val communityViewPagerAdapter by lazy {
        CommunityViewPagerAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommunityMainFragmentBinding.inflate(inflater, container, false)
        initView()
        setdialog()
        return binding.root
    }

    private fun setdialog() = with(binding) {
        addPostBtn.setOnClickListener {
            viewModel = ViewModelProvider(requireActivity())[CommunityViewModel::class.java]
            val newPostDialog = CommunityNewPostDialog(viewModel)
            newPostDialog.show(childFragmentManager, "newpost")
        }
    }

    private fun initView() = with(binding) {
        // viewpager와 adpater 연결
        viewpager.adapter = communityViewPagerAdapter
        // viewpager를 tablayout이랑 연결하여 따로 adapter를 구성하지 않아도 됨
        TabLayoutMediator(tabLayout, viewpager) { tab, pos ->
            tab.text = getString(communityViewPagerAdapter.getTitle(pos))
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}