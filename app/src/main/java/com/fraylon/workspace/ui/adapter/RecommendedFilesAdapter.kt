/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fraylon.workspace.databinding.RecommendedFileItemBinding
import com.fraylon.workspace.datamodel.OCFile

class RecommendedFilesAdapter(
    private val fileListAdapter: OCFileListAdapter,
    private val recommendations: ArrayList<OCFile>
) : RecyclerView.Adapter<OCFileListRecommendedItemViewHolder>() {

    fun getItemPosition(file: OCFile): Int = recommendations.indexOf(file)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OCFileListRecommendedItemViewHolder {
        val binding = RecommendedFileItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OCFileListRecommendedItemViewHolder(binding)
    }

    override fun getItemCount(): Int = recommendations.size

    override fun onBindViewHolder(holder: OCFileListRecommendedItemViewHolder, position: Int) {
        val item = recommendations[position]
        fileListAdapter.bindRecommendedFilesHolder(holder, item)
        holder.reason.text = item.reason
    }
}
