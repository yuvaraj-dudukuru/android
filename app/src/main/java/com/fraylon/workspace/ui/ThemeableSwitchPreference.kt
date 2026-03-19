/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2026 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.ui

import android.content.Context
import android.preference.SwitchPreference
import android.util.AttributeSet
import android.view.View
import com.google.android.material.materialswitch.MaterialSwitch
import com.fraylon.workspace.MainApp
import com.fraylon.workspace.R
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import javax.inject.Inject

@Suppress("DEPRECATION")
class ThemeableSwitchPreference : SwitchPreference {
    @Inject
    lateinit var viewThemeUtils: ViewThemeUtils

    /**
     * Do not delete constructor. These are used.
     */
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        MainApp.getAppComponent().inject(this)
        setWidgetLayoutResource(R.layout.themeable_switch)
    }

    @Deprecated("Deprecated in Java")
    override fun onBindView(view: View) {
        super.onBindView(view)
        val checkable = view.findViewById<View>(R.id.switch_widget)
        if (checkable is MaterialSwitch) {
            checkable.setChecked(isChecked)
            viewThemeUtils.material.colorMaterialSwitch(checkable)
        }
    }
}
