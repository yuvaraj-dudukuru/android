/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.nextcloud.android.lib.resources.dashboard.DashboardWidget
import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.network.ClientFactory
import com.fraylon.workspace.widget.DashboardWidgetConfigurationInterface
import com.fraylon.utils.GlideHelper
import com.fraylon.workspace.R
import com.fraylon.workspace.databinding.WidgetListItemBinding
import com.owncloud.android.lib.common.OwnCloudClientManagerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetListItemViewHolder(
    val binding: WidgetListItemBinding,
    val accountManager: UserAccountManager,
    val clientFactory: ClientFactory,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        dashboardWidget: DashboardWidget,
        dashboardWidgetConfigurationInterface: DashboardWidgetConfigurationInterface
    ) {
        binding.layout.setOnClickListener { dashboardWidgetConfigurationInterface.onItemClicked(dashboardWidget) }

        CoroutineScope(Dispatchers.IO).launch {
            val client = OwnCloudClientManagerFactory.getDefaultSingleton()
                .getNextcloudClientFor(accountManager.currentOwnCloudAccount, context)

            withContext(Dispatchers.Main) {
                GlideHelper.loadIntoImageView(
                    context,
                    client,
                    dashboardWidget.iconUrl,
                    binding.icon,
                    R.drawable.ic_dashboard
                )
            }
        }

        binding.name.text = dashboardWidget.title
    }
}
