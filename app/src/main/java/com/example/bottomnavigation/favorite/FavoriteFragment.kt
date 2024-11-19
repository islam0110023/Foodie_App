package com.example.bottomnavigation.favorite

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
import com.example.bottomnavigation.utils.MyMealListener
import com.example.bottomnavigation.utils.makeSnackBar

class FavoriteFragment : Fragment(), MyMealListener {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: MyAdapterFavorite
    private lateinit var viewModel: FavoriteMealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setupViewModel()
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.meals = meals
            adapter.notifyDataSetChanged()
        }
        viewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                makeSnackBar(message, view)
            }
        }
    }

    private fun initUi() {
        rv = view?.findViewById(R.id.rv)!!
        adapter = MyAdapterFavorite(listOf(), this)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
    // LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onMealClicked(meal: MealX) {
        viewModel.deleteMeal(meal)
    }

    private fun setupViewModel() {
        val dao = MealDatabase.getInstance(requireContext()).mealDao()
        val factory = Factory(dao)
        viewModel = ViewModelProvider(this, factory).get(FavoriteMealViewModel::class.java)

    }
}
