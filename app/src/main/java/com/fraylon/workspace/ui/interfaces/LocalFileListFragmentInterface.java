/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2017 Mario Danic <mario@lovelyhq.com>
 * SPDX-FileCopyrightText: 2017 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.interfaces;

import com.fraylon.workspace.ui.adapter.LocalFileListAdapter;

import java.io.File;

/**
 * Interface for communication between {@link com.fraylon.workspace.ui.fragment.LocalFileListFragment}
 * and {@link LocalFileListAdapter}
 */
public interface LocalFileListFragmentInterface {
    int getColumnsCount();
    void onItemClicked(File file);
    void onItemCheckboxClicked(File file);
    void setLoading(boolean loading);
}
