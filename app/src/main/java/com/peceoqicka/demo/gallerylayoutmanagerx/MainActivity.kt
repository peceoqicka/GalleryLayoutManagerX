package com.peceoqicka.demo.gallerylayoutmanagerx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.peceoqicka.demo.gallerylayoutmanagerx.databinding.ActivityMainBinding
import com.peceoqicka.demo.gallerylayoutmanagerx.scroll.ScrollFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBindModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mTitleArray: Array<String>
    private var mFragmentList = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.model = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            mBindModel = this
        }
        mBinding.lifecycleOwner = this

        loadData()
    }

    private fun loadData() {
        mFragmentList.add(ScrollFragment())
        mTitleArray = resources.getStringArray(R.array.index_tab)

        mBinding.viewpager.isUserInputEnabled = false
        mBinding.viewpager.adapter = MainAdapter(this, mFragmentList)
        TabLayoutMediator(mBinding.tab, mBinding.viewpager) { tab, position ->
            tab.text = mTitleArray[position]
        }.attach()
    }

    class MainAdapter(activity: FragmentActivity, private val fragmentList: List<Fragment>) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    class MainViewModel : ViewModel() {

    }
}