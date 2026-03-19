/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2026 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.jobs.download

import android.content.Context
import android.content.Intent
import com.fraylon.workspace.account.User
import com.fraylon.workspace.authentication.AuthenticatorActivity
import com.fraylon.workspace.operations.DownloadFileOperation
import com.fraylon.workspace.ui.activity.FileActivity
import com.fraylon.workspace.ui.activity.FileDisplayActivity
import com.fraylon.workspace.ui.preview.PreviewImageActivity
import com.fraylon.workspace.ui.preview.PreviewImageFragment

class FileDownloadIntents(private val context: Context) {

    fun credentialContentIntent(user: User): Intent = Intent(context, AuthenticatorActivity::class.java).apply {
        putExtra(AuthenticatorActivity.EXTRA_ACCOUNT, user.toPlatformAccount())
        putExtra(
            AuthenticatorActivity.EXTRA_ACTION,
            AuthenticatorActivity.ACTION_UPDATE_EXPIRED_TOKEN
        )
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        addFlags(Intent.FLAG_FROM_BACKGROUND)
    }

    fun detailsIntent(operation: DownloadFileOperation?): Intent = if (operation != null) {
        if (PreviewImageFragment.canBePreviewed(operation.file)) {
            Intent(context, PreviewImageActivity::class.java)
        } else {
            Intent(context, FileDisplayActivity::class.java)
        }.apply {
            putExtra(FileActivity.EXTRA_FILE, operation.file)
            putExtra(FileActivity.EXTRA_USER, operation.user)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    } else {
        Intent()
    }
}
