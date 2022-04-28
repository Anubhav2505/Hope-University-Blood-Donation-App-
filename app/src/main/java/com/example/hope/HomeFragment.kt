package com.example.hope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var appointArrayList: ArrayList<RaiseRequest>
    private lateinit var AppointAdapter: RaiseAdapter
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.raise_recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(false)
        appointArrayList = arrayListOf()

        AppointAdapter = RaiseAdapter(appointArrayList)

        recyclerView.adapter = AppointAdapter


        EventChangeListner()



        return view
    }

    private fun EventChangeListner() {

        db = FirebaseDatabase.getInstance().getReference("Blood_Requests")
        db.addValueEventListener(object  : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                appointArrayList.clear()
                if(snapshot.exists()){
                    for (empsnap in snapshot.children){
                        val data = empsnap.getValue(RaiseRequest::class.java)
                        appointArrayList.add(data!!)
                    }


            }

                AppointAdapter.notifyDataSetChanged()

        }

    })

}


}