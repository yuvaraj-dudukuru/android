/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 TSI-mc <surinder.kumar@t-systems.com>
 * SPDX-FileCopyrightText: 2019 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fraylon.workspace.documentscan.DocumentScanViewModel
import com.fraylon.workspace.etm.EtmViewModel
import com.fraylon.workspace.logger.ui.LogsViewModel
import com.fraylon.ui.fileactions.FileActionsViewModel
import com.fraylon.workspace.ui.preview.pdf.PreviewPdfViewModel
import com.fraylon.ui.trashbinFileActions.TrashbinFileActionsViewModel
import com.fraylon.workspace.ui.unifiedsearch.UnifiedSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(EtmViewModel::class)
    abstract fun etmViewModel(vm: EtmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LogsViewModel::class)
    abstract fun logsViewModel(vm: LogsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UnifiedSearchViewModel::class)
    abstract fun unifiedSearchViewModel(vm: UnifiedSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreviewPdfViewModel::class)
    abstract fun previewPDFViewModel(vm: PreviewPdfViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FileActionsViewModel::class)
    abstract fun fileActionsViewModel(vm: FileActionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DocumentScanViewModel::class)
    abstract fun documentScanViewModel(vm: DocumentScanViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrashbinFileActionsViewModel::class)
    abstract fun trashbinFileActionsViewModel(vm: TrashbinFileActionsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
