package com.yafianwary.juaraandroid.todolistminimalist.ui.edit

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yafianwary.juaraandroid.todolistminimalist.R
import com.yafianwary.juaraandroid.todolistminimalist.data.MyEntity
import com.yafianwary.juaraandroid.todolistminimalist.data.ViewModelFactory
import com.yafianwary.juaraandroid.todolistminimalist.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private var isEdit = false
    private var entity: MyEntity? = null
    private var oldEntity: MyEntity? = null
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dropdownItem = resources.getStringArray(R.array.due)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, dropdownItem)
        binding.tvAutoComplete.setAdapter(arrayAdapter)

        viewModel = getViewModel(this@EditActivity)

        entity = intent.getParcelableExtra(EXTRA_DATA)
        oldEntity = entity
        if (entity != null) {
            isEdit = true
        } else {
            entity = MyEntity()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (entity != null) {
                entity?.let { thisEntity ->
                    binding.edtTitle.setText(thisEntity.title)
                    binding.tvAutoComplete.setText(thisEntity.dueDate)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
            binding.btnDelete.visibility = View.GONE
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            var dueDate = binding.tvAutoComplete.text.toString().trim()

            dueDate = dateHelper(dueDate)

            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }
                dueDate.isEmpty() -> {
                    binding.tvAutoComplete.error = getString(R.string.empty)
                }
                else -> {
                    entity.let { thisEntity ->
                        thisEntity?.title = title
                        thisEntity?.dueDate = dueDate
                    }
                    if (isEdit) {
                        viewModel.updateData(entity as MyEntity)
                        showToast(getString(R.string.changed))
                    } else {
                        viewModel.insertData(entity as MyEntity)
                        showToast(getString(R.string.added))
                    }

                    finish()
                }
            }
        }
        binding.btnDelete.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                showAlertDialog(ALERT_DIALOG_DELETE)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        entity?.let {
            if (oldEntity != entity) {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    viewModel.deleteData(entity as MyEntity)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getViewModel(activity: AppCompatActivity): EditViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[EditViewModel::class.java]
    }

    private fun dateHelper(dateString: String): String {

        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        when (dateString) {
            "Today" -> {
                val today = calendar.time
                return dateFormat.format(today)
            }
            "Tomorrow" -> {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                val tomorrow = calendar.time
                return dateFormat.format(tomorrow)
            }
            "Next Week" -> {
                calendar.add(Calendar.DAY_OF_YEAR, 7)
                val nextWeek = calendar.time
                return dateFormat.format(nextWeek)
            }
            else -> {
                val today = calendar.time
                return dateFormat.format(today)
            }
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 1
        const val ALERT_DIALOG_DELETE = 2
    }
}