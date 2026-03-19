/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2023 Andy Scherzinger <info@andy-scherzinger.de>
 * SPDX-FileCopyrightText: 2023-2024 TSI-mc <surinder.kumar@t-systems.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.nmc.android.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.utils.mdm.MDMConfig
import com.fraylon.workspace.R
import com.fraylon.workspace.authentication.AuthenticatorActivity
import com.fraylon.workspace.databinding.ActivitySplashBinding
import com.fraylon.workspace.ui.activity.BaseActivity
import com.fraylon.workspace.ui.activity.FileDisplayActivity
import com.fraylon.workspace.ui.activity.SettingsActivity
import javax.inject.Inject

class LauncherActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Mandatory to call this before super method to show system launch screen for api level 31+
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        updateTitleVisibility()
        scheduleSplashScreen()
    }

    @VisibleForTesting
    fun setSplashTitles(boldText: String, normalText: String) {
        binding.splashScreenBold.visibility = View.VISIBLE
        binding.splashScreenNormal.visibility = View.VISIBLE

        binding.splashScreenBold.text = boldText
        binding.splashScreenNormal.text = normalText
    }

    private fun updateTitleVisibility() {
        if (TextUtils.isEmpty(resources.getString(R.string.splashScreenBold))) {
            binding.splashScreenBold.visibility = View.GONE
        }
        if (TextUtils.isEmpty(resources.getString(R.string.splashScreenNormal))) {
            binding.splashScreenNormal.visibility = View.GONE
        }
    }

    private fun scheduleSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (user.isPresent) {
                if (MDMConfig.enforceProtection(this) && appPreferences.lockPreference == SettingsActivity.LOCK_NONE) {
                    startActivity(Intent(this, SettingsActivity::class.java))
                } else {
                    startActivity(Intent(this, FileDisplayActivity::class.java))
                }
            } else {
                startActivity(Intent(this, AuthenticatorActivity::class.java))
            }
            finish()
        }, SPLASH_DURATION)
    }

    companion object {
        const val SPLASH_DURATION = 1500L
    }
}
