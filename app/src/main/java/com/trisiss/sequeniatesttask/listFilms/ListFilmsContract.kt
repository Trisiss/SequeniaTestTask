package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmListItem
import com.trisiss.sequeniatesttask.mvp.MvpPresenter
import com.trisiss.sequeniatesttask.mvp.MvpView

/**
 * Created by trisiss on 8/7/2021.
 */
interface ListFilmsContract {
    interface Presenter: MvpPresenter<View> {
        fun load()
        fun loadFromState(state: Bundle)
        fun getFilmList(): ArrayList<FilmDto>
        fun getFilmListUI(): ArrayList<FilmListItem>
    }
    interface View: MvpView {
        fun changeState(state: StateView)
    }
}