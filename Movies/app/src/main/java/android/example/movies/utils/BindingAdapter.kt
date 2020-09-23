package android.example.movies.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("data")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Any>?) {

    (recyclerView.adapter as? BindableAdapter<Any>)?.setData(data.orEmpty())

}


