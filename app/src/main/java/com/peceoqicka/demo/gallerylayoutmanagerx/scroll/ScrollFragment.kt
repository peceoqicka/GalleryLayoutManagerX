package com.peceoqicka.demo.gallerylayoutmanagerx.scroll

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peceoqicka.demo.gallerylayoutmanagerx.R
import com.peceoqicka.demo.gallerylayoutmanagerx.databinding.FragmentScrollBinding
import com.peceoqicka.demo.gallerylayoutmanagerx.util.appMoshi
import com.peceoqicka.demo.gallerylayoutmanagerx.util.getItemViewModelFromAssets
import com.peceoqicka.demo.gallerylayoutmanagerx.util.toIntOrDefault
import com.peceoqicka.x.gallerylayoutmanager.GalleryLayoutManager
import com.peceoqicka.x.gallerylayoutmanager.OnScrollListener
import com.peceoqicka.x.gallerylayoutmanager.SimpleViewTransformListener
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScrollFragment : Fragment() {
    private lateinit var mBinding: FragmentScrollBinding
    private lateinit var mBindModel: MyViewModel
    private lateinit var mActivity: Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mActivity = activity ?: return null
        val binding = DataBindingUtil.inflate<FragmentScrollBinding>(
            inflater,
            R.layout.fragment_scroll,
            container,
            false
        )
        binding.model = ViewModelProvider(this).get(MyViewModel::class.java).apply {
            handler = mHandler
            bannerLayoutManager = GalleryLayoutManager.create {
                itemSpace = 120
                onScrollListener = mOnScrollListener
                viewTransformListener = SimpleViewTransformListener(1.2f, 1.2f)
            }
            supplyLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            mBindModel = this
        }
        binding.lifecycleOwner = this
        mBinding = binding

        loadData()
        return binding.root
    }

    private fun loadData() {
        lifecycleScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }) {
            val bannerList =
                getItemViewModelFromAssets<BannerAdapter.ItemViewModel>("source.json")//.subList(0, 1)
            mBindModel.bannerAdapter.value = BannerAdapter(bannerList)
            mBindModel.bannerTouchHelper.value = ItemTouchHelper(BannerTouchCallback { position ->
                moveToSupplyList(position)
            })

            delay(500)

            val list = getItemViewModelFromAssets<SupplyAdapter.ItemViewModel>("source2.json")
            mBindModel.supplyAdapter.value = SupplyAdapter(list)
            mBindModel.supplyTouchHelper.value = ItemTouchHelper(SupplyTouchCallback { position ->
                addToBannerList(position)
            })
        }
    }

    private fun moveToSupplyList(position: Int) {
        val data = mBindModel.bannerAdapter.value?.getItem(position)
        mBindModel.bannerAdapter.value?.removeAt(position)
        data?.let { removingData ->
            val json =
                appMoshi.adapter(BannerAdapter.ItemViewModel::class.java).toJson(removingData)
            val transformedData =
                appMoshi.adapter(SupplyAdapter.ItemViewModel::class.java).fromJson(json)
            transformedData?.let { addData ->
                mBindModel.supplyAdapter.value?.addData(0, addData)
                mBinding.rvProvider.smoothScrollToPosition(0)
            }
        }
    }

    private fun addToBannerList(position: Int) {
        val data = mBindModel.supplyAdapter.value?.getItem(position)
        mBindModel.supplyAdapter.value?.removeAt(position)
        val insertPosition = mBindModel.scrollPosition.value?.toIntOrDefault() ?: 0
        data?.let { removingData ->
            val json =
                appMoshi.adapter(SupplyAdapter.ItemViewModel::class.java).toJson(removingData)
            val transformedData =
                appMoshi.adapter(BannerAdapter.ItemViewModel::class.java).fromJson(json)
            transformedData?.let { addData ->
                mBindModel.bannerAdapter.value?.addData(insertPosition, addData)
            }
        }
    }

    private fun scrollToPosition() {
        val targetPosition = mBindModel.scrollPosition.value?.toIntOrDefault() ?: 0
        val smoothScroll = mBindModel.smoothScroll.value ?: true
        if (smoothScroll) {
            mBinding.recyclerview.smoothScrollToPosition(targetPosition)
        } else {
            mBinding.recyclerview.scrollToPosition(targetPosition)
        }
    }

    private val mHandler = object : MyViewModel.Handler {
        override fun onItemClick(v: View, model: MyViewModel) {
            when (v.id) {
                R.id.btn_scroll_to_index -> {
                    scrollToPosition()
                }
            }
        }
    }

    private val mOnScrollListener = object : OnScrollListener {
        override fun onIdle(snapViewPosition: Int) {
            mBindModel.currentSelectionPosition.value = snapViewPosition * 1f
        }

        override fun onScrolling(scrollingPercentage: Float, fromPosition: Int, toPosition: Int) {
            mBindModel.currentSelectionPosition.value = if (fromPosition > toPosition) {
                (fromPosition - toPosition) * scrollingPercentage + toPosition
            } else {
                (toPosition - fromPosition) * scrollingPercentage + fromPosition
            }
        }
    }

    class MyViewModel : ViewModel() {
        interface Handler {
            fun onItemClick(v: View, model: MyViewModel)
        }

        lateinit var handler: Handler
        lateinit var bannerLayoutManager: RecyclerView.LayoutManager
        lateinit var supplyLayoutManager: RecyclerView.LayoutManager

        val bannerAdapter = MutableLiveData<BannerAdapter>(null)
        val bannerTouchHelper = MutableLiveData<ItemTouchHelper>(null)
        val scrollPosition = MutableLiveData("0")
        val smoothScroll = MutableLiveData(true)
        val currentSelectionPosition = MutableLiveData(0f)
        val supplyAdapter = MutableLiveData<SupplyAdapter>(null)
        val supplyTouchHelper = MutableLiveData<ItemTouchHelper>(null)
    }
}