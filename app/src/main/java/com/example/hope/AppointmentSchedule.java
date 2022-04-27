package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentSchedule extends AppCompatActivity {
    private DatePickerDialog picker;
    private EditText editapoint;
    private Spinner spinner;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,confirm;
    String time;
    private FirebaseAuth mAuth;
    FirebaseFirestore dbroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_schedule);
        editapoint=findViewById(R.id.editappoint);
        spinner=findViewById(R.id.bankspin);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        confirm=findViewById(R.id.confirm_button);
        dbroot=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="11:00 - 12:00";
                btn1.setBackgroundColor(Color.GRAY);
                btn2.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="12:30 - 13:30";
                btn2.setBackgroundColor(Color.GRAY);
                btn1.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="14:00 - 15:00";
                btn3.setBackgroundColor(Color.GRAY);
                btn2.setBackgroundColor(Color.WHITE);
                btn1.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="15:30 - 16:30";
                btn4.setBackgroundColor(Color.GRAY);
                btn2.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn1.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="17:00 - 18:00";
                btn5.setBackgroundColor(Color.GRAY);
                btn2.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn1.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time="18:30 - 19:30";
                btn6.setBackgroundColor(Color.GRAY);
                btn2.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn1.setBackgroundColor(Color.WHITE);
            }
        });

        editapoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialogue;;
                picker = new DatePickerDialog(AppointmentSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editapoint.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  insertdata();
            }
        });
    }
    public void insertdata(){
        String currentUserId = mAuth.getCurrentUser().getUid();
        String Email= mAuth.getCurrentUser().getEmail();
        HashMap items = new HashMap();
        items.put("id",currentUserId);
        items.put("Email",Email);
        items.put("Blood_Bank_Location",spinner.getSelectedItem().toString());
        items.put("Date",editapoint.getText().toString().trim());
        items.put("Time",time);
        dbroot.collection("Appointments").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                     spinner.setSelection(0);
                     editapoint.setText("    /     /");
                     time="";
                        btn1.setBackgroundColor(Color.WHITE);
                        btn2.setBackgroundColor(Color.WHITE);
                        btn3.setBackgroundColor(Color.WHITE);
                        btn4.setBackgroundColor(Color.WHITE);
                        btn5.setBackgroundColor(Color.WHITE);
                        btn6.setBackgroundColor(Color.WHITE);


                     Toast.makeText(getApplicationContext(),"Appointment Schedule Sucessfully",Toast.LENGTH_LONG).show();
                    }
                });
    }
}