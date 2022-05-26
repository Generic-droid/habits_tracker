package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.FragmentHabitsListModalBottomSheetBinding
import ru.vidos.habitstracker.domain.HabitsTrackerApplication
import ru.vidos.habitstracker.ui.HabitsTrackerViewModel
import ru.vidos.habitstracker.ui.HabitsTrackerViewModelFactory

/**
 *
 */
class HabitsListModalBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: HabitsTrackerViewModel by activityViewModels {
        HabitsTrackerViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .appComponent.getGetHabitsUseCase(),
            (activity?.application as HabitsTrackerApplication)
                .appComponent.getDeleteHabitUseCase(),
            (activity?.application as HabitsTrackerApplication)
                .appComponent.getFetchHabitsUseCase(),
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