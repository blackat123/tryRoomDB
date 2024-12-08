package com.example.tryroomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tryroomdb.database.daftarBelanja
import com.example.tryroomdb.database.historyBelanja

class adapterRecView(private val historyBelanja: MutableList<historyBelanja>) :
    RecyclerView.Adapter<adapterRecView.ListViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun delData(dtBelanja: historyBelanja)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: adapterRecView.OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _tvItem = itemView.findViewById<TextView>(R.id.tvItem)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvJumlah)
        var _btnDelete = itemView.findViewById<TextView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterRecView.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.history_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterRecView.ListViewHolder, position: Int) {
        var item = historyBelanja[position]

        holder._tvTanggal.setText(item.tanggal)
        holder._tvItem.setText(item.item)
        holder._tvJumlah.setText(item.jumlah)

        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(item)
        }
    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }

    fun isiData(daftar: List<historyBelanja>) {
        historyBelanja.clear()
        historyBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}