package com.anthony.revolut.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.revolut.R
import com.anthony.revolut.base.BaseActivity
import com.anthony.revolut.data.*
import com.anthony.revolut.data.entity.Rates
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity<MainActivityViewModel>() {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        currencyAdapter = CurrencyAdapter(
            this@MainActivity,
            mutableListOf(),
            viewModel::onNewAmountInput,
            viewModel::onCurrencyChanged,
            viewModel::onRateListsDifferences
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        viewModel.liveData.observe(this, Observer { apiResponse ->

            bindResponse(apiResponse)
            Timber.d(apiResponse.toString())
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadLatestRates()
    }


    private fun bindResponse(apiResponse: Resource<MutableList<Rates>>) {


        when (apiResponse) {

            is Loading -> {
                Timber.d("LOADING :: ")
                showLoader()
            }

            is Success -> {
                Timber.d("SUCCESS :: %s", apiResponse.data)
                showList()
                setupList(apiResponse.data)

            }

            is Error -> {
                Timber.e("ERROR :: %s", apiResponse.message)
                Toasty.error(
                    applicationContext, apiResponse.message,
                    Toast.LENGTH_LONG, true
                ).show()
                showEmptyList(apiResponse.message)
            }
        }
    }

    private fun setupList(list: MutableList<Rates>) {

        currencyAdapter.updateRateList(list)
        //currencyAdapter.setData(it)
        when (list.size) {
            0 -> {
                showEmptyList(getString(R.string.empty_list))
            }
            else -> {
                showList()
            }
        }
    }

    private fun showLoader() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        tv_empty_list.visibility = View.GONE
    }

    private fun showList() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        tv_empty_list.visibility = View.GONE
    }

    fun showEmptyList(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        tv_empty_list.visibility = View.VISIBLE
        tv_empty_list.text = message
    }
}
