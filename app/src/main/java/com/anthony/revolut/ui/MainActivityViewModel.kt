package com.anthony.revolut.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.anthony.revolut.data.repository.RatesRepository
import javax.inject.Inject


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class MainActivityViewModel @Inject constructor(@VisibleForTesting val repository: RatesRepository) :
    ViewModel() {
}