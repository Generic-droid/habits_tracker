package ru.vidos.habitstracker.adapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.models.Habit
import ru.vidos.habitstracker.models.HabitTypes


const val LOG_TAG = "myLogs"

@BindingAdapter("habitsListData")
fun bindDealsRecyclerView(recyclerView: RecyclerView, data: List<Habit>?) {

    val adapter = recyclerView.adapter as HabitsRecyclerViewAdapter

    // This call notifies the RecyclerView that a new list of data is ready.
    adapter.submitList(data)

    Log.d(LOG_TAG, "BindingAdapter: $data, $adapter")

}

@BindingAdapter("tint")
fun ImageView.setTintDrawable(item: Habit) {

    setColorFilter(
        if(item.type == HabitTypes.GOOD.name) {
            item.color.toColorInt()
        } else "#FF0000".toColorInt()
            )
}

@BindingAdapter("priority")
fun TextView.setPriority(item: Habit) {
    text = resources.getStringArray(R.array.habitPriority)[item.priority]
}

/**
 * Sets the imageDrawable for the habit type
 */
@BindingAdapter("src")
fun ImageView.setDrawable(item: Habit) {
    setImageResource(
        if (item.type == HabitTypes.GOOD.name) {
            R.drawable.ic_good_habit_smile
        }   else  R.drawable.ic_bad_habit_smile
    )
}

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    error = errorMessage
}