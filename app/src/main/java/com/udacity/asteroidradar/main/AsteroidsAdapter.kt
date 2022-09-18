package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.bindAsteroidStatusImage
import com.udacity.asteroidradar.databinding.ItemContainerBinding

class AsteroidsAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(DiffCallback) {
    class AsteroidViewHolder(val binding: ItemContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid){
            binding.asteroid = asteroid
        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
            return oldAsteroid == newAsteroid
        }

        override fun areContentsTheSame(oldAsteroid: Asteroid, newAsteroid: Asteroid): Boolean {
            return oldAsteroid.id == newAsteroid.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(ItemContainerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onClicked(getItem(position))
        }
        holder.bind(getItem(position))
    }
}

class OnClickListener(val onClick: (asteroid : Asteroid)-> Unit){
    fun onClicked(asteroid: Asteroid) = onClick(asteroid)
}