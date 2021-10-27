package com.saurabhbadola.statesman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BaseState> : AppCompatActivity() {

    companion object {
        const val TAG = "LoginViewModel.TAG"
    }

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
            val oldState = baseViewModel.oldState
            if (!oldState.compareTo(newState)) {
                onStateChanged(newState, oldState)
                baseViewModel.currentState = newState.getCopy() as T
                baseViewModel.oldState = baseViewModel.currentState
            }
        })

        baseViewModel.observableRoute.observe(this, { newRoute ->
            val oldRoute = baseViewModel.oldRoute
            if (oldRoute.routeValue != newRoute.routeValue || oldRoute.routeName != newRoute.routeName) {
                onNavigationRouteChange(newRoute, baseViewModel.oldRoute)
                baseViewModel.oldRoute = newRoute
            }
        })
        baseViewModel.createInitialState()
    }

    fun getViewModel(): BaseViewModel<T> {
        return baseViewModel
    }

    fun showLoading(loadingText: String) {

    }

    private fun dismissLoading() {
0
    }

    override fun onDestroy() {
        dismissLoading()
        super.onDestroy()
    }


}