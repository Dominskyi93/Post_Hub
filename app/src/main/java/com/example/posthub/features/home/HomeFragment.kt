package com.example.posthub.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posthub.core.ui.PostAdapter
import com.example.posthub.databinding.FragmentHomeBinding
import com.example.posthub.retrofit.Photo
import com.example.posthub.util.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeFragment : Fragment() {
    private lateinit var responsePhotos: List<Photo>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            getPosts()
        }
        return binding.root
    }

    private fun createPostAdapter(photoList: List<Photo>) {
        val adapter = PostAdapter(photoList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun getPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val photoService = RetrofitInstance.api
                val response = photoService.getPosts()
                if (response.isSuccessful && response.body() != null) {
                    binding.textView.text = response.body()!![0].author
                    createPostAdapter(response.body()!!)
                }
            } catch (e: Exception) {
                println("app error ${e.message}")
                return@launch
            } catch (e: HttpException) {
                println("http error ${e.message}")
                return@launch
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}