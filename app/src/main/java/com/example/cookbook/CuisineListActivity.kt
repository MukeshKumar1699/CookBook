package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.ActivityCuisineListBinding
import com.example.risingskills.RecyclerAdapter
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class CuisineListActivity : AppCompatActivity(), ItemClickListener {

    lateinit var binding: ActivityCuisineListBinding

    var user : String? = null
    val scope = CoroutineScope(IO)
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference()
    private var cuisinesList = ArrayList<String>()
    private lateinit var recyclerAdapter: RecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCuisineListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        init()
        progressBarVisiblity(View.VISIBLE)
        scope.launch {
            readFromFireBase()
        }
    }

    private fun getIntentData() {
        val bundle: Bundle? = intent.extras
        user = bundle?.get("User") as String?
    }

    private fun init() {


        setRecycler()

        if (!user.equals(null)) {
            binding.ivAdd.visibility = View.VISIBLE
        }

//        binding.ivAccount.setOnClickListener {
//
//            val intent = Intent(this@CuisineListActivity, LoginActivity::class.java)
//            startActivity(intent)
//        }

    }

    fun progressBarVisiblity(visiblity: Int) {

            binding.progressBar.visibility = visiblity

    }

    private fun setRecycler() {

        recyclerAdapter = RecyclerAdapter(cuisinesList, this)
        val layoutManager = LinearLayoutManager(this@CuisineListActivity, RecyclerView.VERTICAL, false)

        binding.rcView.apply {
            adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun readFromFireBase() {


        val helperListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                cuisinesList.clear()
                for (postSnapShot: DataSnapshot in dataSnapshot.child("Cuisines").children) {

                    cuisinesList.add(
                        dataSnapshot.child("Cuisines")
                            .child(postSnapShot.key.toString()).key.toString()
                    )
                }
                recyclerAdapter.notifyDataSetChanged()

//                dataSnapshot.child("Cuisines").key
//                (dataSnapshot.value as HashMap<*, *>).keys.toString()


            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        progressBarVisiblity(View.GONE)
        myRef.addValueEventListener(helperListener)
    }

    companion object {
        private const val TAG = "CuisineListActivity"
    }

    override fun onItemClicked(position: Int, cuisine: String) {

        val intent = Intent(this@CuisineListActivity, RecipeListActivity::class.java)
        intent.putExtra("Cuisine", cuisine)
        startActivity(intent)

    }
}