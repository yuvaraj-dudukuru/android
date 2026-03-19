/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2023 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.jobs.download

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.fraylon.workspace.jobs.notification.WorkerNotificationManager
import com.fraylon.utils.numberFormatter.NumberFormatter
import com.fraylon.workspace.R
import com.fraylon.workspace.operations.DownloadFileOperation
import com.fraylon.workspace.ui.notifications.NotificationUtils
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import java.io.File
import java.security.SecureRandom

@Suppress("TooManyFunctions")
class DownloadNotificationManager(id: Int, private val context: Context, viewThemeUtils: ViewThemeUtils) :
    WorkerNotificationManager(
        id,
        context,
        viewThemeUtils,
        tickerId = R.string.downloader_download_in_progress_ticker,
        channelId = NotificationUtils.NOTIFICATION_CHANNEL_DOWNLOAD
    ) {

    private var lastPercent = -1

    @Suppress("MagicNumber")
    fun prepareForStart(operation: DownloadFileOperation) {
        currentOperationTitle = File(operation.savePath).name

        notificationBuilder.run {
            setContentTitle(currentOperationTitle)
            setOngoing(false)
            setProgress(100, 0, operation.size < 0)
        }

        showNotification()
    }

    fun prepareForResult() {
        notificationBuilder
            .setAutoCancel(true)
            .setOngoing(false)
            .setProgress(0, 0, false)
    }

    @Suppress("MagicNumber")
    fun updateDownloadProgress(percent: Int, totalToTransfer: Long) {
        // If downloads are so fast, no need to notify again.
        if (percent == lastPercent) {
            return
        }
        lastPercent = percent

        val progressText = NumberFormatter.getPercentageText(percent)
        setProgress(percent, progressText, totalToTransfer < 0)
        showNotification()
    }

    @Suppress("MagicNumber")
    fun dismissNotification() {
        dismissNotification(2000)
    }

    fun showNewNotification(text: String) {
        val notifyId = SecureRandom().nextInt()

        notificationBuilder.run {
            setProgress(0, 0, false)
            setContentTitle(text)
            setOngoing(false)
            notificationManager.notify(notifyId, this.build())
        }
    }

    fun setContentIntent(intent: Intent, flag: Int) {
        notificationBuilder.setContentIntent(
            PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                intent,
                flag
            )
        )
    }
}
