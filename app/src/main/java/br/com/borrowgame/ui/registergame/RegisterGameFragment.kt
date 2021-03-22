package br.com.borrowgame.ui.registergame

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.GamesRemoteDatasourceImpl
import br.com.borrowgame.data.repository.GameRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.game.RegisterGameUseCase
import br.com.borrowgame.ui.base.auth.BaseAuthFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RegisterGameFragment : BaseAuthFragment(), AdapterView.OnItemSelectedListener {
    override val layout = R.layout.fragment_registergame
    private lateinit var btnAddGame: Button
    private lateinit var gameName: EditText
    private lateinit var etNumberOfPlayers: EditText
    private lateinit var spinnerGameCondition: Spinner
    private lateinit var gameCondition: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        registerObserver()
    }

    private fun setupView(view: View) {
        btnAddGame = view.findViewById(R.id.btnRegisterGame)
        gameName = view.findViewById(R.id.etGameName)
        etNumberOfPlayers = view.findViewById(R.id.etNumberOfPlayers)
        gameCondition = resources.getStringArray(R.array.game_condition)[0].toString()

        spinnerGameCondition = view.findViewById(R.id.game_condition)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.game_condition,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGameCondition.adapter = adapter
            }
        }

        btnAddGame.setOnClickListener {
            registerViewModel.registerGame(gameName.text.toString(), etNumberOfPlayers.text.toString().toInt(), gameCondition)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        gameCondition = resources.getStringArray(R.array.game_condition)[0].toString()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
         parent.getItemAtPosition(pos).toString()
    }

    private fun registerObserver() {
        this.registerViewModel.newGameState.observe(viewLifecycleOwner, Observer {
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

    private val registerViewModel:RegisterGameViewModel by lazy {
        ViewModelProvider(
            this,
            RegisterGameViewModelFactory(
                RegisterGameUseCase(
                    GameRepositoryImpl(
                        GamesRemoteDatasourceImpl(
                            Firebase.firestore,
                            Firebase.auth
                        )
                    )
                )
            )
        ).get(RegisterGameViewModel::class.java)
    }
}