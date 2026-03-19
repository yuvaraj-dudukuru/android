/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2016-2024 Fraylon GmbH and Fraylon contributors
 * SPDX-FileCopyrightText: 2024 TSI-mc <surinder.kumar@t-systems.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;

import com.nextcloud.android.common.ui.util.extensions.AppCompatActivityExtensionsKt;
import com.fraylon.workspace.account.User;
import com.fraylon.workspace.account.UserAccountManager;
import com.fraylon.workspace.di.Injectable;
import com.fraylon.workspace.mixins.MixinRegistry;
import com.fraylon.workspace.mixins.SessionMixin;
import com.fraylon.workspace.preferences.AppPreferences;
import com.fraylon.workspace.preferences.DarkMode;
import com.fraylon.repository.ClientRepository;
import com.fraylon.repository.RemoteClientRepository;
import com.fraylon.workspace.datamodel.FileDataStorageManager;
import com.fraylon.workspace.datamodel.OCFile;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.lib.resources.status.OCCapability;

import java.util.Optional;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Base activity with common behaviour for activities dealing with Fraylon {@link Account}s .
 */
public abstract class BaseActivity extends AppCompatActivity implements Injectable {

    private static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * Tracks whether the activity should be recreate()'d after a theme change
     */
    private boolean themeChangePending;
    private boolean paused;
    protected boolean enableAccountHandling = true;

    private final MixinRegistry mixinRegistry = new MixinRegistry();
    private SessionMixin sessionMixin;

    @Inject UserAccountManager accountManager;
    @Inject AppPreferences preferences;
    @Inject FileDataStorageManager fileDataStorageManager;

    private final AppPreferences.Listener onPreferencesChanged = new AppPreferences.Listener() {
        @Override
        public void onDarkThemeModeChanged(DarkMode mode) {
            onThemeSettingsModeChanged();
        }
    };

    public UserAccountManager getUserAccountManager() {
        return accountManager;
    }

    private ClientRepository clientRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatActivityExtensionsKt.applyEdgeToEdgeWithSystemBarPadding(this);
        super.onCreate(savedInstanceState);
        sessionMixin = new SessionMixin(this, accountManager);
        mixinRegistry.add(sessionMixin);

        if (enableAccountHandling) {
            mixinRegistry.onCreate(savedInstanceState);
        }

        clientRepository = new RemoteClientRepository(accountManager.getUser(), this, this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        preferences.addListener(onPreferencesChanged);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mixinRegistry.onDestroy();
        preferences.removeListener(onPreferencesChanged);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mixinRegistry.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (enableAccountHandling) {
            mixinRegistry.onResume();
        }
        paused = false;

        if (themeChangePending) {
            recreate();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mixinRegistry.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        Log_OC.v(TAG, "onRestart() start");
        super.onRestart();
        if (enableAccountHandling) {
            mixinRegistry.onRestart();
        }
    }

    private void onThemeSettingsModeChanged() {
        if (paused) {
            themeChangePending = true;
        } else {
            recreate();
        }
    }

    /**
     * Sets and validates the Fraylon {@link Account} associated to the Activity.
     *
     * If not valid, tries to swap it for other valid and existing Fraylon {@link Account}.
     *
     * @param account      New {@link Account} to set.
     * @param savedAccount When 'true', account was retrieved from a saved instance state.
     */
    @Deprecated
    protected void setAccount(Account account, boolean savedAccount) {
        sessionMixin.setAccount(account);
    }

    protected void setUser(User user) {
        sessionMixin.setUser(user);
    }

    protected void startAccountCreation() {
        sessionMixin.startAccountCreation();
    }

    /**
     * Getter for the capabilities of the server where the current OC account lives.
     *
     * @return Capabilities of the server where the current OC account lives. Null if the account is not
     * set yet.
     */
    public OCCapability getCapabilities() {
        return sessionMixin.getCapabilities();
    }

    /**
     * Getter for the Fraylon {@link Account} where the main {@link OCFile} handled by the activity
     * is located.
     *
     * @return OwnCloud {@link Account} where the main {@link OCFile} handled by the activity
     * is located.
     */
    public Account getAccount() {
        return sessionMixin.getCurrentAccount();
    }

    public Optional<User> getUser() {
        return sessionMixin.getUser();
    }

    public FileDataStorageManager getStorageManager() {
        return fileDataStorageManager;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

}
