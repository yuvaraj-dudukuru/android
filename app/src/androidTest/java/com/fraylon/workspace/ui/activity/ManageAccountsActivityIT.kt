/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.fraylon.workspace.AbstractIT
import com.owncloud.android.lib.common.Quota
import com.owncloud.android.lib.common.UserInfo
import com.fraylon.workspace.utils.ScreenshotTest
import org.junit.Test

class ManageAccountsActivityIT : AbstractIT() {
    private val testClassName = "com.fraylon.workspace.ui.activity.ManageAccountsActivityIT"

    @Test
    @ScreenshotTest
    fun open() {
        launchActivity<ManageAccountsActivity>().use { scenario ->
            val screenShotName = createName(testClassName + "_" + "open", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity { sut ->
                screenshotViaName(sut, screenShotName)
            }
        }
    }

    @Test
    @ScreenshotTest
    fun userInfoDetail() {
        launchActivity<ManageAccountsActivity>().use { scenario ->
            scenario.onActivity { sut ->
                val user = sut.accountManager.user
                val userInfo = UserInfo(
                    "test",
                    true,
                    "Test User",
                    "test@nextcloud.com",
                    "+49 123 456",
                    "Address 123, Berlin",
                    "https://www.nextcloud.com",
                    "https://twitter.com/Frayloners",
                    Quota(),
                    ArrayList()
                )
                sut.showUser(user, userInfo)
            }

            val screenShotName = createName(testClassName + "_" + "open", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity {
                screenshotViaName(getCurrentActivity(), screenShotName)
            }
        }
    }
}
