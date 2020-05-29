package id.ac.unhas.finaltodolist.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.finaltodolist.R
import id.ac.unhas.finaltodolist.db.todolist.ToDoList
import kotlinx.android.synthetic.main.item_list.view.*

class ToDoListAdapter(private val context: Context?, private val listener: (ToDoList, Int) -> Unit):
    RecyclerView.Adapter<ToDoListViewHolder>(){

    private var toDoLists = listOf<ToDoList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = toDoLists.count()

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        if(context != null){
            holder.bindItem(context, toDoLists[position], listener)
        }
    }

    fun setLists(lists: List<ToDoList>){
        this.toDoLists = lists
        notifyDataSetChanged()
    }

}

class ToDoListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bindItem(context: Context, toDoList: ToDoList, listener: (ToDoList, Int) -> Unit){
        itemView.item_title_list.text = toDoList.title
        itemView.item_due_time.text = "${toDoList.strDueDate}, ${toDoList.strDueHour}"
        itemView.item_note.text = toDoList.note

        itemView.setOnClickListener{
            listener(toDoList, layoutPosition)
        }
    }
}
