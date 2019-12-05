package com.anthony.revolut.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anthony.revolut.R
import com.anthony.revolut.base.BaseViewHolder
import com.anthony.revolut.data.entity.Rates
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_rate.*


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class CurrencyAdapter(private val context: Context, dataList: List<Any>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var adapterDataList: List<Any> = dataList

    var onItemClick: ((Any) -> Unit)? = null

    override fun getItemCount(): Int = adapterDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CurrencyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_currency_rate, parent, false)
        )
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        (holder as CurrencyViewHolder).bind(element as Rates)
    }


    fun setData(dataList: List<Any>) {

        this.adapterDataList = dataList
        notifyDataSetChanged()
    }

    /* My Item View in the List */
    inner class CurrencyViewHolder(itemView: View) : BaseViewHolder<Rates>(itemView)
        , LayoutContainer {

        override val containerView: View?
            get() = itemView

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterDataList[adapterPosition])
            }
        }

        override fun bind(item: Rates) {
            tv_name.text = item.currency.displayName
            tv_code.text = item.currency.currencyCode

            et_amount.setText(item.rate.toBigDecimal().toPlainString())

            iv_flag.setImageResource(
                context.resources.getIdentifier(
                    "ic_" + item.currency.currencyCode.toLowerCase() +"_flag"
                    , "drawable", context.packageName
                )
            )


        }
    }
}