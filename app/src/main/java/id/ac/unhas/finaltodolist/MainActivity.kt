package id.ac.unhas.finaltodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.fab)

        floatingActionButton.setOnClickListener {
            addList()
        }
    }

    private fun addList() {
        val addIntent = Intent(this, AddListActivity::class.java)
        startActivity(addIntent)
    }
}
