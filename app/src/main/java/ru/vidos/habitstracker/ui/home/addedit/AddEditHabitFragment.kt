package ru.vidos.habitstracker.ui.home.addedit

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.FragmentAddEditHabitBinding
import ru.vidos.habitstracker.domain.HabitsTrackerApplication
import ru.vidos.habitstracker.ui.adapters.ColorPickerRecyclerViewAdapter
import ru.vidos.habitstracker.utils.GradientItemDecoration


class AddEditHabitFragment : Fragment() {

    private val args: AddEditHabitFragmentArgs by navArgs()

    private val viewModel: AddEditHabitViewModel by viewModels {
        AddEditHabitViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .appComponent.getInsertHabitUseCase(),
            (activity?.application as HabitsTrackerApplication)
                .appComponent.getUpdateHabitUseCase(),
                args.currentHabit)
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
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the AddEditHabitViewModel
        binding.viewModel = viewModel

        val colorPickerAdapter = ColorPickerRecyclerViewAdapter {

            setColorFromView(it)
        }

        binding.colorPickerRecyclerView.apply {
            adapter = colorPickerAdapter
            addItemDecoration( GradientItemDecoration() )
        }

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
            viewModel.currentHabit.value?.count == 0 -> {
                viewModel.setQuantityError(resources.getString(R.string.empty_quantity))
                false
            }
            viewModel.currentHabit.value?.frequency == 0 -> {
                viewModel.setPeriodicityError(resources.getString(R.string.empty_quantity))
                false
            }
            else -> { true }
        }
    }

    private fun setColorFromView(view: View) {

        activity?.let {
            val root: View = it.window.decorView.rootView

            // Here we got whole screen bitmap
            val screenshot = root.drawToBitmap()

            // get view coordinates
             val location = IntArray(2)
             view.getLocationInWindow(location)

            if (location[0] < 0) location[0] = 0

            val pixel = screenshot.getPixel(location[0], location[1] - 24)

            viewModel.setColor(pixel)
        }
    }
}