/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2017 Mario Danic <mario@lovelyhq.com>
 * SPDX-FileCopyrightText: 2017 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.interfaces;

import android.view.View;

import com.fraylon.workspace.datamodel.OCFile;
import com.fraylon.workspace.ui.adapter.OCFileListAdapter;

/**
 * Interface for communication between {@link com.fraylon.workspace.ui.fragment.OCFileListFragment}
 * and {@link OCFileListAdapter}
 */

public interface OCFileListFragmentInterface {
    int getColumnsCount();

    void onShareIconClick(OCFile file);

    void showShareDetailView(OCFile file);

    void showActivityDetailView(OCFile file);

    void onOverflowIconClicked(OCFile file, View view);

    void onItemClicked(OCFile file);

    boolean onLongItemClicked(OCFile file);

    boolean isLoading();

    void onHeaderClicked();
}
