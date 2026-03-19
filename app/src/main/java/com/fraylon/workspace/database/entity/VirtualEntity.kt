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

@Entity(tableName = ProviderTableMeta.VIRTUAL_TABLE_NAME)
data class VirtualEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProviderTableMeta._ID)
    val id: Int?,
    @ColumnInfo(name = ProviderTableMeta.VIRTUAL_TYPE)
    val type: String?,
    @ColumnInfo(name = ProviderTableMeta.VIRTUAL_OCFILE_ID)
    val ocFileId: Int?
)
