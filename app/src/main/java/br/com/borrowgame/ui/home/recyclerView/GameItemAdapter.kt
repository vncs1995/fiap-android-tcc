package br.com.borrowgame.ui.home.recyclerView

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.borrowgame.R
import br.com.borrowgame.domain.entity.Game

class GameItemAdapter(private val gamesList: List<Game>) : RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder>() {

    class GameItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameName: TextView = itemView.findViewById(R.id.game_name)
        val gameCondition: TextView = itemView.findViewById(R.id.game_condition)
        val numberOfPlayers: TextView = itemView.findViewById(R.id.game_number_of_players)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item_view, parent, false)
        return GameItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
        val currentItem = gamesList[position]

        holder.gameName.text = currentItem.name
        holder.gameCondition.text = currentItem.gameCondition
        holder.numberOfPlayers.text = currentItem.numberOfPlayers.toString() + " "
    }

    override fun getItemCount() = gamesList.size

}