package com.aeryz.seimbanginapp.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aeryz.seimbanginapp.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    suspend fun saveUserToken(token: String)
    fun getUserTokenFlow(): Flow<String>
    suspend fun saveTokenExpires(expiresTime: Long)
    fun getTokenExpires(): Flow<Long>
    suspend fun deleteTokenExpires()
    suspend fun getUserToken(): String
    suspend fun deleteToken()
    suspend fun getUserDarkModePref(): Boolean
    fun getUserDarkModePrefFlow(): Flow<Boolean>
    suspend fun setUserDarkModePref(isUsingDarkMode: Boolean)
    suspend fun setShouldShowIntroPage(isFirstTime: Boolean)
    fun getShouldShowIntroPage(): Flow<Boolean>
    suspend fun saveAdvise(advice: String)
    fun getAdvise(): Flow<String>
    suspend fun deleteAdvise()
}

class UserPreferenceDataSourceImpl(private val preferenceHelper: PreferenceDataStoreHelper) :
    UserPreferenceDataSource {

    override suspend fun saveUserToken(token: String) {
        return preferenceHelper.putPreference(PREF_USER_TOKEN, token)
    }

    override suspend fun saveTokenExpires(expiresTime: Long) {
        return preferenceHelper.putPreference(PREF_TOKEN_EXPIRES, expiresTime)
    }

    override fun getTokenExpires(): Flow<Long> {
        return preferenceHelper.getPreference(PREF_TOKEN_EXPIRES, 0)
    }

    override suspend fun deleteTokenExpires() {
        return preferenceHelper.removePreference(PREF_TOKEN_EXPIRES)
    }

    override fun getUserTokenFlow(): Flow<String> {
        return preferenceHelper.getPreference(PREF_USER_TOKEN, "")
    }

    override suspend fun getUserToken(): String {
        return preferenceHelper.getFirstPreference(PREF_USER_TOKEN, "")
    }

    override suspend fun deleteToken() {
        return preferenceHelper.removePreference(PREF_USER_TOKEN)
    }

    override suspend fun setShouldShowIntroPage(isFirstTime: Boolean) {
        return preferenceHelper.putPreference(PREF_SHOW_INTRO_PAGE, isFirstTime)
    }

    override fun getShouldShowIntroPage(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_SHOW_INTRO_PAGE, true)
    }

    override suspend fun saveAdvise(advice: String) {
        return preferenceHelper.putPreference(PREF_USER_ADVISE, advice)
    }

    override fun getAdvise(): Flow<String> {
        return preferenceHelper.getPreference(PREF_USER_ADVISE, "")
    }

    override suspend fun deleteAdvise() {
        return preferenceHelper.removePreference(PREF_USER_ADVISE)
    }

    override suspend fun getUserDarkModePref(): Boolean {
        return preferenceHelper.getFirstPreference(PREF_USER_DARK_MODE, false)
    }

    override fun getUserDarkModePrefFlow(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_USER_DARK_MODE, false)
    }

    override suspend fun setUserDarkModePref(isUsingDarkMode: Boolean) {
        return preferenceHelper.putPreference(PREF_USER_DARK_MODE, isUsingDarkMode)
    }

    companion object {
        val PREF_USER_TOKEN = stringPreferencesKey("PREF_USER_TOKEN")
        val PREF_TOKEN_EXPIRES = longPreferencesKey("PREF_TOKEN_EXPIRES")
        val PREF_SHOW_INTRO_PAGE = booleanPreferencesKey("PREF_SHOW_INTRO_PAGE")
        val PREF_USER_DARK_MODE = booleanPreferencesKey("PREF_USER_DARK_MODE")
        val PREF_USER_ADVISE = stringPreferencesKey("PREF_USER_ADVISE")
    }
}
