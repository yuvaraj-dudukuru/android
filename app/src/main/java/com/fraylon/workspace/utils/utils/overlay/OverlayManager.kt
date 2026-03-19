/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2026 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.utils.overlay

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.elyeproj.loaderviewlibrary.LoaderImageView
import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.di.Injectable
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.datamodel.SyncedFolderObserver
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.fraylon.workspace.utils.DisplayUtils
import com.fraylon.workspace.utils.MimeTypeUtil
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import javax.inject.Inject

class OverlayManager @Inject constructor(
    private val syncedFolderProvider: SyncedFolderProvider,
    private val preferences: AppPreferences,
    private val viewThemeUtils: ViewThemeUtils,
    private val context: Context,
    private val accountManager: UserAccountManager
) : Injectable {

    fun setFolderOverlayIcon(folder: OCFile?, imageView: ImageView) {
        val overlayIconId = folder
            ?.takeIf { it.isFolder }
            ?.let { currentFolder ->
                val isAutoUploadFolder = SyncedFolderObserver.isAutoUploadFolder(
                    currentFolder,
                    accountManager.user
                )
                currentFolder.getFileOverlayIconId(isAutoUploadFolder)
            }

        if (overlayIconId == null) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            imageView.setImageDrawable(ContextCompat.getDrawable(context, overlayIconId))
        }
    }

    fun setFolderThumbnail(folder: OCFile?, imageView: ImageView, loaderImageView: LoaderImageView?) {
        if (folder == null || !folder.isFolder) return

        DisplayUtils.stopShimmer(loaderImageView, imageView)

        val isAutoUploadFolder =
            SyncedFolderObserver.isAutoUploadFolder(folder, accountManager.user)
        val isDarkModeActive = preferences.isDarkModeEnabled()

        val overlayIconId = folder.getFileOverlayIconId(isAutoUploadFolder)
        val icon = MimeTypeUtil.getFolderIcon(isDarkModeActive, overlayIconId, context, viewThemeUtils)
        imageView.setImageDrawable(icon)
    }
}
