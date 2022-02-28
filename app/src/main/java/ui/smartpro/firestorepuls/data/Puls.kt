package ui.smartpro.firestorepuls.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Puls(
    var uid: String = "",
    var date: String = "",
    var time: String = "",
    var pressure_one: String = "",
    var pressure_two: String = "",
    var puls: String = "",
    val type: TYPE? = TYPE.HEADER
):Parcelable{
    enum class TYPE {
        HEADER,
        RESPONSE
    }
}
