package ui.smartpro.firestorepuls.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import ui.smartpro.firestorepuls.R

class FireBaseModule(private val context: Context) {

    val options = FirebaseOptions.Builder()
        .setApplicationId(context.getString(R.string.google_app_id)) // Required for Analytics.
        .setProjectId(context.getString(R.string.project_id)) // Required for Firebase Installations.
        .setApiKey(context.getString(R.string.google_api_key)) // Required for Auth.
        .build()

    var firebaseApp = FirebaseApp.initializeApp(context, options, "CarApp")

    var provideFirebaseFirestore = FirebaseFirestore.getInstance()

}