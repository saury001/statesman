package com.saurabhbadola.statesman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<T : BaseState> : AppCompatActivity() {

    private val TAG = "LoginViewModel.TAG"

    private lateinit var baseViewModel: BaseViewModel<T>
    abstract fun createViewModel(): BaseViewModel<T>
    abstract fun onStateChanged(newState: T, oldState: T)
    abstract fun onNavigationRouteChange(
        newRoute: NavigationRoute,
        oldRoute: NavigationRoute
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        baseViewModel = createViewModel()
        super.onCreate(savedInstanceState)

        baseViewModel.observableState.observe(this, { newState ->
            onStateChanged(newState, baseViewModel.oldState)
        })

        baseViewModel.observableRoute.observe(this, { newRoute ->
            onNavigationRouteChange(newRoute, baseViewModel.oldRoute)
        })
        baseViewModel.createInitialState()
    }

    fun getViewModel(): BaseViewModel<T> {
        return baseViewModel
    }

    fun showLoading(loadingText: String) {

    }

    private fun dismissLoading() {

    }

    override fun onDestroy() {
        dismissLoading()
        super.onDestroy()
    }


}