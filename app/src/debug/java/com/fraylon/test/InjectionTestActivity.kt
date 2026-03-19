/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2023 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fraylon.workspace.di.Injectable
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.databinding.ActivityInjectionTestBinding
import javax.inject.Inject

/**
 * Sample activity to check test overriding injections
 */
class InjectionTestActivity :
    AppCompatActivity(),
    Injectable {
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInjectionTestBinding.inflate(layoutInflater)
        // random pref, just needs to match the one in the test
        binding.text.text = appPreferences.lastUploadPath
        setContentView(binding.root)
    }
}
