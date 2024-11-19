package com.example.bottomnavigation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigation.Event
import com.example.bottomnavigation.dp.MealDao
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.modelCategory.CategoryX
import com.example.bottomnavigation.modelCountry.CountryX
import com.example.bottomnavigation.network.MealService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeMealViewModel(val retrofit: MealService, val dao: MealDao) : ViewModel() {
    private val _meals : MutableLiveData<List<MealX>> = MutableLiveData()
    val meals : LiveData<List<MealX>> = _meals
    private val _countries: MutableLiveData<List<CountryX>> = MutableLiveData()
    val countries: LiveData<List<CountryX>> = _countries
    private val _categories: MutableLiveData<List<CategoryX>> = MutableLiveData()
    val categories: LiveData<List<CategoryX>> = _categories
    private val _MealByCountry: MutableLiveData<List<MealX>> = MutableLiveData()
    val MealByCountry: LiveData<List<MealX>> = _MealByCountry


    private val _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> = _message
    private val _meal: MutableLiveData<List<MealX>> = MutableLiveData()
    val meal: LiveData<List<MealX>> = _meal

//
//    fun setMessage(msg: String) {
//        _message.value = Event(msg)
//    }

    init {
        getMeals()
        getCountries()
        getCategory()

    }
    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofit.getCountry()
                val countryList = response.body()?.meals ?: emptyList()
                withContext(Dispatchers.Main) {
                    if (countryList.isEmpty()) {
                        _message.postValue(Event("No countries found"))
                    } else {
                        _countries.postValue(countryList)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _message.postValue(Event("Error fetching countries: ${e.message}"))
                }
            }
        }
    }
    fun getCategory()
    {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = retrofit.getCategory()
                val categoryList = response.body()?.categories ?: emptyList()
                withContext(Dispatchers.Main) {
                    if (categoryList.isEmpty()) {
                        _message.postValue(Event("No categories found"))
                    } else {
                        _categories.postValue(categoryList)
                    }
                }

            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _message.postValue(Event("Error fetching categories: ${e.message}"))
                }
            }
        }
    }
    fun clearMeal() {
        _meal.value = emptyList()
        }




//    fun getMeals(){
//        viewModelScope.launch(Dispatchers.IO) {
//            val list=retrofit.getRandomMeal().body()?.meals
//            withContext(Dispatchers.Main){
//                if (list.isNullOrEmpty()) {
//                    _message.postValue(Event("No meals found"))
//                }
//                else {
//                    val l:List<MealX> = list
//                    _meals.postValue(l)
//                }
//
//            }
//
//        }
//    }
fun getMeals(){
    viewModelScope.launch(Dispatchers.IO) {
        val mealList= mutableListOf<MealX>()
        repeat(2){
            val list=retrofit.getRandomMeal().body()?.meals
            if (!list.isNullOrEmpty()) {
                mealList.addAll(list)
            }
        }

        withContext(Dispatchers.Main){
            if (mealList.isNullOrEmpty()) {
                _message.postValue(Event("No meals found"))
            }
            else {

                _meals.postValue(mealList)
            }

        }

    }
}





    fun getMealByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mealResponse = retrofit.getMealByName(name).body()
            val myMeal = mealResponse?.meals
            withContext(Dispatchers.Main) {
                if (myMeal != null) {
                    _meal.postValue(myMeal!!)
                }
            }
        }
    }



    fun getMealByCountry(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mealResponse = retrofit.getMealByCountry(country).body()
            val myMeal = mealResponse?.meals
            withContext(Dispatchers.Main) {
                if (myMeal != null) {
                    _MealByCountry.postValue(myMeal!!)
                }
        }
    }
    }
    fun getMealByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val mealResponse = retrofit.getMealByCategory(category).body()
            val myMeal = mealResponse?.meals
            withContext(Dispatchers.Main) {
                if (myMeal != null) {
                    _MealByCountry.postValue(myMeal!!)
                }
            }
        }
    }
    fun getMealByIngredient(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO){
            val mealResponse = retrofit.getMealByIngredient(ingredient).body()
            val myMeal = mealResponse?.meals
            withContext(Dispatchers.Main) {
                if (myMeal != null) {
                    _MealByCountry.postValue(myMeal!!)
                }

        }
    }
    }




    fun addMeal(meal: MealX) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.addMeal(meal)
            withContext(Dispatchers.Main) {
                if (result.isNotEmpty()) {
                    _message.postValue(Event("Meal added successfully"))
                } else {
                    _message.postValue(Event("Error adding meal"))
                }
            }
        }
    }



}
class Factory(private val dao:MealDao, private val retrofit:MealService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeMealViewModel::class.java)) {
            return HomeMealViewModel(retrofit, dao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}
/*
* fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            val allMeals = mutableListOf<MealX>()
            val letters = ('a'..'z') + ('0'..'9')
            val deferreds = letters.map { letter ->
                async() {
                    try {
                        val list = retrofit.getMealList(letter.toString())
                        if (list.isSuccessful) {
                            list.body()?.meals?.let {
                                allMeals.addAll(it)
                            }
                        }
                        else {
                            _message.postValue(list.message())
                        }
                    }
                    catch (e: Exception) {
                        _message.postValue(e.message)
                    }
                }
            }
            deferreds.awaitAll()
            withContext(Dispatchers.Main) {
                if(allMeals.isNullOrEmpty()) {
                    _message.postValue("No meals found")
                }
                else {
                    _meals.postValue(allMeals)
                }
            }
        }
    }*/