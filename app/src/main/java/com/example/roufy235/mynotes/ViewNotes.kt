package com.example.roufy235.mynotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_view_notes.*

class ViewNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)

        var myDate = intent.extras

        val title = myDate.getString("Title")
        val des = myDate.getString("Des")
        val date = myDate.getString("Date")

        viewTitle.text = title
        viewDate.text = date
        viewDes.text = des
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.delete, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
