package com.aeryz.seimbanginapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appDataStore by preferencesDataStore(
    name = "SeimbanginDataStore"
)
