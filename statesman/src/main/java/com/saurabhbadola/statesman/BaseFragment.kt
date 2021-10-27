package com.saurabhbadola.statesman

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment<T : BaseViewModel<out BaseState>> : Fragment() {

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ((requireActivity() as BaseActivity<out BaseState>).getViewModel() as T)
    }
}