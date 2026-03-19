/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.di

import com.nextcloud.android.common.ui.theme.MaterialSchemes
import com.fraylon.workspace.utils.theme.MaterialSchemesProvider
import com.fraylon.workspace.utils.theme.MaterialSchemesProviderImpl
import com.fraylon.workspace.utils.theme.ThemeColorUtils
import com.fraylon.workspace.utils.theme.ThemeUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal abstract class ThemeModule {

    @Binds
    abstract fun bindMaterialSchemesProvider(provider: MaterialSchemesProviderImpl): MaterialSchemesProvider

    companion object {

        @Provides
        @Singleton
        fun themeColorUtils(): ThemeColorUtils = ThemeColorUtils()

        @Provides
        @Singleton
        fun themeUtils(): ThemeUtils = ThemeUtils()

        @Provides
        fun provideMaterialSchemes(materialSchemesProvider: MaterialSchemesProvider): MaterialSchemes =
            materialSchemesProvider.getMaterialSchemesForCurrentUser()
    }
}
