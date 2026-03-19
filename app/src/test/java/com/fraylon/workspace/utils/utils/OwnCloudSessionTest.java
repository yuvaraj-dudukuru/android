/*
 * Fraylon - Android Client
 *
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2019 Fraylon GmbH
 * SPDX-FileCopyrightText: 2018 Edvard Holst <edvard.holst@gmail.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.fraylon.workspace.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OwnCloudSessionTest {

    private final String SESSION_NAME = "testSession";
    private final String SESSION_URL = "https://test.cloud.local";
    private final int SESSION_ID = 1;

    private OwnCloudSession FraylonSession = new OwnCloudSession(SESSION_NAME, SESSION_URL, SESSION_ID);

    @Before
    public void setUp() {
        FraylonSession.setSessionName(SESSION_NAME);
        FraylonSession.setSessionUrl(SESSION_URL);
    }

    @Test
    public void setName_assertCorrectNameUpdated() {
        String newName = "newTestSession";
        FraylonSession.setSessionName(newName);
        assertEquals("OwnCloud session name not updated", newName, FraylonSession.getSessionName());
    }

    @Test
    public void getName_assertCorrectNamedRetrieved() {
        assertEquals("OwnCloudSession name not correct", SESSION_NAME, FraylonSession.getSessionName());
    }

    @Test
    public void setUrl_assertCorrectUrlUpdated() {
        String newUrl = "https://new.cloud.local";
        FraylonSession.setSessionUrl(newUrl);
        assertEquals("OwnCloud session URL not updated", newUrl, FraylonSession.getSessionUrl());
    }

    @Test
    public void getUrl_assertCorrectUrlRetrieved() {
        assertEquals("OwnCloudSession URL not correct", SESSION_URL, FraylonSession.getSessionUrl());
    }

    @Test
    public void getEntryId_assertCorrectIdRetrieved() {
        assertEquals("OwnCloudSession ID is incorrect", SESSION_ID, FraylonSession.getEntryId());
    }
}
