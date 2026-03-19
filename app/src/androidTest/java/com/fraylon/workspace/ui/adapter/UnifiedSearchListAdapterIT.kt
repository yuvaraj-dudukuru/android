/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2026 Alper Ozturk <alper.ozturk@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.fraylon.workspace.ui.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import com.fraylon.workspace.account.UserAccountManagerImpl
import com.fraylon.workspace.preferences.AppPreferences
import com.fraylon.workspace.preferences.AppPreferencesImpl
import com.fraylon.workspace.AbstractIT
import com.fraylon.workspace.datamodel.OCFile
import com.fraylon.workspace.datamodel.SyncedFolderProvider
import com.owncloud.android.lib.common.SearchResultEntry
import com.fraylon.workspace.ui.activity.FileDisplayActivity
import com.fraylon.workspace.ui.interfaces.UnifiedSearchCurrentDirItemAction
import com.fraylon.workspace.ui.interfaces.UnifiedSearchListInterface
import com.fraylon.workspace.ui.unifiedsearch.ProviderID
import com.fraylon.workspace.ui.unifiedsearch.UnifiedSearchSection
import com.fraylon.workspace.utils.MimeType
import com.fraylon.workspace.utils.ScreenshotTest
import com.fraylon.workspace.utils.overlay.OverlayManager
import org.junit.Before
import org.junit.Test

@Suppress("TooManyFunctions")
class UnifiedSearchListAdapterIT : AbstractIT() {

    private val testClassName = "com.fraylon.workspace.ui.adapter.UnifiedSearchListAdapterIT"

    private lateinit var overlayManager: OverlayManager
    private lateinit var preferences: AppPreferences

    @Suppress("DEPRECATION")
    @Before
    fun setup() {
        preferences = AppPreferencesImpl.fromContext(targetContext)
    }

    // region Fake Data

    private fun makeFolderEntry() = SearchResultEntry(
        thumbnailUrl = "",
        title = "My Folder",
        subline = "Documents/My Folder",
        resourceUrl = "",
        icon = "icon-folder",
        rounded = false,
        attributes = mapOf("path" to "/Documents/My Folder")
    )

    private fun makeFileEntry() = SearchResultEntry(
        thumbnailUrl = "",
        title = "report.pdf",
        subline = "Documents/report.pdf",
        resourceUrl = "",
        icon = "application-pdf",
        rounded = false,
        attributes = mapOf("path" to "/Documents/report.pdf", "fileId" to "12345")
    )

    private fun makeContactEntry() = SearchResultEntry(
        thumbnailUrl = "",
        title = "John Doe",
        subline = "john@example.com",
        resourceUrl = "",
        icon = "icon-contacts",
        rounded = true,
        attributes = emptyMap()
    )

    private fun makeCalendarEntry() = SearchResultEntry(
        thumbnailUrl = "",
        title = "Team Meeting",
        subline = "Today at 10:00 AM",
        resourceUrl = "",
        icon = "icon-calendar",
        rounded = false,
        attributes = emptyMap()
    )

    private fun makeAppEntry() = SearchResultEntry(
        thumbnailUrl = "",
        title = "Settings",
        subline = "App",
        resourceUrl = "",
        icon = "icon-settings",
        rounded = false,
        attributes = emptyMap()
    )

    private fun makeSections(vararg pairs: Pair<String, List<SearchResultEntry>>): List<UnifiedSearchSection> =
        pairs.map { (name, entries) ->
            UnifiedSearchSection(
                providerID = name.lowercase().replace(" ", "_"),
                name = name,
                entries = entries,
                hasMoreResults = false
            )
        }

    private fun makeSectionsWithMore(vararg pairs: Pair<String, List<SearchResultEntry>>): List<UnifiedSearchSection> =
        pairs.map { (name, entries) ->
            UnifiedSearchSection(
                providerID = name.lowercase().replace(" ", "_"),
                name = name,
                entries = entries,
                hasMoreResults = true
            )
        }

    // endregion

    // region Helpers

    private val noopListInterface = object : UnifiedSearchListInterface {
        override fun onSearchResultClicked(searchResultEntry: SearchResultEntry) = Unit
        override fun onLoadMoreClicked(providerID: ProviderID) = Unit
    }

    private val noopCurrentDirAction = object : UnifiedSearchCurrentDirItemAction {
        override fun openFile(remotePath: String, showMoreActions: Boolean) = Unit
    }

    private fun setupAdapterOnActivity(
        sut: FileDisplayActivity,
        sections: List<UnifiedSearchSection> = emptyList(),
        currentDirItems: List<OCFile> = emptyList(),
        supportsCalendarContacts: Boolean = false
    ): UnifiedSearchListAdapter {
        val syncedFolderProvider = SyncedFolderProvider(
            targetContext.contentResolver,
            preferences,
            sut.clock
        )

        val accountManager = UserAccountManagerImpl.fromContext(targetContext)

        overlayManager = OverlayManager(
            syncedFolderProvider = syncedFolderProvider,
            preferences = preferences,
            viewThemeUtils = sut.viewThemeUtils,
            context = targetContext,
            accountManager = accountManager
        )

        val adapter = UnifiedSearchListAdapter(
            supportsOpeningCalendarContactsLocally = supportsCalendarContacts,
            storageManager = sut.storageManager,
            listInterface = noopListInterface,
            filesAction = object : UnifiedSearchItemViewHolder.FilesAction {
                override fun showFilesAction(searchResultEntry: SearchResultEntry) = Unit
                override fun loadFileThumbnail(
                    searchResultEntry: SearchResultEntry,
                    onClientReady: (com.nextcloud.common.NextcloudClient) -> Unit
                ) = Unit
            },
            user = sut.user.get(),
            context = sut,
            viewThemeUtils = sut.viewThemeUtils,
            appPreferences = preferences,
            currentDirItemAction = noopCurrentDirAction,
            overlayManager = overlayManager
        )

        adapter.shouldShowFooters(true)

        sut.runOnUiThread {
            val recyclerView = RecyclerView(sut).apply {
                id = android.R.id.list
                layoutManager = GridLayoutManager(sut, 1)
                this.adapter = adapter
            }
            sut.setContentView(recyclerView)
            adapter.setData(sections)
            adapter.setDataCurrentDirItems(currentDirItems)
        }

        return adapter
    }

    private fun screenshot(sut: FileDisplayActivity, suffix: String) {
        val name = createName("${testClassName}_$suffix", "")
        screenshotViaName(sut, name)
    }

    private fun waitForIdle() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Thread.sleep(500)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }

    // endregion

    // region Remote thumbnails (contacts, calendar, apps)
    @Test
    @ScreenshotTest
    fun showContactEntry() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Contacts" to listOf(makeContactEntry()))
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "contactEntry") }
        }
    }

    @Test
    @ScreenshotTest
    fun showCalendarEntry() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Calendar" to listOf(makeCalendarEntry()))
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "calendarEntry") }
        }
    }

    @Test
    @ScreenshotTest
    fun showAppEntry() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Apps" to listOf(makeAppEntry()))
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "appEntry") }
        }
    }
    // endregion

    // region File entries
    @Test
    @ScreenshotTest
    fun showFileEntry() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Files" to listOf(makeFileEntry()))
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "fileEntry") }
        }
    }

    @Test
    @ScreenshotTest
    fun showFolderEntry() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                val folder = OCFile("/Documents/My Folder").apply {
                    mimeType = MimeType.DIRECTORY
                    remoteId = "0001"
                    setFolder()
                }
                sut.storageManager.saveFile(folder)
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Files" to listOf(makeFolderEntry()))
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "folderEntry") }
        }
    }
    // endregion

    // region Multiple sections
    @Test
    @ScreenshotTest
    fun showMultipleSections() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections(
                        "Files" to listOf(makeFileEntry(), makeFolderEntry()),
                        "Contacts" to listOf(makeContactEntry()),
                        "Calendar" to listOf(makeCalendarEntry())
                    )
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "multipleSections") }
        }
    }

    @Test
    @ScreenshotTest
    fun showSectionsWithLoadMoreFooter() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                setupAdapterOnActivity(
                    sut,
                    sections = makeSectionsWithMore(
                        "Files" to listOf(makeFileEntry(), makeFolderEntry()),
                        "Contacts" to listOf(makeContactEntry())
                    )
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "sectionsWithLoadMoreFooter") }
        }
    }
    // endregion

    // region Current directory items
    @Test
    @ScreenshotTest
    fun showCurrentDirectoryItems() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                val file1 = OCFile("/Documents/report.pdf").apply {
                    mimeType = MimeType.PDF
                    remoteId = "0002"
                    fileLength = 1024 * 512
                    modificationTimestamp = System.currentTimeMillis()
                }
                val file2 = OCFile("/Documents/photo.jpg").apply {
                    mimeType = MimeType.JPEG
                    remoteId = "0003"
                    fileLength = 1024 * 1024
                    modificationTimestamp = System.currentTimeMillis()
                }
                setupAdapterOnActivity(
                    sut,
                    currentDirItems = listOf(file1, file2)
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "currentDirectoryItems") }
        }
    }

    @Test
    @ScreenshotTest
    fun showCurrentDirectoryItemsWithUnifiedSearchSections() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                val localFile = OCFile("/Documents/notes.txt").apply {
                    mimeType = MimeType.TEXT_PLAIN
                    remoteId = "00005"
                    fileLength = 1024
                    modificationTimestamp = System.currentTimeMillis()
                }
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections("Files" to listOf(makeFileEntry())),
                    currentDirItems = listOf(localFile)
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "currentDirItemsWithUnifiedSections") }
        }
    }
    // endregion

    // region Scroll / recycle stress test
    @Test
    @ScreenshotTest
    fun showManyEntries() {
        launchActivity<FileDisplayActivity>().use { scenario ->
            scenario.onActivity { sut ->
                val fileEntries = (1..10).map { i ->
                    SearchResultEntry(
                        thumbnailUrl = "",
                        title = "File $i.pdf",
                        subline = "Documents/File $i.pdf",
                        resourceUrl = "",
                        icon = "application-pdf",
                        rounded = false,
                        attributes = mapOf("fileId" to "$i")
                    )
                }
                val contactEntries = (1..5).map { i ->
                    SearchResultEntry(
                        thumbnailUrl = "",
                        title = "Contact $i",
                        subline = "user$i@example.com",
                        resourceUrl = "",
                        icon = "icon-contacts",
                        rounded = true,
                        attributes = emptyMap()
                    )
                }
                setupAdapterOnActivity(
                    sut,
                    sections = makeSections(
                        "Files" to fileEntries,
                        "Contacts" to contactEntries
                    )
                )
            }
            waitForIdle()
            scenario.onActivity { sut -> screenshot(sut, "manyEntriesScrollStress") }
        }
    }
    // endregion
}
