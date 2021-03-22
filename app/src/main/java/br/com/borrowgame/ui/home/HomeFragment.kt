package br.com.borrowgame.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.UserRemoteDataSourceImpl
import br.com.borrowgame.data.repository.UserRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.LogoutUserUseCase
import br.com.borrowgame.ui.base.auth.BaseAuthFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home
    private lateinit var btnMyGames: Button
    private lateinit var btnLogout: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerBackPressedAction()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
    }

    private fun setUpView(view: View) {
        btnMyGames = view.findViewById(R.id.btnMyGames)
        btnLogout = view.findViewById(R.id.logout)

        btnMyGames.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerGameFragment)
        }

        btnLogout.setOnClickListener {
            this.homeViewModel.logoutUser()
        }
    }

    private fun registerObserver() {
        this.homeViewModel.logoutUserState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.main_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> {
                    showLoading(R.string.loading_message_processing.toString())
                }
            }
        })
    }

    private val homeViewModel:HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                LogoutUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(HomeViewModel::class.java)
    }

    private fun registerBackPressedAction() {
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}