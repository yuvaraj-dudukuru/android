/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2021 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.fragment.contactsbackup

import android.view.View
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.fraylon.workspace.databinding.ContactlistListItemBinding

class ContactItemViewHolder internal constructor(var binding: ContactlistListItemBinding) :
    SectionedViewHolder(binding.root) {
    init {
        binding.root.tag = this
    }

    fun setVCardListener(onClickListener: View.OnClickListener?) {
        itemView.setOnClickListener(onClickListener)
    }
}
