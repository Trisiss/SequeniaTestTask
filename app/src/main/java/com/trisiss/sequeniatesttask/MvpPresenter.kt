package com.trisiss.sequeniatesttask

/**
 * Created by trisiss on 8/4/2021.
 */
interface MvpPresenter<V: MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
    fun destroy()
}