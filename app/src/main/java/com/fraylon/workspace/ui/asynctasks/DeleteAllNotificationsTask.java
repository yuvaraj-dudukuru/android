/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2019 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.asynctasks;

import android.os.AsyncTask;

import com.nextcloud.common.NextcloudClient;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.notifications.DeleteAllNotificationsRemoteOperation;
import com.owncloud.android.lib.resources.notifications.models.Action;
import com.fraylon.workspace.ui.activity.NotificationsActivity;
import com.fraylon.workspace.ui.notifications.NotificationsContract;

public class DeleteAllNotificationsTask extends AsyncTask<Action, Void, Boolean> {
    private NextcloudClient client;
    private final NotificationsContract.View notificationsActivity;

    public DeleteAllNotificationsTask(NextcloudClient client, NotificationsActivity notificationsActivity) {
        this.client = client;
        this.notificationsActivity = notificationsActivity;
    }

    @Override
    protected Boolean doInBackground(Action... actions) {

        RemoteOperationResult result = new DeleteAllNotificationsRemoteOperation().execute(client);

        return result.isSuccess();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        notificationsActivity.onRemovedAllNotifications(success);
    }
}
