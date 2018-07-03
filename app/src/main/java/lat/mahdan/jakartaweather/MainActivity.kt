package lat.mahdan.jakartaweather

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import lat.mahdan.jakartaweather.model.Weather
import lat.mahdan.jakartaweather.model.WeatherNowResponse
import lat.mahdan.jakartaweather.model.WeatherResponse
import lat.mahdan.jakartaweather.model.X
import lat.mahdan.jakartaweather.network.Service
import lat.mahdan.jakartaweather.viewModel.WeatherNowViewModel
import lat.mahdan.jakartaweather.viewModel.WeatherViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val service by lazy {
        Service.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherDefault = WeatherNowResponse()
        weatherDefault.main.temp = 0.0
        weatherDefault.main.temp_min = 0.0
        weatherDefault.weather = listOf(
                Weather(
                        0,
                        "",
                        "-",
                        "http://openweathermap.org/img/w/10d.png"
                )
        )

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)
        binding.setVariable(BR.weather, WeatherNowViewModel(weatherDefault))

        service.getWeatherNow()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            weather -> binding.setVariable(BR.weather, WeatherNowViewModel(weather))
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )

        service.getWeatherList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            weather -> handleWeatherList(weather)
                        },
                        {
                            error -> Log.e("Error", error.message)
                        }
                )

    }

    private fun handleWeatherList(weatherList: WeatherResponse?){
        val list: List<X> = weatherList!!.list

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewWeatherList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WeatherAdapter(list)
    }

    class WeatherAdapter(val data: List<X>): RecyclerView.Adapter<WeatherViewHolder>() {
        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ViewDataBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.weather_row, parent, false)

            return WeatherViewHolder(binding)
        }

        override fun getItemCount(): Int = data.size
    }

    class WeatherViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: X) {
            binding.setVariable(BR.weather, WeatherViewModel(data))
            binding.executePendingBindings()
        }
    }
}
