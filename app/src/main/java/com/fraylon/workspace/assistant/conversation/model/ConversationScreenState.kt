/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2025 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.fraylon.workspace.assistant.conversation.model

import com.fraylon.workspace.R

sealed class ConversationScreenState {
    data object Loading : ConversationScreenState()

    data class EmptyContent(val descriptionId: Int) : ConversationScreenState()

    companion object {
        fun emptyConversationList(): ConversationScreenState = EmptyContent(
            descriptionId = R.string.conversation_screen_empty_conversation_list_title
        )
    }
}
