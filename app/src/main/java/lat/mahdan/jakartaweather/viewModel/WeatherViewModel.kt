package lat.mahdan.jakartaweather.viewModel

import android.databinding.BaseObservable
import android.databinding.Bindable
import lat.mahdan.jakartaweather.BR
import lat.mahdan.jakartaweather.model.X
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.*
import kotlin.text.Typography.degree

class WeatherViewModel(private val weather: X) : Observer, BaseObservable() {

    init {
        weather.addObserver(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        notifyPropertyChanged(BR._all)
    }

    val dt_txt: String
        @Bindable get() {
            return weather.dt_txt
        }

    val temp: String
        @Bindable get() {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return (df.format(weather.main.temp-273.15)).toString()+degree
        }

    val temp_min: String
        @Bindable get() {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return "Min. "+(df.format(weather.main.temp_min-273.15)).toString()+degree
        }

    val description: String
        @Bindable get() {
            return weather.weather[0].description
        }

    val icon: String?
        @Bindable get() {
            return "http://openweathermap.org/img/w/"+weather.weather[0].icon+".png"
        }
}