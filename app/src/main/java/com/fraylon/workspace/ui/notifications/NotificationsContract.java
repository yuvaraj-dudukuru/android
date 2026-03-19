/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2019 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.notifications;

import com.owncloud.android.lib.resources.notifications.models.Notification;
import com.fraylon.workspace.ui.adapter.NotificationListAdapter;

public interface NotificationsContract {

    interface View {
        void onRemovedNotification(boolean isSuccess);

        void removeNotification(NotificationListAdapter.NotificationViewHolder holder);

        void onRemovedAllNotifications(boolean isSuccess);

        void onActionCallback(boolean isSuccess,
                              Notification notification,
                              NotificationListAdapter.NotificationViewHolder holder);
    }
}
