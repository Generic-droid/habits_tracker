package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.HabitsTrackerViewModel
import ru.vidos.habitstracker.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.adapters.HabitsRecyclerViewAdapter
import ru.vidos.habitstracker.data.Habit
import ru.vidos.habitstracker.databinding.FragmentHabitsListBinding

class HabitsListFragment : Fragment() {

    private val viewModel: HabitsTrackerViewModel by activityViewModels {
        HabitsTrackerViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .habitsTrackerRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHabitsListBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the HabitsTrackerViewModel
        binding.viewModel = viewModel

        val adapter = HabitsRecyclerViewAdapter {
            viewModel.currentHabit = it
            val action = HabitsListFragmentDirections.actionGlobalAddEditFragment()
            findNavController().navigate(action)
        }

        // Sets the adapter of the dealsRecyclerView RecyclerView
        binding.habitsRecyclerView.adapter = adapter

        binding.habitsFab.setOnClickListener {

            viewModel.currentHabit =  Habit(
                id = 0,
                color = "#ADFF2F",
                title = "",
                description = "",
                priority = 0,
                type = true,
                quantity = "",
                periodicity = ""
            )

            val action = HabitsListFragmentDirections.actionGlobalAddEditFragment()
            findNavController().navigate(action)
        }

        /*
         Ensures that all the clicks/events of
         view correctly works when app is running.
         */
        return binding.root
    }
}