package com.example.roufy235.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_view_notes.*

class ViewNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)

        val myDate = intent.extras ?: return
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
