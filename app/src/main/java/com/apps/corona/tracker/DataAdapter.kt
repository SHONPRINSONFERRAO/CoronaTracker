package com.apps.corona.tracker

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    var dataList: ArrayList<DataModel>,
    val context: Context
) :
    RecyclerView.Adapter<ViewHolder>(), Filterable {

    private lateinit var list: ArrayList<DataModel>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_lay, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    fun filter(arraylist: ArrayList<DataModel>) {
        this.list = arraylist
        notifyDataSetChanged()
    }

    init {
        list = dataList
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exp = Regex("[^A-Za-z0-9]")
        val countryName: String = list[position].country_name.replace(exp, "")
        val res: Int = context.resources.getIdentifier(
            countryName.toLowerCase(),
            "drawable",
            context.packageName
        )
        holder.country.text = list[position].country_name
        holder.image.setImageResource(res)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.active_cases.text = Html.fromHtml(
                "<b>Active:</b> " + list[position].active_cases,
                Html.FROM_HTML_MODE_COMPACT
            )
            holder.total_cases.text =
                Html.fromHtml("<b>Cases:</b> " + list[position].cases, Html.FROM_HTML_MODE_COMPACT)
            holder.total_deaths.text = Html.fromHtml(
                "<b>Deaths:</b> " + list[position].deaths,
                Html.FROM_HTML_MODE_COMPACT
            )
            holder.total_recovered.text = Html.fromHtml(
                "<b>Recovered:</b> " + list[position].total_recovered,
                Html.FROM_HTML_MODE_COMPACT
            )
            holder.new_cases.text = Html.fromHtml(
                "<b>New Cases:</b> " + list[position].new_cases,
                Html.FROM_HTML_MODE_COMPACT
            )
            holder.new_deaths.text = Html.fromHtml(
                "<b>New Deaths:</b> " + list[position].new_deaths,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            holder.active_cases.text =
                Html.fromHtml("<b>Active:</b> " + list[position].active_cases)
            holder.total_cases.text = Html.fromHtml("<b>Cases:</b> " + list[position].cases)
            holder.total_deaths.text = Html.fromHtml("<b>Deaths:</b> " + list[position].deaths)
            holder.total_recovered.text =
                Html.fromHtml("<b>Recovered:</b> " + list[position].total_recovered)
            holder.new_cases.text = Html.fromHtml("<b>New Cases:</b> " + list[position].new_cases)
            holder.new_deaths.text =
                Html.fromHtml("<b>New Deaths:</b> " + list[position].new_deaths)
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString: String = constraint.toString()
                if (charString.isEmpty()) {
                    list = dataList
                } else {
                    val filteredList: MutableList<DataModel> = ArrayList()
                    for (row in dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.country_name.toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    list = filteredList as ArrayList<DataModel>
                }

                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<DataModel>
                notifyDataSetChanged()
            }

        }
    }


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = itemView.findViewById(R.id.img)
    val country: TextView = itemView.findViewById(R.id.country)
    val total_deaths: TextView = itemView.findViewById(R.id.total_deaths)
    val total_cases: TextView = itemView.findViewById(R.id.total_cases)
    val total_recovered: TextView = itemView.findViewById(R.id.total_recovered)
    val active_cases: TextView = itemView.findViewById(R.id.active_cases)
    val new_cases: TextView = itemView.findViewById(R.id.new_cases)
    val new_deaths: TextView = itemView.findViewById(R.id.new_deaths)

}
