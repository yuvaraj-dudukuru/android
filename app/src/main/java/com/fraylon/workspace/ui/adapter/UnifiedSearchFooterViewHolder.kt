/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2021 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2021 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter

import android.content.Context
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.fraylon.workspace.databinding.UnifiedSearchFooterBinding
import com.fraylon.workspace.ui.interfaces.UnifiedSearchListInterface
import com.fraylon.workspace.ui.unifiedsearch.UnifiedSearchSection

class UnifiedSearchFooterViewHolder(
    val binding: UnifiedSearchFooterBinding,
    val context: Context,
    private val listInterface: UnifiedSearchListInterface
) : SectionedViewHolder(binding.root) {

    fun bind(section: UnifiedSearchSection) {
        binding.unifiedSearchFooterLayout.setOnClickListener {
            listInterface.onLoadMoreClicked(section.providerID)
        }
    }
}
