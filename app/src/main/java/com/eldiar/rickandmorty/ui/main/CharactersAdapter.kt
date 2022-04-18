package com.eldiar.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.eldiar.rickandmorty.data.models.Character
import com.eldiar.rickandmorty.databinding.CharacterItemBinding

class CharactersAdapter :
    PagingDataAdapter<Character, CharactersAdapter.CharactersViewHolder>(CharactersComparator()) {

    var onClick: ((characterId: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersViewHolder {
        val binding = CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return CharactersViewHolder(binding)
    }

    inner class CharactersViewHolder(private val binding: CharacterItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun bind(item: Character) {
                    binding.apply {
                        nameTv.text = item.name
                        genderTv.text = item.gender
                        speciesTv.text = item.species
                        avatarIv.load(item.image)
                        characterCard.setOnClickListener { onClick?.invoke(item.id) }
                    }
                }
            }

    class CharactersComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}