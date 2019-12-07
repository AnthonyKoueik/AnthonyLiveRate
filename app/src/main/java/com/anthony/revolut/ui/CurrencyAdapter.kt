package com.anthony.revolut.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anthony.revolut.R
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.utils.EditTextInputWatcher
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
    onNewCurrency: (Rates) -> Unit,
    private val onDiffList: (List<Rates>, List<Rates>) -> Single<DiffUtil.DiffResult>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapterDataList: MutableList<Rates> = dataList

    private val amountEditTextWatcher = EditTextInputWatcher(onNewAmountInput)

    private val newCurrencySelected = onNewCurrency


    private var differenceListDisposable: Disposable? = null

    override fun getItemCount(): Int = adapterDataList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val viewHolder =
            CurrencyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_currency_rate, parent, false)
            )
        viewHolder.itemView.setOnClickListener {
            if (viewHolder.adapterPosition != 0) viewHolder.et_amount.requestFocus()
        }
        viewHolder.et_amount.apply {
            setOnFocusChangeListener { view, focused ->
                when (focused) {
                    true -> {
                        //swapRows(viewHolder)
                        newCurrencySelected(adapterDataList[viewHolder.adapterPosition])
                        addTextChangedListener(amountEditTextWatcher)
                    }
                    false -> removeTextChangedListener(amountEditTextWatcher)
                }
            }
        }
        return viewHolder
    }


    @SuppressWarnings("Unused")
    private fun swapRows(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.layoutPosition.takeIf { it > 0 }
            ?.also { position ->
                adapterDataList.removeAt(position).also {
                    adapterDataList.add(0, it)
                }
                notifyItemMoved(position, 0)
            }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (payloads.isEmpty()) {
            true -> onBindViewHolder(holder, position)
            false -> with((holder as CurrencyViewHolder).et_amount) {
                if (!isFocused) setText((payloads[0] as CurrencyAmountDifference).newAmountValue.toString())
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val element = adapterDataList[position]
        (holder as CurrencyViewHolder).bind(element, context)
    }


    @SuppressWarnings("Unused")
    fun setData(dataList: MutableList<Rates>) {

        this.adapterDataList = dataList
        notifyItemRangeChanged(0, itemCount - 1)
    }

    /* a better way for updating Recycler view adapter */
    fun updateRateList(newList: MutableList<Rates>) {

        differenceListDisposable?.dispose()
        onDiffList(adapterDataList, newList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapterDataList.clear()
                adapterDataList.addAll(newList)
                it.dispatchUpdatesTo(this@CurrencyAdapter)
            }, {})
            .let { differenceListDisposable = it }

    }

    /* My Item View in the List */
    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        , LayoutContainer {

        override val containerView: View?
            get() = itemView

        @SuppressLint("DefaultLocale")
        fun bind(item: Rates, context: Context) {

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

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
            CurrencyAmountDifference(newList[newItemPosition].rate)
    }

    data class CurrencyAmountDifference(val newAmountValue: Double)


}