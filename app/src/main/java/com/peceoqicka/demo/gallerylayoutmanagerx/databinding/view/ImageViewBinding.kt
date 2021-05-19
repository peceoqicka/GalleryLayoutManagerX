package com.peceoqicka.demo.gallerylayoutmanagerx.databinding.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    load(url)
}

@BindingAdapter("imageUrl", "withCircleCrop")
fun ImageView.bindImageUrl(url: String?, withCircleCrop: Boolean) {
    load(url) {
        if (withCircleCrop) {
            transformations(CircleCropTransformation())
        }
    }
}