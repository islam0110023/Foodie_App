package com.example.bottomnavigation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.dp.MealDatabase
import com.example.bottomnavigation.home.Factory
import com.example.bottomnavigation.home.HomeMealViewModel
import com.example.bottomnavigation.home.MyAdapterMealR
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.network.RetrofitHealper
import com.example.bottomnavigation.utils.MyMealListener


class SearchFragment : Fragment(), MyMealListener {
    private lateinit var viewModel:HomeMealViewModel
    private lateinit var adapter:MyAdapterMealR
    private lateinit var rv_search:RecyclerView
    private lateinit var searchView:SearchView


    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setupViewModel()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query:String?):Boolean {
                searchView.clearFocus()
                adapter.meals = listOf()
                adapter.notifyDataSetChanged()
                query?.let {
                    viewModel.getMealByName(it)
                } ?: run {
                    Toast.makeText(requireContext(), "Meal name is missing.", Toast.LENGTH_SHORT)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText:String?):Boolean {
                if (!newText.isNullOrEmpty()) {
                    newText?.let {
                        viewModel.getMealByName(it)
                    } ?: run {
                        Toast.makeText(
                            requireContext(),
                            "Meal name is missing.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    adapter.meals = listOf()
                    adapter.notifyDataSetChanged()
                }
                return false

            }

        })

        viewModel.meal.observe(viewLifecycleOwner) {
            adapter.meals = it
            adapter.notifyDataSetChanged()
        }


        viewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun initUi() {
        rv_search = view?.findViewById(R.id.rv_search)!!
        searchView = view?.findViewById(R.id.searchView)!!
        searchView.clearFocus()
        adapter = MyAdapterMealR(listOf(), this)
        rv_search.adapter = adapter
        rv_search.layoutManager = GridLayoutManager(requireContext(), 2)


        //LinearLayoutManager(requireContext())
    }

    fun setupViewModel() {
        val retrofit = RetrofitHealper.service
        val dao = MealDatabase.getInstance(requireContext()).mealDao()
        val factory = Factory(dao, retrofit)
        viewModel = ViewModelProvider(this, factory).get(HomeMealViewModel::class.java)

    }

    override fun onMealClicked(meal:MealX) {
        viewModel.addMeal(meal)
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearMeal()

    }


}