package com.example.bottomnavigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.dp.MealDatabase
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.network.RetrofitHealper
import com.example.bottomnavigation.utils.MyMealListener
import com.example.bottomnavigation.utils.makeSnackBar



class HomeFragment : Fragment(), MyMealListener {
    lateinit var rv:RecyclerView
    lateinit var adapter:MyAdapterMealR
    lateinit var viewModel:HomeMealViewModel
    lateinit var rv_country:RecyclerView
    lateinit var adapter_country:MyAdapterCountry
    lateinit var adapter_category:MyAdapterCategory
    lateinit var rv_category:RecyclerView

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setupViewModel()

        viewModel.meals.observe(viewLifecycleOwner) {meals ->
            adapter.meals = meals.take(5)
            adapter.notifyDataSetChanged()
        }

        viewModel.countries.observe(viewLifecycleOwner) {countries ->
            adapter_country.countries = countries
            adapter_country.notifyDataSetChanged()
        }
        viewModel.categories.observe(viewLifecycleOwner) {categories ->
            adapter_category.categories = categories
            adapter_category.notifyDataSetChanged()
        }

        viewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                // اعرض الرسالة فقط إذا لم يتم التعامل معها من قبل
                makeSnackBar(message, view)
            }
        }


    }
    fun initUi() {
        rv = view?.findViewById(R.id.rv)!!
        adapter = MyAdapterMealR(listOf(), this)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false)
               // LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_country = view?.findViewById(R.id.rv_country)!!
        adapter_country = MyAdapterCountry(listOf())
        rv_country.adapter = adapter_country
        rv_country.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_category = view?.findViewById(R.id.rv_categroy)!!
        adapter_category = MyAdapterCategory(listOf())
        rv_category.adapter = adapter_category
        rv_category.layoutManager = GridLayoutManager(requireContext(), 3)


    }
    fun setupViewModel() {
        val dao= MealDatabase.getInstance(requireContext()).mealDao()
        val retrofit = RetrofitHealper.service
        val factory = Factory(dao, retrofit)
        viewModel = ViewModelProvider(this, factory).get(HomeMealViewModel::class.java)
    }
    override fun onMealClicked(meal:MealX) {
        viewModel.addMeal(meal)
    }




}