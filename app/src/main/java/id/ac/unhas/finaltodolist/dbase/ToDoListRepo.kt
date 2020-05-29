package id.ac.unhas.todolist.db.todolist

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.unhas.todolist.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoListRepo (application: Application){
    private val toDoListDao: ToDoListDao?
    private var lists: LiveData<List<ToDoList>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        toDoListDao = db?.toDoListDao()
        lists = toDoListDao?.getToDoList()
    }

    fun getLists(): LiveData<List<ToDoList>>?{
        return lists
    }

    fun insertList(list: ToDoList) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoListDao?.insertList(list)
        }
    }

    fun deleteList(list: ToDoList) = runBlocking{
        this.launch(Dispatchers.IO){
            toDoListDao?.deleteList(list)
        }
    }

    fun updateList(list: ToDoList) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoListDao?.updateList(list)
        }
    }

    fun searchResult(title: String): LiveData<List<ToDoList>>?{
        return toDoListDao?.searchResult(title)
    }

    fun sortByDueDateDescending(): LiveData<List<ToDoList>>?{
        return toDoListDao?.sortByDueDateDescending()
    }

    fun sortByCreatedDateAscending(): LiveData<List<ToDoList>>?{
        return toDoListDao?.sortByCreatedDateAscending()
    }

    fun sortByCreatedDateDescending(): LiveData<List<ToDoList>>?{
        return toDoListDao?.sortByCreatedDateDescending()
    }
}