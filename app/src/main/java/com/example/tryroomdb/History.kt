package com.example.tryroomdb

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryroomdb.database.daftarBelanja
import com.example.tryroomdb.database.daftarBelanjaDB
import com.example.tryroomdb.database.historyBelanja
import com.example.tryroomdb.database.historyBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {
    // global variable
    private lateinit var DB: historyBelanjaDB
    private lateinit var adapterHistory: adapterRecView
    private var arHistory: MutableList<historyBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi view
        adapterHistory = adapterRecView(arHistory)
        var _rvDaftar = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvHistory)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterHistory

        // inisialisasi db
        DB = historyBelanjaDB.getDatabase(this)

        // event listener
        adapterHistory.setOnItemClickCallBack(
            object : adapterRecView.OnItemClickCallBack {
                override fun delData(dtBelanja: historyBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funhistoryBelanjaDAO().delete(dtBelanja)
                        val historyBelanja = DB.funhistoryBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterHistory.isiData(historyBelanja)
                        }
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBelanja = DB.funhistoryBelanjaDAO().selectAll()
            adapterHistory.isiData(historyBelanja)
            Log.d("data ROOM", historyBelanja.toString())
        }
    }
}