package com.example.posthub.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.posthub.R
import com.example.posthub.databinding.FragmentHomeBinding
import com.example.posthub.model.entity.Post
import com.example.posthub.model.room.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //    private val viewModel: OverviewViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
        val db = AppDatabase.getDataBase(requireContext())
//        var ppost = Post(null, "gog", "comma", LocalDate.now(), R.color.red)
//        CoroutineScope(Dispatchers.IO).launch {
//            db.getDao().createPost(ppost)
//        }
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createPostFragment)
        }
        lifecycleScope.launch {
            db.getDao().getAllPosts().collect { posts ->
                val stringBuilder = StringBuilder()
                for (post in posts) {
                    stringBuilder.append(post.toString()).append("\n")
                }
                binding.tvPost.text = stringBuilder.toString()
            }

        }
        return binding.root
    }
}
