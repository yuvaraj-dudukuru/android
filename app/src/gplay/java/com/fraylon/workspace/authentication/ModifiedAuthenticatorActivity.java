/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2017 Mario Danic <mario@lovelyhq.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.authentication;

import android.os.Bundle;

import com.fraylon.workspace.di.Injectable;
import com.fraylon.workspace.utils.GooglePlayUtils;

public class ModifiedAuthenticatorActivity extends AuthenticatorActivity implements Injectable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GooglePlayUtils.checkPlayServices(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GooglePlayUtils.checkPlayServices(this);
    }

}
