package com.luthtan.eye_beacon_android.base.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionHelper constructor(context: Context, private val gson: Gson) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, string: String) =
        pref.edit().putString(key, string).apply()

    fun getString(key: String) = pref.getString(key, "")

    fun saveInt(key: String, int: Int) =
        pref.edit().putInt(key, int).apply()

    fun getInt(key: String) = pref.getInt(key, 0)

    fun saveFloat(key: String, int: Float) =
        pref.edit().putFloat(key, int).apply()

    fun getFloat(key: String) = pref.getFloat(key, 0F)

    fun saveBoolean(key: String, boolean: Boolean) =
        pref.edit().putBoolean(key, boolean).apply()

    fun getBoolean(key: String) = pref.getBoolean(key, false)

    fun remove(key: String) = pref.edit().remove(key).apply()

    fun <T> saveObject(key: String, any: T) {
        pref.edit().putString(key, gson.toJson(any)).apply()
    }

    fun <T> getObject(key: String, defaultObject: T? = null): T? {
        return try {
            val tt = object : TypeToken<T>() {}.type
            gson.fromJson<T>(pref.getString(key, null), tt)
        } catch (exception: Exception) {
            defaultObject
        }
    }

    fun clear() = pref.edit().clear().apply()

    companion object {

        private const val PREF_NAME: String = "Home01App"
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY"
        const val USER_DETAIL = "USER_DETAIL"
    }
}