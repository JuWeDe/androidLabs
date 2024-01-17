package com.example.messenger411.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger411.Data.Hero
import com.example.messenger411.databinding.HeroInfoBinding

class ResponseAdapter(private var items: List<Hero>) :
    RecyclerView.Adapter<ResponseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HeroInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Hero>) {
        this.items = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: HeroInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(characterDTO: Hero) {
            with(binding) {
                nameTextView.text = if (characterDTO.name != "") characterDTO.name else "Нет данных"
                cultureTextView.text = "[culture] " + if (characterDTO.culture != "") characterDTO.culture else "Нет данных"
                bornTextView.text = "[born] " + if (characterDTO.born != "") characterDTO.born else "Нет данных"
                titlesTextView.text = "[titles] " + if (characterDTO.aliases!!.joinToString(", ") != "") characterDTO.aliases!!.joinToString(", ") else "Нет данных"
                aliasesTextView.text = "[aliases] " + if (characterDTO.titles!!.joinToString(", ") != "") characterDTO.titles!!.joinToString(", ") else "Нет данных"
                playedByNameTextView.text = "[playedBy] " + if (characterDTO.playedBy!!.joinToString(", ") != "") characterDTO.playedBy!!.joinToString(", ") else "Нет данных"
            }
        }
    }
}