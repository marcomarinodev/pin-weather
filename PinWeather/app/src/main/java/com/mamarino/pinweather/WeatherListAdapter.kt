package com.mamarino.pinweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class WeatherListAdapter(
    private val context: Context,
    private val dataSource: ArrayList<WeatherObject>
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
        var cityTextView = convertView.findViewById<TextView>(R.id.city_name)
        var tempTextView = convertView.findViewById<TextView>(R.id.temp)

        cityTextView.text = dataSource[p0].cityName
        tempTextView.text = dataSource[p0].temp.toString()

        return convertView
    }


}