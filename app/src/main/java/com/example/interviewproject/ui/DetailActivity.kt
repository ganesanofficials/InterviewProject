package com.example.interviewproject.ui
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.interviewproject.DataAdapter
import com.example.interviewproject.MyApplication
import com.example.interviewproject.R
import com.example.interviewproject.databinding.ActivityDetailBinding
import com.example.interviewproject.databinding.ActivityMainBinding
import com.example.interviewproject.db.Data
import com.example.interviewproject.db.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailBinding
    lateinit var database:MyDatabase
    lateinit var data:Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)
         database  = (application as MyApplication).database
        val index = intent.getStringExtra("Id")
        if(index==null){
            binding.addDataDelete.visibility = GONE
        }else{
           setData(index)
            binding.addData.text = "Update"
        }
        binding.addData.setOnClickListener {
            if(index==null){
                insertData()
            }else{
                updateData(index)
            }
        }
        binding.addDataDelete.setOnClickListener {
            deleteData()
        }

    }

    private fun updateData(oldId:String) {

                lifecycleScope.launch(Dispatchers.IO) {
                    data.id = binding.addDataId.text.toString()
                    data.name = binding.addDataName.text.toString()
                    data.dob = binding.addDataDob.text.toString()
                    database.dataDao().updateData(data.id,data.name,data.dob,oldId)
                    finish()
        }
    }


    private fun insertData(){
        lifecycleScope.launch(Dispatchers.IO) {
            var await = lifecycleScope.async {
                database.dataDao().checkData(binding.addDataId.text.toString())
            }
            val isDataExist = await.await()
            if(!isDataExist) {
                database.dataDao().insert(
                    Data(
                        binding.addDataName.text.toString(),
                        binding.addDataDob.text.toString(),
                        binding.addDataId.text.toString()
                    )
                )
                finish()
            }else{
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"Data already exist",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    private fun setData(index:String){
        val job = lifecycleScope.launch(Dispatchers.IO) {
            database.dataDao().getData(index).collect{
                if(it!=null) {
                    data = it
                    binding.addDataId.setText(it.id)
                    binding.addDataName.setText(it.name)
                    binding.addDataDob.setText(it.dob)
                }

            }
        }
    }

    private fun deleteData(){
        lifecycleScope.launch {
            database.dataDao().delete(data)
            finish()
        }
    }
}