package com.saurabhbadola.statesman.examples.sample

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.saurabhbadola.statesman.BaseActivity
import com.saurabhbadola.statesman.BaseViewModel
import com.saurabhbadola.statesman.NavigationRoute
import com.saurabhbadola.statesman.examples.R
import com.saurabhbadola.statesman.examples.databinding.ActivitySampleBinding

class SampleActivity : BaseActivity<SampleState>() {

    companion object {
        const val TAG = "SampleActivity.TAG"
    }

    private lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivitySampleBinding>(this, R.layout.activity_sample)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun createViewModel(): BaseViewModel<SampleState> {
        viewModel = ViewModelProvider(this).get(SampleViewModel::class.java) //Creating instance of our viewModel
        return viewModel
    }



    override fun onStateChanged(newState: SampleState, oldState: SampleState) { //This method is called whenever the state is changed.
        Log.d(
            TAG,
            "onStateChanged: New State: {${newState.mSampleFieldA}, ${newState.mSampleFieldB}}" +
                    "\n" +
                    "Old State: {${oldState.mSampleFieldA}, ${oldState.mSampleFieldB}}"
        )
    }

    override fun onNavigationRouteChange(newRoute: NavigationRoute, oldRoute: NavigationRoute) { //This method is called whenever the route is changed
        Log.d(
            TAG,
            "onNavigationRouteChange: New Route: {${newRoute.routeName}, ${newRoute.routeValue}}" +
                    "\n" +
                    "Old Route: {${oldRoute.routeName}, ${oldRoute.routeValue}}"
        )
    }
}