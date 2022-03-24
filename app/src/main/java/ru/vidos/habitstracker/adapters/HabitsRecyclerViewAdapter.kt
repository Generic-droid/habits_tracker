package ru.vidos.habitstracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vidos.habitstracker.data.Habit
import ru.vidos.habitstracker.databinding.HabitItemBinding

class HabitsRecyclerViewAdapter(private val onItemClicked: (Habit) -> Unit) : ListAdapter<Habit,
        HabitsRecyclerViewAdapter.HabitsViewHolder>(DiffCallback) {

    class HabitsViewHolder(private val binding: HabitItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            binding.habit = habit
            // This call is an optimization that asks data binding to execute any pending bindings
            // right away. It's always a good idea to call executePendingBindings() when you use
            // binding adapters in a RecyclerView, because it can slightly speed up sizing the views.
            binding.executePendingBindings()
        }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        return HabitsViewHolder(
            HabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        holder.bind(current)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Habit>() {

        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }
}