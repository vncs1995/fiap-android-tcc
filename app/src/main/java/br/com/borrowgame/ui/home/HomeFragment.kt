package br.com.borrowgame.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.borrowgame.R
import br.com.borrowgame.data.remote.datasource.GamesRemoteDatasourceImpl
import br.com.borrowgame.data.repository.GameRepositoryImpl
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.user.GetGamesUseCase
import br.com.borrowgame.ui.base.auth.BaseAuthFragment
import br.com.borrowgame.ui.home.recyclerView.GameItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home
    private lateinit var btnRegisterGame: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerBackPressedAction()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
        this.homeViewModel.getGames()
    }

    private fun setUpView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_games)
    }

    private fun registerObserver() {
        this.homeViewModel.gamesState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    recyclerView.adapter = GameItemAdapter(it.data)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.setHasFixedSize(true)
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
                GetGamesUseCase(
                    GameRepositoryImpl(
                        GamesRemoteDatasourceImpl(
                            Firebase.firestore,
                            Firebase.auth
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