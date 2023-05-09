package com.mertkadir.cryptoapiapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertkadir.cryptoapiapp.databinding.RecyclerRowBinding

import com.mertkadir.cryptoapiapp.model.CryptoModel


class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    private val colors : Array<String> = arrayOf("#0062ff","#0850c4","#e600ff","#ff0055","#f6ff00","#ff7700","#00b7ff","#1100ff")

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoModel : CryptoModel, colors: Array<String>, position: Int,listener: Listener){

            itemView.setOnClickListener {

                listener.onItemClick(cryptoModel)
            }

            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            binding.textname.text = cryptoModel.currency
            binding.textprice.text = cryptoModel.price

        }

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.bind(cryptoList[position],colors,position,listener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }


    override fun getItemCount(): Int {
        return cryptoList.size
    }



}