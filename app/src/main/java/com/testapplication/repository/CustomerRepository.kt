package com.testapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.testapplication.api.RetrofitClient
import com.testapplication.model.CustomerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CustomerRepository {

    val responseData = MutableLiveData<CustomerResponse>()
    fun getCustomers(name: String?, number: String?, pageNo: Int?,pageSize: Int?): MutableLiveData<CustomerResponse> {

        val call = RetrofitClient.apiInterface.getCustomers(name, number, true, pageNo, pageSize)

        call.enqueue(object : Callback<CustomerResponse> {
            override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<CustomerResponse>,
                response: Response<CustomerResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()
                Log.d("Response : ", data?.data.toString())
                responseData.value = data!!
            }
        })

        return responseData
    }
}
