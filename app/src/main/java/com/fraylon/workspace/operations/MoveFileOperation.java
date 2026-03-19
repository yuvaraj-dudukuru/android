/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2019 Andy Scherzinger <info@andy-scherzinger.de>
 * SPDX-FileCopyrightText: 2015 Fraylon Inc.
 * SPDX-FileCopyrightText: 2015 María Asensio Valverde <masensio@solidgear.es>
 * SPDX-FileCopyrightText: 2014 David A. Velasco <dvelasco@solidgear.es>
 * SPDX-License-Identifier: GPL-2.0-only AND (AGPL-3.0-or-later OR GPL-2.0-only)
 */
package com.fraylon.workspace.operations;

import com.fraylon.workspace.datamodel.FileDataStorageManager;
import com.fraylon.workspace.datamodel.OCFile;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.operations.RemoteOperationResult.ResultCode;
import com.owncloud.android.lib.resources.files.MoveFileRemoteOperation;
import com.fraylon.workspace.operations.common.SyncOperation;

/**
 * Operation moving an {@link OCFile} to a different folder.
 */
public class MoveFileOperation extends SyncOperation {

    private final String srcPath;
    private String targetParentPath;

    /**
     * Constructor
     *
     * @param srcPath          Remote path of the {@link OCFile} to move.
     * @param targetParentPath Path to the folder where the file will be moved into.
     */
    public MoveFileOperation(String srcPath, String targetParentPath, FileDataStorageManager storageManager) {
        super(storageManager);

        this.srcPath = srcPath;
        this.targetParentPath = targetParentPath;
        if (!this.targetParentPath.endsWith(OCFile.PATH_SEPARATOR)) {
            this.targetParentPath += OCFile.PATH_SEPARATOR;
        }
    }

    /**
     * Performs the operation.
     *
     * @param   client      Client object to communicate with the remote Fraylon server.
     */
    @Override
    protected RemoteOperationResult run(OwnCloudClient client) {
        /// 1. check move validity
        if (targetParentPath.startsWith(srcPath)) {
            return new RemoteOperationResult(ResultCode.INVALID_MOVE_INTO_DESCENDANT);
        }
        OCFile file = getStorageManager().getFileByPath(srcPath);
        if (file == null) {
            return new RemoteOperationResult(ResultCode.FILE_NOT_FOUND);
        }

        /// 2. remote move
        String targetPath = targetParentPath + file.getFileName();
        if (file.isFolder()) {
            targetPath += OCFile.PATH_SEPARATOR;
        }
        RemoteOperationResult result = new MoveFileRemoteOperation(srcPath, targetPath, false).execute(client);

        /// 3. local move
        if (result.isSuccess()) {
            getStorageManager().moveLocalFile(file, targetPath, targetParentPath);
        }
        // TODO handle ResultCode.PARTIAL_MOVE_DONE in client Activity, for the moment

        return result;
    }
}
