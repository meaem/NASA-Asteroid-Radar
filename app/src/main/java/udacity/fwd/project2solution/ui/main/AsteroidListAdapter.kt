package udacity.fwd.project2solution.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import udacity.fwd.project2solution.databinding.AsteroidRecyclerViewItemBinding
import udacity.fwd.project2solution.model.Asteroid

class AsteroidListAdapter(private val onAsteroidClick: (ast: Asteroid) -> Unit) :
    ListAdapter<Asteroid, AsteroidListAdapter.AstroidViewHolder>(DiffCallback) {

    class AstroidViewHolder(
        private val binding: AsteroidRecyclerViewItemBinding,
        private val onAsteroidClick: (ast: Asteroid) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.root.setOnClickListener {
                onAsteroidClick(item)
            }
            binding.executePendingBindings()
//                binding.closeApproachDateTv.text = item.closeApproachDate

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstroidViewHolder {

        return AstroidViewHolder(
            AsteroidRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onAsteroidClick
        )
    }

    override fun onBindViewHolder(holder: AstroidViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return newItem.id == oldItem.id
        }

    }
}


