/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2018 Mario Danic <mario@lovelyhq.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.utils;

import android.content.Intent;

import com.google.android.gms.security.ProviderInstaller;
import com.fraylon.workspace.MainApp;

public class SecurityUtils implements ProviderInstaller.ProviderInstallListener {
    public SecurityUtils() {
        ProviderInstaller.installIfNeededAsync(MainApp.getAppContext(), this);
    }

    @Override
    public void onProviderInstalled() {
        // Does nothing
    }

    @Override
    public void onProviderInstallFailed(int i, Intent intent) {
        // Does nothing
    }
}
