package com.found404.core.models

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREFERENCES_NAME = "MyAppPreferences"

    private const val KEY_MERCHANT_NAME = "merchantName"
    private const val KEY_MERCHANT_CITY = "merchantCity"
    private const val KEY_STREET_NAME = "streetName"
    private const val KEY_STREET_NUMBER = "streetNumber"
    private const val KEY_POSTAL_CODE = "postalCode"
    private const val KEY_ACCEPTED_CARDS = "acceptedCards"

    fun saveMerchantName(context: Context, merchantName: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_MERCHANT_NAME, merchantName)
        editor.apply()
    }

    fun getMerchantName(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_MERCHANT_NAME, null)
    }

    fun saveMerchantAddress(context: Context, merchantCity: String, streetName: String, streetNumber: String, postalCode: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_MERCHANT_CITY, merchantCity)
        editor.putString(KEY_STREET_NAME, streetName)
        editor.putString(KEY_STREET_NUMBER, streetNumber)
        editor.putInt(KEY_POSTAL_CODE, postalCode)
        editor.apply()
    }

    fun getMerchantCity(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_MERCHANT_CITY, null)
    }

    fun getMerchantStreetName(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_STREET_NAME, null)
    }

    fun getMerchantStreetNumber(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_STREET_NUMBER, null)
    }

    fun getMerchantPostCode(context: Context): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_POSTAL_CODE, 0)
    }

    fun saveAcceptedCards(context: Context, acceptedCards: Set<String>) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putStringSet(KEY_ACCEPTED_CARDS, acceptedCards)
        editor.apply()
    }

    fun getAcceptedCards(context: Context): Set<String>? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(KEY_ACCEPTED_CARDS, null)
    }
    fun clearAll(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveMerchantStreetName(context: Context, streetName: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_STREET_NAME, streetName)
        editor.apply()
    }

    fun saveMerchantStreetNumber(context: Context, streetNumber: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_STREET_NUMBER, streetNumber)
        editor.apply()
    }

    fun saveMerchantCity(context: Context, city: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_MERCHANT_CITY, city)
        editor.apply()
    }

    fun saveMerchantPostCode(context: Context, postalCode: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_POSTAL_CODE, postalCode)
        editor.apply()
    }

}
