package com.example.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val taskArray = ArrayList<String>()
    var listAdapter: ArrayAdapter<String>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileReader()
        addItems()
        val taskList = findViewById<ListView>(R.id.list_ToDo)
        taskList.setOnItemLongClickListener { Parent, view, position, id ->
            taskArray.remove(taskList.getItemAtPosition(position))


        }
        listAdapter!!.notifyDataSetChanged()

    }

    fun refreshAction(view: View){
        val btnRefresh =findViewById<Button>(R.id.btnRefresh)
        val taskList = findViewById<ListView>(R.id.list_ToDo)
        taskList.setOnItemLongClickListener { Parent, view, position, id ->
            taskArray.remove(taskList.getItemAtPosition(position))

        }
        listAdapter!!.notifyDataSetChanged()
        Toast.makeText(this, "Got it!", Toast.LENGTH_SHORT).show()



    }


    fun addTask(view: View) {
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val itemTask = findViewById<EditText>(R.id.textTask).text.toString()
            if(itemTask.isNullOrEmpty()){
                Toast.makeText(this, "valor ingresado no valido", Toast.LENGTH_SHORT).show()
                return
            }

        //Llenar arreglo
        taskArray.add(itemTask)
        val taskList = findViewById<ListView>(R.id.list_ToDo)

        //Llenar la listView
        addItems()



    }

    fun addItems(){
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskArray)
        list_ToDo?.adapter = listAdapter
    }





    fun fileReader() : ArrayList<String>{
        val inputStream = resources.openRawResource(R.raw.taskfile)
        val scan= Scanner(inputStream)
        while (scan.hasNextLine()){
            val line = scan.nextLine()
            taskArray.add(line)
        }
        return taskArray
    }




}