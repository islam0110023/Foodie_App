package com.example.bottomnavigation.showMeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.LoginActivity
import com.example.bottomnavigation.R
import com.example.bottomnavigation.dp.MealDatabase
import com.example.bottomnavigation.home.Factory
import com.example.bottomnavigation.home.HomeMealViewModel
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.modelIngredient.IngredientX
import com.example.bottomnavigation.network.RetrofitHealper

class ShowMealActivity : AppCompatActivity() {
    private lateinit var txt_meal: TextView
    private lateinit var txt_country: TextView
    private lateinit var txt_step: TextView
    private lateinit var rv_ingredients: RecyclerView
    private lateinit var img_meal: ImageView
    private lateinit var btn_fav: Button
    private lateinit var web_youtube: WebView
    private lateinit var viewModel: HomeMealViewModel
    private lateinit var adapter: MyAdapterIngredient
    private lateinit var btnExit: Button
    private lateinit var txt_Category: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_meal)
        initUi()
        setupViewModel()
        val prefs=getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isGuest = prefs.getBoolean("isLoggedIn", false)
//        if (isGuest) {
//            btn_fav.visibility = View.GONE
//        } else {
//            btn_fav.visibility = View.VISIBLE
//        }

        val mealName = intent.getStringExtra("meal")
        btnExit.setOnClickListener {
            finish()
        }

        mealName?.let {
            viewModel.getMealByName(it)
        } ?: run {
            Toast.makeText(this, "Meal name is missing.", Toast.LENGTH_SHORT).show()
            //finish()
        }
        setupObservers()
        btn_fav.setOnClickListener {
            if (isGuest) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Sign Up More Features")
                builder.setMessage("You must be logged in to add meals to favorites.")
                builder.setPositiveButton("Sign Up") { dialog, which ->
                    val signInIntent = Intent(this, LoginActivity::class.java)
                    signInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(signInIntent)
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()

            }
            else {
            viewModel.meal.value?.let {
                viewModel.addMeal(it[0])
            }
            }
        }
        viewModel.message.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })




    }

    private fun initUi() {
        btnExit = findViewById(R.id.btn_exit)
        txt_meal = findViewById(R.id.txt_meal)
        txt_country = findViewById(R.id.txt_country)
        txt_step = findViewById(R.id.txt_step)
        rv_ingredients = findViewById(R.id.rv_ingredients)
        img_meal = findViewById(R.id.img_meal)
        btn_fav = findViewById(R.id.btn_fav)
        web_youtube = findViewById(R.id.web_youtube)
        adapter = MyAdapterIngredient(listOf())
        rv_ingredients.adapter = adapter
        rv_ingredients.layoutManager = GridLayoutManager(this, 2)
        txt_Category = findViewById(R.id.txt_Category)

    }
    private fun setupViewModel() {
        val dao = MealDatabase.getInstance(this).mealDao()
        val retrofit = RetrofitHealper.service
        val factory = Factory(dao, retrofit)

        viewModel = ViewModelProvider(this, factory).get(HomeMealViewModel::class.java)
    }
    private fun setupObservers() {
        viewModel.meal.observe(this, Observer { meal ->
            meal?.let {
                showData(it[0])
                setupWebView(it[0])
            }
        })



//        viewModel.isFav.observe(this, Observer { isFav ->
//            btn_fav.text = if (isFav) "Unfavorite" else "Favorite"
//        })


    }

    private fun showData(meal: MealX) {
        Glide.with(this)
            .load(meal.strMealThumb)
            .into(img_meal)

        txt_meal.text = meal.strMeal
        txt_country.text = meal.strArea
        txt_step.text = meal.strInstructions
        adapter.ingredients = getValidIngredients(meal)
        adapter.notifyDataSetChanged()
        txt_Category.text = meal.strCategory
    }

    private fun setupWebView(meal: MealX) {
        val videoId = meal.strYoutube?.split("=")?.get(1) ?: ""
        if (videoId.isNotEmpty()) {
            val html = """
                    <html>
                    <body>
                    <iframe width="100%" height="100%" src="https://www.youtube.com/embed/$videoId" frameborder="0" allowfullscreen></iframe>
                    </body>
                    </html>
                """
            web_youtube.settings.javaScriptEnabled = true
            web_youtube.settings.domStorageEnabled = true
            web_youtube.visibility = View.VISIBLE
            web_youtube.loadData(html, "text/html", "utf-8")
    }
    }

    private fun getValidIngredients(meal: MealX): List<IngredientX> {
        val ingredientList: List<IngredientX> = listOf(
            IngredientX(strIngredient = meal.strIngredient1),
            IngredientX(strIngredient = meal.strIngredient2),
            IngredientX(strIngredient = meal.strIngredient3),
            IngredientX(strIngredient = meal.strIngredient4),
            IngredientX(strIngredient = meal.strIngredient5),
            IngredientX(strIngredient = meal.strIngredient6),
            IngredientX(strIngredient = meal.strIngredient7),
            IngredientX(strIngredient = meal.strIngredient8),
            IngredientX(strIngredient = meal.strIngredient9),
            IngredientX(strIngredient = meal.strIngredient10),
            IngredientX(strIngredient = meal.strIngredient11),
            IngredientX(strIngredient = meal.strIngredient12),
            IngredientX(strIngredient = meal.strIngredient13),
            IngredientX(strIngredient = meal.strIngredient14),
            IngredientX(strIngredient = meal.strIngredient15),
            IngredientX(strIngredient = meal.strIngredient16),
            IngredientX(strIngredient = meal.strIngredient17),
            IngredientX(strIngredient = meal.strIngredient18),
            IngredientX(strIngredient = meal.strIngredient19),
            IngredientX(strIngredient = meal.strIngredient20)
        )

        // تصفية وإرجاع العناصر الصالحة فقط
        return ingredientList.filter { !it.strIngredient.isNullOrBlank() }
    }

}
