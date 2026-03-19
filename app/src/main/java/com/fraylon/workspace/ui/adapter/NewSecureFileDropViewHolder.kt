/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fraylon.workspace.databinding.FileDetailsShareSecureFileDropAddNewItemBinding

internal class NewSecureFileDropViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: FileDetailsShareSecureFileDropAddNewItemBinding? = null

    constructor(binding: FileDetailsShareSecureFileDropAddNewItemBinding) : this(binding.root) {
        this.binding = binding
    }

    fun bind(listener: ShareeListAdapterListener) {
        binding!!.addNewSecureFileDrop.setOnClickListener { v: View? -> listener.createSecureFileDrop() }
    }
}
