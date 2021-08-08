package com.trisiss.sequeniatesttask.data

import com.trisiss.sequeniatesttask.data.model.FilmsDto
import retrofit2.Response

/**
 * Created by trisiss on 8/8/2021.
 */
interface FilmsService {
    suspend fun load(): Response<FilmsDto>
}