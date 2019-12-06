package com.anthony.revolut.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anthony.revolut.R
import com.anthony.revolut.base.BaseViewHolder
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.utils.EditTextInputWatcher
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_rate.*




/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class CurrencyAdapter(
    private val context: Context,
    dataList: MutableList<Rates>,
    onNewAmountInput: (Double) -> Unit,
    onNewCurrency: (Rates) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var adapterDataList: MutableList<Rates> = dataList

    private val amountEditTextWatcher = EditTextInputWatcher(onNewAmountInput)

    private val newCurrencySelected = onNewCurrency

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


    fun setData(dataList: MutableList<Rates>) {

        this.adapterDataList = dataList
        notifyItemRangeChanged(0, itemCount - 1)
    }

    /* a better way for updating Recycler view adapter */
    fun updateRateList(newList : MutableList<Rates>){
        val ratesDiffCallback = RatesDiffCallback(adapterDataList, newList)
        val diffResult = DiffUtil.calculateDiff(ratesDiffCallback)

        this.adapterDataList.clear()
        this.adapterDataList.addAll(newList)

        diffResult.dispatchUpdatesTo(this@CurrencyAdapter)
    }

    /* My Item View in the List */
    inner class CurrencyViewHolder(itemView: View) : BaseViewHolder<Rates>(itemView)
        , LayoutContainer {

        override val containerView: View?
            get() = itemView

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterDataList[adapterPosition])
                if (adapterPosition != 0) et_amount.requestFocus()
            }
        }

        override fun bind(item: Rates) {

            et_amount.apply {
                setOnFocusChangeListener { view, focused ->

                    when (focused) {
                        true -> {
                            swapRows()
                            newCurrencySelected(adapterDataList[adapterPosition])
                            //onNewCurrencyFun()
                            addTextChangedListener(amountEditTextWatcher)
                        }
                        false -> {
                            removeTextChangedListener(amountEditTextWatcher)
                        }
                    }
                }
            }

            tv_name.text = item.currency.displayName
            tv_code.text = item.currency.currencyCode

            /*Only Update the Not focused Edit Text*/
            with(et_amount) {
                if (!isFocused) setText(item.rate.toBigDecimal().toPlainString())
            }

            iv_flag.setImageResource(
                context.resources.getIdentifier(
                    "ic_" + item.currency.currencyCode.toLowerCase() + "_flag"
                    , "drawable", context.packageName
                )
            )
        }

        private fun swapRows() {
            layoutPosition.takeIf { it > 0 }
                ?.also { position ->
                    adapterDataList.removeAt(position).also {
                        adapterDataList.add(0, it)
                    }
                    notifyItemMoved(position, 0)
                }
        }
    }


    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class RatesDiffCallback(
        private val oldList: List<Rates>,
        private val newList: List<Rates>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].currency.currencyCode == newList[newItemPosition].currency.currencyCode
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].rate == newList[newItemPosition].rate
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

}