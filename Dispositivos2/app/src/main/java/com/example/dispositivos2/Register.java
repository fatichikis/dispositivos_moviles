package com.example.dispositivos2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.example.dispositivos2.User;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class Register extends AppCompatActivity {
    EditText edtMail, edtname, edtPassword, edtcellphone;
    Button btnSimgUp, button_iniciando;
    ProgressDialog pd;


    //FIREBASE;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference users;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FIREBASE CONEXIOn
        users = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        edtname= (EditText)  findViewById(R.id.Username);
        edtMail= (EditText)  findViewById(R.id.eMAIL);
        edtPassword = (EditText)  findViewById(R.id.Passwoord);
        edtcellphone = (EditText)  findViewById(R.id.Pcellphone);

        btnSimgUp = (Button) findViewById(R.id.btn_signUp);
        button_iniciando = (Button)findViewById(R.id.button_iniciarsesion);

        button_iniciando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Register.this,MainActivity.class);
                startActivity(a);
            }
        });


        btnSimgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                edtMail.setText("");
                edtPassword.setText("");
                edtname.setText("");
                edtcellphone.setText("");
                /********/
            }
            private void registerUser() {
                final String name = edtname.getText().toString().trim();
                final String email = edtMail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String phone = edtcellphone.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Register.this, "Se debe ingresa un username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Se Debe Ingresar Un Correo Valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(Register.this, "Se Debe Ingresar Una Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Register.this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.isEmpty()) {
                    Toast.makeText(Register.this, "Se Debe Ingresar Un Celular", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.length() != 10) {
                    Toast.makeText(Register.this, "El celular debe ser mayor a 10 digitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    User user = new User(
                                            name,
                                            email,
                                            password,
                                            phone

                                    );
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this, "Correcto", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //display a failure message
                                            }
                                        }

                                    });

                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}