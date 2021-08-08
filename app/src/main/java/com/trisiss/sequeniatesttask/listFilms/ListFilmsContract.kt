package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import com.trisiss.sequeniatesttask.mvp.MvpPresenter
import com.trisiss.sequeniatesttask.mvp.MvpView

/**
 * Created by trisiss on 8/7/2021.
 */
interface ListFilmsContract {
    interface Presenter: MvpPresenter<View> {
        fun load()
        fun loadFromState(state: Bundle)
    }
    interface View: MvpView {

    }
}