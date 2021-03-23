package br.com.borrowgame.ui.settings

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
class SettingsFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_settings
    private lateinit var btnLogout: Button
    private lateinit var btnAbout: Button

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
        btnLogout = view.findViewById(R.id.logout)
        btnAbout = view.findViewById(R.id.about)

        btnLogout.setOnClickListener {
            this.settingsViewModel.logoutUser()
        }
        btnAbout.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }

    private fun registerObserver() {
        this.settingsViewModel.logoutUserState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.action_settingsFragment_to_login_graph)
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

    private val settingsViewModel: SettingsViewModel by lazy {
        ViewModelProvider(
            this,
            SettingsViewModelFactory(
                LogoutUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(SettingsViewModel::class.java)
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