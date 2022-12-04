package com.example.kulupapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val userList: ArrayList<user>,val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvname: TextView = view.findViewById(R.id.tv_name1)
        val tvstdnumber: TextView = view.findViewById(R.id.tv_stdnmr1)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvname.text = userList[position].name
        viewHolder.tvstdnumber.text = userList[position].stdnumber

        viewHolder.itemView.setOnClickListener{

            var user = userList[position]
            var name:String? = user.name
            var stdnumber:String? = user.stdnumber

            var intent = Intent(context,ProfileInfoActivity::class.java)
            /*intent.putExtra("putadisoyadi",name)
            intent.putExtra("putyasi",stdnumber)*/

            context.startActivity(intent)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList.size

}
