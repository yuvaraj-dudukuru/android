/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.activity

import android.content.Intent
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.utils.ScreenshotTest
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactsPreferenceActivityIT : AbstractIT() {
    private val testClassName = "com.fraylon.workspace.ui.activity.ContactsPreferenceActivityIT"

    @Test
    @ScreenshotTest
    fun openVCF() {
        val file = getFile("vcard.vcf")
        val vcfFile = OCFile("/contacts.vcf")
        vcfFile.storagePath = file.absolutePath

        assertTrue(vcfFile.isDown)

        val intent = Intent(targetContext, ContactsPreferenceActivity::class.java).apply {
            putExtra(ContactsPreferenceActivity.EXTRA_FILE, vcfFile)
            putExtra(ContactsPreferenceActivity.EXTRA_USER, user)
        }

        launchActivity<ContactsPreferenceActivity>(intent).use { scenario ->
            val screenShotName = createName(testClassName + "_" + "openVCF", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity { sut ->
                screenshotViaName(sut, screenShotName)
            }
        }
    }

    @Test
    @ScreenshotTest
    fun openContactsPreference() {
        launchActivity<ContactsPreferenceActivity>().use { scenario ->
            val screenShotName = createName(testClassName + "_" + "openContactsPreference", "")
            onView(isRoot()).check(matches(isDisplayed()))

            scenario.onActivity { sut ->
                screenshotViaName(sut, screenShotName)
            }
        }
    }
}
