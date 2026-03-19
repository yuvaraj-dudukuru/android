/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fraylon.workspace.account.User
import com.fraylon.utils.extensions.getParcelableArgument
import com.fraylon.workspace.R
import com.fraylon.workspace.ui.dialog.setupEncryption.SetupEncryptionDialogFragment
import com.fraylon.workspace.utils.DisplayUtils

class SetupEncryptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent?.getParcelableArgument("EXTRA_USER", User::class.java)

        if (user == null) {
            DisplayUtils.showSnackMessage(this, R.string.error_showing_encryption_dialog)
            finish()
        }

        val setupEncryptionDialogFragment = SetupEncryptionDialogFragment.newInstance(user, null)
        supportFragmentManager.setFragmentResultListener(
            SetupEncryptionDialogFragment.RESULT_REQUEST_KEY,
            this
        ) { requestKey, result ->
            if (requestKey == SetupEncryptionDialogFragment.RESULT_REQUEST_KEY) {
                if (!result.getBoolean(SetupEncryptionDialogFragment.RESULT_KEY_CANCELLED, false)) {
                    setResult(
                        SetupEncryptionDialogFragment.SETUP_ENCRYPTION_RESULT_CODE,
                        buildResultIntentFromBundle(result)
                    )
                }
            }
            finish()
        }
        setupEncryptionDialogFragment.show(supportFragmentManager, "setup_encryption")
    }

    private fun buildResultIntentFromBundle(result: Bundle): Intent {
        val intent = Intent()
        intent.putExtra(
            SetupEncryptionDialogFragment.SUCCESS,
            result.getBoolean(SetupEncryptionDialogFragment.SUCCESS)
        )
        intent.putExtra(
            SetupEncryptionDialogFragment.ARG_FILE_PATH,
            result.getInt(SetupEncryptionDialogFragment.ARG_FILE_PATH)
        )
        return intent
    }
}
