/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.database.typeConverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.fraylon.model.OfflineOperationType
import com.google.gson.GsonBuilder
import com.fraylon.workspace.database.typeAdapter.OfflineOperationTypeAdapter

@ProvidedTypeConverter
class OfflineOperationTypeConverter {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(OfflineOperationType::class.java, OfflineOperationTypeAdapter())
        .create()

    @TypeConverter
    fun fromOfflineOperationType(type: OfflineOperationType?): String? = gson.toJson(type)

    @TypeConverter
    fun toOfflineOperationType(type: String?): OfflineOperationType? =
        gson.fromJson(type, OfflineOperationType::class.java)
}
