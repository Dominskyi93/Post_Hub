package com.example.posthub.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.posthub.R
import com.example.posthub.core.ui.adapters.ColorAdapter
import com.example.posthub.core.ui.viewModels.CreatePostViewModel
import com.example.posthub.core.ui.adapters.PhotoAdapter
import com.example.posthub.databinding.FragmentCreatePostBinding
import com.example.posthub.util.Colors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment : Fragment() {

    private  val viewModel: CreatePostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatePostBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvPhotosItems.adapter = PhotoAdapter()
        binding.rvColorsItems.adapter = ColorAdapter(Colors.entries)
        val button = binding.bCancel
        button.setOnClickListener {
            findNavController().navigate(R.id.action_createPostFragment_to_homeFragment)
        }
        return binding.root
    }
}