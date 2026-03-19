/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter

import android.content.Context
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.fraylon.workspace.databinding.UnifiedSearchHeaderBinding
import com.fraylon.workspace.ui.unifiedsearch.UnifiedSearchSection
import com.fraylon.workspace.utils.theme.ViewThemeUtils

class UnifiedSearchHeaderViewHolder(
    val binding: UnifiedSearchHeaderBinding,
    val viewThemeUtils: ViewThemeUtils,
    val context: Context
) : SectionedViewHolder(binding.root) {

    fun bind(section: UnifiedSearchSection) {
        binding.title.text = section.name
        viewThemeUtils.platform.colorPrimaryTextViewElement(binding.title)
    }
}
