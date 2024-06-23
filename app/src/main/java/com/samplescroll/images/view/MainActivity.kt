package com.samplescroll.images.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samplescroll.images.viewmodels.ImagesViewModel
import com.samplescroll.util.ApiResponse
import com.samplescroll.R
import com.samplescroll.adapters.HorizontalAdapter
import com.samplescroll.adapters.VerticalAdapter
import com.samplescroll.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ImagesViewModel by viewModels()
    private lateinit var adapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe()
        setUpAdapter()
        quoteApi()
    }

    private fun setUpAdapter() {
        adapter = HorizontalAdapter(this@MainActivity)
        binding.horizontalRecyclerView.adapter = adapter
        binding.horizontalRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        verticalAdapter = VerticalAdapter(this@MainActivity)
        binding.verticalRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.verticalRecyclerView.adapter = verticalAdapter
    }

    private fun quoteApi() {
        viewModel.fetchQuotes()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.imagesResponse.collect {
                when (it) {
                    is ApiResponse.Loading -> {

                    }

                    is ApiResponse.Success -> {
                        it.data?.let { images ->
                            adapter.updateData(images)
                            verticalAdapter.updateData(images)
                        }
                    }

                    is ApiResponse.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.something_went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }
        }
    }
}
