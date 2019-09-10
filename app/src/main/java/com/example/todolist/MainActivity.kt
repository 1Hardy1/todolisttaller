package com.example.todolist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var listAdapter: ArrayAdapter<String>? = null
    private var ToDoList: MutableList<String>? = null
    var FILE_NAME: String = "taskfile.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToDoList= ArrayList()
        fileReader()
        addItems()
        val taskList = findViewById<ListView>(R.id.list_ToDo)
        taskList.setOnItemLongClickListener { Parent, view, position, id ->
            ToDoList!!.remove(taskList.getItemAtPosition(position))


        }
        listAdapter!!.notifyDataSetChanged()

    }

    fun refreshAction(view: View){
        val btnRefresh =findViewById<Button>(R.id.btnRefresh)
        val taskList = findViewById<ListView>(R.id.list_ToDo)
        taskList.setOnItemLongClickListener { Parent, view, position, id ->
            ToDoList!!.remove(taskList.getItemAtPosition(position))

        }
        listAdapter!!.notifyDataSetChanged()
        true
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
        ToDoList!!.add(itemTask)
        val taskList = findViewById<ListView>(R.id.list_ToDo)

        //Llenar la listView
        addItems()


    }

    fun addItems(){
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ToDoList!!)
        list_ToDo?.adapter = listAdapter
        listAdapter!!.notifyDataSetChanged()
    }


    fun fileReader() : MutableList<String>? {
        val inputStream = resources.openRawResource(R.raw.taskfile)
        val scan= Scanner(inputStream)
        while (scan.hasNextLine()){
            val line = scan.nextLine()
            ToDoList!!.add(line)
        }
        return ToDoList
    }

    override fun onResume() {
        super.onResume()
        if (ToDoList != null) {
            try {
                val scanner = Scanner(openFileInput(FILE_NAME))
                while (scanner.hasNext()) {
                    val line = scanner.nextLine()
                    ToDoList!!.add(line)
                }
                scanner.close()
                listAdapter!!.notifyDataSetChanged()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }

    }
    override fun onPause() {
        super.onPause()

        try {
            val printStream = PrintStream(openFileOutput(FILE_NAME, MODE_PRIVATE))
            for (i in ToDoList!!.indices) {
                printStream.println(ToDoList!![i])
            }
            printStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }



}