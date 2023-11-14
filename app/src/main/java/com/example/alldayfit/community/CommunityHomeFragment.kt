package com.example.alldayfit.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.alldayfit.community.adapter.CommunityViewAdapter
import com.example.alldayfit.databinding.CommunityHomeFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CommunityHomeFragment : Fragment() {
    private var _binding: CommunityHomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CommunityViewAdapter
    private lateinit var viewModel: CommunityViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommunityHomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CommunityViewModel::class.java)
        adapter = CommunityViewAdapter(viewModel,childFragmentManager)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        viewModel.communityLivedata.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })
        //커뮤니티 라이브데이터 관찰
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var instance: CommunityHomeFragment? = null

        fun newInstance(): CommunityHomeFragment {
            if (instance == null) {
                instance = CommunityHomeFragment()
            }
            return instance!!
        }
    }
}