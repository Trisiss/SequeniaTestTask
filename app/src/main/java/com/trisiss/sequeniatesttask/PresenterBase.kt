package com.trisiss.sequeniatesttask

/**
 * Created by trisiss on 8/4/2021.
 */
abstract class PresenterBase<T: MvpView> : MvpPresenter<T> {

    private var view: T? = null

    protected fun isViewAttached(): Boolean {
        return view != null
    }

    override fun attachView(mvpView: T) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {
    }
}