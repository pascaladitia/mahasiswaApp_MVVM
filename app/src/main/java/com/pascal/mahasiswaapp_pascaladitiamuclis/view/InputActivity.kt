package com.pascal.mahasiswaapp_pascaladitiamuclis.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.mahasiswaapp_pascaladitiamuclis.R
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.action.ResponseAction
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.getData.DataItem
import com.pascal.mahasiswaapp_pascaladitiamuclis.viewModel.ViewModelInput
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {

    private lateinit var viewModel : ViewModelInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        viewModel = ViewModelProviders.of(this).get(ViewModelInput::class.java)

        val getData = intent.getParcelableExtra<DataItem>("data")
        if (getData != null) {
            edt_nama.setText(getData.mahasiswaNama)
            edt_nohp.setText(getData.mahasiswaNohp)
            edt_alamat.setText(getData.mahasiswaAlamat)

            btn_simpan.text = "Update"
        }

        when (btn_simpan.text) {
            "Update" -> {
                btn_simpan.setOnClickListener {
                    getData?.idMahasiswa?.let { it1 ->
                        viewModel.updateDataView(
                            it1,
                            edt_nama.text.toString(),
                            edt_nohp.text.toString(),
                            edt_alamat.text.toString()
                        )
                    }
                }
            }
            else -> {
                btn_simpan.setOnClickListener {
                    viewModel.inputDataView(
                        edt_nama.text.toString(),
                        edt_nohp.text.toString(),
                        edt_alamat.text.toString()
                    )
                }
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }

        attachObserve()
    }

    private fun attachObserve() {
        viewModel.responseInput.observe(this, Observer { inputData(it) })
        viewModel.responseUpdate.observe(this, Observer { updateData(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
    }

    private fun showError(it: Throwable?) {
        showToast(it?.message.toString())
    }

    private fun updateData(it: ResponseAction?) {
        showToast("Data berhasil diupdate")
        finish()
    }

    private fun inputData(it: ResponseAction?) {
        showToast("Data berhasil disimpan")
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}