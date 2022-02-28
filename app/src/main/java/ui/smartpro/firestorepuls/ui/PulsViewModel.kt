package ui.smartpro.firestorepuls.ui

import android.app.Application
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.smartpro.firestorepuls.base.BaseViewModel
import ui.smartpro.firestorepuls.data.Puls
import ui.smartpro.firestorepuls.data.PulsList
import ui.smartpro.firestorepuls.ui.adapter.PulsAdapter
import ui.smartpro.firestorepuls.utils.FirebaseConstants
import ui.smartpro.firestorepuls.utils.asCustomDate

class PulsViewModel(
    app: Application,
    private var fireStore: FirebaseFirestore
) : BaseViewModel(app) {

    val pulsFlow: MutableStateFlow<List<Puls>?> = MutableStateFlow(null)

    fun savePulsToDb(
        id: Long,
        date: String,
        time: String,
        pressureOne: String,
        pressureTwo: String,
        puls: String
    ) {
        modelScope.launch {
            val item = Puls(
                uid = id.toString(),
                date = date,
                time = time,
                pressure_one = pressureOne,
                pressure_two = pressureTwo,
                puls = puls,
                type = Puls.TYPE.RESPONSE
            )

            val collection = fireStore.collection(FirebaseConstants.Puls)
            val document = collection.document(item.date)
            document
                .update(FirebaseConstants.DAILY_ITEM, FieldValue.arrayUnion(item))
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
        }
    }

    fun getPulsFromDb(date: String, adapter: PulsAdapter) {
        modelScope.launch {
            fireStore.collection(FirebaseConstants.Puls).document(date)
                .get()
                .addOnSuccessListener { link ->
                    val pulsList = link.toObject<PulsList>()
                    if (pulsList?.dailyItem !== null) {
                        pulsFlow.value = pulsList.dailyItem!!
                        adapter.setPuls(pulsList.dailyItem as MutableList<Puls>)
                    }

                }
                .addOnFailureListener {
                    pulsFlow.value = emptyList()
                }
        }
    }

    fun createNode(data: String) {
        modelScope.launch {
            val org = PulsList(
                date = asCustomDate(),
                dailyItem = listOf<Puls>(
                    Puls(
                        uid = "0L",
                        date = asCustomDate(),
                        time = "time",
                        pressure_one = "pressureOne",
                        pressure_two = "pressureTwo",
                        puls = "puls",
                        type = Puls.TYPE.HEADER
                    )
                )
            )
            val collection = fireStore.collection(FirebaseConstants.Puls)
            val document = collection.document(data)
            document
                .set(org)
                .addOnSuccessListener { }
                .addOnFailureListener { }
        }
    }

    fun getAllItems(adapter: PulsAdapter) {
        fireStore.collection(FirebaseConstants.Puls)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pulsList = document.toObject<PulsList>()
                    pulsFlow.value = pulsList.dailyItem as MutableList<Puls>
                    adapter.setPuls(pulsList.dailyItem as MutableList<Puls>)
                    adapter.submitList(pulsList.dailyItem as MutableList<Puls>)
                }
            }
            .addOnFailureListener {}
    }
}