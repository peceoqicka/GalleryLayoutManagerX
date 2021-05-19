package com.peceoqicka.demo.gallerylayoutmanagerx.scroll

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SupplyTouchCallback(private val onSupplyCallback: (Int) -> Unit) :
    ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val swipe = ItemTouchHelper.UP
        return makeMovementFlags(drag, swipe)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSupplyCallback(viewHolder.bindingAdapterPosition)
    }
}