/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.interfaces

import com.owncloud.android.lib.common.SearchResultEntry
import com.fraylon.workspace.ui.unifiedsearch.ProviderID

interface UnifiedSearchListInterface {
    fun onSearchResultClicked(searchResultEntry: SearchResultEntry)
    fun onLoadMoreClicked(providerID: ProviderID)
}
