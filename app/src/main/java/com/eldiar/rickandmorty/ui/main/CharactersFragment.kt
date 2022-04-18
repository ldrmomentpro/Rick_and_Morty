package com.eldiar.rickandmorty.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.eldiar.rickandmorty.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModel()
    private val adapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initRecycler() {
        binding.characterRecycler.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> binding.progressBar.isVisible = true
                    is LoadState.NotLoading -> binding.progressBar.isVisible = false
                    is LoadState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}