package lat.mahdan.jakartaweather.network

import com.google.gson.GsonBuilder
import lat.mahdan.jakartaweather.model.WeatherNowResponse
import lat.mahdan.jakartaweather.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface Service {

    @GET("weather?q=Jakarta,id&APPID=64e0fb575f7a24afa745dd7c57641447")
    fun getWeatherNow(): Observable<WeatherNowResponse>

    @GET("forecast?q=Jakarta,id&mode=json&APPID=64e0fb575f7a24afa745dd7c57641447")
    fun getWeatherList(): Observable<WeatherResponse>

    companion object {

        fun create(): Service{
            //initialized gson
            val gson = GsonBuilder().create()

            //initialized retrofit
            val retrofit: Retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .build()

            return retrofit.create(Service::class.java)

        }
    }
}