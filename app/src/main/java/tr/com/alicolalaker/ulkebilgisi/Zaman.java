package tr.com.alicolalaker.ulkebilgisi;

import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MuhammedAli on 12.11.2017.
 */

public class Zaman{

    int zaman=0;
    int saveZaman;
    boolean Stop=false;
    TextView textView;

    Zaman(final TextView textView, int startime, final Oyun oyun){

        zaman=startime;
        saveZaman=startime;
        this.textView=textView;


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!Stop){
                    if(zaman>=0)
                        textView.setText(String.valueOf(zaman--));
                    else{
                        textView.setText(String.valueOf(saveZaman));

                        oyun.dogruCevapSayisi++;
                        oyun.toplamZaman += (9-zaman);
                        if(oyun.dogruCevapSayisi!=oyun.TOPLAM_SORU_SAYISI)
                            oyun.sonrakiSoru();
                        else
                            oyun.dialog();
                    }

                }
                handler.postDelayed(this,1000);
            }
        }, 0);
    }

    public void ResetTextView(){
        textView.setText(String.valueOf(saveZaman));
    }
    public void ResetTime(){
        zaman=saveZaman;
    }

    public void StopTime(){
        Stop=true;
    }
    public void StartTime(){
        Stop=false;
    }

    public int getTime(){
        return zaman;
    }
}
