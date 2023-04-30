package com.example.myapplicationcse225

import android.icu.text.CaseMap.Title
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplicationcse225.Fragmentimportant

class ViewPagerAdapter( fm: FragmentManager) : FragmentPagerAdapter(fm)
{
    private  val  fragmentList:MutableList<Fragment> =ArrayList()
    private val titleList:MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
    override fun getCount(): Int {
        return fragmentList.size
    }
    fun addFragment(fragment:Fragment,title:String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return  titleList[position]
    }
}