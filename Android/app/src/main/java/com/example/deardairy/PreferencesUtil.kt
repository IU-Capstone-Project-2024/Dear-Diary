package com.example.deardairy.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFERENCES_FILE_KEY = "com.example.deardairy.PREFERENCE_FILE_KEY"
    private const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
    private const val GOALS_SELECTED_KEY = "goals_selected"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        return getPreferences(context).getBoolean(ONBOARDING_COMPLETED_KEY, false)
    }

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        getPreferences(context).edit().putBoolean(ONBOARDING_COMPLETED_KEY, completed).apply()
    }

    fun isGoalsSelected(context: Context): Boolean {
        return getPreferences(context).getBoolean(GOALS_SELECTED_KEY, false)
    }

    fun setGoalsSelected(context: Context, selected: Boolean) {
        getPreferences(context).edit().putBoolean(GOALS_SELECTED_KEY, selected).apply()
    }
}
