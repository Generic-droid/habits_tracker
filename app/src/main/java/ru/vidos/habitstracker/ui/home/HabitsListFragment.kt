package ru.vidos.habitstracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vidos.habitstracker.HabitsTrackerApplication
import ru.vidos.habitstracker.HabitsTrackerViewModel
import ru.vidos.habitstracker.HabitsTrackerViewModelFactory
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.adapters.HabitsRecyclerViewAdapter
import ru.vidos.habitstracker.databinding.FragmentHabitsListBinding

class HabitsListFragment : Fragment() {

    private val viewModel: HabitsTrackerViewModel by activityViewModels {
        HabitsTrackerViewModelFactory(
            (activity?.application as HabitsTrackerApplication)
                .habitsTrackerRepository
        )
    }

    private val habitsListModalBottomSheet = HabitsListModalBottomSheet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

            val action = HabitsListFragmentDirections.actionGlobalAddEditFragment(it)
            findNavController().navigate(action)
        }

        // Sets the adapter of the HabitsRecyclerView RecyclerView
        binding.habitsRecyclerView.adapter = adapter

        binding.habitsFab.setOnClickListener {

            val action = HabitsListFragmentDirections.actionGlobalAddEditFragment()
            findNavController().navigate(action)
        }

        // Hides FAB when scrolling the list up
        binding.habitsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0){
                    binding.habitsFab.hide()
                } else{
                    binding.habitsFab.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        /*
         Ensures that all the clicks/events of
         view correctly works when app is running.
         */
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.habits_list_options, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val menuItem = menu.findItem(R.id.habits_list_search)
        val search = menuItem.actionView as SearchView
        searching(search)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.habits_list_bottom_sheet -> {
                activity?.supportFragmentManager?.let {
                    habitsListModalBottomSheet.show(it, HabitsListModalBottomSheet.TAG)
                }
                false
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searching (search: SearchView) {

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.sortHabitsByTitle(newText)
                Log.d("HabitsList", "Search: $newText")
                return true
            }
        })
    }
}