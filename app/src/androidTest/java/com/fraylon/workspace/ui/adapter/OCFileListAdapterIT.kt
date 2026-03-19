/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter

import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.datamodel.OCFile
import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("MagicNumber")
class OCFileListAdapterIT : AbstractIT() {
    @Test
    fun testParseMedia() {
        // empty start
        storageManager.deleteAllFiles()

        val startDate: Long = 0
        val endDate: Long = 3642752043
        assertEquals(0, storageManager.getGalleryItems(startDate, endDate).size)

        // create dummy files
        OCFile("/test.txt").apply {
            mimeType = "text/plain"
        }.let {
            storageManager.saveFile(it)
        }

        OCFile("/image.png").apply {
            mimeType = "image/png"
            modificationTimestamp = 1000000
        }.let {
            storageManager.saveFile(it)
        }

        OCFile("/image2.png").apply {
            mimeType = "image/png"
            modificationTimestamp = 1000050
        }.let {
            storageManager.saveFile(it)
        }

        OCFile("/video.mpg").apply {
            mimeType = "video/mpg"
            modificationTimestamp = 1000045
        }.let {
            storageManager.saveFile(it)
        }

        OCFile("/video2.avi").apply {
            mimeType = "video/avi"
            modificationTimestamp = endDate + 10
        }.let {
            storageManager.saveFile(it)
        }

        // list of remoteFiles
        assertEquals(5, storageManager.allFiles.size)
        assertEquals(3, storageManager.getGalleryItems(startDate, endDate).size)
        assertEquals(4, storageManager.allGalleryItems.size)
    }
}
