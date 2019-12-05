package com.anthony.revolut.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 *
 * base view holder for binding view
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}