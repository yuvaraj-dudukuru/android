/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.widget

import com.nextcloud.android.lib.resources.dashboard.DashboardButton
import com.fraylon.workspace.account.User
import java.util.Optional

data class WidgetConfiguration(
    val widgetId: String,
    val title: String,
    val iconUrl: String,
    val roundIcon: Boolean,
    val user: Optional<User>,
    val addButton: DashboardButton?,
    val moreButton: DashboardButton?
)
