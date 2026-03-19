/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Tobias Kaminsky <tobias.kaminsky@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fraylon.workspace.R
import com.fraylon.workspace.databinding.InternalTwoWaySyncViewHolderBinding
import com.fraylon.workspace.datamodel.FileDataStorageManager
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.utils.DisplayUtils

class InternalTwoWaySyncViewHolder(val binding: InternalTwoWaySyncViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        folder: OCFile,
        context: Context,
        dataStorageManager: FileDataStorageManager,
        internalTwoWaySyncAdapter: InternalTwoWaySyncAdapter
    ) {
        binding.run {
            size.text = DisplayUtils.bytesToHumanReadable(folder.fileLength)
            name.text = folder.decryptedFileName

            if (folder.internalFolderSyncResult.isEmpty()) {
                syncResult.visibility = View.GONE
                syncResultDivider.visibility = View.GONE
            } else {
                syncResult.visibility = View.VISIBLE
                syncResultDivider.visibility = View.VISIBLE
                syncResult.text = folder.internalFolderSyncResult
            }

            if (folder.internalFolderSyncTimestamp == 0L) {
                syncTimestamp.text = context.getString(R.string.internal_two_way_sync_not_yet)
            } else {
                syncTimestamp.text = DisplayUtils.getRelativeTimestamp(
                    context,
                    folder.internalFolderSyncTimestamp
                )
            }

            unset.setOnClickListener {
                folder.internalFolderSyncTimestamp = -1L
                dataStorageManager.saveFile(folder)
                internalTwoWaySyncAdapter.update()
            }
        }
    }
}
