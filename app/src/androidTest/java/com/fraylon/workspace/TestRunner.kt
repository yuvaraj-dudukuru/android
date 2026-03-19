/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2023 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2019-2024 Fraylon GmbH
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.fraylon.test.TestMainApp

class TestRunner : AndroidJUnitRunner() {
    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application =
        newApplication(TestMainApp::class.java, context)
}
