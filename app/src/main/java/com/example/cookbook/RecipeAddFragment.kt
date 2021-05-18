package com.example.cookbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cookbook.databinding.FragmentRecipeAddBinding
import com.google.firebase.database.FirebaseDatabase


class RecipeAddFragment : Fragment() {

    lateinit var binding: FragmentRecipeAddBinding

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("message")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun writeToFirebase(cuisine: String, recipe: String, description: String) {

        val recipeData = RecipeData(recipe.trim(), description.trim())
        myRef.child(cuisine).setValue(recipeData).addOnCompleteListener {

            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {


        binding.etRecipeDescription.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        binding.btnSubmit.setOnClickListener {
            object : View.OnClickListener {

                override fun onClick(v: View?) {

                    val cuisine: String = binding.etCuisine.text.toString()
                    val recipe: String = binding.etRecipe.text.toString()
                    val description: String = binding.etRecipeDescription.text.toString()

                    writeToFirebase(cuisine, recipe, description)

                }

            }
        }
    }

    companion object {
        private const val TAG = "RecipeAddFragment"
    }


}