package com.example.hope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppointAdapter(private val appointList: ArrayList<Appoint>) : RecyclerView.Adapter<AppointAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointment_card,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppointAdapter.MyViewHolder, position: Int) {
        val appoint:Appoint =appointList[position]
        holder.Blood_Bank_Location.text= appoint.Blood_Bank_Location;
        holder.email.text=appoint.Email;
        holder.Date.text=appoint.Date;
        holder.time.text=appoint.Time;
    }

    override fun getItemCount(): Int {
        return appointList.size
    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val Blood_Bank_Location: TextView =itemView.findViewById(R.id.location);
        val email: TextView =itemView.findViewById(R.id.appointEmail)
        val Date: TextView =itemView.findViewById(R.id.appointdate);
        val time: TextView =itemView.findViewById(R.id.appointtime)

    }
}