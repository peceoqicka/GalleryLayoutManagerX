package com.peceoqicka.demo.gallerylayoutmanagerx.databinding.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.adapter = adapter
}

@BindingAdapter("layoutManager")
fun RecyclerView.bindLayoutManager(layoutManager: RecyclerView.LayoutManager) {
    this.layoutManager = layoutManager
}

@BindingAdapter("itemDecoration")
fun RecyclerView.bindItemDecoration(itemDecoration: RecyclerView.ItemDecoration?) {
    itemDecoration?.let {
        this.addItemDecoration(it)
    }
}

@BindingAdapter("itemTouchHelper")
fun RecyclerView.bindItemTouchHelper(itemTouchHelper: ItemTouchHelper?) {
    itemTouchHelper?.attachToRecyclerView(this)
}