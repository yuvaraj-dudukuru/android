/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */

package com.fraylon.workspace.di;

import android.app.Application;

import com.nextcloud.appReview.InAppReviewModule;
import com.fraylon.workspace.appinfo.AppInfoModule;
import com.fraylon.workspace.database.DatabaseModule;
import com.fraylon.workspace.device.DeviceModule;
import com.fraylon.workspace.integrations.IntegrationsModule;
import com.fraylon.workspace.jobs.JobsModule;
import com.fraylon.workspace.jobs.download.FileDownloadHelper;
import com.fraylon.workspace.jobs.offlineOperations.receiver.OfflineOperationReceiver;
import com.fraylon.workspace.jobs.folderDownload.FolderDownloadWorkerReceiver;
import com.fraylon.workspace.jobs.upload.FileUploadBroadcastReceiver;
import com.fraylon.workspace.jobs.upload.FileUploadHelper;
import com.fraylon.workspace.media.BackgroundPlayerService;
import com.fraylon.workspace.network.NetworkModule;
import com.fraylon.workspace.onboarding.OnboardingModule;
import com.fraylon.workspace.preferences.PreferencesModule;
import com.owncloud.android.MainApp;
import com.owncloud.android.media.MediaControlView;
import com.owncloud.android.ui.ThemeableSwitchPreference;
import com.owncloud.android.ui.whatsnew.ProgressIndicator;

import javax.inject.Singleton;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
    AndroidSupportInjectionModule.class,
    AppModule.class,
    PreferencesModule.class,
    AppInfoModule.class,
    NetworkModule.class,
    DeviceModule.class,
    OnboardingModule.class,
    ViewModelModule.class,
    JobsModule.class,
    IntegrationsModule.class,
    InAppReviewModule.class,
    ThemeModule.class,
    DatabaseModule.class,
    DispatcherModule.class,
    VariantModule.class,
})
@Singleton
public interface AppComponent {

    void inject(MainApp app);

    void inject(MediaControlView mediaControlView);

    @OptIn(markerClass = UnstableApi.class)
    void inject(BackgroundPlayerService backgroundPlayerService);

    void inject(ThemeableSwitchPreference switchPreference);

    void inject(FileUploadHelper fileUploadHelper);

    void inject(FileDownloadHelper fileDownloadHelper);

    void inject(ProgressIndicator progressIndicator);

    void inject(FileUploadBroadcastReceiver fileUploadBroadcastReceiver);

    void inject(OfflineOperationReceiver offlineOperationReceiver);

    void inject(FolderDownloadWorkerReceiver folderDownloadWorkerReceiver);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
