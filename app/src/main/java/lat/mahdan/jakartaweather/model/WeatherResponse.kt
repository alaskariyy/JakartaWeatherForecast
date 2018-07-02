package lat.mahdan.jakartaweather.model
import android.databinding.BaseObservable
import com.google.gson.annotations.SerializedName
import java.util.*


data class WeatherResponse(
    var cod: String = "",
    var message: Double = 0.0,
    var cnt: Int = 0,
    var list: List<X> = listOf(),
    var city: City = City()
) : BaseObservable()

data class WeatherNowResponse(
        var coord: Coord = Coord(),
        var sys: Sys = Sys(),
        var weather: List<Weather> = listOf(),
        var main: Main = Main(),
        var wind: Wind = Wind(),
        var clouds: Clouds = Clouds(),
        var dt: Int = 0,
        var id: Int = 0,
        var name: String = "",
        var cod: Int = 0
) : Observable()

class X: Observable(){
    var dt: Int = 0
    set(value) {
        field = value
        setChangedAndNotify("dt")
    }

    var main: Main = Main()
        set(value) {
            field = value
            setChangedAndNotify("main")
        }

    var weather: List<Weather> = listOf()
        set(value) {
            field = value
            setChangedAndNotify("weather")
        }

    var clouds: Clouds = Clouds()
        set(value) {
            field = value
            setChangedAndNotify("clouds")
        }

    var wind: Wind = Wind()
        set(value) {
            field = value
            setChangedAndNotify("wind")
        }

    var sys: Sys = Sys()
        set(value) {
            field = value
            setChangedAndNotify("sys")
        }

    var dt_txt: String = ""
        set(value) {
            field = value
            setChangedAndNotify("dt_txt")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}

data class Clouds(
    var all: Int = 0
) : BaseObservable()

data class Wind(
    var speed: Double = 0.0,
    var deg: Double = 0.0
) : BaseObservable()

data class Sys(
    var pod: String = ""
)

data class Weather(
    var id: Int = 0,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
) : BaseObservable()

data class Main(
    var temp: Double = 0.0,
    var temp_min: Double = 0.0,
    var temp_max: Double = 0.0,
    var pressure: Double = 0.0,
    var sea_level: Double = 0.0,
    var grnd_level: Double = 0.0
) : BaseObservable()

data class City(
    var id: Int = 0,
    var name: String = "",
    var coord: Coord = Coord(),
    var country: String = "",
    var population: Int = 0
) : BaseObservable()

data class Coord(
    var lat: Double = 0.0,
    var lon: Double = 0.0
) : BaseObservable()