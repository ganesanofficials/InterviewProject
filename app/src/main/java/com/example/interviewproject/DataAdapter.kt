package com.example.interviewproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewproject.databinding.DataAdapterLayoutBinding
import com.example.interviewproject.db.Data

class DataAdapter(val list:List<Data>,val callBack:(Data)->Unit):RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataAdapterLayoutBinding.inflate(inflater,parent,false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.binding.dataLayout.setOnClickListener {
            callBack(data)
        }
    }
}

class DataViewHolder(val binding: DataAdapterLayoutBinding) :RecyclerView.ViewHolder(binding.root){

    fun bind(data: Data){
        binding.dataId.text = data.id.toString()
        binding.dataName.text = data.name
        binding.dataDob.text = data.dob
    }
}