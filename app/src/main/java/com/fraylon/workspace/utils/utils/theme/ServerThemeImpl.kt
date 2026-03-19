/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.utils.theme

import com.nextcloud.android.common.ui.color.ColorUtil
import com.nextcloud.android.common.ui.theme.ServerTheme
import com.fraylon.workspace.R
import com.owncloud.android.lib.resources.status.OCCapability
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ServerThemeImpl @AssistedInject constructor(colorUtil: ColorUtil, @Assisted capability: OCCapability) :
    ServerTheme {
    override val colorElement: Int
    override val colorElementBright: Int
    override val colorElementDark: Int
    override val colorText: Int
    override val primaryColor: Int

    init {
        primaryColor =
            colorUtil.getNullSafeColorWithFallbackRes(capability.serverColor, R.color.primary)
        colorElement = colorUtil.getNullSafeColor(capability.serverElementColor, primaryColor)
        colorElementBright =
            colorUtil.getNullSafeColor(capability.serverElementColorBright, primaryColor)
        colorElementDark = colorUtil.getNullSafeColor(capability.serverElementColorDark, primaryColor)
        colorText = colorUtil.getTextColor(capability.serverTextColor, primaryColor)
    }

    @AssistedFactory
    interface Factory {
        fun create(capability: OCCapability): ServerThemeImpl
    }
}
