package com.peceoqicka.demo.gallerylayoutmanagerx.scroll

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.recyclerview.widget.ItemTouchHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.peceoqicka.demo.gallerylayoutmanagerx.BR
import com.peceoqicka.demo.gallerylayoutmanagerx.R
import com.peceoqicka.demo.gallerylayoutmanagerx.databinding.ItemBannerBinding

class BannerAdapter(dataList: MutableList<ItemViewModel>) :
    BaseQuickAdapter<BannerAdapter.ItemViewModel, BaseDataBindingHolder<ItemBannerBinding>>(
        R.layout.item_banner, dataList
    ) {

    override fun convert(holder: BaseDataBindingHolder<ItemBannerBinding>, item: ItemViewModel) {
        holder.dataBinding?.let { binding ->
            binding.model = item
        }
    }

    class ItemViewModel : BaseObservable() {
        @get:Bindable
        var imageUrl: String = ""
            set(value) {
                field = value;notifyPropertyChanged(BR.imageUrl)
            }

        @get:Bindable
        var title: String = ""
            set(value) {
                field = value;notifyPropertyChanged(BR.title)
            }
    }
}