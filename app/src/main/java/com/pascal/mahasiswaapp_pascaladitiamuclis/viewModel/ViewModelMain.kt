package com.pascal.mahasiswaapp_pascaladitiamuclis.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.action.ResponseAction
import com.pascal.mahasiswaapp_pascaladitiamuclis.model.getData.ResponseGetData
import com.pascal.mahasiswaapp_pascaladitiamuclis.repo.Repository

class ViewModelMain : ViewModel() {

    val repository = Repository()

    var responseData = MutableLiveData<ResponseGetData>()
    var responseAction = MutableLiveData<ResponseAction>()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()

    fun getDataView() {
        isLoading.value = true

        repository.getData({
            isLoading.value = false
            responseData.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }

    fun deleteDataView(id : String) {
        isLoading.value = true

        repository.hapusData(id, {
            isLoading.value = false
            responseAction.value = it
            getDataView()
        } , {
            isLoading.value = false
            isError.value = it
        })
    }

}