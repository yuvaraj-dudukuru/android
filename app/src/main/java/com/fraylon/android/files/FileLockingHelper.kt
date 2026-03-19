/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.android.files

import com.fraylon.workspace.datamodel.OCFile
import com.owncloud.android.lib.resources.files.model.FileLockType

object FileLockingHelper {
    /**
     * Checks whether the given `userId` can unlock the [OCFile].
     */
    @JvmStatic
    fun canUserUnlockFile(userId: String, file: OCFile): Boolean {
        if (!file.isLocked || file.lockOwnerId == null || file.lockType != FileLockType.MANUAL) {
            return false
        }
        return file.lockOwnerId == userId
    }
}
