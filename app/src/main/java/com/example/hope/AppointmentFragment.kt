package com.example.hope

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import java.lang.reflect.Array.get

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AppointmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppointmentFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private lateinit var appointArrayList: ArrayList<Appoint>
    private lateinit var AppointAdapter: AppointAdapter
    private lateinit var db: FirebaseFirestore
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View
        view=inflater.inflate(R.layout.fragment_appointment, container, false)
        val btn= view.findViewById<Button>(R.id.confirm_button)
        btn.setOnClickListener {

            requireActivity().run{
                startActivity(Intent(this, AppointmentSchedule::class.java))

            }
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.apRecylerView)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        appointArrayList= arrayListOf()

        AppointAdapter= AppointAdapter(appointArrayList)

        recyclerView.adapter = AppointAdapter

        EventChangeListner()
        // Inflate the layout for this fragment
        return view
    }

    private fun EventChangeListner() {
        db= FirebaseFirestore.getInstance()
        db.collection("Appointments").addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error!=null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        appointArrayList.add(dc.document.toObject(Appoint::class.java))
                    }
                }
                AppointAdapter.notifyDataSetChanged()

            }

        })
    }


}