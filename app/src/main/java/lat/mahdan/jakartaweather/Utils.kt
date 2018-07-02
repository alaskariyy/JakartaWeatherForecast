package lat.mahdan.jakartaweather

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("bind:icon")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}