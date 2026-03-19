/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.etm

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.utils.ScreenshotTest
import org.junit.Test

class EtmActivityTest : AbstractIT() {
    private val testClassName = "com.fraylon.workspace.etm.EtmActivityTest"

    @Test
    @ScreenshotTest
    fun overview() {
        launchActivity<EtmActivity>().use { scenario ->
            val screenShotName = createName(testClassName + "_" + "overview", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity { sut ->
                screenshotViaName(sut, screenShotName)
            }
        }
    }

    @Test
    @ScreenshotTest
    fun accounts() {
        launchActivity<EtmActivity>().use { scenario ->
            val screenShotName = createName(testClassName + "_" + "accounts", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity { sut ->
                sut.vm.onPageSelected(1)
                screenshotViaName(sut, screenShotName)
            }
        }
    }
}
