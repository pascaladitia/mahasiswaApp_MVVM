package com.pascal.mahasiswaapp_pascaladitiamuclis.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.action.ResponseAction
import com.pascal.mahasiswaapp_pascaladitiamuclis.repo.Repository

class ViewModelInput : ViewModel() {

    val repository = Repository()

    var responseInput = MutableLiveData<ResponseAction>()
    var responseUpdate = MutableLiveData<ResponseAction>()
    var isError = MutableLiveData<Throwable>()

    fun inputDataView(nama: String, nohp: String, alamat: String) {

        repository.inputData(nama, nohp, alamat, {
            responseInput.value = it
        }, {
            isError.value = it
        })
    }

    fun updateDataView(id: String, nama: String, nohp: String, alamat: String) {
        repository.updateData(id, nama, nohp, alamat, {
                responseUpdate.value = it
        }, {
            isError.value = it
        })
    }
}