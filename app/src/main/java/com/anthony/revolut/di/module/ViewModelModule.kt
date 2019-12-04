package com.anthony.revolut.di.module

import androidx.lifecycle.ViewModelProvider
import com.anthony.revolut.base.ViewModelFactory
import dagger.Binds
import dagger.Module


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}