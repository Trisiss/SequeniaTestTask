package com.trisiss.sequeniatesttask.listFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.data.model.FilmListItem
import com.trisiss.sequeniatesttask.data.model.GenreDto
import com.trisiss.sequeniatesttask.data.model.HeaderItem
import com.trisiss.sequeniatesttask.databinding.ListItemFilmBinding
import com.trisiss.sequeniatesttask.databinding.ListItemGenreBinding
import com.trisiss.sequeniatesttask.databinding.ListItemHeaderBinding
import com.trisiss.sequeniatesttask.loadImage

/**
 * Created by trisiss on 8/7/2021.
 */
class ListFilmsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = arrayListOf<FilmListItem>()
    var clickListener: OnClickItemFilm? = null

    fun setNewData(newData: ArrayList<FilmListItem>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyItemRangeInserted(0, dataList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FilmListItem.HEADER -> HeaderViewHolder(ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FilmListItem.GENRE -> GenreViewHolder(ListItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false), clickListener)
            else -> FilmViewHolder(ListItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false), clickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmViewHolder -> holder.bind(model = dataList[position] as FilmDto)
            is HeaderViewHolder -> holder.bind(model = dataList[position] as HeaderItem)
            is GenreViewHolder -> holder.bind(model = dataList[position] as GenreDto)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is FilmDto -> FilmListItem.FILM
            is HeaderItem -> FilmListItem.HEADER
            is GenreDto -> FilmListItem.GENRE
        }
    }

    override fun getItemCount(): Int = dataList.size

    class FilmViewHolder(val binding: ListItemFilmBinding, val clickItemFilm: OnClickItemFilm?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FilmDto){
            binding.apply {
                previewImageview.loadImage(model.imageUrl)
                previewTextview.text = model.localizedName
                itemView.setOnClickListener {
                    clickItemFilm?.openFilm(model)
                }
            }
        }

    }

    class GenreViewHolder(val binding: ListItemGenreBinding, val clickItemFilm: OnClickItemFilm?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GenreDto){
            binding.apply {
                genreButton.text = model.title
                itemView.setOnClickListener {
                    clickItemFilm?.filter(model)
                }
            }
        }
    }

    class HeaderViewHolder(val binding: ListItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HeaderItem){
            binding.apply {
                headerTextview.text = model.title
            }
        }
    }
}

interface OnClickItemFilm {
    fun filter(genre: GenreDto)
    fun openFilm(film: FilmDto)
}