package com.trisiss.sequeniatesttask.listFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.trisiss.sequeniatesttask.StateView
import com.trisiss.sequeniatesttask.databinding.FragmentListFilmsBinding
import org.koin.android.ext.android.inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFilmsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFilmsFragment : Fragment(), ListFilmsContract.View {

    private val presenter: ListFilmsContract.Presenter by inject()
    private lateinit var binding: FragmentListFilmsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_films, container, false)

        binding = FragmentListFilmsBinding.inflate(layoutInflater, container, false)

        val gridLayoutManager = GridLayoutManager(context, 2)
        val listFilmsAdapter = ListFilmsAdapter()

        binding.recyclerListFilms.apply {
            layoutManager = gridLayoutManager
            adapter = listFilmsAdapter
        }
        if (savedInstanceState != null) loadDataFromState(savedInstanceState) else loadData()

        return binding.root
    }

    private fun loadDataFromState(state: Bundle) {
        presenter.loadFromState(state)
    }

    private fun loadData() {
        presenter.load()
    }

    fun changeState(state: StateView) {
        when (state) {
            StateView.LOADING -> {

            }
            StateView.ERROR -> {

            }
            StateView.COMPLETE -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val adapter = binding.recyclerListFilms.adapter as ListFilmsAdapter
        outState.putParcelableArrayList("listFilm", adapter.dataList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFilmsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFilmsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
