package id.ac.unhas.todolist.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.finaltodolist.R
import id.ac.unhas.todolist.db.todolist.ToDoList
import id.ac.unhas.todolist.ui.Converter
import id.ac.unhas.todolist.ui.view_model.ToDoListViewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddListActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextNote: EditText
    private lateinit var editTextTime: EditText
    private lateinit var btnSave: Button
    private lateinit var toDoListViewModel: ToDoListViewModel
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)
        if (supportActionBar != null) {
            supportActionBar?.title = "Penambahan Tugas"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Button

        editTextTitle = findViewById(R.id.title_content)
        editTextDate = findViewById(R.id.due_date_content)
        editTextNote = findViewById(R.id.note_content)
        editTextTime = findViewById(R.id.due_time_content)
        btnSave = findViewById(R.id.btn_save)
        toDoListViewModel = ViewModelProvider(this).get(ToDoListViewModel::class.java)

        editTextDate.setOnClickListener {
            setDueDate()
        }

        editTextTime.setOnClickListener {
            setDueTime()
        }

        btnSave.setOnClickListener {
            saveList()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun setDueDate() {
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        // Date picker dialog
        val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, date ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, date)
            editTextDate.setText(SimpleDateFormat("EEE, MMM dd, yyyy").format(calendar.time))
        }

        DatePickerDialog(this, dateListener, year, month, date).show()
    }

    private fun setDueTime() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            editTextTime.setText(SimpleDateFormat("HH:mm").format(calendar.time))
        }

        TimePickerDialog(this, timeSetListener, hour, minute, true).show()
    }

    private fun saveList() {
        val current = ZonedDateTime.now(ZoneId.of("+8"))
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy, HH:mm:ss")

        val strCreatedDate = current.format(formatter)
        val createdDate = Converter.dateToInt(current)

        val strDueDate = editTextDate.text.toString().trim()
        val dueDate = Converter.stringDateToInt(strDueDate)

        val strDueHour = editTextTime.text.toString().trim()
        val dueHour = Converter.stringTimeToInt(strDueHour)

        val title = editTextTitle.text.toString().trim()
        val note = editTextNote.text.toString().trim()

            toDoListViewModel.insertList(
                ToDoList(
                    createdDate = createdDate,
                    strCreatedDate = strCreatedDate,
                    title = title,
                    dueDate = dueDate,
                    dueHour = dueHour,
                    strDueDate = strDueDate,
                    strDueHour = strDueHour,
                    note = note,
                    isFinished = false
                )
            )

            finish()
        }

    }
