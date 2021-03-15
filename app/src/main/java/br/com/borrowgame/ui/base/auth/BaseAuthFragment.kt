package br.com.borrowgame.ui.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.UserRemoteDataSourceImpl
import br.com.borrowgame.data.repository.UserRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.GetUserLoggedUseCase
import br.com.borrowgame.ui.base.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NAVIGATION_KEY = "NAV_KEY"
@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {
    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
            this,

            BaseViewModelFactory(GetUserLoggedUseCase(UserRepositoryImpl(UserRemoteDataSourceImpl(Firebase.auth))))
        ).get(BaseAuthViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerObserver()
        baseAuthViewModel.getUserLogged()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, Observer {
                result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                }
                is RequestState.Error -> {
                    hideLoading()
                    findNavController().navigate(
                        R.id.login_graph, bundleOf(
                            NAVIGATION_KEY to
                                    findNavController().currentDestination?.id
                        )
                    )
                }
            }
        })
    }
}