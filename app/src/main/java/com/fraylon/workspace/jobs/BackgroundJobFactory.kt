/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.jobs

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.core.Clock
import com.fraylon.workspace.database.FraylonDatabase
import com.fraylon.workspace.device.PowerManagementService
import com.fraylon.workspace.documentscan.GeneratePDFUseCase
import com.fraylon.workspace.documentscan.GeneratePdfFromImagesWork
import com.fraylon.workspace.integrations.deck.DeckApi
import com.fraylon.workspace.jobs.autoUpload.AutoUploadHelper
import com.fraylon.workspace.jobs.autoUpload.AutoUploadWorker
import com.fraylon.workspace.jobs.autoUpload.FileSystemRepository
import com.fraylon.workspace.jobs.download.FileDownloadWorker
import com.fraylon.workspace.jobs.folderDownload.FolderDownloadWorker
import com.fraylon.workspace.jobs.metadata.MetadataWorker
import com.fraylon.workspace.jobs.offlineOperations.OfflineOperationsWorker
import com.fraylon.workspace.jobs.upload.FileUploadWorker
import com.fraylon.workspace.logger.Logger
import com.fraylon.workspace.network.ConnectivityService
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.datamodel.ArbitraryDataProvider
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.fraylon.workspace.datamodel.UploadsStorageManager
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Provider

/**
 * This factory is responsible for creating all background jobs and for injecting worker dependencies.
 *
 * This class is doing too many things and should be split up into smaller factories.
 */
@Suppress("LongParameterList", "TooManyFunctions") // satisfied by DI
class BackgroundJobFactory @Inject constructor(
    private val logger: Logger,
    private val preferences: AppPreferences,
    private val contentResolver: ContentResolver,
    private val clock: Clock,
    private val powerManagementService: PowerManagementService,
    private val backgroundJobManager: Provider<BackgroundJobManager>,
    private val accountManager: UserAccountManager,
    private val resources: Resources,
    private val arbitraryDataProvider: ArbitraryDataProvider,
    private val uploadsStorageManager: UploadsStorageManager,
    private val connectivityService: ConnectivityService,
    private val notificationManager: NotificationManager,
    private val eventBus: EventBus,
    private val deckApi: DeckApi,
    private val viewThemeUtils: Provider<ViewThemeUtils>,
    private val localBroadcastManager: Provider<LocalBroadcastManager>,
    private val generatePdfUseCase: GeneratePDFUseCase,
    private val syncedFolderProvider: SyncedFolderProvider,
    private val database: FraylonDatabase
) : WorkerFactory() {

    @SuppressLint("NewApi")
    @Suppress("ComplexMethod") // it's just a trivial dispatch
    override fun createWorker(
        context: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerClass = try {
            Class.forName(workerClassName).kotlin
        } catch (ex: ClassNotFoundException) {
            null
        }

        return if (workerClass == ContentObserverWork::class) {
            createContentObserverJob(context, workerParameters)
        } else {
            when (workerClass) {
                ContactsBackupWork::class -> createContactsBackupWork(context, workerParameters)
                ContactsImportWork::class -> createContactsImportWork(context, workerParameters)
                AutoUploadWorker::class -> createAutoUploadWorker(context, workerParameters)
                OfflineSyncWork::class -> createOfflineSyncWork(context, workerParameters)
                MediaFoldersDetectionWork::class -> createMediaFoldersDetectionWork(context, workerParameters)
                NotificationWork::class -> createNotificationWork(context, workerParameters)
                AccountRemovalWork::class -> createAccountRemovalWork(context, workerParameters)
                CalendarBackupWork::class -> createCalendarBackupWork(context, workerParameters)
                CalendarImportWork::class -> createCalendarImportWork(context, workerParameters)
                FilesExportWork::class -> createFilesExportWork(context, workerParameters)
                FileUploadWorker::class -> createFilesUploadWorker(context, workerParameters)
                FileDownloadWorker::class -> createFilesDownloadWorker(context, workerParameters)
                GeneratePdfFromImagesWork::class -> createPDFGenerateWork(context, workerParameters)
                HealthStatusWork::class -> createHealthStatusWork(context, workerParameters)
                TestJob::class -> createTestJob(context, workerParameters)
                OfflineOperationsWorker::class -> createOfflineOperationsWorker(context, workerParameters)
                InternalTwoWaySyncWork::class -> createInternalTwoWaySyncWork(context, workerParameters)
                MetadataWorker::class -> createMetadataWorker(context, workerParameters)
                FolderDownloadWorker::class -> createFolderDownloadWorker(context, workerParameters)
                else -> null // caller falls back to default factory
            }
        }
    }

    private fun createOfflineOperationsWorker(context: Context, params: WorkerParameters): ListenableWorker =
        OfflineOperationsWorker(
            accountManager.user,
            context,
            connectivityService,
            viewThemeUtils.get(),
            params
        )

    private fun createFilesExportWork(context: Context, params: WorkerParameters): ListenableWorker = FilesExportWork(
        context,
        accountManager.user,
        contentResolver,
        viewThemeUtils.get(),
        params
    )

    private fun createContentObserverJob(context: Context, workerParameters: WorkerParameters): ListenableWorker =
        ContentObserverWork(
            context,
            workerParameters,
            SyncedFolderProvider(contentResolver, preferences, clock),
            powerManagementService,
            backgroundJobManager.get(),
            AutoUploadHelper(
                FileSystemRepository(dao = database.fileSystemDao(), uploadsStorageManager, context)
            )
        )

    private fun createContactsBackupWork(context: Context, params: WorkerParameters): ContactsBackupWork =
        ContactsBackupWork(
            context,
            params,
            resources,
            arbitraryDataProvider,
            contentResolver,
            accountManager
        )

    private fun createContactsImportWork(context: Context, params: WorkerParameters): ContactsImportWork =
        ContactsImportWork(
            context,
            params,
            logger,
            contentResolver
        )

    private fun createCalendarBackupWork(context: Context, params: WorkerParameters): CalendarBackupWork =
        CalendarBackupWork(
            context,
            params,
            contentResolver,
            accountManager,
            preferences
        )

    private fun createCalendarImportWork(context: Context, params: WorkerParameters): CalendarImportWork =
        CalendarImportWork(
            context,
            params,
            logger,
            contentResolver
        )

    private fun createAutoUploadWorker(context: Context, params: WorkerParameters): AutoUploadWorker = AutoUploadWorker(
        context = context,
        params = params,
        userAccountManager = accountManager,
        uploadsStorageManager = uploadsStorageManager,
        connectivityService = connectivityService,
        powerManagementService = powerManagementService,
        syncedFolderProvider = syncedFolderProvider,
        repository = FileSystemRepository(dao = database.fileSystemDao(), uploadsStorageManager, context),
        viewThemeUtils = viewThemeUtils.get(),
        localBroadcastManager = localBroadcastManager.get(),
        autoUploadHelper = AutoUploadHelper(
            FileSystemRepository(dao = database.fileSystemDao(), uploadsStorageManager, context)
        )
    )

    private fun createOfflineSyncWork(context: Context, params: WorkerParameters): OfflineSyncWork = OfflineSyncWork(
        context = context,
        params = params,
        contentResolver = contentResolver,
        userAccountManager = accountManager,
        connectivityService = connectivityService,
        powerManagementService = powerManagementService
    )

    private fun createMediaFoldersDetectionWork(context: Context, params: WorkerParameters): MediaFoldersDetectionWork =
        MediaFoldersDetectionWork(
            context,
            params,
            resources,
            contentResolver,
            accountManager,
            preferences,
            clock,
            viewThemeUtils.get(),
            syncedFolderProvider
        )

    private fun createNotificationWork(context: Context, params: WorkerParameters): NotificationWork = NotificationWork(
        context,
        params,
        notificationManager,
        accountManager,
        deckApi,
        viewThemeUtils.get()
    )

    private fun createAccountRemovalWork(context: Context, params: WorkerParameters): AccountRemovalWork =
        AccountRemovalWork(
            context,
            params,
            uploadsStorageManager,
            accountManager,
            backgroundJobManager.get(),
            clock,
            eventBus,
            preferences,
            syncedFolderProvider
        )

    private fun createFilesUploadWorker(context: Context, params: WorkerParameters): FileUploadWorker =
        FileUploadWorker(
            uploadsStorageManager,
            connectivityService,
            powerManagementService,
            accountManager,
            viewThemeUtils.get(),
            localBroadcastManager.get(),
            backgroundJobManager.get(),
            preferences,
            context,
            params
        )

    private fun createFilesDownloadWorker(context: Context, params: WorkerParameters): FileDownloadWorker =
        FileDownloadWorker(
            viewThemeUtils.get(),
            accountManager,
            localBroadcastManager.get(),
            context,
            params
        )

    private fun createPDFGenerateWork(context: Context, params: WorkerParameters): GeneratePdfFromImagesWork =
        GeneratePdfFromImagesWork(
            appContext = context,
            generatePdfUseCase = generatePdfUseCase,
            viewThemeUtils = viewThemeUtils.get(),
            notificationManager = notificationManager,
            userAccountManager = accountManager,
            logger = logger,
            params = params
        )

    private fun createHealthStatusWork(context: Context, params: WorkerParameters): HealthStatusWork = HealthStatusWork(
        context,
        params,
        accountManager,
        arbitraryDataProvider,
        backgroundJobManager.get()
    )

    private fun createTestJob(context: Context, params: WorkerParameters): TestJob = TestJob(
        context,
        params,
        backgroundJobManager.get()
    )

    private fun createInternalTwoWaySyncWork(context: Context, params: WorkerParameters): InternalTwoWaySyncWork =
        InternalTwoWaySyncWork(
            context,
            params,
            accountManager,
            powerManagementService,
            connectivityService,
            preferences
        )

    private fun createMetadataWorker(context: Context, params: WorkerParameters): MetadataWorker = MetadataWorker(
        context,
        params,
        accountManager.user
    )

    private fun createFolderDownloadWorker(context: Context, params: WorkerParameters): FolderDownloadWorker =
        FolderDownloadWorker(
            accountManager,
            context,
            viewThemeUtils.get(),
            localBroadcastManager.get(),
            params
        )
}
