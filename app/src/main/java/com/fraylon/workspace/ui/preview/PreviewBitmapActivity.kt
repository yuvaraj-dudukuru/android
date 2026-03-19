/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.fraylon.workspace.ui.preview

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fraylon.workspace.di.Injectable
import com.fraylon.workspace.databinding.ActivityPreviewBitmapBinding
import com.fraylon.workspace.utils.theme.ViewThemeUtils
import javax.inject.Inject

/**
 * Zoomable preview of a single bitmap
 */
class PreviewBitmapActivity :
    AppCompatActivity(),
    Injectable {

    companion object {
        const val EXTRA_BITMAP_PATH = "EXTRA_BITMAP_PATH"
    }

    @Inject
    lateinit var viewThemeUtils: ViewThemeUtils

    private lateinit var binding: ActivityPreviewBitmapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBitmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupImage()

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            viewThemeUtils.files.setWhiteBackButton(this, it)
        }
    }

    private fun setupImage() {
        val path = intent.getStringExtra(EXTRA_BITMAP_PATH)
        require(path != null)

        val bitmap = BitmapFactory.decodeFile(path)
        binding.image.setImageBitmap(bitmap)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
