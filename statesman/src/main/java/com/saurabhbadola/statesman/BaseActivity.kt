package com.saurabhbadola.statesman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<T : BaseState> : AppCompatActivity() {

    private val TAG = "LoginViewModel.TAG"

    private var baseViewModel: BaseViewModel<T>? = null
    abstract fun createViewModel(): BaseViewModel<T>
    abstract fun onStateChanged(newState: T)
    abstract fun onNavigationRouteChange(
        navigationRoute: NavigationRoute,
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        baseViewModel = createViewModel()
        super.onCreate(savedInstanceState)

        baseViewModel!!.state.observe(this, { newState ->
            onStateChanged(newState)
        })
        baseViewModel!!.route.observe(this, { newRoute ->
            onNavigationRouteChange(newRoute)
        })
        baseViewModel!!.createInitialState()
    }

    fun getViewModel(): BaseViewModel<T> {
        return baseViewModel!!
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