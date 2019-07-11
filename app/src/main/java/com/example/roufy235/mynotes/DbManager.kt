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

class DbManager(context : Context) {
    val dbName: String = "MyNotes"
    private val dbTable: String = "Notes"
    val dbVersion:Int = 1

    private val colTitle: String = "Title"
    private val colContent: String = "Content"
    private val colDate: String = "Date"
    private val colId: String = "Id"

    val drop = "DROP TABLE IF EXISTS $dbTable"

    var sqlCreateTable: String = "CREATE TABLE IF NOT EXISTS $dbTable($colId INTEGER PRIMARY KEY, $colTitle TEXT, $colContent TEXT, $colDate TEXT)"
    var sqldb : SQLiteDatabase? = null

    init {
        val db = MyDatabaseHelper(context)
        sqldb = db.writableDatabase
    }

    inner class MyDatabaseHelper(context : Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
        var context: Context? = context
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL(drop)
        }


    }

    fun insert(data: ContentValues): Long{
        return sqldb!!.insert(dbTable, null, data)
    }

    fun query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String):Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable

        return qb.query(sqldb, projection, selection, selectionArgs, null, null, sortOrder)

    }

    fun update(data: ContentValues, selection: String, selectionArgs: Array<String>): Int{
        return sqldb!!.update(dbTable, data, selection, selectionArgs)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int{
        return sqldb!!.delete(dbTable, selection, selectionArgs)
    }


}