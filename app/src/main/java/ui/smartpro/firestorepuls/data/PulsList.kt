package ui.smartpro.firestorepuls.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PulsList(
    val date: String? = null,
    var dailyItem: List<Puls>? = null
):Parcelable