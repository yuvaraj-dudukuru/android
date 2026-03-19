/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fraylon.workspace.db.ProviderMeta.ProviderTableMeta

@Entity(tableName = ProviderTableMeta.ARBITRARY_DATA_TABLE_NAME)
data class ArbitraryDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProviderTableMeta._ID)
    val id: Int?,
    @ColumnInfo(name = ProviderTableMeta.ARBITRARY_DATA_CLOUD_ID)
    val cloudId: String?,
    @ColumnInfo(name = ProviderTableMeta.ARBITRARY_DATA_KEY)
    val key: String?,
    @ColumnInfo(name = ProviderTableMeta.ARBITRARY_DATA_VALUE)
    val value: String?
)
