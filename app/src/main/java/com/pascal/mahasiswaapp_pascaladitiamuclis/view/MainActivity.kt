package com.pascal.mahasiswaapp_pascaladitiamuclis.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.mahasiswaapp_pascaladitiamuclis.R
import com.pascal.mahasiswaapp_pascaladitiamuclis.adapter.AdapterMain
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.action.ResponseAction
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.getData.DataItem
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.getData.ResponseGetData
import com.pascal.mahasiswaapp_pascaladitiamuclis.viewModel.ViewModelMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
        viewModel.getDataView()

        btn_floating.setOnClickListener {
            val intent = Intent(this@MainActivity, InputActivity::class.java)
            startActivity(intent)
        }

        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responseData.observe(this, Observer { showData(it) })
        viewModel.responseAction.observe(this, Observer { deleteData(it) })
        viewModel.isLoading.observe(this, Observer { showLoading(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
    }

    private fun showError(it: Throwable?) {
        Toast.makeText(applicationContext, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_main.visibility = View.VISIBLE
        } else {
            progress_main.visibility = View.GONE
        }
    }

    private fun deleteData(it: ResponseAction?) {
        Toast.makeText(applicationContext, it?.message, Toast.LENGTH_SHORT).show()
    }

    private fun showData(it: ResponseGetData?) {
        val adapter = AdapterMain(it?.data, object : AdapterMain.OnClickListener {
            override fun detail(item: DataItem?) {
                val intent = Intent(applicationContext, InputActivity::class.java)
                intent.putExtra("data", item)
                startActivity(intent)
            }

            override fun delete(item: DataItem?) {
                AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Delete Data")
                    setMessage("apakah anda yakin ingin menghapus?")
                    setPositiveButton("Delete") { dialogInterface, i ->

                        viewModel.deleteDataView(item?.idMahasiswa ?: "")
                        dialogInterface.dismiss()
                    }
                    setNegativeButton("Cancel") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                }.show()
            }

        })

        recyclerview_main.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataView()
    }
}