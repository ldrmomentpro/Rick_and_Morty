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
        initAdapter()
        subscribeToLiveData()
        doOnClick()
        doOnSwipeRefresh()
    }

    private fun subscribeToLiveData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun doOnSwipeRefresh() {
        binding.apply {
            swpRefresh.setOnRefreshListener {
                subscribeToLiveData()
                viewModel.getCharacters()
                swpRefresh.isRefreshing = false
            }
        }
    }

    private fun doOnClick() {
        adapter.onClick = {
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterFragment(it)
            this.findNavController().navigate(action)
        }
    }

    private fun initAdapter() {
        binding.characterRecycler.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        binding.errorTv.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is LoadState.NotLoading -> {
                        binding.progressBar.isVisible = false
                        binding.errorTv.isVisible = false
                        binding.characterRecycler.isVisible = true
                    }
                    is LoadState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.characterRecycler.isVisible = false
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