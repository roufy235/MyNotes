package com.example.roufy235.mynotes

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notes_tickets.view.*

class MainActivity : AppCompatActivity() {

    private var noteList = ArrayList<Notes>()

    private var adapter: NotesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //noteList.add(Notes(1, "bello", "Programming is fun", "17 / 03 / 2018"))
        //noteList.add(Notes(2, "bello", "Programming is fun", "17 / 03 / 2018"))

        //adapter = NotesListAdapter(this, noteList)
        //listAdapter.adapter = adapter
        loadNotes("%")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        sv.setSearchableInfo(sm.getSearchableInfo(componentName))

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadNotes("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                loadNotes("%$newText%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null){
            when(item.itemId){
                R.id.addNotes -> {
                    val intent = Intent(this, AddNotes::class.java)
                    startActivity(intent)
                }else -> {}
            }
        }
        return super.onOptionsItemSelected(item)
    }




    override fun onResume() {
        super.onResume()
        loadNotes("%")
    }

    fun loadNotes(title: String){
        val database = DbManager(this)
        val projection = arrayOf("Id", "Title", "Content", "Date")
        val selectionArgs = arrayOf(title)

        noteList.clear()
        val cursor = database.query(projection, "Title LIKE ?", selectionArgs, "Title")

        if (cursor.moveToFirst()){
            do {

                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val titleDb = cursor.getString(cursor.getColumnIndex("Title"))
                val contentDb = cursor.getString(cursor.getColumnIndex("Content"))
                val dateDb = cursor.getString(cursor.getColumnIndex("Date"))



                noteList.add(Notes(id, titleDb, contentDb, dateDb))
            }while (cursor.moveToNext())
        }
        adapter = NotesListAdapter(this, noteList)
        listAdapter.adapter = adapter

    }


    inner class NotesListAdapter(context : Context, list : ArrayList<Notes>) : BaseAdapter() {
        var noteList:ArrayList<Notes>? = list
        var context: Context? = context

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val firstNote = this.noteList!![position]

            val inflater = this.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val myView = inflater.inflate(R.layout.notes_tickets, null)

            myView.noteDes.setOnClickListener{
                val intent = Intent(this.context!!, ViewNotes::class.java)

                intent.putExtra("Title", firstNote.noteTitle)
                intent.putExtra("Des", firstNote.noteContent)
                intent.putExtra("Date", firstNote.noteDate)

                this.context!!.startActivity(intent)
            }


            myView.imageViewDelete.setOnClickListener{
                val confirmPop = ConfirmFragment()
                confirmPop.noteId = firstNote.noteId
                val fm = fragmentManager

                confirmPop.show(fm, "Confirm")
            }
            myView.noteTitle.setOnClickListener{
                val intent = Intent(this.context!!, ViewNotes::class.java)

                intent.putExtra("Title", firstNote.noteTitle)
                intent.putExtra("Des", firstNote.noteContent)
                intent.putExtra("Date", firstNote.noteDate)

                this.context!!.startActivity(intent)
            }

            myView.imageViewEdit.setOnClickListener{
                val intent = Intent(this.context, AddNotes::class.java)
                intent.putExtra("title", firstNote.noteTitle)
                intent.putExtra("des", firstNote.noteContent)
                intent.putExtra("id", firstNote.noteId)
                this.context!!.startActivity(intent)
            }

            myView.noteTitle.text = firstNote.noteTitle


            var max = 162
            val str : String?
            if (firstNote.noteContent!!.length <= max){
                max = firstNote.noteContent!!.length

                str = firstNote.noteContent!!.substring(0, max)
            }else{
                str = firstNote.noteContent!!.substring(0, max) + "...."
            }


            myView.noteDes.text = str


            myView.textView4.text = firstNote.noteDate


            return myView
        }

        override fun getItem(position: Int): Any {
            return this.noteList!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.noteList!!.size
        }

    }

    fun deleteNote(index: Int){
        val db = DbManager(this)

        val selectionArgs = arrayOf(index.toString())

        val id = db.delete("Id = ?", selectionArgs)

        if (id > 0){
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
            loadNotes("%")
        }else{
            Toast.makeText(this, "Unable to delete note", Toast.LENGTH_SHORT).show()
        }
    }


}
