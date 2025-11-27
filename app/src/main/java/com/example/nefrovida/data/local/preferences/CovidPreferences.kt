package com.example.nefrovida.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CovidPreferences
@Inject
constructor(
    @ApplicationContext context: Context,
    private val gson: Gson,
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            PreferencesConstants.PREF_NAME,
            Context.MODE_PRIVATE,
        )

    fun saveLastCountry(
        country: String,
    ) {
        prefs
            .edit()
            .putString(PreferencesConstants.COUNTRY, country)
            .putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis())
            .apply()
    }

    fun getCountryCache(): CovidCache? {
        val country = prefs.getString(PreferencesConstants.COUNTRY, "canada")

        if (country == null) return null


        return CovidCache(
            country = country
        )
    }

    fun isCacheValid(): Boolean {
        val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
        return System.currentTimeMillis() - lastUpdate < PreferencesConstants.CACHE_DURATION
    }

    fun clearCache() {
        prefs.edit().clear().apply()
    }
}

data class CovidCache(
    val country: String
)