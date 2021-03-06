package com.adyen.android.assignment.domain.usecases

import com.adyen.android.assignment.domain.models.AstronomyPicture
import com.adyen.android.assignment.domain.repositories.AstronomyPictureRepository
import javax.inject.Inject

class GetAstronomyPictureListUseCase @Inject constructor(
    private val pictureRepository: AstronomyPictureRepository,
    private val sortPictureListUseCase: SortAstronomyPictureListUseCase
) {

    suspend operator fun invoke(refresh: Boolean, count: Int, sortBy: SortBy): List<AstronomyPicture> =
        sortPictureListUseCase(pictureRepository.getPictures(refresh = refresh, count = count), sortBy)
}