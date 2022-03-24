package ru.vidos.habitstracker.ui.appinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.vidos.habitstracker.databinding.FragmentAppInfoBinding

class AppInfoFragment : Fragment() {

    private lateinit var appInfoViewModel: AppInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        appInfoViewModel =
            ViewModelProvider(this)[AppInfoViewModel::class.java]

        val binding =
            FragmentAppInfoBinding.inflate(inflater, container, false)

        appInfoViewModel.text.observe(viewLifecycleOwner) {
            binding.textInfo.text = it
        }

        /* Ensures that all the clicks/events of
         view correctly works when app is running.
         */
        return binding.root
    }
}