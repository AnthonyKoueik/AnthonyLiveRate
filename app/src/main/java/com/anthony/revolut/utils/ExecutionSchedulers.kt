package com.anthony.revolut.utils

import io.reactivex.Scheduler


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
interface ExecutionSchedulers {
    val subscribeScheduler: Scheduler
    val observeScheduler: Scheduler

}