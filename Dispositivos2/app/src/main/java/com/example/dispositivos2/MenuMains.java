package com.example.dispositivos2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

public class MenuMains extends AppCompatActivity {
    public static final String user2="names";

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    TextView nameuser, walletuser, review, network, plugins, myapps, mainmenus,
            pagetitle, pagesubtitle, btnclose,userdato;

    Button btnguide;
    TextView perfil;
    Animation atg, atgtwo, atgthree;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mains);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("");

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MenuMains.this, MainActivity.class));
                }
            }
        };

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        nameuser = findViewById(R.id.nameuser);
        walletuser = findViewById(R.id.walletuser);

        imageView3 = findViewById(R.id.imageView3);

        review = findViewById(R.id.review);
        network = findViewById(R.id.network);
        plugins = findViewById(R.id.plugins);
        myapps = findViewById(R.id.myapps);
        mainmenus = findViewById(R.id.mainmenus);

        pagetitle = findViewById(R.id.pagetitle);
        pagesubtitle = findViewById(R.id.pagesubtitle);
        userdato = (TextView) findViewById(R.id.obtenciondeuser);

        btnguide = findViewById(R.id.btnguide);
        btnclose = findViewById(R.id.close_session);
        perfil = findViewById(R.id.walletuser);

        //muestra el correo del usuario
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String etiqueta = user.getUid().toString();
        userdato.setText(etiqueta);
        userdato.setVisibility(View.INVISIBLE);

        DatabaseReference dbUsuarios = FirebaseDatabase.getInstance().getReference().child("Users");
        dbUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String textonameuser = dataSnapshot.child(etiqueta).child("name").getValue().toString();
                nameuser.setText("Bienvenido: "+ textonameuser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });


        btnguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MenuMains.this,Dashboard.class);
                startActivity(a);
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MenuMains.this,activity_profile.class);
                startActivity(a);
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
            }
        });

        // pass an animation
        imageView3.startAnimation(atg);
        pagetitle.startAnimation(atgtwo);
        pagesubtitle.startAnimation(atgtwo);
        btnguide.startAnimation(atgthree);
    }
}
