package ru.vidos.habitstracker.ui.home.addedit

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.FragmentAddEditHabitBinding

class AddEditHabitFragment : Fragment() {

    private val args: AddEditHabitFragmentArgs by navArgs()

    private val viewModel: AddEditHabitViewModel by viewModels {
        AddEditHabitViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .habitsTrackerRepository, args.currentHabit
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

        val binding =
            FragmentAddEditHabitBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the AddEditHabitViewModel
        binding.addEditHabitViewModel = viewModel

        /*
         Ensures that all the clicks/events of
         view correctly works when app is running.
         */
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val save = menu.findItem(R.id.save_habit)
        val edit = menu.findItem(R.id.edit_habit)

        if (args.currentHabit == null) {
            save.apply {
                isVisible = true
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
            edit.isVisible = false
        } else {
            edit.apply {
                isVisible = true
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
            save.isVisible = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_edit_habit_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_habit -> {
                if (isFieldsNotEmpty()) {
                    viewModel.saveHabit()
                    Toast.makeText(
                        activity, resources.getString(R.string.habit_added), Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()
                }
                false
            }
            R.id.edit_habit -> {
                if (isFieldsNotEmpty()) {
                    viewModel.changeHabit()
                    Toast.makeText(
                        activity, resources.getString(R.string.changes_saved), Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()
                }
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isFieldsNotEmpty(): Boolean {
        return when {
            viewModel.currentHabit.value?.title.isNullOrBlank() -> {
                viewModel.setTitleError(resources.getString(R.string.empty_title))
                false
            }
            viewModel.currentHabit.value?.description.isNullOrBlank() -> {
                viewModel.setDescriptionError(resources.getString(R.string.empty_description))
                false
            }
            viewModel.currentHabit.value?.quantity.isNullOrBlank() -> {
                viewModel.setQuantityError(resources.getString(R.string.empty_quantity))
                false
            }
            viewModel.currentHabit.value?.periodicity.isNullOrBlank() -> {
                viewModel.setPeriodicityError(resources.getString(R.string.empty_quantity))
                false
            }
            else -> { true }
        }
    }
}