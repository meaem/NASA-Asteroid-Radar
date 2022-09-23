package udacity.fwd.project2solution.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import udacity.fwd.project2solution.databinding.AsteroidRecyclerViewItemBinding
import udacity.fwd.project2solution.model.Asteroid

class AsteroidListAdapter(private val onClickListener: onAsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidListAdapter.AstroidViewHolder>(DiffCallback) {

    class AstroidViewHolder(
        private val binding: AsteroidRecyclerViewItemBinding,
        private val onClickListener: onAsteroidClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) {
            binding.asteroid = item

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
            ), onClickListener
        )
    }

    override fun onBindViewHolder(holder: AstroidViewHolder, position: Int) {
        val ast = getItem(position)
        holder.itemView.setOnClickListener {

            onClickListener.onClick(ast)

        }
        holder.bind(ast)

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return newItem.id == oldItem.id
        }

    }

    class onAsteroidClickListener(private val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

}


