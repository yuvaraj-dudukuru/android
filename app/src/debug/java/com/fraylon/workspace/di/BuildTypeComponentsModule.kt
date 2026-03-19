/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.di

import com.fraylon.test.InjectionTestActivity
import com.fraylon.test.TestActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Register classes that require dependency injection. This class is used by Dagger compiler only.
 */
@Module
interface BuildTypeComponentsModule {
    @ContributesAndroidInjector
    fun testActivity(): TestActivity?

    @ContributesAndroidInjector
    fun injectionTestActivity(): InjectionTestActivity?
}
