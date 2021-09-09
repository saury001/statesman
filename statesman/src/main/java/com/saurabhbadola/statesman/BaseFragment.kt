package com.saurabhbadola.statesman

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment<T : BaseViewModel<out BaseState>?> : Fragment() {
    protected var viewModel: T? = null


    override fun onAttach(context: Context) {
        Log.d("LoginViewModel.TAG", "onAttach: ")
        super.onAttach(context)

//        viewModel = (requireActivity() as BaseActivity<out BaseState>).getViewModel() as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as BaseActivity<out BaseState>).getViewModel() as T
        Log.d("LoginViewModel.TAG", "onCreate: ")
    }
}