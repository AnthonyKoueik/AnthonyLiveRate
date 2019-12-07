package com.anthony.revolut.util

import com.anthony.revolut.utils.ExecutionSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
class TestSchedulers : ExecutionSchedulers {
    override val observeScheduler: Scheduler = Schedulers.trampoline()
    override val subscribeScheduler: Scheduler = Schedulers.trampoline()
}
