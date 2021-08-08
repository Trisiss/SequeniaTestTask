package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import android.util.Log
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.data.FilmsService
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmsDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by trisiss on 8/5/2021.
 */
class ListFilmsPresenter(private val filmsService: FilmsService) : ListFilmsContract.Presenter {

    var view: ListFilmsContract.View? = null
    var listFilm: ArrayList<FilmDto> = arrayListOf<FilmDto>()
    var response: Response<FilmsDto>? = null

    override fun load() {
        view?.changeState(StateView.LOADING)
        Log.e("CreatePresenter", "create: ${response ?: "Nothing"}")
        CoroutineScope(Dispatchers.IO).launch {
            response = filmsService.load()
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {
                    listFilm = ArrayList(response!!.body()?.films) ?: arrayListOf()
                    view?.changeState(StateView.COMPLETE)
                }
            }
        }
    }

    fun isViewAttached(): Boolean {
        return view != null
    }

    override fun attachView(mvpView: ListFilmsContract.View) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {

    }

    override fun getFilmList() = listFilm

    override fun loadFromState(state: Bundle) {
        view?.changeState(StateView.LOADING)
        listFilm = state.getParcelableArrayList("listFilms") ?: arrayListOf()
        view?.changeState(StateView.COMPLETE)
    }


}