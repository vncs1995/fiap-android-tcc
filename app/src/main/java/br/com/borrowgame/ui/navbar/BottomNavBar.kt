package br.com.borrowgame.ui.navbar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.com.borrowgame.R
import br.com.borrowgame.ui.base.auth.BaseAuthFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BottomNavBar : BaseAuthFragment() {
    override val layout = R.layout.fragment_bottom_nav_bar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerBackPressedAction()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view)

        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.bottom_fragment) as? NavHostFragment

        val navController = nestedNavHostFragment?.navController

        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        }
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