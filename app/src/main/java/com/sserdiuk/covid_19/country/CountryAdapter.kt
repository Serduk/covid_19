package com.sserdiuk.covid_19.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sserdiuk.covid_19.R
import com.sserdiuk.covid_19.entities.ResultItem


class CountryAdapter(private val clickListener: ListItemClickListener) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    interface ListItemClickListener {
        fun onItemClick(position: Int)
    }

    private var results: List<ResultItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.view_country_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setItems(newResult: List<ResultItem>) {
        results = newResult
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        private var country: TextView = itemView.findViewById(R.id.tvCountry)
        private var infected: TextView = itemView.findViewById(R.id.tvInfected)
        private var deaths: TextView = itemView.findViewById(R.id.tvDeaths)

        fun bind(position: Int) {
            val item = results[position]
            country.text = item.country
            infected.text = item.confirmed
            deaths.text = item.deaths
        }

        override fun onClick(p0: View?) {
            clickListener.onItemClick(adapterPosition)
        }
    }
}