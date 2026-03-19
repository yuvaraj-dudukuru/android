/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.fraylon.workspace.ui.adapter.progressListener

import android.widget.ProgressBar
import com.fraylon.workspace.db.OCUpload

class UploadProgressListener(val upload: OCUpload?, progressBar: ProgressBar?) : ProgressListener(progressBar) {

    fun isWrapping(progressBar: ProgressBar?): Boolean {
        val wrappedProgressBar = progressBarRef.get()
        return wrappedProgressBar != null && wrappedProgressBar === progressBar
    }
}
