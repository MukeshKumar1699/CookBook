package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.ActivityRecipeListBinding
import com.example.risingskills.RecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecipeListActivity : AppCompatActivity(), ItemClickListener {

    lateinit var binding: ActivityRecipeListBinding


    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference()
    private var recipeList = ArrayList<String>()
    private lateinit var recyclerAdapter: RecyclerAdapter

    var cuisine : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        getIntentData()
        setRecycler()
        if (!cuisine.equals(null)) {
            readFromFireBase()
        }
    }

    private fun getIntentData() {
        val bundle: Bundle? = intent.extras
        cuisine = bundle?.get("Cuisine") as String
    }

    private fun setRecycler() {

        recyclerAdapter = RecyclerAdapter(recipeList, this)
        val layoutManager =
            LinearLayoutManager(this@RecipeListActivity, RecyclerView.VERTICAL, false)

        binding.rcView.apply {
            adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun readFromFireBase() {

        val helperListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                recipeList.clear()

                for (postSnapShot: DataSnapshot in snapshot.child("Cuisines/"+cuisine).children) {

                    recipeList.add(
                        snapshot.child("Cuisines")
                            .child(postSnapShot.key.toString()).key.toString()
                    )
                }
                recyclerAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        myRef.addValueEventListener(helperListener)
    }

    override fun onItemClicked(position: Int, data: String) {

        val intent = Intent(this@RecipeListActivity, RecipeDetailActivity::class.java)
        intent.putExtra("Recipe", data)
        intent.putExtra("Cuisine", cuisine)
        startActivity(intent)
    }
}