/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.utils.extensions

import com.fraylon.workspace.MainApp
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.datamodel.OCFileDepth
import com.fraylon.workspace.datamodel.OCFileDepth.DeepLevel
import com.fraylon.workspace.datamodel.OCFileDepth.FirstLevel
import com.fraylon.workspace.datamodel.OCFileDepth.Root
import com.fraylon.workspace.utils.FileStorageUtils

fun List<OCFile>.filterFilenames(): List<OCFile> = distinctBy { it.fileName }

fun OCFile.isTempFile(): Boolean {
    val context = MainApp.getAppContext()
    val appTempPath = FileStorageUtils.getAppTempDirectoryPath(context)
    return storagePath?.startsWith(appTempPath) == true
}

fun OCFile.mediaSize(defaultThumbnailSize: Float): Pair<Int, Int> {
    val width = (imageDimension?.width?.toInt() ?: defaultThumbnailSize.toInt())
    val height = (imageDimension?.height?.toInt() ?: defaultThumbnailSize.toInt())
    return width to height
}

fun OCFile?.isPNG(): Boolean {
    if (this == null) {
        return false
    }
    return "image/png".equals(mimeType, ignoreCase = true)
}

@Suppress("ReturnCount")
fun OCFile?.getDepth(): OCFileDepth? {
    if (this == null) {
        return null
    }

    // Check if it's the root directory
    if (this.isRootDirectory) {
        return Root
    }

    // If parent is root ("/"), this is a direct child of root
    val parentPath = this.parentRemotePath ?: return null
    if (parentPath == OCFile.ROOT_PATH) {
        return FirstLevel
    }

    // Otherwise, it's a subdirectory of a subdirectory
    return DeepLevel
}
