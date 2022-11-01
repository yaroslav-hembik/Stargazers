package it.hembik.stargazers.ui.stargazers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.hembik.domain.model.Stargazer
import it.hembik.stargazers.databinding.AdapterStargazerBinding

class StargazersPagerAdapter :
    PagingDataAdapter<Stargazer, StargazersPagerAdapter.StargazerViewHolder>(StargazerComparator) {

    override fun onBindViewHolder(holder: StargazerViewHolder, position: Int) {
        val stargazer = getItem(position)!!
        holder.view.ownerTv.text = stargazer.login
        Glide.with(holder.view.ownerImage)
            .load(stargazer.profileImageUrl)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerInside()
            .into(holder.view.ownerImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StargazerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterStargazerBinding.inflate(inflater, parent, false)
        return StargazerViewHolder(binding)
    }

    class StargazerViewHolder(val view: AdapterStargazerBinding) :
        RecyclerView.ViewHolder(view.root)

    object StargazerComparator : DiffUtil.ItemCallback<Stargazer>() {
        override fun areItemsTheSame(oldItem: Stargazer, newItem: Stargazer): Boolean {
            // Id is unique.
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Stargazer, newItem: Stargazer): Boolean {
            return oldItem == newItem
        }
    }
}