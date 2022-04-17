package com.yafianwary.juaraandroid.todolistminimalist.ui.main

import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity
import com.yafianwary.juaraandroid.todolistminimalist.databinding.RecyclerViewListBinding
import com.yafianwary.juaraandroid.todolistminimalist.ui.edit.EditActivity

class RecyclerAdapter(private val setCheckboxStatus: SetCheckboxStatus) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private val todoListItem = ArrayList<MyEntity>()

    fun setListTodo(todoListItemNew: List<MyEntity>) {
//        val diffCallback = RecyclerDiffUtilCallback(this.todoListItem, todoListItemNew)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.todoListItem.clear()
        this.todoListItem.addAll(todoListItemNew)
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding =
            RecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(todoListItem[position])
        Log.e("onBindViewHolder", todoListItem[position].toString())
    }

    override fun getItemCount(): Int {
        return todoListItem.size
    }

    inner class RecyclerViewHolder(private val binding: RecyclerViewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyEntity) {
            with(binding) {
                itemCheckbox.isChecked = data.isDone
                itemTvDate.text = data.dueDate
                itemTvTitle.text = data.title
                if (itemCheckbox.isChecked) {
                    itemTvTitle.paintFlags = itemTvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    itemTvTitle.paintFlags = itemTvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
                }
                Log.e("ViewHolder", data.title.toString())
                itemCheckbox.setOnCheckedChangeListener { _, b ->
                    data.isDone = b
                    itemCheckbox.isChecked = b
                    setCheckboxStatus.onItemClicked(data)
//                    if (b) {
//                        itemTvTitle.paintFlags = itemTvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    } else {
//                        itemTvTitle.paintFlags = itemTvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
//                    }
                }
                root.setOnClickListener {
                    val intent = Intent(it.context, EditActivity::class.java)
                    intent.putExtra(EditActivity.EXTRA_DATA, data)
                    it.context.startActivity(intent)
                }
            }
        }

//        fun dateHelper(date: String): String {
//            val calendar1 = Calendar.getInstance()
//            val calendar2 = Calendar.getInstance()
//            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
//            val dateForReal = dateFormat.parse(date)
//            val dateForReal2 = calendar2.time(dateFormat.parse(date))
//            val diff: Long = dateForReal.time - calendar1.time
//            return dateForReal - DateUtils.DAY_IN_MILLIS
//        }
    }
}