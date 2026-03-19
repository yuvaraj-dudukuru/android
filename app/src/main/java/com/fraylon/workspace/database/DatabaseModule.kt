/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.database

import android.content.Context
import com.fraylon.workspace.core.Clock
import com.fraylon.workspace.database.dao.ArbitraryDataDao
import com.fraylon.workspace.database.dao.FileDao
import com.fraylon.workspace.database.dao.OfflineOperationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun database(context: Context, clock: Clock): NextcloudDatabase = NextcloudDatabase.getInstance(context, clock)

    @Provides
    fun arbitraryDataDao(nextcloudDatabase: NextcloudDatabase): ArbitraryDataDao = nextcloudDatabase.arbitraryDataDao()

    @Provides
    fun fileDao(nextcloudDatabase: NextcloudDatabase): FileDao = nextcloudDatabase.fileDao()

    @Provides
    fun offlineOperationsDao(nextcloudDatabase: NextcloudDatabase): OfflineOperationDao =
        nextcloudDatabase.offlineOperationDao()
}
