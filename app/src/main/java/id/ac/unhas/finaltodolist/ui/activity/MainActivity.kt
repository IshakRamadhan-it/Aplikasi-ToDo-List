package id.ac.unhas.finaltodolist.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.unhas.finaltodolist.db.todolist.ToDoList
import id.ac.unhas.finaltodolist.R
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        searchList(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_list -> sortList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addList(){
        val addIntent = Intent(this, AddListActivity::class.java)
        startActivity(addIntent)
    }

    private fun searchList(menu: Menu?){
        val item = menu?.findItem(R.id.search_list)

        val searchView = item?.actionView as androidx.appcompat.widget.SearchView?
        searchView?.isSubmitButtonEnabled = true

        searchView?.setOnQueryTextListener(
            object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query != null){
                        getItemsFromDb(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText != null){
                        getItemsFromDb(newText)
                    }
                    return true
                }
            }
        )
    }

    private fun getItemsFromDb(searchText: String){
        var searchText = searchText
        searchText = "%$searchText%"

        toDoListViewModel.searchResult(searchText)?.observe(this, Observer {
            toDoListAdapter.setLists(it)
        })
    }

    private fun sortList(){
        val items = arrayOf("Batas tanggal terakhir", "\n" +
                "Tanggal dibuat")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setTitle("Urutan Berdasarkan")
            .setItems(items){dialog, which ->
                when(which){
                    0 -> {
                        alert.setTitle(items[which])
                            .setPositiveButton("Urutan Naik"){dialog, _ ->
                                toDoListViewModel.getLists()?.observe(this, Observer {
                                    toDoListAdapter.setLists(it)
                                })
                                dialog.dismiss()
                            }
                            .setNegativeButton("Urutan Turun"){dialog, _ ->
                                toDoListViewModel.sortByDueDateDescending()?.observe(this, Observer {
                                    toDoListAdapter.setLists(it)
                                })
                                dialog.dismiss()
                            }
                            .show()
                    }
                    1 -> {
                        alert.setTitle(items[which])
                            .setPositiveButton("Urutan Naik"){dialog, _ ->
                                toDoListViewModel.sortByCreatedDateAscending()?.observe(this, Observer {
                                    toDoListAdapter.setLists(it)
                                })
                                dialog.dismiss()
                            }
                            .setNegativeButton("Urutan Turun"){dialog, _ ->
                                toDoListViewModel.sortByCreatedDateDescending()?.observe(this, Observer {
                                    toDoListAdapter.setLists(it)
                                })
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        builder.show()
    }

    private fun showAlertMenu(toDoList: ToDoList){
        val items = arrayOf("Info", "Ubah", "Hapus")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setItems(items){ dialog, which ->
            when(which){
                0 -> {
                    listDetails(alert, toDoList)
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

    private fun listDetails(alert: AlertDialog.Builder, toDoList: ToDoList){
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.item_details, null)

        val title: TextView = dialogView.findViewById(R.id.title)
        val createdDate: TextView = dialogView.findViewById(R.id.created_date_content)
        val dueTime: TextView = dialogView.findViewById(R.id.due_time_content)
        val note: TextView = dialogView.findViewById(R.id.note_content)

        title.text = toDoList.title
        createdDate.text = toDoList.strCreatedDate
        dueTime.text = "${toDoList.strDueDate}, ${toDoList.strDueHour}"
        note.text = toDoList.note

        alert.setView(dialogView)
            .setNeutralButton("OKE"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
