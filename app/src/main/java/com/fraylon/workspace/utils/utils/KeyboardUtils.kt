/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 ZetaTom
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022-2023 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.utils

import android.view.Window
import android.widget.EditText
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import javax.inject.Inject

class KeyboardUtils @Inject constructor() {

    fun showKeyboardForEditText(window: Window?, editText: EditText) {
        if (window != null) {
            editText.requestFocus()
            WindowCompat.getInsetsController(window, editText).show(WindowInsetsCompat.Type.ime())
        }
    }
}
