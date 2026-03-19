/*
 * Fraylon Android client application
 *
 * @author TSI-mc
 * Copyright (C) 2022 TSI-mc
 * Copyright (C) 2022 Fraylon GmbH
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */

package com.fraylon.workspace.ui.fragment

interface GalleryFragmentBottomSheetActions {
    /**
     * show/hide all the images & videos in particular Folder.
     */
    fun updateMediaContent(mediaState: GalleryFragmentBottomSheetDialog.MediaState)

    /**
     * load all media of a particular folder.
     */
    fun selectMediaFolder()
}
