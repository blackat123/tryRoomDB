package com.example.tryroomdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tryroomdb.database.daftarBelanja
import com.example.tryroomdb.database.daftarBelanjaDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    // global variable
    private lateinit var DB: daftarBelanjaDB
    private lateinit var adapterDaftar: adapterRevView
    private var arDaftar: MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi view
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        adapterDaftar = adapterRevView(arDaftar)
        var _rvDaftar = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvDaftar)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        // inisialisasi db
        DB = daftarBelanjaDB.getDatabase(this)

        // event click listener
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        adapterDaftar.setOnItemClickCallBack(
            object : adapterRevView.OnItemClickCallBack {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftarBelanja)
                        }
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }
}