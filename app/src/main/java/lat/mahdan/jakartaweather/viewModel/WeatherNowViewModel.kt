package lat.mahdan.jakartaweather.viewModel

import android.databinding.BaseObservable
import android.databinding.Bindable
import lat.mahdan.jakartaweather.BR
import lat.mahdan.jakartaweather.model.WeatherNowResponse
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherNowViewModel(private val weatherNowResponse: WeatherNowResponse) : Observer, BaseObservable() {

    init {
        weatherNowResponse.addObserver(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        notifyPropertyChanged(BR._all)
    }

    val dt_txt: String
        @Bindable get() {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            return currentDate
        }

    val temp: String
        @Bindable get() {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return (df.format(weatherNowResponse.main.temp-273.15)).toString()+ Typography.degree
        }

    val temp_min: String
        @Bindable get() {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return "Min. "+(df.format(weatherNowResponse.main.temp_min-273.15)).toString()+ Typography.degree
        }

    val description: String
        @Bindable get() {
            return weatherNowResponse.weather[0].description
        }

    val icon: String
        @Bindable get() {
            return "http://openweathermap.org/img/w/"+weatherNowResponse.weather[0].icon+".png"
        }
}