/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2023 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.datamodel.e2e.v2.encrypted

import com.fraylon.workspace.datamodel.e2e.v2.decrypted.DecryptedFile

class FiledropData {
    private val files: Map<String, DecryptedFile> = mutableMapOf()
}
