package com.example.posthub.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.posthub.R
import com.example.posthub.core.ui.adapters.ColorSpinnerAdapter
import com.example.posthub.core.ui.adapters.PhotoAdapter
import com.example.posthub.core.ui.viewModels.CreatePostViewModel
import com.example.posthub.databinding.FragmentCreatePostBinding
import com.example.posthub.util.Colors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment : Fragment() {
    private val args by navArgs<CreatePostFragmentArgs>()
    private val viewModel: CreatePostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatePostBinding.inflate(inflater)
        binding.etComment.setText(args.postArg.comment)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvPhotosItems.adapter = PhotoAdapter()

        val colorSpinner = binding.colorSpinner
        val customAdapter = ColorSpinnerAdapter(requireContext(), Colors.entries)
        colorSpinner.adapter = customAdapter

        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedColor: Colors = customAdapter.getItem(position) as Colors
                Toast.makeText(requireContext(), "Selected color: ${selectedColor.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        val cancelButton = binding.bCancel
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_createPostFragment_to_homeFragment)
        }

        val saveButton = binding.saveButton
        saveButton.setOnClickListener {
            viewModel.saveDataToDatabase()
        }

        return binding.root
    }

    private fun updatePost() {

    }


//    private fun savePost() {
//        val comment = commentEditText.text.toString()
//        val date = LocalDate.now()
//        val imageUrl = selectedPostModel?.photo ?: ""
//        val color = colorSpinner.selectedItem
//        val post = Post(null, imageUrl, comment, date, color as Int)
//        if (selectedPostModel != null) {
//            post.id = selectedPostModel!!.id
//            CoroutineScope(Dispatchers.IO).launch {
//                db.getDao().updatePost(post)
//            }
//        } else {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.getDao().createPost(post)
//            }
//        }
//    }
}
