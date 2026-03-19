/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.datamodel

import com.fraylon.utils.OCFileUtils

data class GalleryRow(val files: List<OCFile>, val defaultHeight: Int, val defaultWidth: Int) {
    fun getMaxHeight(): Float = files.maxOfOrNull {
        OCFileUtils.getImageSize(it, defaultHeight.toFloat()).second.toFloat()
    } ?: 0f
}
