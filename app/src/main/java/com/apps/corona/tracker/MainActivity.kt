package com.apps.corona.tracker

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shon.projects.payappmodel.utils.glide.networking.ApiClient
import kotlinx.android.synthetic.main.sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var responseStats: Response<CoronaByCountry>? = null
    private lateinit var editsearch: SearchView
    private val dataList = ArrayList<DataModel>()
    private lateinit var adapter: DataAdapter
    private var aHeight: Int = 0
    private var bHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        history_list.layoutManager = LinearLayoutManager(this)
        adapter = DataAdapter(dataList, this)
        history_list.adapter = adapter
        getData()
        getCountryData()
        findViewById<CardView>(R.id.total_cases).setOnClickListener { filter() }
        editsearch = findViewById(R.id.search)
        editsearch.setOnQueryTextListener(this)

        val observer: ViewTreeObserver = findViewById<ConstraintLayout>(R.id.total).viewTreeObserver
        observer.addOnGlobalLayoutListener {
            val height = Resources.getSystem().displayMetrics.heightPixels.minus(
                findViewById<ConstraintLayout>(R.id.total).height
            )
            BottomSheetBehavior.from(findViewById<ConstraintLayout>(R.id.bottom_sheet)).peekHeight =
                height

            findViewById<ConstraintLayout>(R.id.bottom_sheet).minHeight = height
        }
    }

    fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["x-rapidapi-host"] = "coronavirus-monitor.p.rapidapi.com"
        headerMap["x-rapidapi-key"] = "f93beb3a82msha42fbaf6bfe7e1cp1cbda6jsn9374b3300261"
        return headerMap
    }


    /* private fun getCountryData() {
         val call: Call<CoronaByCountry> = ApiClient.getClient.getCountryStats(getHeaderMap())
         call.enqueue(object : Callback<CoronaByCountry> {

             override fun onResponse(
                 call: Call<CoronaByCountry>?,
                 response: Response<CoronaByCountry>?
             ) {
                // println("Data "+response?.body())
                 val array: ArrayList<DataModel>? = response?.body()?.countries_stat
                // val data = response?.body()?.countries_stat.let { dataList?.addAll(it) }
                 val datas: ArrayList<DataModel>? = response?.body()?.countries_stat
                 //val data = response?.body()?.countries_stat.let { dataList?.addAll(it) }
                 val data = datas?.let { dataList.addAll(it)  }
                 println("Data "+data)
                 println("Datas "+datas)

             }

             override fun onFailure(call: Call<CoronaByCountry>?, t: Throwable?) {
                 println(t?.localizedMessage+ t?.cause+ t?.message)
             }

         })
     }*/


    fun getCountryData() {
        val call: Call<CoronaByCountry> = ApiClient.getClient.getCountryStats(getHeaderMap())
        call.enqueue(object : Callback<CoronaByCountry> {

            override fun onResponse(
                call: Call<CoronaByCountry>?,
                response: Response<CoronaByCountry>?
            ) {
                responseStats = response
                // println("Data "+response?.body())
                val array: ArrayList<DataModel>? = response?.body()?.countries_stat
                // val data = response?.body()?.countries_stat.let { dataList?.addAll(it) }
                val datas: ArrayList<DataModel>? = response?.body()?.countries_stat
                //val data = response?.body()?.countries_stat.let { dataList?.addAll(it) }
                val data = datas?.let { dataList.addAll(it) }
                println("Data " + data)
                println("Datas " + datas)
                history_list.adapter?.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<CoronaByCountry>?, t: Throwable?) {
                println(t?.localizedMessage + t?.cause + t?.message)
            }

        })
    }

    private fun getData() {
        val call: Call<CoronaCountModel> = ApiClient.getClient.getData(getHeaderMap())
        call.enqueue(object : Callback<CoronaCountModel> {

            override fun onResponse(
                call: Call<CoronaCountModel>?,
                response: Response<CoronaCountModel>?
            ) {
                val loader: SpinKitView = findViewById(R.id.loader)
                loader.visibility = View.GONE
                val data = response?.body()!!
                println("Data" + data)
                findViewById<TextView>(R.id.total_cases_txt).text = data.total_cases
                findViewById<TextView>(R.id.new_cases_txt).text = data.new_cases
                findViewById<TextView>(R.id.total_deaths_txt).text = data.total_deaths
                findViewById<TextView>(R.id.new_deaths_txt).text = data.new_deaths
                findViewById<TextView>(R.id.total_recovered_txt).text = data.total_recovered
                val format: NumberFormat = NumberFormat.getInstance(Locale.US)
                val totRecovered: Int? = format.parse(data.total_recovered)?.toInt()
                val totDeaths: Int? = format.parse(data.total_deaths)?.toInt()
                val totalCases: Int? = format.parse(data.total_cases)?.toInt()
                val count: Int? =
                    totRecovered?.let { it -> totDeaths?.plus(it)?.let { totalCases?.minus(it) } }
                findViewById<TextView>(R.id.active_cases_txt).text = count.toString()
            }

            override fun onFailure(call: Call<CoronaCountModel>?, t: Throwable?) {
            }

        })
    }

    private fun filter() {
        var monthList: ArrayList<DataModel> = dataList.filter {
            println("COUNTRY" + it.country_name)
            it.country_name == "China"

        } as ArrayList<DataModel>
        adapter.filter(monthList)
        println("datas" + monthList.toString())

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        println("country: " + query)
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        println("country: " + newText)
        return false;
    }

}
