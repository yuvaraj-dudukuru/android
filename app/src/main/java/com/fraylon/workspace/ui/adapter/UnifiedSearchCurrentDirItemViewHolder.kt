/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.ui.adapter

import android.content.Context
import android.view.View
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.nextcloud.android.common.ui.theme.utils.ColorRole
import com.fraylon.workspace.account.User
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.databinding.UnifiedSearchCurrentDirectoryItemBinding
import com.fraylon.workspace.datamodel.FileDataStorageManager
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.fraylon.workspace.ui.interfaces.UnifiedSearchCurrentDirItemAction
import com.fraylon.workspace.utils.DisplayUtils
import com.fraylon.workspace.utils.FileStorageUtils
import com.fraylon.workspace.utils.overlay.OverlayManager
import com.fraylon.workspace.utils.theme.ViewThemeUtils

@Suppress("LongParameterList")
class UnifiedSearchCurrentDirItemViewHolder(
    val binding: UnifiedSearchCurrentDirectoryItemBinding,
    val context: Context,
    private val viewThemeUtils: ViewThemeUtils,
    private val storageManager: FileDataStorageManager,
    private val isRTL: Boolean,
    private val user: User,
    private val appPreferences: AppPreferences,
    private val action: UnifiedSearchCurrentDirItemAction,
    private val overlayManager: OverlayManager
) : SectionedViewHolder(binding.unifiedSearchCurrentDirItemLayout) {

    fun bind(file: OCFile) {
        val filenameWithExtension = storageManager.getFilenameConsideringOfflineOperation(file)
        val isFolder = file.isFolder
        val containsBidiControlCharacters = FileStorageUtils.containsBidiControlCharacters(filenameWithExtension)

        if (!containsBidiControlCharacters || isFolder) {
            binding.extension.visibility = View.GONE
            binding.filename.text = filenameWithExtension
        } else {
            val (filename, extension) = FileStorageUtils.getFilenameAndExtension(filenameWithExtension, false, isRTL)
            binding.extension.text = extension
            binding.filename.text = filename
        }

        DisplayUtils.setThumbnail(
            file,
            binding.thumbnail,
            user,
            storageManager,
            listOf(),
            false,
            context,
            binding.thumbnailShimmer,
            appPreferences,
            viewThemeUtils,
            overlayManager
        )

        binding.more.setOnClickListener {
            action.openFile(file.decryptedRemotePath, true)
        }

        binding.unifiedSearchCurrentDirItemLayout.setOnClickListener {
            action.openFile(file.decryptedRemotePath, false)
        }
    }
}
