package com.example.cookbook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cookbook.databinding.FragmentCuisinesListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CuisinesListFragment : Fragment() {

    lateinit var binding: FragmentCuisinesListBinding

    var database = FirebaseDatabase.getInstance()
    var myFireBaseRef = database.getReference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuisinesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }



    private fun init(view: View) {

        binding.btnNavAddRecipe.setOnClickListener {
            View.OnClickListener {

                view.findNavController().navigate(R.id.action_cuisinesListFragment_to_recipeAddFragment)

            }
        }
    }

    fun readFromFirebase() {
        myFireBaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    companion object {

        private const val TAG = "CuisinesListFragment"
    }

}