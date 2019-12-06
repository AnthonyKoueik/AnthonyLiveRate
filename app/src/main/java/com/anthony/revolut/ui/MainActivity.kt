package com.anthony.revolut.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.revolut.R
import com.anthony.revolut.base.BaseActivity
import com.anthony.revolut.data.DataResource
import com.anthony.revolut.data.Status
import com.anthony.revolut.data.entity.Rates
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity<MainActivityViewModel>(), MainView {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        currencyAdapter = CurrencyAdapter(this@MainActivity,
            mutableListOf(),
            viewModel::onNewAmountInput,
            viewModel::onCurrencyChanged)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        }

        viewModel.liveData.observe(this, Observer { apiResponse ->

            bindResponse(apiResponse)
            Timber.i(apiResponse.message)
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadLatestRates()
    }


    private fun bindResponse(apiResponse: DataResource<MutableList<Rates>>) {


        when (apiResponse.status) {

            Status.LOADING -> {
                Timber.d("LOADING :: ")
                showLoader()
            }

            Status.SUCCESS -> {
                Timber.d("SUCCESS :: %s", apiResponse.data)
                showList()
                apiResponse.data?.let {
                    setupList(apiResponse.data)
                }
            }

            Status.ERROR -> {
                Timber.e("ERROR :: %s", apiResponse.message)
                Toasty.error(
                    applicationContext, apiResponse.message.toString(),
                    Toast.LENGTH_LONG, true
                ).show()
                showEmptyList(apiResponse.message.toString())
            }
        }
    }

    override fun setRateList(rateList: ArrayList<Rates>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setNewAmount(amount: Double) {
       // viewModel.ratesUseCase.
    }

    override fun setNewCurrency(currency: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupList(list: MutableList<Rates>) {

        list.let {
            currencyAdapter.updateRateList(it)
           // currencyAdapter.setData(it)
            when (it.size) {
                0 -> {
                    showEmptyList(getString(R.string.empty_list))
                }
                else -> {
                    showList()
                }
            }
        }
    }

    fun showLoader(){
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        tv_empty_list.visibility = View.GONE
    }

    fun showList(){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        tv_empty_list.visibility = View.GONE
    }

    fun showEmptyList(message: String){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        tv_empty_list.visibility = View.VISIBLE
        tv_empty_list.text = message
    }
}
