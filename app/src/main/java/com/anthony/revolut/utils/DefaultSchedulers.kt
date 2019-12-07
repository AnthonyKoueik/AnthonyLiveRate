package com.anthony.revolut.utils

import com.anthony.revolut.utils.ExecutionSchedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */

class DefaultSchedulers @Inject constructor() : ExecutionSchedulers {
    override val observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    override val subscribeScheduler: Scheduler = Schedulers.io()//.newThread()
}
