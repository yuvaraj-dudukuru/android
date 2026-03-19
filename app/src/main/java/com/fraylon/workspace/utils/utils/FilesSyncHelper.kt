/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Jonas Mayer <jonas.mayer@nextcloud.com>
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-FileCopyrightText: 2017 Mario Danic <mario@lovelyhq.com>
 * SPDX-FileCopyrightText: 2017 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.utils

import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.device.PowerManagementService
import com.fraylon.workspace.jobs.BackgroundJobManager
import com.fraylon.workspace.jobs.upload.FileUploadHelper.Companion.instance
import com.fraylon.workspace.network.ConnectivityService
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.fraylon.workspace.datamodel.UploadsStorageManager
import com.owncloud.android.lib.common.utils.Log_OC

object FilesSyncHelper {
    private const val TAG: String = "FileSyncHelper"
    const val GLOBAL: String = "global"

    @JvmStatic
    fun restartUploadsIfNeeded(
        uploadsStorageManager: UploadsStorageManager,
        accountManager: UserAccountManager,
        connectivityService: ConnectivityService,
        powerManagementService: PowerManagementService
    ) {
        Log_OC.d(TAG, "restartUploadsIfNeeded, called")
        instance().retryFailedUploads(
            uploadsStorageManager,
            connectivityService,
            accountManager,
            powerManagementService
        )
    }

    @JvmStatic
    fun startAutoUploadForEnabledSyncedFolders(
        provider: SyncedFolderProvider,
        manager: BackgroundJobManager,
        overridePowerSaving: Boolean
    ) {
        Log_OC.d(TAG, "start auto upload worker for each enabled folder")

        provider.syncedFolders.forEach {
            if (it.isEnabled) {
                manager.startAutoUpload(it, overridePowerSaving)
            }
        }
    }
}
