package com.eldiar.rickandmorty.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import coil.load
import com.eldiar.rickandmorty.R
import com.eldiar.rickandmorty.databinding.FragmentCharacterBinding
import com.eldiar.rickandmorty.utils.ApiStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModel()

    private val navigationArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharacterDetailResponse()
        loadStatus()
    }

    private fun getCharacterDetailResponse() {
        viewModel.getCharacterDetail(navigationArgs.id)
    }

    private fun loadStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            binding.apply {
                when (status) {
                    ApiStatus.LOADING -> {
                        contentCl.isVisible = false
                        errorTv.isVisible = false
                        progressBar.isVisible = true
                    }
                    ApiStatus.ERROR -> {
                        contentCl.isVisible = false
                        progressBar.isVisible = false
                        errorTv.isVisible = true
                    }
                    ApiStatus.DONE -> {
                        progressBar.isVisible = false
                        errorTv.isVisible = false
                        contentCl.isVisible = true
                        subscribeToLiveData()
                    }
                }
            }
        }

    }

    private fun subscribeToLiveData() {
        viewModel.characterDetail.observe(viewLifecycleOwner) {
            binding.apply {
                avatarIv.load(it.image)
                nameTv.text = getString(R.string.name, it.name)
                speciesTv.text = getString(R.string.species, it.species)
                genderTv.text = getString(R.string.gender, it.gender)
                statusTv.text = getString(R.string.status, it.status)
                episodesTv.text = getString(R.string.episodes_count, it.episode.size.toString())
                locationTv.text = getString(R.string.last_location, it.location.name)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}