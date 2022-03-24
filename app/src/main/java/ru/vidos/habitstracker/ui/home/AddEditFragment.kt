package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.HabitsTrackerViewModel
import ru.vidos.habitstracker.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.FragmentAddEditHabitBinding

class AddEditFragment : Fragment() {

    private val viewModel: HabitsTrackerViewModel by activityViewModels {
        HabitsTrackerViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .habitsTrackerRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAddEditHabitBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the HabitsListViewModel
        binding.viewModel = viewModel

        /*
         Ensures that all the clicks/events of
         view correctly works when app is running.
         */
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.save_habit)
        item.isVisible = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_habit_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_habit -> {
                if (viewModel.isFieldsNotEmpty()) {
                    viewModel.saveHabit()
                    findNavController().navigateUp()
                }
                return false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}