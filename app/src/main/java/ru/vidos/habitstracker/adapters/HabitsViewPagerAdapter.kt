package ru.vidos.habitstracker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.vidos.habitstracker.ui.home.HabitsListFragment

/**
 * The number of pages (wizard steps) to show in this demo.
 */
private const val NUM_PAGES = 2

class HabitsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = HabitsListFragment()

}