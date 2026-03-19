/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.fraylon.workspace.utils

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.fraylon.workspace.account.UserAccountManager
import com.fraylon.workspace.account.UserAccountManagerImpl
import com.fraylon.workspace.mixins.SessionMixin
import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.ui.activity.FileDisplayActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionMixinTest : AbstractIT() {

    private lateinit var userAccountManager: UserAccountManager
    private lateinit var session: SessionMixin

    private var scenario: ActivityScenario<FileDisplayActivity>? = null
    val intent = Intent(ApplicationProvider.getApplicationContext(), FileDisplayActivity::class.java)

    @get:Rule
    val activityRule = ActivityScenarioRule<FileDisplayActivity>(intent)

    @Before
    fun setUp() {
        userAccountManager = UserAccountManagerImpl.fromContext(targetContext)

        scenario = activityRule.scenario
        scenario?.onActivity { sut ->
            session = SessionMixin(
                sut,
                userAccountManager
            )
        }
    }

    @Test
    fun startAccountCreation() {
        session.startAccountCreation()

        scenario = activityRule.scenario
        scenario?.onActivity { sut ->
            assert(sut.account.name == userAccountManager.accounts.first().name)
        }
    }
}
