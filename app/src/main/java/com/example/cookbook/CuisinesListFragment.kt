package com.example.cookbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.cookbook.databinding.FragmentCuisinesListBinding

class CuisinesListFragment : Fragment() {

    lateinit var binding: FragmentCuisinesListBinding
    val navController = Navigation.findNavController(requireActivity(), R.id.nav_graph_main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuisinesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {


        binding.brnNavAddRecipe.setOnClickListener { object : View.OnClickListener {
            override fun onClick(v: View?) {

                navController.navigate(R.id.action_cuisinesListFragment_to_recipeAddFragment)

            }

        } }
    }

}