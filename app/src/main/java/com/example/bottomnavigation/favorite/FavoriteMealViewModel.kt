package com.example.bottomnavigation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigation.Event
import com.example.bottomnavigation.dp.MealDao
import com.example.bottomnavigation.modelMeal.MealX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMealViewModel (val dao: MealDao) : ViewModel() {
    val meals: LiveData<List<MealX>> = dao.getAllMeals()

    private val _message: MutableLiveData<Event<String>> = MutableLiveData()
    val message: LiveData<Event<String>> = _message


//    fun getMeals() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val list=dao.getAllMeals()
//            withContext(Dispatchers.Main)
//            {
//                meals.postValue(list)
//            }
//
//        }
//    }
    fun deleteMeal(meal: MealX) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.delete(meal)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue(Event("Meal deleted successfully"))

                } else {
                    _message.postValue(Event("Error deleting meal"))
                    }
                }

        }

    }
    fun setMessage(msg: String) {
        _message.value = Event(msg)
    }
}
class Factory(private val dao:MealDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteMealViewModel::class.java)) {
            return FavoriteMealViewModel( dao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}