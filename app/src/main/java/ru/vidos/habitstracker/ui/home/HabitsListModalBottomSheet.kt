package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.HabitsTrackerViewModel
import ru.vidos.habitstracker.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.FragmentHabitsListModalBottomSheetBinding

/**
 *
 */
class HabitsListModalBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: HabitsTrackerViewModel by activityViewModels {
        HabitsTrackerViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .habitsTrackerRepository
        )
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHabitsListModalBottomSheetBinding.inflate(
            layoutInflater, container, false
        )

        // Giving the binding access to the HabitsTrackerViewModel
        binding.viewModel = viewModel

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }


}