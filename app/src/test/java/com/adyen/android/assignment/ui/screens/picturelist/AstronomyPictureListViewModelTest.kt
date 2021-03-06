package com.adyen.android.assignment.ui.screens.picturelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adyen.android.assignment.domain.usecases.FormatDateUseCase
import com.adyen.android.assignment.domain.usecases.GetAstronomyPictureListUseCase
import com.adyen.android.assignment.domain.usecases.SortBy
import com.adyen.android.assignment.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AstronomyPictureListViewModelTest {

    // This rule swaps the background executor used by LiveData
    // with a different one which executes each task synchronously
    @get:Rule(order = 0)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Replaces Dispatchers.Main with a testing dispatcher because tests do not have a main Looper.
    // Used by viewModelScope.launch().
    @get:Rule(order = 1)
    val coroutinesTestRule = MainDispatcherRule()

    private lateinit var viewModel: AstronomyPictureListViewModel
    private lateinit var mockGetAstronomyPictureListUseCase: GetAstronomyPictureListUseCase
    private lateinit var mockFormatDateUseCase: FormatDateUseCase

    @Before
    fun setUp() {
        mockGetAstronomyPictureListUseCase = mock()
        mockFormatDateUseCase = mock()
        viewModel = AstronomyPictureListViewModel(mockGetAstronomyPictureListUseCase, mockFormatDateUseCase)
    }

    // Test method naming: subjectUnderTest_input_expectedResult
    @Test
    fun picturesLiveData_pictureModelList_returnsPictureListItems() = runTest {
        // Given
        whenever(mockGetAstronomyPictureListUseCase.invoke(any(), any(), any())).thenReturn(testPictureModelList)
        whenever(mockFormatDateUseCase.invoke(any())).thenReturn(testDate)

        // When
        viewModel.getPictureList(count = 2)
        val pictures = viewModel.pictures.getOrAwaitValue()

        // Then
        assertThat(pictures, IsEqual(testPictureListItems)) // Verify state - variable value
    }

    @Test
    fun getPictureList_refreshAndCountAndSort_callsUseCase() = runTest {
        val refresh = true
        val count = 2
        val sortBy = SortBy.TITLE_ASC
        whenever(mockGetAstronomyPictureListUseCase.invoke(any(), any(), any())).thenReturn(testPictureModelList)
        whenever(mockFormatDateUseCase.invoke(any())).thenReturn(testDate)

        viewModel.getPictureList(refresh, count, sortBy)
        viewModel.pictures.getOrAwaitValue()

        verify(mockGetAstronomyPictureListUseCase).invoke(refresh, count, sortBy) // Verify behaviour - method calls
    }
}