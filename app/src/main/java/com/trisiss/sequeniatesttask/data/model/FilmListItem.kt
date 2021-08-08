package com.trisiss.sequeniatesttask.data.model

/**
 * Created by trisiss on 8/8/2021.
 */
sealed class FilmListItem {

    companion object {
        const val FILM = 0
        const val HEADER = 1
        const val GENRE = 2
    }
}
