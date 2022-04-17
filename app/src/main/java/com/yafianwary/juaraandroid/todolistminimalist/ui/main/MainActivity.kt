package com.yafianwary.juaraandroid.todolistminimalist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yafianwary.juaraandroid.todolistminimalist.R
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity
import com.yafianwary.juaraandroid.todolistminimalist.data.ViewModelFactory
import com.yafianwary.juaraandroid.todolistminimalist.databinding.ActivityMainBinding
import com.yafianwary.juaraandroid.todolistminimalist.ui.edit.EditActivity

class MainActivity : AppCompatActivity(), SetCheckboxStatus {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = getViewModel(this@MainActivity)
        mainViewModel.getAllData().observe(this) { dataList ->
            if (dataList != null) {
                recyclerAdapter.setListTodo(dataList)
                Log.e("observe", dataList.toString())
            }
        }

        recyclerAdapter = RecyclerAdapter(this)

        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.setHasFixedSize(true)
        binding.rvTodoList.adapter = recyclerAdapter

        binding.myFab.setOnClickListener { view ->
            if (view.id == R.id.my_fab) {
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onItemClicked(myEntity: MyEntity) {
        mainViewModel.updateData(myEntity)
        Log.e("onItemClicked", myEntity.toString())
//        recyclerAdapter.notifyDataSetChanged()
    }
}