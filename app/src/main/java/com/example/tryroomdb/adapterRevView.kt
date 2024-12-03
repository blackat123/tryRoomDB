package com.example.tryroomdb

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tryroomdb.database.daftarBelanja

class adapterRevView(private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterRevView.ListViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _tvItem = itemView.findViewById<TextView>(R.id.tvItem)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvJumlah)
        var _btnDelete = itemView.findViewById<TextView>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<TextView>(R.id.btnEdit)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterRevView.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterRevView.ListViewHolder, position: Int) {
        var item = daftarBelanja[position]

        holder._tvTanggal.setText(item.tanggal)
        holder._tvItem.setText(item.item)
        holder._tvJumlah.setText(item.jumlah)

        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(item)
        }
        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}