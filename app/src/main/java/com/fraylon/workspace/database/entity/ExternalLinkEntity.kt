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

@Entity(tableName = ProviderTableMeta.EXTERNAL_LINKS_TABLE_NAME)
data class ExternalLinkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProviderTableMeta._ID)
    val id: Int?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_ICON_URL)
    val iconUrl: String?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_LANGUAGE)
    val language: String?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_TYPE)
    val type: Int?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_NAME)
    val name: String?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_URL)
    val url: String?,
    @ColumnInfo(name = ProviderTableMeta.EXTERNAL_LINKS_REDIRECT)
    val redirect: Int?
)
