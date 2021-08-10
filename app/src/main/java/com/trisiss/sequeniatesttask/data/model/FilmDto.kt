package com.trisiss.sequeniatesttask.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Created by trisiss on 8/7/2021.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class FilmDto(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "localized_name") val localizedName: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "rating") val rating: Float?,
    @field:Json(name = "image_url") val imageUrl: String?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "genres") val genres: List<String>,
    var visible: Boolean = true
): Parcelable, FilmListItem()
