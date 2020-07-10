package com.example.enedilim.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.enedilim.R
import com.example.enedilim.database.LetterDatabase
import com.example.enedilim.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {

    private val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ///binding
        val binding: FragmentTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        val application = requireNotNull(this.activity).application
        val newTask = args.newTask
        val dataSource = LetterDatabase.getInstance(application).letterDatabaseDao

        ///viewModelFactory and viewModel and added to the binding of the fragment

        val viewModelFactory = TaskViewModelFactory(application, dataSource, newTask)
        val taskViewModel: TaskViewModel by viewModels { viewModelFactory }
        binding.taskVewModel = taskViewModel
        binding.lifecycleOwner = this




////////hide keyboard when touched other than edittext
//        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(this.view?.windowToken, 0)

        if (newTask.taskCompleted ==1) {
            binding.submitButton.visibility = View.GONE
            binding.completedMessage.visibility = View.VISIBLE
            binding.wordEdit.visibility = View.GONE
        }

        taskViewModel.taskCompleteClicked.observe(
            viewLifecycleOwner,
            Observer { comleteTask ->
                if (comleteTask == null || binding.wordEdit.text.toString() == "") {
                    Toast.makeText(context, "Sözü girmansiniz!", Toast.LENGTH_LONG).show()
                } else if (binding.wordEdit.text.toString() != taskViewModel.newLetterTask.letterWord) {
                    Toast.makeText(
                        context,
                        "Giren sözüñüz ýalñyş! Täzeden deñäñ!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Gutlaýaryn! Tabşyryk Üstünlükli Tamamlandy!",
                        Toast.LENGTH_LONG
                    ).show()
                    taskViewModel.makeSubmitButtonNotVisible()
                    if (taskViewModel.submitButtonVisible.value == false) {
                        binding.submitButton.visibility = View.GONE
                        binding.completedMessage.visibility = View.VISIBLE
                        binding.wordEdit.visibility = View.GONE
                    }
                }
            }
        )

        return binding.root
    }
}
