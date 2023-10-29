package com.example.alldayfit.community.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alldayfit.R
import com.example.alldayfit.community.CommunityHomeFragment
import com.example.alldayfit.community.CommunityMyPostFragment
import com.example.alldayfit.community.model.CommunityTabsModel

class CommunityViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments: ArrayList<CommunityTabsModel> = arrayListOf(
        addNewFragment(R.string.community_tab_home, CommunityHomeFragment.newInstance()),
        addNewFragment(R.string.community_tab_my_post, CommunityMyPostFragment.newInstance()),
    )

    private fun addNewFragment(title: Int, fragment: Fragment): CommunityTabsModel =
        CommunityTabsModel(title, fragment)

    fun getTitle(position: Int): Int = fragments[position].title

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].fragment
}