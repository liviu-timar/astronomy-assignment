package com.adyen.android.assignment.data.sources

import com.adyen.android.assignment.data.local.dao.AstronomyPictureDao
import com.adyen.android.assignment.data.local.models.AstronomyPictureEntity
import com.adyen.android.assignment.data.local.models.asDomainModel
import com.adyen.android.assignment.data.local.models.asEntity
import com.adyen.android.assignment.domain.models.AstronomyPicture
import com.adyen.android.assignment.domain.sources.AstronomyPictureLocalDataSource
import javax.inject.Inject

class AstronomyPictureLocalDataSourceImpl @Inject constructor(
    private val astronomyPictureDao: AstronomyPictureDao
): AstronomyPictureLocalDataSource {

    override suspend fun getPictures(count: Int): List<AstronomyPicture> =
        astronomyPictureDao.get(count = count).map(AstronomyPictureEntity::asDomainModel)

    override suspend fun getPicture(id: Int): AstronomyPicture =
        astronomyPictureDao.getById(id = id).asDomainModel()

    override suspend fun insertPictures(pictures: List<AstronomyPicture>) =
        astronomyPictureDao.insert(pictures.map(AstronomyPicture::asEntity))

    override suspend fun deleteAllPictures() = astronomyPictureDao.deleteAll()
}