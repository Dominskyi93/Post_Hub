package com.example.posthub.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.posthub.R
import com.example.posthub.core.ui.adapters.ColorSpinnerAdapter
import com.example.posthub.core.ui.adapters.PhotoAdapter
import com.example.posthub.core.ui.viewModels.UpdatePostViewModel
import com.example.posthub.databinding.FragmentUpdatePostBinding
import com.example.posthub.model.entity.Post
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.Colors
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class UpdatePostFragment : Fragment() {
    private val args by navArgs<UpdatePostFragmentArgs>()
    private val viewModel: UpdatePostViewModel by viewModels()
    private val _selectedColor = MutableLiveData<Colors>()
    private val selectedColor: LiveData<Colors> = _selectedColor
    private val _selectedPhoto = MutableLiveData<String>()
    private val selectedPhoto: LiveData<String> = _selectedPhoto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUpdatePostBinding.inflate(inflater)
        binding.etComment.setText(args.postArg?.comment)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val rvPhotosAdapter = PhotoAdapter()
        binding.rvPhotosItems.adapter = rvPhotosAdapter
        rvPhotosAdapter.setOnItemClickListener(object : PhotoAdapter.OnItemClickListener {
            override fun onItemClick(photo: Photo) {
                _selectedPhoto.value = photo.downloadUrl
            }
        })

        val colorSpinner = binding.colorSpinner
        val colorAdapter = ColorSpinnerAdapter(requireContext(), Colors.entries)
        colorSpinner.adapter = colorAdapter
        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                _selectedColor.value = colorSpinner.selectedItem as Colors
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        binding.bCancel.setOnClickListener {
            navigateFromUpdateToHome()
        }

        binding.saveButton.setOnClickListener {
            val newComment = binding.etComment.text.toString()
            val newColor = selectedColor.value!!.colorValue
            val photo: String = selectedPhoto.value ?: args.postArg?.photo ?: ""
            val createDate = args.postArg?.createDate ?: LocalDate.now()
            val post = Post(
                null,
                photo,
                newComment,
                createDate,
                newColor,
                null
            )
            if (args.postArg?.id == null) {
                createPost(post)
            } else {
                post.editDate = LocalDate.now()
                updatePost(post)
            }
        }
        return binding.root
    }

    private fun navigateFromUpdateToHome() {
        findNavController().navigate(R.id.action_updatePostFragment_to_homeFragment)
    }

    private fun toastMaker(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun createPost(post: Post) {
        viewModel.insertData(post)
        navigateFromUpdateToHome()
        toastMaker(getString(R.string.post_added))
    }

    private fun updatePost(post: Post) {
        post.id = args.postArg?.id
        viewModel.updateData(post)
        navigateFromUpdateToHome()
        toastMaker(getString(R.string.post_updated))
    }
}
