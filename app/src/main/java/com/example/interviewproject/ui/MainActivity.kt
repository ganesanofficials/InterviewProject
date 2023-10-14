package com.example.interviewproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.interviewproject.DataAdapter
import com.example.interviewproject.MyApplication
import com.example.interviewproject.R
import com.example.interviewproject.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var adapter:DataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.listRecyclerView.layoutManager = layoutManager
        binding.saveData.setOnClickListener {
            detailActivity(null)
        }



    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    private fun getData(){
        val database  = (application as MyApplication).database
        lifecycleScope.launch {
            database.dataDao().getDataList().collect{
                adapter = DataAdapter(it){
                    detailActivity(it.id)
                }
                binding.listRecyclerView.adapter = adapter
            }
        }
    }

    private fun detailActivity(index:String?){
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("Id",index)
        startActivity(intent)
    }
}