package ru.vidos.habitstracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vidos.habitstracker.databinding.HabitColorPickerItemBinding

class ColorPickerRecyclerViewAdapter(private val onItemClicked: (View) -> Unit) :
    RecyclerView.Adapter<ColorPickerRecyclerViewAdapter.ColorPickerViewHolder>() {

    private val colors = List(16) {}

    class ColorPickerViewHolder(binding: HabitColorPickerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {}
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitColorPickerItemBinding.inflate(inflater, parent, false)
        return ColorPickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClicked(it)
        }

        holder.bind()
    }

    override fun getItemCount(): Int = colors.size

}