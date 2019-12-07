package com.anthony.revolut.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.anthony.revolut.MyApplication
import com.anthony.revolut.R
import com.anthony.revolut.data.Loading
import com.anthony.revolut.data.Resource
import com.anthony.revolut.data.Success
import com.anthony.revolut.data.Error
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.domain.GetRatesUseCase
import com.anthony.revolut.utils.ExecutionSchedulers
import com.anthony.revolut.utils.calculate
import com.anthony.revolut.utils.getCustomErrorMessage
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class MainActivityViewModel @Inject constructor(
    private val ratesUseCase: GetRatesUseCase,
    private val defaultSchedulers: ExecutionSchedulers
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _liveData = MutableLiveData<Resource<MutableList<Rates>>>()
    val liveData: LiveData<Resource<MutableList<Rates>>> get() = _liveData


    private var _currentCurrency = "EUR"
    private var _currentAmount = 1.00

    private var isLoaded: Boolean = false


    @SuppressWarnings("Unused")
    fun testDispose() {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun loadLatestRates() {

        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }

        disposable = ratesUseCase.getRates(_currentCurrency)
            .subscribeOn(defaultSchedulers.subscribeScheduler)
            .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .observeOn(defaultSchedulers.observeScheduler)
            .doOnSubscribe {
                if (!isLoaded) _liveData.value = Loading(null)
            }
            .subscribe(
                { result ->
                    Timber.d("_currentBaseCurrency %s", result.base)
                    val ratesList = ArrayList<Rates>()
                    ratesList.add(Rates(Currency.getInstance(result.base), _currentAmount))
                    ratesList.addAll(
                        result.rates.map {
                            calculate(result.rates, Currency.getInstance(it.key), _currentAmount)
                            Rates(Currency.getInstance(it.key), (it.value * _currentAmount))
                        }
                    )
                    isLoaded = true
                    _liveData.setValue(Success(ratesList))
                },
                { throwable ->
                    _liveData.setValue(
                        Error(
                            getCustomErrorMessage(throwable, MyApplication.instance)
                        )
                    )
                }
            )
    }

    fun onNewAmountInput(newAmount: Double) {
        _currentAmount = newAmount
        Timber.d("_currentAmount $_currentAmount")
    }

    fun onCurrencyChanged(rates: Rates) {
        _currentAmount = rates.rate
        _currentCurrency = rates.currency.currencyCode
        Timber.d("_currentCurrency $_currentCurrency")
        Timber.d("_currentAmount $_currentAmount")
        loadLatestRates()
    }

    fun onRateListsDifferences(
        oldList: List<Rates>,
        newList: List<Rates>
    ): Single<DiffUtil.DiffResult> {
        return Single.just(
            DiffUtil.calculateDiff(
                CurrencyAdapter.RatesDiffCallback(
                    oldList,
                    newList
                )
            )
        )
            .subscribeOn(Schedulers.io())
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }


}