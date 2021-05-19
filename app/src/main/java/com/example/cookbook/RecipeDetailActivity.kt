package com.example.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cookbook.databinding.ActivityRecipeDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecipeDetailBinding
    var cuisine : String =""
    var recipe : String = ""

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference()
    val scope = CoroutineScope(IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        scope.launch {
            readFromFirebase()
        }
    }

    private fun readFromFirebase() {

        val helperListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val recipeItem = snapshot.child("Cuisines/$cuisine/$recipe").getValue(RecipeData::class.java)

                    Log.e(TAG, "onDataChange: Message data is updated: " + recipeItem!!.title + ", " + recipeItem.description)

                    binding.tvRecipeTitle.text = recipe
                    binding.tvRecipeDescription.text = recipeItem.description

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        myRef.addValueEventListener(helperListener)
    }

    private fun getIntentData() {
        val bundle: Bundle? = intent.extras
        cuisine = bundle?.get("Cuisine") as String
        recipe = bundle?.get("Recipe") as String
    }

    companion object {
        private const val TAG = "RecipeDetailActivity"
    }

}