package com.example.cookbook

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.databinding.ActivityRecipeAddBinding
import com.google.firebase.database.FirebaseDatabase


class RecipeAddActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecipeAddBinding

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Cuisines")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get radio group selected item using on checked change listener
        binding.rgCusine.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(
                applicationContext, " On checked change :" +
                        " ${radio.text}",
                Toast.LENGTH_SHORT
            ).show()
        }


        binding.btnSubmit.setOnClickListener {

            val recipeTitle: String = binding.etRecipeTitle.text.toString().trim()
            val recipeDescription: String = binding.etRecipeDescription.text.toString().trim()

            var id: Int = binding.rgCusine.checkedRadioButtonId

            if (id!=-1){

                val radio:RadioButton = findViewById(id)
                writeToFirebase(radio.text.toString(), recipeTitle, recipeDescription)

                Toast.makeText(applicationContext,"On button click :" + " ${radio.text}", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(applicationContext,"On button click :" + " nothing selected", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun writeToFirebase(cusine: String, recipeTitle: String, recipeDescription: String) {

        val recipeData = RecipeData(recipeTitle, recipeDescription)

        myRef.child(cusine).child(recipeTitle).setValue(recipeData)

    }
}