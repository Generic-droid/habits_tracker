package ru.vidos.habitstracker.ui.adapters

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import ru.vidos.habitstracker.R
import ru.vidos.habitstracker.domain.models.Habit
import ru.vidos.habitstracker.domain.models.HabitTypes
import ru.vidos.habitstracker.utils.Resource
import kotlin.math.round

@BindingAdapter("habitsListData")
fun bindHabitsRecyclerView(recyclerView: RecyclerView, data: List<Habit>?) {

    val adapter = recyclerView.adapter as HabitsRecyclerViewAdapter

    // This call notifies the RecyclerView that a new list of data is ready.
    adapter.submitList(data)

}

@BindingAdapter("rGbChannels")
fun TextView.setRgBChannels(color: Int) {
    text = resources.getString(R.string.rgb_value, "${color.red}, ${color.green}, ${color.blue}")
}

@BindingAdapter("hSvColorModel")
fun TextView.setHsVModel(color: Int){
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)

    text = resources.getString(R.string.hsv_value, "${round(hsv[0])}°, ${hsv[1]}, ${hsv[2]}")
}

@BindingAdapter("priority")
fun TextView.setPriority(item: Habit) {
    text = resources.getStringArray(R.array.habitPriority)[item.priority]
}

/**
 * Sets the imageDrawable for the habit type
 */
@BindingAdapter("src")
fun ImageView.setDrawable(type: Int) {
    setImageResource(
        if (type == HabitTypes.GOOD.ordinal) {
            R.drawable.ic_good_habit_smile
        }   else  R.drawable.ic_bad_habit_smile
    )
}

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    error = errorMessage
}

@BindingAdapter("habitsApiStatus")
fun ImageView.statusImageView(status: Resource.HabitsApiStatus?) {
    when(status) {
        Resource.HabitsApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        Resource.HabitsApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        Resource.HabitsApiStatus.SUCCESS -> {
            visibility = View.GONE
        }
        else -> {}
    }
}