package com.example.alldayfit.community;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.community.adapter.CommunityMyPostAdapter
import com.example.alldayfit.databinding.CommunityMypostFragmentBinding


class CommunityMyPostFragment : Fragment() {
    private var _binding: CommunityMypostFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CommunityMyPostAdapter
    private lateinit var viewModel: CommunityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommunityMypostFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CommunityViewModel::class.java)
        adapter = CommunityMyPostAdapter(viewModel, childFragmentManager)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        viewModel.communitymydata.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CommunityMyPostFragment()
    }
}
