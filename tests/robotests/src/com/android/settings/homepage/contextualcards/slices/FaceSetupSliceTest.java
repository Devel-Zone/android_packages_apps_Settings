/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.settings.homepage.contextualcards.slices;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.os.UserHandle;

import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.widget.SliceLiveData;

import com.android.settings.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)
public class FaceSetupSliceTest {

    private Context mContext;

    @Before
    public void setUp() {
        // Set-up specs for SliceMetadata.
        SliceProvider.setSpecs(SliceLiveData.SUPPORTED_SPECS);
        mContext = spy(RuntimeEnvironment.application);
    }

    @Test
    public void getSlice_noFaceManager_shouldReturnNull() {
        when(mContext.getSystemService(FaceManager.class)).thenReturn(null);
        final FaceSetupSlice setupSlice = new FaceSetupSlice(mContext);
        assertThat(setupSlice.getSlice()).isNull();
    }

    @Test
    public void getSlice_faceEnrolled_shouldReturnNull() {
        final FaceManager faceManager = mock(FaceManager.class);
        when(mContext.getSystemService(FaceManager.class)).thenReturn(faceManager);
        when(faceManager.hasEnrolledTemplates(UserHandle.myUserId())).thenReturn(true);
        final FaceSetupSlice setupSlice = new FaceSetupSlice(mContext);
        assertThat(setupSlice.getSlice()).isNull();
    }

    @Test
    public void getSlice_faceNotEnrolled_shouldReturnNonNull() {
        final FaceManager faceManager = mock(FaceManager.class);
        when(mContext.getSystemService(FaceManager.class)).thenReturn(faceManager);
        when(faceManager.hasEnrolledTemplates(UserHandle.myUserId())).thenReturn(false);
        final FaceSetupSlice setupSlice = new FaceSetupSlice(mContext);
        assertThat(setupSlice.getSlice()).isNotNull();
    }
}