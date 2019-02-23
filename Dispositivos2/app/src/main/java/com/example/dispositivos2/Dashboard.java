package com.example.dispositivos2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView pagetitle, pagesubtitle;
    ImageView packagePlace;
    SeekBar packageRange;
    Animation atg, packageimg;
    Button button_register, button_register2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        button_register = findViewById(R.id.btntake_comienzo);
        button_register2 = findViewById(R.id.btntake_comienzo2);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            pagetitle.setText("Primer Paso");
                            pagesubtitle.setText("Una aplicacion sencilla para un maestro grandioso");
                            packagePlace.setImageResource(R.drawable.icstarter);

                            // pass animation
                            packagePlace.startAnimation(packageimg);
                            pagetitle.startAnimation(atg);
                            pagesubtitle.startAnimation(atg);
            }
        });

        button_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagetitle.setText("Tercero");
                pagesubtitle.setText("Activa las notificaciones para que podamos ser tu asistente");
                packagePlace.setImageResource(R.drawable.icvip);

                // pass animation
                packagePlace.startAnimation(packageimg);
                packageRange.getProgress();
                pagetitle.startAnimation(atg);
                pagesubtitle.startAnimation(atg);
            }
    });

        atg = AnimationUtils.loadAnimation(Dashboard.this, R.anim.atg);
        packageimg = AnimationUtils.loadAnimation(Dashboard.this, R.anim.packageimg);

        pagetitle = findViewById(R.id.pagetitle);
        pagesubtitle = findViewById(R.id.pagesubtitle);

        packagePlace = findViewById(R.id.packagePlace);

        packageRange = findViewById(R.id.packageRange);

        packageRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 35){
                    pagetitle.setText("Primer Paso");
                    pagesubtitle.setText("Una aplicacion sencilla para un maestro grandioso");
                    packagePlace.setImageResource(R.drawable.icstarter);

                    // pass animation
                    packagePlace.startAnimation(packageimg);
                    pagetitle.startAnimation(atg);
                    pagesubtitle.startAnimation(atg);
                }
                else if(progress == 75){
                    pagetitle.setText("Segundo");
                    pagesubtitle.setText("En el menu principal escoge la opcion que necesites");
                    packagePlace.setImageResource(R.drawable.icbusinessplayer);

                    // pass animation
                    packagePlace.startAnimation(packageimg);
                    pagetitle.startAnimation(atg);
                    pagesubtitle.startAnimation(atg);
                }
                else if(progress == 100){
                    pagetitle.setText("Tercero");
                    pagesubtitle.setText("Activa las notificaciones para que podamos ser tu asistente");
                    packagePlace.setImageResource(R.drawable.icvip);

                    // pass animation
                    packagePlace.startAnimation(packageimg);
                    pagetitle.startAnimation(atg);
                    pagesubtitle.startAnimation(atg);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


    }
}
