package id.ac.unhas.finaltodolist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.unhas.finaltodolist.R
import id.ac.unhas.finaltodolist.db.todolist.ToDoList
import id.ac.unhas.finaltodolist.ui.adapter.ToDoListAdapter
import id.ac.unhas.finaltodolist.ui.view_model.ToDoListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var toDoListViewModel: ToDoListViewModel
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.fab)

        listRV.layoutManager = LinearLayoutManager(this)
        toDoListAdapter =
            ToDoListAdapter(this) { toDoList, i ->
                showAlertMenu(toDoList)
            }
        listRV.adapter = toDoListAdapter

        toDoListViewModel = ViewModelProvider(this).get(ToDoListViewModel::class.java)

        toDoListViewModel.getLists()?.observe(this, Observer {
            toDoListAdapter.setLists(it)
        })

        floatingActionButton.setOnClickListener{
            addList()
        }
    }
    private fun addList() {
        val addIntent = Intent(this, AddListActivity::class.java)
        startActivity(addIntent)
    }
    private fun showAlertMenu(toDoList: ToDoList){
        val items = arrayOf("Info", "Ubah", "Hapus")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setItems(items){ dialog, which ->
            when(which){
                0 -> {
                }
                1 -> {
                    updateList(toDoList)
                }
                2 -> {
                    alert.setTitle("Hapus Tugas ?")
                        .setMessage("Apakah anda yakin ?")
                        .setPositiveButton("Iya"){dialog, _ ->
                            toDoListViewModel.deleteList(toDoList)
                            dialog.dismiss()
                        }
                        .setNegativeButton("Tidak"){dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
        builder.show()
    }
    private fun updateList(toDoList: ToDoList){
        val addIntent = Intent(this, UpdateListActivity::class.java)
            .putExtra("EXTRA_LIST", toDoList)
            .putExtra(UpdateListActivity.EXTRA_TITLE_UPDATE, toDoList.title)
            .putExtra(UpdateListActivity.EXTRA_DATE_UPDATE, toDoList.strDueDate)
            .putExtra(UpdateListActivity.EXTRA_TIME_UPDATE, toDoList.strDueHour)
            .putExtra(UpdateListActivity.EXTRA_NOTE_UPDATE, toDoList.note)
            .putExtra(UpdateListActivity.EXTRA_IS_FINISHED_UPDATE, toDoList.isFinished)

        startActivity(addIntent)
    }

}
