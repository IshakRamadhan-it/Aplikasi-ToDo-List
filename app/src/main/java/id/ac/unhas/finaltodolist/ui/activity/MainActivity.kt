package id.ac.unhas.finaltodolist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.unhas.finaltodolist.R
import id.ac.unhas.finaltodolist.db.todolist.ToDoList
import id.ac.unhas.finaltodolist.ui.view_model.ToDoListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var toDoListViewModel: ToDoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.fab)

        listRV.layoutManager = LinearLayoutManager(this)
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
}
