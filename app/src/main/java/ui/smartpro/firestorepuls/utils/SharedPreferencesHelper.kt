package ui.smartpro.firestorepuls.utils

import android.content.Context

private const val APP_PREFERENCES = "prefs"

class SharedPreferencesHelper(ctx: Context) {
    private val prefs = ctx.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private const val FIRST_OPEN_KEY = "FIRST_OPEN"
        private const val CURRENT_DAY_KEY = "CURRENT_DAY"
    }

    var currentDate: String? = null
        set(value) {
            field = value
            prefs.edit().putString(CURRENT_DAY_KEY, value).apply()
        }
        get() = prefs.getString(CURRENT_DAY_KEY, null)

    var isFirstOpen = true
        set(value) {
            field = value
            prefs.edit().putBoolean(FIRST_OPEN_KEY, value).apply()
        }
        get() = prefs.getBoolean(FIRST_OPEN_KEY, true)
}