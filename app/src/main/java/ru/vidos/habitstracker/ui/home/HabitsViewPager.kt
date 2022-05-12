package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.HabitsTrackerViewModel
import ru.vidos.habitstracker.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.adapters.HabitsViewPagerAdapter
import ru.vidos.habitstracker.databinding.HabitsViewPagerBinding
import ru.vidos.habitstracker.models.HabitTypes

class HabitsViewPager : Fragment() {

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

        val binding = HabitsViewPagerBinding.inflate(inflater, container, false)

        val habitsViewPagerAdapter = HabitsViewPagerAdapter(this)
        val viewPager = binding.pager
        viewPager.adapter = habitsViewPagerAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = resources.getString(R.string.good)
            } else tab.text = resources.getString(R.string.bad)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

                if (position == 0) {
                    viewModel.setHabitType(HabitTypes.GOOD)
                }
                else if (position == 1) {
                    viewModel.setHabitType(HabitTypes.BAD)
                }
                super.onPageSelected(position)
            }
        })

        return binding.root
    }

}