package br.com.borrowgame.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.UserRemoteDataSourceImpl
import br.com.borrowgame.data.repository.UserRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.RegisterUserUseCase
import br.com.borrowgame.ui.base.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@ExperimentalCoroutinesApi
class SignupFragment : BaseFragment() {
    override val layout = R.layout.fragment_signup

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var etStreetName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etDistrict: EditText
    private lateinit var etState: EditText
    private lateinit var etZipCode: EditText
    private lateinit var btnRegister: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
    }

    private fun registerObserver() {
        this.signUpViewModel.newUserState.observe(viewLifecycleOwner, Observer {
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

    private fun setUpView(view: View) {
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etName = view.findViewById(R.id.etSurname)
        etSurname = view.findViewById(R.id.etSurname)
        etStreetName = view.findViewById(R.id.etStreetName)
        etNumber = view.findViewById(R.id.etNumber)
        etDistrict = view.findViewById(R.id.etDistrict)
        etState = view.findViewById(R.id.etState)
        etZipCode = view.findViewById(R.id.etZipCode)
        btnRegister = view.findViewById(R.id.btnRegister)
        setupListener()
    }

    private fun setupListener() {
        btnRegister.setOnClickListener {
            signUpViewModel.registerUser(
                name = etName.text.toString(),
                surname = etSurname.text.toString(),
                email = etEmail.text.toString(),
                streetName = etStreetName.text.toString(),
                number = etNumber.text.toString(),
                district = etDistrict.text.toString(),
                zipCode = etZipCode.text.toString(),
                state = etState.text.toString(),
                password = etPassword.text.toString()
            )
        }
    }

    private val signUpViewModel:SignUpViewModel by lazy {
        ViewModelProvider(
            this,
            SignUpViewModelFactory(
                RegisterUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(SignUpViewModel::class.java)
    }

}