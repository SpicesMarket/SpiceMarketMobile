package tk.mohithaiyappa.coffeeprices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.mohithaiyappa.coffeeprices.adapter.RecyclerViewAdapter
import tk.mohithaiyappa.coffeeprices.repository.CoffeePricesApi
import tk.mohithaiyappa.coffeeprices.repository.CoffeePricesData
import tk.mohithaiyappa.coffeeprices.repository.CoffeePricesDataList


class MainActivity : AppCompatActivity() {
    private var pricesList : List<CoffeePricesDataList.Data> = emptyList()
    private lateinit var newData : CoffeePricesDataList.Data
    private lateinit var oldData : CoffeePricesDataList.Data
    lateinit var adapter: RecyclerViewAdapter
    lateinit var layoutManager: LinearLayoutManager
    //private var runAgain:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //while (runAgain){
            getData()
        //}




    }
    fun setupRecyclerView(){
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager =layoutManager
        recycler_view.setHasFixedSize(true)
        adapter = RecyclerViewAdapter(newData,oldData,this)
        recycler_view.adapter= adapter
    }
    fun getData(){
        //Toast.makeText(this,"in get data method",Toast.LENGTH_SHORT).show()
        val service = CoffeePricesApi.startApi()
        GlobalScope.launch(Dispatchers.IO) {
            val response = service.getAllPrices()

            if (response.isSuccessful) {
                newData= response.body()!!.mData[0]
                oldData= response.body()!!.mData[1]
                //pricesList=response.body()!!.mData.prices
                withContext(Dispatchers.Main){
                    //runAgain=!response.isSuccessful
                    setupRecyclerView()
                }
            }
        }

    }
}