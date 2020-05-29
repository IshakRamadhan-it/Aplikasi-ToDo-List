package id.ac.unhas.finaltodolist.db.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM todolist ORDER BY due_date ASC, due_hour ASC")
    fun getToDoList(): LiveData<List<ToDoList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(list: ToDoList)

    @Delete
    suspend fun deleteList(list: ToDoList)

    @Update
    suspend fun updateList(list: ToDoList)

    @Query("SELECT * FROM todolist WHERE title LIKE :title")
    fun searchResult(title: String): LiveData<List<ToDoList>>

    @Query("SELECT * FROM todolist ORDER BY due_date DESC, due_hour DESC")
    fun sortByDueDateDescending(): LiveData<List<ToDoList>>

    @Query("SELECT * FROM todolist ORDER BY created_date ASC")
    fun sortByCreatedDateAscending(): LiveData<List<ToDoList>>

    @Query("SELECT * FROM todolist ORDER BY created_date DESC")
    fun sortByCreatedDateDescending(): LiveData<List<ToDoList>>
}