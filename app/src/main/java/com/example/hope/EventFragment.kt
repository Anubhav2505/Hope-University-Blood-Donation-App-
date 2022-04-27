package com.example.hope

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var eventArrayList: ArrayList<Events>
    private lateinit var eventAdapter: EventAdapter
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
        view= inflater.inflate(R.layout.fragment_event, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        eventArrayList= arrayListOf()

        eventAdapter= EventAdapter(eventArrayList)

        recyclerView.adapter = eventAdapter

        EventChangeListner()



        // Inflate the layout for this fragment
        return view
    }

    private fun EventChangeListner() {
       db= FirebaseFirestore.getInstance()
       db.collection("Events").addSnapshotListener(object: EventListener<QuerySnapshot>{
           override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
               if(error!=null){
                   Log.e("Firestore Error",error.message.toString())
                   return
               }
               for (dc: DocumentChange in value?.documentChanges!!){
                   if (dc.type == DocumentChange.Type.ADDED){
                       eventArrayList.add(dc.document.toObject(Events::class.java))
                   }
               }
               eventAdapter.notifyDataSetChanged()

           }

       })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}