package com.example.roufy235.mynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.text.SimpleDateFormat
import java.util.*

class AddNotes : AppCompatActivity() {

    var database: DbManager? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        var name = "Add Note"

        database = DbManager(this)
        try {
            val myData: Bundle? = intent.extras
            id = myData!!.getInt("id", 0)
            if (id != 0){
                name = "Edit Note"
                addTitle.setText(myData.getString("title"))
                addContent.setText(myData.getString("des"))
            }
        }catch (ex: Exception){}

        title = name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.check, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null){
            when(item.itemId){
                R.id.checkAddNote -> {
                    val title = addTitle.text.toString()
                    val content = addContent.text.toString()

                    if (title.isNotEmpty() && content.isNotEmpty()){
                        insert(title, content)
                    }else{
                        Toast.makeText(this, "Unable to add note", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun insert(title: String, content: String){

        val date = SimpleDateFormat("dd / MM / YYYY", Locale.ENGLISH).format(Date())
        val values = ContentValues()
        values.put("Title", title)
        values.put("Content", content)
        values.put("Date", date)

        if (id == 0){
            val id = database!!.insert(values)
            if (id > 0) {
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Unable to add note", Toast.LENGTH_SHORT).show()
            }
        }else{
            val id = database!!.update(values, "Id = ?", arrayOf(id.toString()))
            if (id > 0){
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Unable to update note", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}
