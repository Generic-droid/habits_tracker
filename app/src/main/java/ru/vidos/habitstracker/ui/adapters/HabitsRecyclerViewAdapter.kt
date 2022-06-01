package ru.vidos.habitstracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vidos.data.models.HabitDto
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.databinding.HabitItemBinding
import java.security.MessageDigest.isEqual

class HabitsRecyclerViewAdapter(
    private val onItemClicked: (HabitDto) -> Unit,
    private val onButtonClicked: (HabitDto) -> Unit
) : ListAdapter<HabitDto,
        HabitsRecyclerViewAdapter.HabitsViewHolder>(DiffCallback) {

    class HabitsViewHolder(private val binding: HabitItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(habitDto: HabitDto) {
            binding.habitDto = habitDto
            // This call is an optimization that asks data binding to execute any pending bindings
            // right away. It's always a good idea to call executePendingBindings() when you use
            // binding adapters in a RecyclerView, because it can slightly speed up sizing the views.
            binding.executePendingBindings()
        }

            }

    fun getHabitAt(position: Int): HabitDto {
        return getItem(position)
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

        val buttonDone = holder.itemView.findViewById<Button>(R.id.done_button)
        buttonDone.setOnClickListener{
            onButtonClicked(current)
        }

        holder.bind(current)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HabitDto>() {

        override fun areItemsTheSame(oldItem: HabitDto, newItem: HabitDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HabitDto, newItem: HabitDto): Boolean {
            return oldItem.color == newItem.color &&
                    oldItem.count == newItem.count &&
                    oldItem.date == newItem.date &&
                    oldItem.description == newItem.description &&
                    oldItem.dates == newItem.dates &&
                    oldItem.frequency == newItem.frequency &&
                    oldItem.priority == newItem.priority &&
                    oldItem.title == newItem.title &&
                    oldItem.type == newItem.type &&
                    oldItem.uid == newItem.uid
        }
    }
}