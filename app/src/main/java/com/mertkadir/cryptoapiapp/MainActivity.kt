package com.mertkadir.cryptoapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertkadir.cryptoapiapp.adapter.RecyclerViewAdapter
import com.mertkadir.cryptoapiapp.databinding.ActivityMainBinding
import com.mertkadir.cryptoapiapp.model.CryptoModel
import com.mertkadir.cryptoapiapp.service.CryptoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //https://raw.githubusercontent.com/
        //atilsamancioglu/K21-JSONDataSet/master/crypto.json


        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }
    private fun loadData(){

        val retrofit = Retrofit.Builder().
        baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoApi::class.java)
        val call = service.getData()


        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {

                if (response.isSuccessful){

                    response.body()?.let {

                        cryptoModels = ArrayList(it)

                        recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!,this@MainActivity)
                        binding.recyclerView.adapter = recyclerViewAdapter


                    }

                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, cryptoModel.currency, Toast.LENGTH_SHORT).show()
    }

}