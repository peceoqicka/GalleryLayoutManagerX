package com.peceoqicka.demo.gallerylayoutmanagerx.scroll

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.peceoqicka.demo.gallerylayoutmanagerx.BR
import com.peceoqicka.demo.gallerylayoutmanagerx.R
import com.peceoqicka.demo.gallerylayoutmanagerx.databinding.ItemSupplyBinding

class SupplyAdapter(dataList: MutableList<ItemViewModel>) :
    BaseQuickAdapter<SupplyAdapter.ItemViewModel, BaseDataBindingHolder<ItemSupplyBinding>>(
        R.layout.item_supply, dataList
    ) {

    override fun convert(holder: BaseDataBindingHolder<ItemSupplyBinding>, item: ItemViewModel) {
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