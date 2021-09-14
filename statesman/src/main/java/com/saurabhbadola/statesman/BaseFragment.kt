package com.saurabhbadola.statesman

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment<T : BaseViewModel<out BaseState>?> : Fragment() {

    private var viewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ((requireActivity() as BaseActivity<out BaseState>).getViewModel() as T)
    }
}