package com.trisiss.sequeniatesttask.data

import com.trisiss.sequeniatesttask.data.model.FilmsDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by trisiss on 8/8/2021.
 */
interface FilmsApi {

    @GET("sequeniatesttask/films.json")
    suspend fun getListFilms() : Response<FilmsDto>
}