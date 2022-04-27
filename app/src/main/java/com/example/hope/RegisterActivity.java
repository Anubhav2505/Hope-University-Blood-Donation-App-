package com.example.hope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    private CircleImageView profile_image;
    private EditText editName,editTextdob,editEmail,editPwd;
    private Spinner bloodgroupspinner;
    private Button signupbtn;
    private Uri resultUri;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaserefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextdob = findViewById(R.id.editDob);
        profile_image = findViewById(R.id.profile_image);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPwd = findViewById(R.id.editPwd);
        bloodgroupspinner = findViewById(R.id.bloodgroupspinner);
        signupbtn = findViewById(R.id.signupbtn);
        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialogue;;
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextdob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editEmail.getText().toString().trim();
                final String password = editPwd.getText().toString().trim();
                final String fullName = editName.getText().toString().trim();
                final String dob = editTextdob.getText().toString().trim();
                final String bloodGroup = bloodgroupspinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(email)){
                    editEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editPwd.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)){
                    editName.setError("Full name is required");
                    return;
                }
                if (bloodGroup.equals("Select your blood group")){
                    Toast.makeText(RegisterActivity.this, "Select Blood group", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaserefer = FirebaseDatabase.getInstance().getReference()
                                        .child("users").child(currentUserId);
                                HashMap userInfo = new HashMap();
                                userInfo.put("id", currentUserId);
                                userInfo.put("DOB", dob);
                                userInfo.put("name", fullName);
                                userInfo.put("email", email);
                                userInfo.put("bloodgroup", bloodGroup);

                                userDatabaserefer.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Data set Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                        //loader.dismiss();
                                    }
                                });
                                if (resultUri !=null) {
                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                            .child("profile images").child(currentUserId);
                                    Bitmap bitmap = null;

                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                    byte[] data = byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask = filePath.putBytes(data);

                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                         if(taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){
                                             Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                             result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                 @Override
                                                 public void onSuccess(Uri uri) {
                                                     String imageUrl = uri.toString();
                                                     Map newImageMap = new HashMap();
                                                     newImageMap.put("profilepictureurl", imageUrl);
                                                     userDatabaserefer.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                         @Override
                                                         public void onComplete(@NonNull Task task) {
                                                             if (task.isSuccessful()){
                                                                 Toast.makeText(RegisterActivity.this, "Image url added to database successfully", Toast.LENGTH_SHORT).show();
                                                             }else {
                                                                 Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                             }
                                                         }
                                                     });
                                                     finish();

                                                 }
                                             });
                                         }
                                        }
                                    });
                                  Intent intent= new Intent(RegisterActivity.this,HomeActivity.class);
                                  startActivity(intent);
                                  finish();
                                  loader.dismiss();
                                }

                            }
                        }
                    });
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data !=null){
            resultUri = data.getData();
            profile_image.setImageURI(resultUri);
        }
    }
}