/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.di;

import com.fraylon.workspace.authentication.ModifiedAuthenticatorActivity;
import com.fraylon.workspace.services.firebase.NCFirebaseMessagingService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class VariantComponentsModule {
    @ContributesAndroidInjector
    abstract NCFirebaseMessagingService nCFirebaseMessagingService();

    @ContributesAndroidInjector
    abstract ModifiedAuthenticatorActivity modifiedAuthenticatorActivity();
}
