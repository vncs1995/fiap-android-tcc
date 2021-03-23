package br.com.borrowgame.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.UserRemoteDataSourceImpl
import br.com.borrowgame.data.repository.UserRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.LoginUseCase
import br.com.borrowgame.ui.base.BaseFragment
import br.com.borrowgame.ui.base.auth.NAVIGATION_KEY
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment() {
    override val layout = R.layout.fragment_login
    private lateinit var btnLogin: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBackPressedAction()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        registerObserver()
        registerBackPressedAction()
    }

    private fun registerObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> showLoading(R.string.loading_message_processing.toString())
            }
        })
    }

    private fun showError(throwable: Throwable) {
        hideLoading()
        email.error = null
        password.error = null
        showMessage(throwable.message)
    }

    private fun showSuccess() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdForArguments == null) {
            findNavController().navigate(R.id.bottomNavBar2)
        } else {
            findNavController().popBackStack(navIdForArguments, false)
        }
    }

    private fun setUpView(view: View) {
        btnLogin = view.findViewById(R.id.btnLogin)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)
        btnRegister = view.findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            loginViewModel.doLogin(email.text.toString(), password.text.toString())
        }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this, LoginViewModelFactory(
                LoginUseCase(
                    UserRepositoryImpl(
                        UserRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(LoginViewModel::class.java)
    }

}