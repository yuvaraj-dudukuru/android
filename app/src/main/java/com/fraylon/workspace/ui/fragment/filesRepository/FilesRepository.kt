/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.ui.fragment.filesRepository

import com.fraylon.workspace.datamodel.FileDataStorageManager
import com.fraylon.workspace.datamodel.OCFile

interface FilesRepository {

    /**
     * Fetches a list of recommended files from the Fraylon server.
     *
     * This function runs on the IO dispatcher and retrieves recommendations
     * using the Fraylon client. The results are passed to the provided callback on the main thread.
     *
     */
    suspend fun fetchRecommendedFiles(
        accountName: String,
        ignoreETag: Boolean,
        storageManager: FileDataStorageManager
    ): ArrayList<OCFile>

    fun createRichWorkspace(remotePath: String, onCompleted: (String) -> Unit, onError: () -> Unit)
}
