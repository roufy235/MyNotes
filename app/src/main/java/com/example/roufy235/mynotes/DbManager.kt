package com.example.roufy235.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

/**
 * Created by roufy235 on 0018 Mar 18, 2018.
 */

class DbManager{
    val dbName: String = "MyNotes"
    val dbTable: String = "Notes"
    val dbVersion:Int = 1

    val colTitle: String = "Title"
    val colContent: String = "Content"
    val colDate: String = "Date"
    val colId: String = "Id"

    val drop = "DROP TABLE IF EXISTS $dbTable"

    var sqlCreateTable: String = "CREATE TABLE IF NOT EXISTS $dbTable($colId INTEGER PRIMARY KEY, $colTitle TEXT, $colContent TEXT, $colDate TEXT)"
    var sqldb : SQLiteDatabase? = null
    constructor(context: Context){
        var db = MyDatabaseHelper(context)
        sqldb = db.writableDatabase
    }

    inner class MyDatabaseHelper: SQLiteOpenHelper{
        var context: Context? = null
        constructor(context: Context):super(context, dbName, null, dbVersion){
            this.context = context

        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL(drop)
        }


    }

    fun Insert(data: ContentValues): Long{
        val id = sqldb!!.insert(dbTable, null, data)
        return id
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String):Cursor{
        var qb = SQLiteQueryBuilder()
        qb.tables = dbTable

        var cursor = qb.query(sqldb, projection, selection, selectionArgs, null, null, sortOrder)

        return cursor

    }

    fun Update(data: ContentValues, selection: String, selectionArgs: Array<String>): Int{
        var count = sqldb!!.update(dbTable, data, selection, selectionArgs)
        return count
    }

    fun Delete(selection: String, selectionArgs: Array<String>): Int{
        var id = sqldb!!.delete(dbTable, selection, selectionArgs)

        return id
    }


}