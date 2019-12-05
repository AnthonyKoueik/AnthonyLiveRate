package com.anthony.revolut.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anthony.revolut.R
import com.anthony.revolut.base.BaseActivity

class MainActivity : BaseActivity<MainActivityViewModel>() {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
