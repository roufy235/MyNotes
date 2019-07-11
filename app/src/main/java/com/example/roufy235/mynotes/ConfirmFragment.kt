package com.example.roufy235.mynotes

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.delete.view.*

/**
 * Created by roufy235 on 0018 Mar 18, 2018.
 */

class ConfirmFragment: DialogFragment(){
    var noteId: Int? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val myView = inflater!!.inflate(R.layout.delete, container, false)


        val yesButton = myView.findViewById<Button>(R.id.buYes)
        val noButton = myView.findViewById<Button>(R.id.buNo)

        noButton.setOnClickListener{
            this.dismiss()
        }

        yesButton.setOnClickListener {
            val ma = activity as MainActivity

            myView.layoutHide.visibility = View.GONE
            myView.progressBar.visibility = View.VISIBLE

            ma.deleteNote(noteId!!)

            this.dismiss()
        }

        return myView
    }
}