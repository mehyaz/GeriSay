package com.mozo.meyaz.gerisay;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import com.mozo.meyaz.gerisay.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    private TextView kalanSure;
    private ImageButton zamanButon,zamaniptal;
    public int simdikiSaat,simdikiDakika;
    private static final String FORMAT = "%02d:%02d:%02d";
    public CountDownTimer  sayici;
    public boolean iptalKontrol=true;
    public boolean iptalbtn=false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kalanSure=(TextView) findViewById(R.id.textView);
        zamanButon=(ImageButton) findViewById(R.id.sureKur);
        zamaniptal= (ImageButton) findViewById(R.id.sureiptal);
    }

    public void Sayici(View view)
    {

        if(view.getId()==R.id.sureKur)
        {
            iptalKontrol=true;
            Calendar simdikiZaman=Calendar.getInstance();
            simdikiSaat=simdikiZaman.get(simdikiZaman.HOUR_OF_DAY);
            simdikiDakika=simdikiZaman.get(simdikiZaman.MINUTE);
            TimePickerDialog zamanDialog;
            zamanDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int saat, int dakika) {
                    int kalanSaat = saat - simdikiSaat;
                    int kalanDakika = dakika - simdikiDakika;
                    int ToplamSure = (kalanSaat * 60 * 60 * 1000) + (kalanDakika * 60 * 1000);
                    if (ToplamSure < 0)
                    {

                        ToplamSure=((kalanSaat+24) * 60 * 60 * 1000) + (kalanDakika * 60 * 1000);

                    }
                          sayici = new CountDownTimer(ToplamSure, 1000) {

                            @Override
                            public void onTick(long sure) {
                                if(iptalKontrol==true) {
                                    sure = sure / 1000;
                                    int saat = (int) (sure / 3600);
                                    sure -= saat * 3600;
                                    int dk = (int) (sure / 60);
                                    sure -= dk * 60;
                                    int sn = (int) sure % 60;

                                    // kalanSure.setText("Kalan Süre:"+saat+":"+dk+":"+sn);
                                    kalanSure.setText(String.format(FORMAT, saat, dk, sn));
                                }
                                else
                                {
                                    cancel();
                                }
                            }

                            @Override
                            public void onFinish() {
                                //System.exit(0);
                                if(iptalbtn==true)
                                {
                                    iptalbtn=!iptalbtn;
                                }
                                else
                                {
                                    Cikis();
                                }

                             /*   if(!iptalKontrol)
                                { Cikis();}
                                else {
                                    this.cancel();
                                    Toast toast = Toast.makeText(getApplicationContext(), "Zamanlayıcı iptal edildi.", Toast.LENGTH_LONG);
                                    toast.show();
                                }*/

                            }


                        }.start();


                }
            },simdikiSaat,simdikiDakika,true);

            zamanDialog.setTitle("Kapanış Saatini Seçiniz");
            zamanDialog.setButton(DatePickerDialog.BUTTON_POSITIVE,"Ayarla",zamanDialog);
            zamanDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            if (which==DialogInterface.BUTTON_NEGATIVE){
                            iptalbtn=true;
                            }
                        }
                    });


            zamanDialog.show();



        }
        else if(view.getId()==R.id.sureiptal)
        {
            iptalKontrol=false;
            kalanSure.setText("Süre durduruldu.");


         //   sayici.onFinish();
        }


    }
    private void Cikis() {
        System.exit(0);
    }


}


