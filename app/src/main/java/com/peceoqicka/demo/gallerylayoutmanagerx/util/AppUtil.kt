package com.peceoqicka.demo.gallerylayoutmanagerx.util

import androidx.fragment.app.Fragment
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

val appMoshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

suspend inline fun <reified T> Fragment.getItemViewModelFromAssets(fileName: String): MutableList<T> =
    withContext(Dispatchers.IO) {
        val textResult = runCatching {
            val inputStream = activity?.assets?.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val reader = BufferedReader(inputStreamReader)
            reader.readText()
        }
        val text = textResult.getOrThrow()

        val listResult = kotlin.runCatching {
            appMoshi.adapter<MutableList<T>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    T::class.java
                )
            ).fromJson(text)
        }
        listResult.getOrThrow() ?: arrayListOf()
    }

fun String.toIntOrDefault(value: Int = 0): Int {
    return toIntOrNull() ?: value
}