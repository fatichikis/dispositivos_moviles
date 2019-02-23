package com.example.dispositivos2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.graphics.PathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.security.auth.callback.PasswordCallback;


public class activity_profile extends AppCompatActivity {
    Button uploadImg;
    ImageView imgView;
    TextView mail, contra, phone,userdato, Etiquetaname;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;

    //
     DatabaseReference dbPrediccion;
     ValueEventListener eventListener;

    //Firebase conexion para almacenar foto
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseStorage storage;
    StorageReference storageReference;
    private DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Etiquetaname = (TextView)findViewById(R.id.etiquetauser);
//        uploadImg = (Button)findViewById(R.id.button_guardarcambionuser);
        imgView = (ImageView)findViewById(R.id.picuser);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        //muestra los datos del usuario
        mail = (TextView) findViewById(R.id.email_user);
        contra = (TextView) findViewById(R.id.pass_user);
        userdato = (TextView) findViewById(R.id.obtenciondeuser);
        phone = (TextView) findViewById(R.id.phone_user);
        Etiquetaname = (TextView) findViewById(R.id.etiquetauser);

        //muestra el correo del usuario
        final String etiqueta = user.getUid().toString();
        userdato.setText(etiqueta);
        userdato.setVisibility(View.INVISIBLE);

        DatabaseReference dbUsuarios = FirebaseDatabase.getInstance().getReference().child("Users");
        dbUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String textoemail = dataSnapshot.child(etiqueta).child("email").getValue().toString();
                mail.setText(textoemail);
                String textotelefono =  dataSnapshot.child(etiqueta).child("phone").getValue().toString();
                phone.setText(textotelefono);
                String textocontraseña = dataSnapshot.child(etiqueta).child("password").getValue().toString();
                contra.setText(textocontraseña);
                String textousername = dataSnapshot.child(etiqueta).child("name").getValue().toString();
                Etiquetaname.setText(textousername);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
    }
}
