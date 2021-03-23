package br.com.borrowgame.ui.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import br.com.borrowgame.BuildConfig
import br.com.borrowgame.R
import br.com.borrowgame.ui.base.BaseFragment

class AboutFragment : BaseFragment() {
    override val layout = R.layout.fragment_about
    private lateinit var txtAppVersion: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        txtAppVersion = view.findViewById(R.id.app_version)

        txtAppVersion.text = BuildConfig.VERSION_NAME;
    }
}