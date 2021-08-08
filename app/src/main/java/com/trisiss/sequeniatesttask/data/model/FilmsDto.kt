package com.trisiss.sequeniatesttask.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by trisiss on 8/8/2021.
 */
@JsonClass(generateAdapter = true)
data class FilmsDto(
    @field:Json(name = "films") val films: List<FilmDto>
)
