package com.example.enedilim.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.enedilim.R
import com.example.enedilim.database.LetterDatabase
import com.example.enedilim.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ///binding
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = LetterDatabase.getInstance(application).letterDatabaseDao

        //viewModelFactory and viewModel
        val viewModelFactory = HomeViewModelFactory(application, dataSource)
        val homeViewModel: HomeViewModel by viewModels{viewModelFactory}
        binding.homeViewModel = homeViewModel

        binding.lifecycleOwner = this

        ///change backgraund color of the letter button if there is any completed (will do later)
        val buttons = arrayOf(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30)

        homeViewModel.navigateToTask.observe(
            viewLifecycleOwner,
            Observer { newTask ->
                newTask?.let {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToTaskFragment(newTask)
                    this.findNavController().navigate(action)
                    homeViewModel.doneNavigation()
                }
            })
        return binding.root
    }
}
