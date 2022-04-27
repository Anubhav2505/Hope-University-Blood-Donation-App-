package com.example.hope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventAdapter(private val eventList: ArrayList<Events>) : RecyclerView.Adapter<EventAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.events,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventAdapter.MyViewHolder, position: Int) {
        val event: Events =eventList[position]
        holder.h1.text= event.h1;
        holder.h2.text= event.location;
        holder.date.text= event.Date;
        holder.time.text= event.Time;
        Glide.with(holder.imgview.context).load(event.iurl).into(holder.imgview)

    }

    override fun getItemCount(): Int {
       return eventList.size
    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
          val imgview :ImageView =itemView.findViewById(R.id.eventimage);
          val h1:TextView =itemView.findViewById(R.id.h1);
          val h2:TextView =itemView.findViewById(R.id.h2)
          val date:TextView =itemView.findViewById(R.id.date);
          val time:TextView =itemView.findViewById(R.id.time)

    }

}