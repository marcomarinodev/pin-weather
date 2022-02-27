package com.mamarino.pinweather.view.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.mamarino.pinweather.R
import com.mamarino.pinweather.model.WeatherObject
import com.mamarino.pinweather.view.utils.RemoveDialog
import com.mamarino.pinweather.viewmodels.MainViewModel

class WeatherListAdapter(
    private val context: Context,
    private val dataSource: ArrayList<WeatherObject>,
    private val viewModel: MainViewModel
    ): BaseAdapter() {

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var convertView = inflater.inflate(R.layout.weather_custom_list_item, p2, false)

        ViewHolder(convertView).bind(dataSource[p0])

        return convertView
    }

    inner class ViewHolder(itemView: View) {
        var cityTextView: TextView = itemView.findViewById(R.id.city_name)
        var tempTextView: TextView = itemView.findViewById(R.id.temp)
        var itemView = itemView

        fun bind(weatherObject: WeatherObject) {
            cityTextView.text = weatherObject.cityName
            tempTextView.text = weatherObject.temp.toString()

            // row behaviours handler
            itemView.setOnLongClickListener {
                RemoveDialog(context,"Weather Entry",
                    "Do you want to remove ${weatherObject.cityName}?",
                    android.R.drawable.ic_dialog_alert,
                ) {
                    viewModel.removeLocationWithId(weatherObject.id)
                    Toast.makeText(context, "${weatherObject.cityName} removed", Toast.LENGTH_LONG)
                        .show()
                }.show()
                true
            }
        }
    }

}