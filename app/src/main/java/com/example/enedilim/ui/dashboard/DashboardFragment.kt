package com.example.enedilim.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.enedilim.R
import com.example.enedilim.database.LetterDatabase
import com.example.enedilim.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ///binding
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = LetterDatabase.getInstance(application).letterDatabaseDao

        ///viewModelFactory and viewModel and added to the binding of the fragment
        val viewModelFactory = DashboardViewModelFactory(application, dataSource)
        val dashboardViewModel: DashboardViewModel by viewModels { viewModelFactory }
        binding.dashboardViewModel = dashboardViewModel

        val adapter = LetterAdapter()
        binding.completedTasksList.adapter = adapter

        dashboardViewModel.completedTasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }
}
