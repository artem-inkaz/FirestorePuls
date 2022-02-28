package ui.smartpro.firestorepuls.utils

import androidx.recyclerview.widget.DiffUtil
import ui.smartpro.firestorepuls.data.Puls

object DiffUtilCallBack : DiffUtil.ItemCallback<Puls>() {
    override fun areItemsTheSame(oldItem: Puls, newItem: Puls): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Puls, newItem: Puls): Boolean {
        return oldItem.uid == newItem.uid
                && oldItem.time == newItem.time
                && oldItem.date == newItem.date
    }
}