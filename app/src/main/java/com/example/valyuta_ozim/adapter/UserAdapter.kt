package com.example.valyuta_ozim.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valyuta_ozim.R
import com.example.valyuta_ozim.databinding.ItemRvBinding
import com.example.valyuta_ozim.models.Valyuta

class UserAdapter(var list: ArrayList<Valyuta>, var rvClick: RvClick) :
    RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(valyuta: Valyuta) {
            itemRvBinding.name.text =
                valyuta.CcyNm_UZ // bu yer da qaysi davlat pulinikini tenglab qoyyapman
            if (valyuta.Diff.toFloat() < 0) {//bularda esa  valyuta 0 dan teppa yoki pastda yoki teng bolsa rasm qanday chiqishi
                itemRvBinding.imageTreding.setImageResource(R.drawable.ic_treding)
            } else if (valyuta.Diff.toFloat() > 0) {
                itemRvBinding.imageTreding.setImageResource(R.drawable.ic_treding)
            } else {
                itemRvBinding.imageTreding.setImageResource(R.drawable.ic_treding)
            }
            itemRvBinding.sum.text = valyuta.Rate//bu yerda narxini tenglab qoydim

            itemRvBinding.cardView.setOnClickListener {
                rvClick.onClick(valyuta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
    interface RvClick {
        fun onClick(valyuta: Valyuta)
    }
}