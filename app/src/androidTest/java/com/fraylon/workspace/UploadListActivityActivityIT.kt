/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.R
import com.fraylon.workspace.ui.activity.UploadListActivity
import com.fraylon.workspace.utils.ScreenshotTest
import org.junit.Test

class UploadListActivityActivityIT : AbstractIT() {
    private val testClassName = "com.fraylon.workspace.UploadListActivityActivityIT"

    @Test
    @ScreenshotTest
    fun openDrawer() {
        launchActivity<UploadListActivity>().use { scenario ->
            onView(isRoot()).check(matches(isDisplayed()))
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())

            val screenShotName = createName(testClassName + "_" + "openDrawer", "")

            scenario.onActivity { sut ->
                screenshotViaName(sut, screenShotName)
            }
        }
    }
}
