/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2023 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextcloud.android.lib.resources.groupfolders.Groupfolder
import com.fraylon.workspace.di.Injectable
import com.fraylon.workspace.logger.Logger
import com.fraylon.workspace.MainApp
import com.fraylon.workspace.R
import com.fraylon.workspace.ui.EmptyRecyclerView
import com.fraylon.workspace.ui.activity.FileDisplayActivity
import com.fraylon.workspace.ui.adapter.GroupFolderListAdapter
import com.fraylon.workspace.ui.asynctasks.GroupfoldersSearchTask
import com.fraylon.workspace.ui.interfaces.GroupfolderListInterface
import javax.inject.Inject

/**
 * A Fragment that lists groupfolders
 */
class GroupfolderListFragment :
    OCFileListFragment(),
    Injectable,
    GroupfolderListInterface {

    lateinit var adapter: GroupFolderListAdapter

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchFragment = true
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentSearchType = SearchType.GROUPFOLDER
        menuItemAddRemoveValue = MenuItemAddRemove.REMOVE_GRID_AND_SORT
        requireActivity().invalidateOptionsMenu()

        search()
    }

    public override fun setAdapter(args: Bundle?) {
        adapter = GroupFolderListAdapter(requireContext(), viewThemeUtils, this, preferences.isDarkModeEnabled)
        setRecyclerViewAdapter(adapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        (recyclerView as EmptyRecyclerView).setHasFooter(false)
    }

    private fun search() {
        GroupfoldersSearchTask(
            this,
            accountManager.user,
            mContainerActivity.storageManager
        ).execute()
    }

    override fun onResume() {
        super.onResume()
        Handler().post {
            if (activity is FileDisplayActivity) {
                val fileDisplayActivity = activity as FileDisplayActivity
                fileDisplayActivity.updateActionBarTitleAndHomeButtonByString(
                    getString(R.string.drawer_item_groupfolders)
                )
                fileDisplayActivity.setMainFabVisible(false)
            }
        }
    }

    override fun onRefresh() {
        super.onRefresh()

        search()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(result: Map<String, Groupfolder>) {
        adapter.setData(result)
        adapter.notifyDataSetChanged()
    }

    override fun onFolderClick(path: String) {
        MainApp.showOnlyFilesOnDevice(false)
        Intent(
            context,
            FileDisplayActivity::class.java
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            action = ACTION_VIEW
            putExtra(FileDisplayActivity.KEY_FILE_PATH, path)
            startActivity(this)
        }
    }
}
