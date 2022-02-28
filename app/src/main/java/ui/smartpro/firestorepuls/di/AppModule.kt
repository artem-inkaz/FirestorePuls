package ui.smartpro.firestorepuls.di

import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ui.smartpro.firestorepuls.ui.PulsViewModel
import ui.smartpro.firestorepuls.utils.SharedPreferencesHelper

val appModule = module {

    //FireBase
    single { FirebaseApp.initializeApp(androidApplication()) }
    factory { FirebaseDatabase.getInstance().reference }
    single { FirebaseFirestore.getInstance() }
    single { FireBaseModule(androidApplication()) }

    //utils
    single { SharedPreferencesHelper(androidApplication()) }

    //vm
   viewModel { PulsViewModel(androidApplication(), get()) }
}