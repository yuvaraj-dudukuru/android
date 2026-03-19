/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.adapter;

import android.view.View;

import com.fraylon.workspace.databinding.FileDetailsSharePublicLinkAddNewItemBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewLinkShareViewHolder extends RecyclerView.ViewHolder {
    private FileDetailsSharePublicLinkAddNewItemBinding binding;

    public NewLinkShareViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public NewLinkShareViewHolder(FileDetailsSharePublicLinkAddNewItemBinding binding) {
        this(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ShareeListAdapterListener listener) {
        binding.addNewPublicShareLink.setOnClickListener(v -> listener.createPublicShareLink());
    }
}
