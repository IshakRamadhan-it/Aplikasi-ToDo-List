package id.ac.unhas.finaltodolist.db.todolist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todolist")
data class ToDoList (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "created_date")
    var createdDate: Int? = null,

    @ColumnInfo(name = "str_created_date")
    var strCreatedDate: String? = null,

    @ColumnInfo(name = "updated_date")
    var updatedDate: Int? = null,

    @ColumnInfo(name = "due_date")
    var dueDate: Int? = null,

    @ColumnInfo(name = "due_hour")
    var dueHour: Int? = null,

    @ColumnInfo(name = "str_due_date")
    var strDueDate: String? = null,

    @ColumnInfo(name = "str_due_hour")
    var strDueHour: String? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "note")
    var note: String,

    @ColumnInfo(name = "is_finished")
    var isFinished: Boolean? = null
) : Parcelable