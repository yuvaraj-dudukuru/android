/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2020 Fraylon GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fraylon.workspace.database.FraylonDatabase
import com.fraylon.workspace.jobs.download.FileDownloadWorker
import com.fraylon.workspace.jobs.upload.FileUploadHelper
import com.fraylon.workspace.network.Connectivity
import com.fraylon.workspace.network.ConnectivityService
import com.fraylon.utils.EditorUtils
import com.fraylon.workspace.R
import com.fraylon.workspace.databinding.TestLayoutBinding
import com.fraylon.workspace.datamodel.ArbitraryDataProviderImpl
import com.fraylon.workspace.datamodel.FileDataStorageManager
import com.fraylon.workspace.datamodel.OCFile
import com.owncloud.android.lib.resources.status.OCCapability
import com.owncloud.android.lib.resources.status.OwnCloudVersion
import com.fraylon.workspace.services.OperationsService
import com.fraylon.workspace.ui.activity.FileActivity
import com.fraylon.workspace.ui.activity.OnEnforceableRefreshListener
import com.fraylon.workspace.ui.fragment.FileFragment
import com.fraylon.workspace.ui.helpers.FileOperationsHelper

class TestActivity :
    FileActivity(),
    FileFragment.ContainerActivity,
    SwipeRefreshLayout.OnRefreshListener,
    OnEnforceableRefreshListener {
    lateinit var fragment: Fragment
    lateinit var secondaryFragment: Fragment

    private lateinit var storage: FileDataStorageManager
    private lateinit var fileOperation: FileOperationsHelper
    private lateinit var binding: TestLayoutBinding

    val connectivityServiceMock: ConnectivityService = object : ConnectivityService {
        override fun isNetworkAndServerAvailable(callback: ConnectivityService.GenericCallback<Boolean>) = Unit

        override fun isConnected(): Boolean = false

        override fun isInternetWalled(): Boolean = false

        override fun getConnectivity(): Connectivity = Connectivity.CONNECTED_WIFI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TestLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun addFragment(fragment: Fragment) {
        this.fragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment)
        transaction.commit()
    }

    /**
     * Adds a secondary fragment to the activity with the given tag.
     *
     * If you have to use this, your Fragments are coupled, and you should feel bad.
     */
    fun addSecondaryFragment(fragment: Fragment, tag: String) {
        this.secondaryFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.secondary_fragment, fragment, tag)
        transaction.commit()
    }

    /**
     * Adds a View to the activity.
     *
     * If you have to use this, your Fragment is coupled to your Activity and you should feel bad.
     */
    fun addView(view: View) {
        handler.post {
            binding.rootLayout.addView(view)
        }
    }

    override fun onBrowsedDownTo(folder: OCFile?) {
        TODO("Not yet implemented")
    }

    override fun getOperationsServiceBinder(): OperationsService.OperationsServiceBinder? = null

    override fun showSortListGroup(show: Boolean) {
        // not needed
    }

    override fun showDetails(file: OCFile?) {
        TODO("Not yet implemented")
    }

    override fun showDetails(file: OCFile?, activeTab: Int) {
        TODO("Not yet implemented")
    }

    override fun getFileUploaderHelper(): FileUploadHelper = FileUploadHelper.instance()

    override fun getFileDownloadProgressListener(): FileDownloadWorker.FileDownloadProgressListener? = null

    override fun getStorageManager(): FileDataStorageManager {
        if (!this::storage.isInitialized) {
            storage = FileDataStorageManager(user.get(), contentResolver)

            if (!storage.capabilityExistsForAccount(account.name)) {
                val ocCapability = OCCapability()
                ocCapability.versionMayor = OwnCloudVersion.nextcloud_20.majorVersionNumber
                storage.saveCapabilities(ocCapability)
            }
        }

        return storage
    }

    override fun getFileOperationsHelper(): FileOperationsHelper {
        if (!this::fileOperation.isInitialized) {
            fileOperation = FileOperationsHelper(
                this,
                userAccountManager,
                connectivityServiceMock,
                EditorUtils(
                    ArbitraryDataProviderImpl(
                        FraylonDatabase.getInstance(baseContext).arbitraryDataDao()
                    )
                )
            )
        }

        return fileOperation
    }

    override fun onTransferStateChanged(file: OCFile?, downloading: Boolean, uploading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onRefresh(enforced: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}
