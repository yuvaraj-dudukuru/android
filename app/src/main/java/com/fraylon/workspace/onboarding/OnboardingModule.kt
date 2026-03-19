/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.onboarding

import android.content.res.Resources
import com.fraylon.workspace.account.CurrentAccountProvider
import com.fraylon.workspace.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OnboardingModule {

    @Provides
    @Singleton
    internal fun onboardingService(
        resources: Resources,
        preferences: AppPreferences,
        accountProvider: CurrentAccountProvider
    ): OnboardingService = OnboardingServiceImpl(resources, preferences, accountProvider)
}
