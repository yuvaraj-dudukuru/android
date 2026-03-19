/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.utils.extensions

import android.content.Intent
import com.fraylon.workspace.MainApp
import com.fraylon.workspace.R
import com.fraylon.workspace.ui.activity.DrawerActivity
import com.fraylon.workspace.ui.activity.FileDisplayActivity

@JvmOverloads
fun DrawerActivity.navigateToAllFiles(onlyPersonal: Boolean = false) {
    MainApp.showOnlyFilesOnDevice(false)
    MainApp.showOnlyPersonalFiles(onlyPersonal)
    highlightNavigationViewItem(R.id.nav_all_files)
    setupHomeSearchToolbarWithSortAndListButtons()

    Intent(applicationContext, FileDisplayActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        action = FileDisplayActivity.ALL_FILES
    }.run {
        startActivity(this)
    }
}
