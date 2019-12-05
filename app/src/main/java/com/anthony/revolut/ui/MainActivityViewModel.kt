package com.anthony.revolut.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthony.revolut.data.DataResource
import com.anthony.revolut.data.entity.Rates
import com.anthony.revolut.data.repository.RatesRepository
import com.anthony.revolut.domain.GetRatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class MainActivityViewModel @Inject constructor(@VisibleForTesting val ratesUseCase: GetRatesUseCase) :
    ViewModel() {

    private var disposable: Disposable? = null

    val liveData = MutableLiveData<DataResource<List<Rates>>>()

    private var _currentCurrency = "EUR"
    private var _currentAmount = 1.00


    fun loadLatestRates() {

        disposable = ratesUseCase.getRates(_currentCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { liveData.setValue(DataResource.loading(null)) }
            .subscribe(
                { result ->
                    val ratesList = ArrayList<Rates>()
                    ratesList.add(Rates(Currency.getInstance(result.base), _currentAmount))
                    ratesList.addAll(
                        result.rates.map {
                            Rates(Currency.getInstance(it.key), it.value)
                        }
                    )
                    liveData.setValue(DataResource.success(ratesList))
                },
                { throwable ->
                    liveData.setValue(
                        DataResource.error(
                            ratesUseCase.getCustomErrorMessage(throwable), null
                        )
                    )
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }


}