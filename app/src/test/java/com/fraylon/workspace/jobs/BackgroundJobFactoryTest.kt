/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.jobs

import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.WorkerParameters
import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.core.Clock
import com.fraylon.workspace.database.FraylonDatabase
import com.fraylon.workspace.database.dao.FileDao
import com.fraylon.workspace.database.dao.FileSystemDao
import com.fraylon.workspace.device.DeviceInfo
import com.fraylon.workspace.device.PowerManagementService
import com.fraylon.workspace.documentscan.GeneratePDFUseCase
import com.fraylon.workspace.integrations.deck.DeckApi
import com.fraylon.workspace.logger.Logger
import com.fraylon.workspace.network.ConnectivityService
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.MainApp
import com.fraylon.workspace.datamodel.ArbitraryDataProvider
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.fraylon.workspace.datamodel.UploadsStorageManager
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.greenrobot.eventbus.EventBus
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class BackgroundJobFactoryTest {

    private val context = mockk<Context>(relaxed = true)

    @Mock
    private lateinit var params: WorkerParameters

    @Mock
    private lateinit var contentResolver: ContentResolver

    @Mock
    private lateinit var preferences: AppPreferences

    @Mock
    private lateinit var powerManagementService: PowerManagementService

    @Mock
    private lateinit var backgroundJobManager: BackgroundJobManager

    @Mock
    private lateinit var deviceInfo: DeviceInfo

    @Mock
    private lateinit var clock: Clock

    @Mock
    private lateinit var accountManager: UserAccountManager

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var dataProvider: ArbitraryDataProvider

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var uploadsStorageManager: UploadsStorageManager

    @Mock
    private lateinit var connectivityService: ConnectivityService

    @Mock
    private lateinit var notificationManager: NotificationManager

    @Mock
    private lateinit var eventBus: EventBus

    @Mock
    private lateinit var deckApi: DeckApi

    @Mock
    private lateinit var viewThemeUtils: ViewThemeUtils

    @Mock
    private lateinit var localBroadcastManager: LocalBroadcastManager

    @Mock
    private lateinit var generatePDFUseCase: GeneratePDFUseCase

    @Mock
    private lateinit var syncedFolderProvider: SyncedFolderProvider

    @Mock
    private lateinit var db: FraylonDatabase

    @Mock private lateinit var fileSystemDao: FileSystemDao

    @Mock private lateinit var fileDao: FileDao

    private lateinit var factory: BackgroundJobFactory

    @Before
    fun setUp() {
        mockkStatic(MainApp::class)
        every { MainApp.getAppContext() } returns context

        MockitoAnnotations.openMocks(this)

        whenever(db.fileDao()).thenReturn(fileDao)
        whenever(db.fileSystemDao()).thenReturn(fileSystemDao)

        factory = BackgroundJobFactory(
            logger,
            preferences,
            contentResolver,
            clock,
            powerManagementService,
            { backgroundJobManager },
            accountManager,
            resources,
            dataProvider,
            uploadsStorageManager,
            connectivityService,
            notificationManager,
            eventBus,
            deckApi,
            { viewThemeUtils },
            { localBroadcastManager },
            generatePDFUseCase,
            syncedFolderProvider,
            db
        )
    }

    @Test
    fun content_observer_worker_is_created() {
        // GIVEN
        //      content URI trigger is supported
        whenever(deviceInfo.apiLevel).thenReturn(Build.VERSION_CODES.P)

        // WHEN
        //      factory is called to create content observer worker
        val worker = factory.createWorker(context, ContentObserverWork::class.java.name, params)

        // THEN
        //      factory creates a worker compatible with API level
        assertNotNull(worker)
    }
}
