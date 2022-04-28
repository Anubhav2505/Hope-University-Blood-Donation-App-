package com.example.hope

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Raise_BloodRequest : AppCompatActivity() {


    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_blood_request)
        // Fetching data from the raise request UI
        var patient_Name = findViewById<EditText>(R.id.raisereq_name)
        var blood_grp = findViewById<Spinner>(R.id.blooodgrpSpinner)
        var req_Raiser = findViewById<EditText>(R.id.raisedBy_name)
        var contact_details = findViewById<EditText>(R.id.contact)
        var location = findViewById<EditText>(R.id.location)
        val isGenuine = findViewById<CheckBox>(R.id.checkBox)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_loader_layout)

        if(dialog.window!=null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }


        val raiseReq = findViewById<Button>(R.id.Bloodreq_btn)
         val userUID = FirebaseAuth.getInstance().currentUser?.uid // User Unique ID from DataBase
        // Raising reqeuest data  which goes into DB for storing them
        raiseReq.setOnClickListener {

            myRef.child("Blood_Requests").push().setValue(RequestTemplate(patient_Name.text.toString(),blood_grp.selectedItem.toString(),
                contact_details.text.toString(),req_Raiser.text.toString(),location.text.toString()))


        }



    }
}