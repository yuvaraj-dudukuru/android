/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-FileCopyrightText: 2016 Fraylon Inc.
 * SPDX-FileCopyrightText: 2015 María Asensio Valverde <masensio@solidgear.es>
 * SPDX-FileCopyrightText: 2014 David A. Velasco <dvelasco@solidgear.es>
 * SPDX-FileCopyrightText: 2012 Bartosz Przybylski <bart.p.pl@gmail.com>
 * SPDX-License-Identifier: GPL-2.0-only AND (AGPL-3.0-or-later OR GPL-2.0-only)
 */
package com.fraylon.workspace.ui.activity;

import com.fraylon.workspace.jobs.download.FileDownloadWorker;
import com.fraylon.workspace.jobs.upload.FileUploadHelper;
import com.fraylon.workspace.datamodel.FileDataStorageManager;
import com.fraylon.workspace.services.OperationsService.OperationsServiceBinder;
import com.fraylon.workspace.ui.helpers.FileOperationsHelper;

public interface ComponentsGetter {

    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the FileDownloadWorker.
     */
    public FileDownloadWorker.FileDownloadProgressListener getFileDownloadProgressListener();

    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the FileUploader service API.
     */
    public FileUploadHelper getFileUploaderHelper();

    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the OperationsService service API.
     */
    public OperationsServiceBinder getOperationsServiceBinder();

    public FileDataStorageManager getStorageManager();

    public FileOperationsHelper getFileOperationsHelper();
}
