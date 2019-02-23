package com.example.dispositivos2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {
    EditText inputEmail, inputPassword;
    Button SignInButton, Registrarte,forgot;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    boolean isActivateRadioButton;
    RadioButton sesion_activa;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        //variables a donde mandaremos los datos
        inputEmail = (EditText) findViewById(R.id.login_user);
        inputPassword = (EditText) findViewById(R.id.Login_pass);

        SignInButton = (Button) findViewById(R.id.button_signin);
        Registrarte = (Button) findViewById(R.id.button_register);
        forgot = (Button)findViewById(R.id.Pass_Forgot);
        progressBar = new ProgressDialog(this);
        sesion_activa = (RadioButton) findViewById(R.id.Recuerdame);



        isActivateRadioButton = sesion_activa.isChecked(); //DESACTIVADO

        //Mantener sesion activa
        sesion_activa.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View v) {
                if(isActivateRadioButton){
                    sesion_activa.setChecked(false);
                }
                isActivateRadioButton = sesion_activa.isChecked();
                SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.commit();
            }
        });


        //LOGIN CORREO Y CONTRASEÑA
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Userregister();
            }

            private void Userregister() {
                String email, pass;
                email = inputEmail.getText().toString();
                pass = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar Un Correo Valido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "Se debe Ingresar Una Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setMessage("Iniciando Sesion");
                progressBar.show();

                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.dismiss();
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MenuMains.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Hubo un error al iniciar sesion, verifica tus dato", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = inputEmail.getText().toString();

                if (TextUtils.isEmpty(emailAddress)) {
                    Toast.makeText(MainActivity.this, "Se Debe Ingresar el correo", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setMessage("Enviando Contraseña");
                progressBar.show();
                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Contraseña Enviada", Toast.LENGTH_SHORT).show();
                                    progressBar.dismiss();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}