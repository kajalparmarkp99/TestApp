package com.testapplication.api

import com.testapplication.model.CustomerResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIzNDAwOGQzNS01OTlhLTQ4YzAtYWQ2My03NmY4N2UyZGIwYzMiLCJlbnRpdHlUeXBlIjoidXNlciIsInYiOiIwLjEiLCJpYXQiOjE3MDE3OTk1NzgsImV4cCI6MTczMzM1NzE3OH0.aolRFer6LQ9JboZT7pDqb3Eq2SGOcUwGRRTzG2mfPJ4")
    @GET("customer/filter")
     fun getCustomers(
        @Query("cgId") cgId: Int = 12070687,
        @Query("name") name: String = "Dev",
        @Query("mobile") mobile: String = "7093690745",
        @Query("paginated") paginated: Boolean = true,
        @Query("pageNo") pageNo: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): Call<CustomerResponse>
}