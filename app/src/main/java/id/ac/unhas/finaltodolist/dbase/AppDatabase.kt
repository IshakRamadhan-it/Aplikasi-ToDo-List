package id.ac.unhas.finaltodolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.unhas.finaltodolist.db.todolist.ToDoList
import id.ac.unhas.finaltodolist.db.todolist.ToDoListDao

@Database(entities = [ToDoList::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun toDoListDao(): ToDoListDao

    companion object{
        private const val DB_NAME = "TO_DO_LIST_DB"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if(instance == null){
                synchronized(AppDatabase::class){
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME
                        )
                        .build()
                }
            }
            return instance
        }
    }
}