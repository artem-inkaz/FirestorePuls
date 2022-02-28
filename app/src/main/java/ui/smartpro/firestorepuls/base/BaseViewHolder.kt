package ui.smartpro.firestorepuls.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.firestorepuls.data.Puls

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(puls: Puls)


}