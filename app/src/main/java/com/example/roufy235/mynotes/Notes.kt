package com.example.roufy235.mynotes

/**
 * Created by roufy235 on 0017 Mar 17, 2018.
 */

class Notes{
    var noteId: Int? = null
    var noteTitle: String? = null
    var noteContent: String? = null
    var noteDate: String? = null

    constructor(noteId: Int, noteTitle: String, noteContent: String, noteDate: String){
        this.noteId = noteId
        this.noteTitle = noteTitle
        this.noteContent = noteContent
        this.noteDate = noteDate
    }

}