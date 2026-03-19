/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 TSI-mc <surinder.kumar@t-systems.com>
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.di;

import com.fraylon.workspace.documentscan.DocumentScanActivity;
import com.fraylon.workspace.editimage.EditImageActivity;
import com.fraylon.workspace.etm.EtmActivity;
import com.fraylon.workspace.etm.pages.EtmBackgroundJobsFragment;
import com.fraylon.workspace.jobs.BackgroundJobManagerImpl;
import com.fraylon.workspace.jobs.NotificationWork;
import com.fraylon.workspace.jobs.TestJob;
import com.fraylon.workspace.jobs.transfer.FileTransferService;
import com.fraylon.workspace.jobs.upload.FileUploadHelper;
import com.fraylon.workspace.logger.ui.LogsActivity;
import com.fraylon.workspace.logger.ui.LogsViewModel;
import com.fraylon.workspace.media.BackgroundPlayerService;
import com.fraylon.workspace.media.PlayerService;
import com.fraylon.workspace.migrations.Migrations;
import com.fraylon.workspace.onboarding.FirstRunActivity;
import com.fraylon.workspace.onboarding.WhatsNewActivity;
import com.fraylon.workspace.widget.DashboardWidgetConfigurationActivity;
import com.fraylon.workspace.widget.DashboardWidgetProvider;
import com.fraylon.workspace.widget.DashboardWidgetService;
import com.fraylon.receiver.NetworkChangeReceiver;
import com.fraylon.ui.ChooseAccountDialogFragment;
import com.fraylon.ui.ChooseStorageLocationDialogFragment;
import com.fraylon.ui.ImageDetailFragment;
import com.fraylon.ui.SetOnlineStatusBottomSheet;
import com.fraylon.ui.SetStatusMessageBottomSheet;
import com.fraylon.ui.composeActivity.ComposeActivity;
import com.fraylon.ui.fileactions.FileActionsBottomSheet;
import com.fraylon.ui.trashbinFileActions.TrashbinFileActionsBottomSheet;
import com.nmc.android.ui.LauncherActivity;
import com.fraylon.workspace.MainApp;
import com.fraylon.workspace.authentication.AuthenticatorActivity;
import com.fraylon.workspace.authentication.DeepLinkLoginActivity;
import com.fraylon.workspace.files.BootupBroadcastReceiver;
import com.fraylon.workspace.providers.DiskLruImageCacheFileProvider;
import com.fraylon.workspace.providers.DocumentsStorageProvider;
import com.fraylon.workspace.providers.FileContentProvider;
import com.fraylon.workspace.providers.UsersAndGroupsSearchProvider;
import com.fraylon.workspace.services.AccountManagerService;
import com.fraylon.workspace.services.OperationsService;
import com.fraylon.workspace.syncadapter.FileSyncService;
import com.fraylon.workspace.ui.activities.ActivitiesActivity;
import com.fraylon.workspace.ui.activity.BaseActivity;
import com.fraylon.workspace.ui.activity.ConflictsResolveActivity;
import com.fraylon.workspace.ui.activity.ContactsPreferenceActivity;
import com.fraylon.workspace.ui.activity.CopyToClipboardActivity;
import com.fraylon.workspace.ui.activity.DrawerActivity;
import com.fraylon.workspace.ui.activity.ErrorsWhileCopyingHandlerActivity;
import com.fraylon.workspace.ui.activity.ExternalSiteWebView;
import com.fraylon.workspace.ui.activity.FileActivity;
import com.fraylon.workspace.ui.activity.FileDisplayActivity;
import com.fraylon.workspace.ui.activity.FilePickerActivity;
import com.fraylon.workspace.ui.activity.FolderPickerActivity;
import com.fraylon.workspace.ui.activity.InternalTwoWaySyncActivity;
import com.fraylon.workspace.ui.activity.ManageAccountsActivity;
import com.fraylon.workspace.ui.activity.ManageSpaceActivity;
import com.fraylon.workspace.ui.activity.NotificationsActivity;
import com.fraylon.workspace.ui.activity.PassCodeActivity;
import com.fraylon.workspace.ui.activity.ReceiveExternalFilesActivity;
import com.fraylon.workspace.ui.activity.RequestCredentialsActivity;
import com.fraylon.workspace.ui.activity.RichDocumentsEditorWebView;
import com.fraylon.workspace.ui.activity.SettingsActivity;
import com.fraylon.workspace.ui.activity.ShareActivity;
import com.fraylon.workspace.ui.activity.SsoGrantPermissionActivity;
import com.fraylon.workspace.ui.activity.SyncedFoldersActivity;
import com.fraylon.workspace.ui.activity.TextEditorWebView;
import com.fraylon.workspace.ui.activity.ToolbarActivity;
import com.fraylon.workspace.ui.activity.UploadFilesActivity;
import com.fraylon.workspace.ui.activity.UploadListActivity;
import com.fraylon.workspace.ui.activity.UserInfoActivity;
import com.fraylon.workspace.ui.dialog.AccountRemovalDialog;
import com.fraylon.workspace.ui.dialog.AppPassCodeDialog;
import com.fraylon.workspace.ui.dialog.ChooseRichDocumentsTemplateDialogFragment;
import com.fraylon.workspace.ui.dialog.ChooseTemplateDialogFragment;
import com.fraylon.workspace.ui.dialog.ConfirmationDialogFragment;
import com.fraylon.workspace.ui.dialog.ConflictsResolveDialog;
import com.fraylon.workspace.ui.dialog.CreateFolderDialogFragment;
import com.fraylon.workspace.ui.dialog.ExpirationDatePickerDialogFragment;
import com.fraylon.workspace.ui.dialog.IndeterminateProgressDialog;
import com.fraylon.workspace.ui.dialog.LoadingDialog;
import com.fraylon.workspace.ui.dialog.LocalStoragePathPickerDialogFragment;
import com.fraylon.workspace.ui.dialog.MultipleAccountsDialog;
import com.fraylon.workspace.ui.dialog.RemoveFilesDialogFragment;
import com.fraylon.workspace.ui.dialog.RenameFileDialogFragment;
import com.fraylon.workspace.ui.dialog.SendFilesDialog;
import com.fraylon.workspace.ui.dialog.SendShareDialog;
import com.fraylon.workspace.ui.dialog.SharePasswordDialogFragment;
import com.fraylon.workspace.ui.dialog.SortingOrderDialogFragment;
import com.fraylon.workspace.ui.dialog.SslUntrustedCertDialog;
import com.fraylon.workspace.ui.dialog.StoragePermissionDialogFragment;
import com.fraylon.workspace.ui.dialog.SyncFileNotEnoughSpaceDialogFragment;
import com.fraylon.workspace.ui.dialog.SyncedFolderPreferencesDialogFragment;
import com.fraylon.workspace.ui.dialog.TermsOfServiceDialog;
import com.fraylon.workspace.ui.dialog.ThemeSelectionDialog;
import com.fraylon.workspace.ui.dialog.setupEncryption.SetupEncryptionDialogFragment;
import com.fraylon.workspace.ui.fragment.ExtendedListFragment;
import com.fraylon.workspace.ui.fragment.FeatureFragment;
import com.fraylon.workspace.ui.fragment.FileDetailActivitiesFragment;
import com.fraylon.workspace.ui.fragment.FileDetailFragment;
import com.fraylon.workspace.ui.fragment.FileDetailSharingFragment;
import com.fraylon.workspace.ui.fragment.FileDetailsSharingProcessFragment;
import com.fraylon.workspace.ui.fragment.GalleryFragment;
import com.fraylon.workspace.ui.fragment.GalleryFragmentBottomSheetDialog;
import com.fraylon.workspace.ui.fragment.GroupfolderListFragment;
import com.fraylon.workspace.ui.fragment.LocalFileListFragment;
import com.fraylon.workspace.ui.fragment.OCFileListBottomSheetDialog;
import com.fraylon.workspace.ui.fragment.OCFileListFragment;
import com.fraylon.workspace.ui.fragment.SharedListFragment;
import com.fraylon.workspace.ui.fragment.UnifiedSearchFragment;
import com.fraylon.workspace.ui.fragment.community.CommunityFragment;
import com.fraylon.workspace.ui.fragment.contactsbackup.BackupFragment;
import com.fraylon.workspace.ui.fragment.contactsbackup.BackupListFragment;
import com.fraylon.workspace.ui.navigation.NavigatorActivity;
import com.fraylon.workspace.ui.preview.FileDownloadFragment;
import com.fraylon.workspace.ui.preview.PreviewBitmapActivity;
import com.fraylon.workspace.ui.preview.PreviewImageActivity;
import com.fraylon.workspace.ui.preview.PreviewImageFragment;
import com.fraylon.workspace.ui.preview.PreviewMediaActivity;
import com.fraylon.workspace.ui.preview.PreviewMediaFragment;
import com.fraylon.workspace.ui.preview.PreviewTextFileFragment;
import com.fraylon.workspace.ui.preview.PreviewTextFragment;
import com.fraylon.workspace.ui.preview.PreviewTextStringFragment;
import com.fraylon.workspace.ui.preview.pdf.PreviewPdfFragment;
import com.fraylon.workspace.ui.trashbin.TrashbinActivity;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Register classes that require dependency injection. This class is used by Dagger compiler only.
 */
@Module
abstract class ComponentsModule {
    @ContributesAndroidInjector
    abstract ActivitiesActivity activitiesActivity();

    @ContributesAndroidInjector
    abstract AuthenticatorActivity authenticatorActivity();

    @ContributesAndroidInjector
    abstract BaseActivity baseActivity();

    @ContributesAndroidInjector
    abstract ConflictsResolveActivity conflictsResolveActivity();

    @ContributesAndroidInjector
    abstract ContactsPreferenceActivity contactsPreferenceActivity();

    @ContributesAndroidInjector
    abstract CopyToClipboardActivity copyToClipboardActivity();

    @ContributesAndroidInjector
    abstract DeepLinkLoginActivity deepLinkLoginActivity();

    @ContributesAndroidInjector
    abstract DrawerActivity drawerActivity();

    @ContributesAndroidInjector
    abstract ErrorsWhileCopyingHandlerActivity errorsWhileCopyingHandlerActivity();

    @ContributesAndroidInjector
    abstract ExternalSiteWebView externalSiteWebView();

    @ContributesAndroidInjector
    abstract FileDisplayActivity fileDisplayActivity();

    @ContributesAndroidInjector
    abstract FilePickerActivity filePickerActivity();

    @ContributesAndroidInjector
    abstract FirstRunActivity firstRunActivity();

    @ContributesAndroidInjector
    abstract FolderPickerActivity folderPickerActivity();

    @ContributesAndroidInjector
    abstract LogsActivity logsActivity();

    @ContributesAndroidInjector
    abstract ManageAccountsActivity manageAccountsActivity();

    @ContributesAndroidInjector
    abstract ManageSpaceActivity manageSpaceActivity();

    @ContributesAndroidInjector
    abstract NotificationsActivity notificationsActivity();

    @ContributesAndroidInjector
    abstract ComposeActivity composeActivity();

    @ContributesAndroidInjector
    abstract PassCodeActivity passCodeActivity();

    @ContributesAndroidInjector
    abstract PreviewImageActivity previewImageActivity();

    @ContributesAndroidInjector
    abstract PreviewMediaActivity previewMediaActivity();

    @ContributesAndroidInjector
    abstract ReceiveExternalFilesActivity receiveExternalFilesActivity();

    @ContributesAndroidInjector
    abstract RequestCredentialsActivity requestCredentialsActivity();

    @ContributesAndroidInjector
    abstract SettingsActivity settingsActivity();

    @ContributesAndroidInjector
    abstract ShareActivity shareActivity();

    @ContributesAndroidInjector
    abstract SsoGrantPermissionActivity ssoGrantPermissionActivity();

    @ContributesAndroidInjector
    abstract SyncedFoldersActivity syncedFoldersActivity();

    @ContributesAndroidInjector
    abstract TrashbinActivity trashbinActivity();

    @ContributesAndroidInjector
    abstract TrashbinFileActionsBottomSheet trashbinFileActionsBottomSheet();

    @ContributesAndroidInjector
    abstract UploadFilesActivity uploadFilesActivity();

    @ContributesAndroidInjector
    abstract UploadListActivity uploadListActivity();

    @ContributesAndroidInjector
    abstract UserInfoActivity userInfoActivity();

    @ContributesAndroidInjector
    abstract WhatsNewActivity whatsNewActivity();

    @ContributesAndroidInjector
    abstract EtmActivity etmActivity();

    @ContributesAndroidInjector
    abstract RichDocumentsEditorWebView richDocumentsWebView();

    @ContributesAndroidInjector
    abstract TextEditorWebView textEditorWebView();

    @ContributesAndroidInjector
    abstract ExtendedListFragment extendedListFragment();

    @ContributesAndroidInjector
    abstract FileDetailFragment fileDetailFragment();

    @ContributesAndroidInjector
    abstract LocalFileListFragment localFileListFragment();

    @ContributesAndroidInjector
    abstract OCFileListFragment ocFileListFragment();

    @ContributesAndroidInjector
    abstract FileDetailActivitiesFragment fileDetailActivitiesFragment();

    @ContributesAndroidInjector
    abstract FileDetailsSharingProcessFragment fileDetailsSharingProcessFragment();

    @ContributesAndroidInjector
    abstract FileDetailSharingFragment fileDetailSharingFragment();

    @ContributesAndroidInjector
    abstract ChooseTemplateDialogFragment chooseTemplateDialogFragment();

    @ContributesAndroidInjector
    abstract AccountRemovalDialog accountRemovalDialog();

    @ContributesAndroidInjector
    abstract ChooseRichDocumentsTemplateDialogFragment chooseRichDocumentsTemplateDialogFragment();

    @ContributesAndroidInjector
    abstract BackupFragment contactsBackupFragment();

    @ContributesAndroidInjector
    abstract PreviewImageFragment previewImageFragment();

    @ContributesAndroidInjector
    abstract BackupListFragment chooseContactListFragment();

    @ContributesAndroidInjector
    abstract PreviewMediaFragment previewMediaFragment();

    @ContributesAndroidInjector
    abstract PreviewTextFragment previewTextFragment();

    @ContributesAndroidInjector
    abstract ChooseAccountDialogFragment chooseAccountDialogFragment();

    @ContributesAndroidInjector
    abstract SetOnlineStatusBottomSheet setOnlineStatusBottomSheet();

    @ContributesAndroidInjector
    abstract PreviewTextFileFragment previewTextFileFragment();

    @ContributesAndroidInjector
    abstract PreviewTextStringFragment previewTextStringFragment();

    @ContributesAndroidInjector
    abstract UnifiedSearchFragment searchFragment();

    @ContributesAndroidInjector
    abstract GalleryFragment photoFragment();

    @ContributesAndroidInjector
    abstract MultipleAccountsDialog multipleAccountsDialog();

    @ContributesAndroidInjector
    abstract ReceiveExternalFilesActivity.DialogInputUploadFilename dialogInputUploadFilename();

    @ContributesAndroidInjector
    abstract BootupBroadcastReceiver bootupBroadcastReceiver();

    @ContributesAndroidInjector
    abstract NetworkChangeReceiver networkChangeReceiver();

    @ContributesAndroidInjector
    abstract NotificationWork.NotificationReceiver notificationWorkBroadcastReceiver();

    @ContributesAndroidInjector
    abstract FileContentProvider fileContentProvider();

    @ContributesAndroidInjector
    abstract UsersAndGroupsSearchProvider usersAndGroupsSearchProvider();

    @ContributesAndroidInjector
    abstract DiskLruImageCacheFileProvider diskLruImageCacheFileProvider();

    @ContributesAndroidInjector
    abstract DocumentsStorageProvider documentsStorageProvider();

    @ContributesAndroidInjector
    abstract AccountManagerService accountManagerService();

    @ContributesAndroidInjector
    abstract OperationsService operationsService();

    @ContributesAndroidInjector
    abstract PlayerService playerService();

    @ContributesAndroidInjector
    abstract FileTransferService fileDownloaderService();

    @ContributesAndroidInjector
    abstract FileSyncService fileSyncService();

    @ContributesAndroidInjector
    abstract DashboardWidgetService dashboardWidgetService();

    @ContributesAndroidInjector
    abstract PreviewPdfFragment previewPDFFragment();

    @ContributesAndroidInjector
    abstract SharedListFragment sharedFragment();

    @ContributesAndroidInjector
    abstract FeatureFragment featureFragment();

    @ContributesAndroidInjector
    abstract IndeterminateProgressDialog indeterminateProgressDialog();

    @ContributesAndroidInjector
    abstract SortingOrderDialogFragment sortingOrderDialogFragment();

    @ContributesAndroidInjector
    abstract ConfirmationDialogFragment confirmationDialogFragment();

    @ContributesAndroidInjector
    abstract ConflictsResolveDialog conflictsResolveDialog();

    @ContributesAndroidInjector
    abstract CreateFolderDialogFragment createFolderDialogFragment();

    @ContributesAndroidInjector
    abstract ExpirationDatePickerDialogFragment expirationDatePickerDialogFragment();

    @ContributesAndroidInjector
    abstract FileActivity fileActivity();

    @ContributesAndroidInjector
    abstract FileDownloadFragment fileDownloadFragment();

    @ContributesAndroidInjector
    abstract LoadingDialog loadingDialog();

    @ContributesAndroidInjector
    abstract LocalStoragePathPickerDialogFragment localStoragePathPickerDialogFragment();

    @ContributesAndroidInjector
    abstract LogsViewModel logsViewModel();

    @ContributesAndroidInjector
    abstract MainApp mainApp();

    @ContributesAndroidInjector
    abstract Migrations migrations();

    @ContributesAndroidInjector
    abstract NotificationWork notificationWork();

    @ContributesAndroidInjector
    abstract RemoveFilesDialogFragment removeFilesDialogFragment();

    @ContributesAndroidInjector
    abstract SendShareDialog sendShareDialog();

    @ContributesAndroidInjector
    abstract SetupEncryptionDialogFragment setupEncryptionDialogFragment();

    @ContributesAndroidInjector
    abstract ChooseStorageLocationDialogFragment chooseStorageLocationDialogFragment();

    @ContributesAndroidInjector
    abstract ThemeSelectionDialog themeSelectionDialog();

    @ContributesAndroidInjector
    abstract AppPassCodeDialog appPassCodeDialog();

    @ContributesAndroidInjector
    abstract SharePasswordDialogFragment sharePasswordDialogFragment();

    @ContributesAndroidInjector
    abstract SyncedFolderPreferencesDialogFragment syncedFolderPreferencesDialogFragment();

    @ContributesAndroidInjector
    abstract ToolbarActivity toolbarActivity();

    @ContributesAndroidInjector
    abstract StoragePermissionDialogFragment storagePermissionDialogFragment();

    @ContributesAndroidInjector
    abstract OCFileListBottomSheetDialog ocfileListBottomSheetDialog();

    @ContributesAndroidInjector
    abstract RenameFileDialogFragment renameFileDialogFragment();

    @ContributesAndroidInjector
    abstract SyncFileNotEnoughSpaceDialogFragment syncFileNotEnoughSpaceDialogFragment();

    @ContributesAndroidInjector
    abstract DashboardWidgetConfigurationActivity dashboardWidgetConfigurationActivity();

    @ContributesAndroidInjector
    abstract DashboardWidgetProvider dashboardWidgetProvider();

    @ContributesAndroidInjector
    abstract GalleryFragmentBottomSheetDialog galleryFragmentBottomSheetDialog();

    @ContributesAndroidInjector
    abstract PreviewBitmapActivity previewBitmapActivity();

    @ContributesAndroidInjector
    abstract FileUploadHelper fileUploadHelper();

    @ContributesAndroidInjector
    abstract SslUntrustedCertDialog sslUntrustedCertDialog();

    @ContributesAndroidInjector
    abstract FileActionsBottomSheet fileActionsBottomSheet();

    @ContributesAndroidInjector
    abstract SendFilesDialog sendFilesDialog();

    @ContributesAndroidInjector
    abstract DocumentScanActivity documentScanActivity();

    @ContributesAndroidInjector
    abstract GroupfolderListFragment groupfolderListFragment();

    @ContributesAndroidInjector
    abstract LauncherActivity launcherActivity();

    @ContributesAndroidInjector
    abstract EditImageActivity editImageActivity();

    @ContributesAndroidInjector
    abstract ImageDetailFragment imageDetailFragment();

    @ContributesAndroidInjector
    abstract EtmBackgroundJobsFragment etmBackgroundJobsFragment();

    @ContributesAndroidInjector
    abstract BackgroundJobManagerImpl backgroundJobManagerImpl();

    @ContributesAndroidInjector
    abstract TestJob testJob();

    @ContributesAndroidInjector
    abstract InternalTwoWaySyncActivity internalTwoWaySyncActivity();

    @OptIn(markerClass = UnstableApi.class)
    @ContributesAndroidInjector
    abstract BackgroundPlayerService backgroundPlayerService();

    @ContributesAndroidInjector
    abstract TermsOfServiceDialog termsOfServiceDialog();

    @ContributesAndroidInjector
    abstract SetStatusMessageBottomSheet setStatusMessageBottomSheet();

    @ContributesAndroidInjector
    abstract NavigatorActivity navigatorActivity();

    @ContributesAndroidInjector
    abstract CommunityFragment communityFragment();
}
