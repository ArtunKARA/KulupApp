package com.example.kulupapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ClubAdapter(private val clubSet: ArrayList<club>,val context: Context) :
    RecyclerView.Adapter<ClubAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val textView: TextView
        val clupname : TextView = view.findViewById(R.id.tv_clubnamecard)
        val clupshortinfo : TextView = view.findViewById(R.id.tv_shortclub1)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.club_card, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.clupname.text = clubSet[position].name
        viewHolder.clupshortinfo.text = clubSet[position].shortInfo

        viewHolder.itemView.setOnClickListener{

            var user = clubSet[position]
            var name:String? = user.name
            var stdnumber:String? = user.shortInfo

            var intent = Intent(context,ClubDetailActivity::class.java)

            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = clubSet.size

}
