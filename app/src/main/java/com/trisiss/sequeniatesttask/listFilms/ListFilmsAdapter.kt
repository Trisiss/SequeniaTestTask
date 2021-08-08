package com.trisiss.sequeniatesttask.listFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trisiss.sequeniatesttask.data.model.FilmDto
import com.trisiss.sequeniatesttask.databinding.ListItemBinding

/**
 * Created by trisiss on 8/7/2021.
 */
class ListFilmsAdapter (): RecyclerView.Adapter<ListFilmsAdapter.ViewHolder>() {
    val dataList = arrayListOf<FilmDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

}