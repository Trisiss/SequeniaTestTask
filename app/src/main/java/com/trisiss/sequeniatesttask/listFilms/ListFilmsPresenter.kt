package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import android.util.Log
import com.trisiss.sequeniatesttask.data.FilmsService
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmsDto
import com.trisiss.sequeniatesttask.mvp.PresenterBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by trisiss on 8/5/2021.
 */
class ListFilmsPresenter(private val filmsService: FilmsService) : ListFilmsContract.Presenter,
    PresenterBase<ListFilmsContract.View>() {

    var listFilm: ArrayList<FilmDto>? = arrayListOf<FilmDto>()
    var response: Response<FilmsDto>? = null

    override fun load() {
        Log.e("CreatePresenter", "create: ${response ?: "Nothing"}")
        CoroutineScope(Dispatchers.IO).launch {
            response = filmsService.load()
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful)
                    Log.e("Load", "load: ${response!!.body()?.films?.get(1)}")
            }
        }
    }

    override fun loadFromState(state: Bundle) {
        listFilm = state.getParcelableArrayList("listFilms")
    }


}