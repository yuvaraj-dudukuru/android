/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */

package com.fraylon.workspace.di;

import android.accounts.AccountManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Handler;

import com.fraylon.workspace.account.CurrentAccountProvider;
import com.fraylon.workspace.account.UserAccountManager;
import com.fraylon.workspace.account.UserAccountManagerImpl;
import com.fraylon.workspace.appinfo.AppInfo;
import com.fraylon.workspace.core.AsyncRunner;
import com.fraylon.workspace.core.Clock;
import com.fraylon.workspace.core.ClockImpl;
import com.fraylon.workspace.core.ThreadPoolAsyncRunner;
import com.fraylon.workspace.database.dao.ArbitraryDataDao;
import com.fraylon.workspace.device.DeviceInfo;
import com.fraylon.workspace.jobs.operation.FileOperationHelper;
import com.fraylon.workspace.logger.FileLogHandler;
import com.fraylon.workspace.logger.Logger;
import com.fraylon.workspace.logger.LoggerImpl;
import com.fraylon.workspace.logger.LogsRepository;
import com.fraylon.workspace.migrations.Migrations;
import com.fraylon.workspace.migrations.MigrationsDb;
import com.fraylon.workspace.migrations.MigrationsManager;
import com.fraylon.workspace.migrations.MigrationsManagerImpl;
import com.fraylon.workspace.network.ClientFactory;
import com.fraylon.workspace.notifications.AppNotificationManager;
import com.fraylon.workspace.notifications.AppNotificationManagerImpl;
import com.fraylon.workspace.preferences.AppPreferences;
import com.fraylon.workspace.utils.Throttler;
import com.owncloud.android.providers.UsersAndGroupsSearchConfig;
import com.owncloud.android.authentication.PassCodeManager;
import com.owncloud.android.datamodel.ArbitraryDataProvider;
import com.owncloud.android.datamodel.ArbitraryDataProviderImpl;
import com.owncloud.android.datamodel.FileDataStorageManager;
import com.owncloud.android.datamodel.SyncedFolderProvider;
import com.owncloud.android.datamodel.UploadsStorageManager;
import com.owncloud.android.ui.activities.data.activities.ActivitiesRepository;
import com.owncloud.android.ui.activities.data.activities.ActivitiesServiceApi;
import com.owncloud.android.ui.activities.data.activities.ActivitiesServiceApiImpl;
import com.owncloud.android.ui.activities.data.activities.RemoteActivitiesRepository;
import com.owncloud.android.ui.activities.data.files.FilesRepository;
import com.owncloud.android.ui.activities.data.files.FilesServiceApiImpl;
import com.owncloud.android.ui.activities.data.files.RemoteFilesRepository;
import com.owncloud.android.ui.dialog.setupEncryption.CertificateValidator;
import com.owncloud.android.utils.overlay.OverlayManager;
import com.owncloud.android.utils.theme.ViewThemeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ComponentsModule.class, VariantComponentsModule.class, BuildTypeComponentsModule.class})
class AppModule {

    @Provides
    AccountManager accountManager(Application application) {
        return (AccountManager) application.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Provides
    Context context(Application application) {
        return application;
    }

    @Provides
    PackageManager packageManager(Application application) {
        return application.getPackageManager();
    }

    @Provides
    ContentResolver contentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    Resources resources(Application application) {
        return application.getResources();
    }

    @Provides
    UserAccountManager userAccountManager(
        Context context,
        AccountManager accountManager) {
        return new UserAccountManagerImpl(context, accountManager);
    }

    @Provides
    ArbitraryDataProvider arbitraryDataProvider(ArbitraryDataDao dao) {
        return new ArbitraryDataProviderImpl(dao);
    }

    @Provides
    SyncedFolderProvider syncedFolderProvider(ContentResolver contentResolver,
                                              AppPreferences appPreferences,
                                              Clock clock) {
        return new SyncedFolderProvider(contentResolver, appPreferences, clock);
    }

    @Provides
    ActivitiesServiceApi activitiesServiceApi(UserAccountManager accountManager) {
        return new ActivitiesServiceApiImpl(accountManager);
    }

    @Provides
    ActivitiesRepository activitiesRepository(ActivitiesServiceApi api) {
        return new RemoteActivitiesRepository(api);
    }

    @Provides
    FilesRepository filesRepository(UserAccountManager accountManager, ClientFactory clientFactory) {
        return new RemoteFilesRepository(new FilesServiceApiImpl(accountManager, clientFactory));
    }

    @Provides
    UploadsStorageManager uploadsStorageManager(CurrentAccountProvider currentAccountProvider,
                                                Context context) {
        return new UploadsStorageManager(currentAccountProvider, context.getContentResolver());
    }

    @Provides
    FileDataStorageManager fileDataStorageManager(CurrentAccountProvider currentAccountProvider,
                                                  Context context) {
        return new FileDataStorageManager(currentAccountProvider.getUser(), context.getContentResolver());
    }

    @Provides
    CurrentAccountProvider currentAccountProvider(UserAccountManager accountManager) {
        return accountManager;
    }

    @Provides
    DeviceInfo deviceInfo() {
        return new DeviceInfo();
    }

    @Provides
    @Singleton
    Clock clock() {
        return new ClockImpl();
    }

    @Provides
    @Singleton
    Logger logger(Context context, Clock clock) {
        File logDir = new File(context.getFilesDir(), "logs");
        FileLogHandler handler = new FileLogHandler(logDir, "log.txt", 1024 * 1024);
        LoggerImpl logger = new LoggerImpl(clock, handler, new Handler(), 1000);
        logger.start();
        return logger;
    }

    @Provides
    @Singleton
    LogsRepository logsRepository(Logger logger) {
        return (LogsRepository) logger;
    }

    @Provides
    @Singleton
    AsyncRunner uiAsyncRunner() {
        Handler uiHandler = new Handler();
        return new ThreadPoolAsyncRunner(uiHandler, 4, "ui");
    }

    @Provides
    @Singleton
    @Named("io")
    AsyncRunner ioAsyncRunner() {
        Handler uiHandler = new Handler();
        return new ThreadPoolAsyncRunner(uiHandler, 8, "io");
    }

    @Provides
    NotificationManager notificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    AudioManager audioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    @Singleton
    EventBus eventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    MigrationsDb migrationsDb(Application application) {
        SharedPreferences store = application.getSharedPreferences("migrations", Context.MODE_PRIVATE);
        return new MigrationsDb(store);
    }

    @Provides
    @Singleton
    MigrationsManager migrationsManager(MigrationsDb migrationsDb,
                                        AppInfo appInfo,
                                        AsyncRunner asyncRunner,
                                        Migrations migrations) {
        return new MigrationsManagerImpl(appInfo, migrationsDb, asyncRunner, migrations.getSteps());
    }

    @Provides
    @Singleton
    AppNotificationManager notificationsManager(Context context,
                                                NotificationManager platformNotificationsManager,
                                                Provider<ViewThemeUtils> viewThemeUtilsProvider) {
        return new AppNotificationManagerImpl(context,
                                              context.getResources(),
                                              platformNotificationsManager,
                                              viewThemeUtilsProvider.get());
    }

    @Provides
    LocalBroadcastManager localBroadcastManager(Context context) {
        return LocalBroadcastManager.getInstance(context);
    }

    @Provides
    Throttler throttler(Clock clock) {
        return new Throttler(clock);
    }

    @Provides
    @Singleton
    PassCodeManager passCodeManager(AppPreferences preferences, Clock clock) {
        return new PassCodeManager(preferences, clock);
    }

    @Provides
    FileOperationHelper fileOperationHelper(CurrentAccountProvider currentAccountProvider, Context context) {
        return new FileOperationHelper(currentAccountProvider.getUser(), context, fileDataStorageManager(currentAccountProvider, context));
    }

    @Provides
    @Singleton
    UsersAndGroupsSearchConfig userAndGroupSearchConfig() {
        return new UsersAndGroupsSearchConfig();
    }


    @Provides
    @Singleton
    CertificateValidator certificateValidator() {
        return new CertificateValidator();
    }

    @Provides
    @Singleton
    OverlayManager overlayManager(
        SyncedFolderProvider syncedFolderProvider,
        AppPreferences appPreferences,
        ViewThemeUtils viewThemeUtils,
        Context context,
        UserAccountManager accountManager) {
        return new OverlayManager(syncedFolderProvider, appPreferences, viewThemeUtils, context, accountManager);
    }
}
