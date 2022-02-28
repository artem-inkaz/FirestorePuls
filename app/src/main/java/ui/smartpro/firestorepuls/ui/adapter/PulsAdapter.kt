package ui.smartpro.firestorepuls.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.firestorepuls.R
import ui.smartpro.firestorepuls.base.BaseViewHolder
import ui.smartpro.firestorepuls.data.Puls
import ui.smartpro.firestorepuls.utils.DiffUtilCallBack

class PulsAdapter: ListAdapter<Puls,BaseViewHolder>(DiffUtilCallBack) {

    private var pulsList: MutableList<Puls> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> PulsHeaderViewHolder(
                inflater.inflate(R.layout.header, parent, false) as View
            )
            else -> PulsResponse(
                inflater.inflate(
                    R.layout.item_day, parent,
                    false
                ) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(pulsList[position])
    }

    override fun getItemCount(): Int = pulsList.size

    override fun getItemViewType(position: Int): Int {
        return when {
            pulsList[position].type == Puls.TYPE.HEADER -> HEADER
            else -> RESPONSE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPuls(puls: MutableList<Puls>) {
        pulsList.addAll(puls)
        notifyDataSetChanged()
    }

    fun clearPuls() {
        pulsList.clear()
    }

    inner class PulsHeaderViewHolder(view: View) :
        BaseViewHolder(view) {
        override fun bind(pulsData: Puls) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.header_date).text = pulsData.date

            }
        }
    }

    inner class PulsResponse(view: View) :
        BaseViewHolder(view) {

        override fun bind(pulsData: Puls) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.time_day).text = pulsData.time
                itemView.findViewById<TextView>(R.id.pressure).text = "${pulsData.pressure_one}  /  ${pulsData.pressure_two}"
                itemView.findViewById<TextView>(R.id.pulse).text = pulsData.puls
            }
        }
    }

    companion object {
        private const val HEADER = 0
        private const val RESPONSE = 1
    }
}