package tr.com.alicolalaker.ulkebilgisi;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Oyun extends AppCompatActivity {

    int TOPLAM_SORU_SAYISI = 10;
    private List<String> genelListe, soruListesi;
    int oyun,toplamCevapSayisi, dogruCevapSayisi, kontrol , kacinciDeneme , toplamPuan , toplamZaman;
    private RelativeLayout soruBankasi;
    private GridLayout butonBankasi;
    private TextView ilerleme_tv, soru_tv, zaman_tv;
    private Random random;
    private Zaman zaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        ImageButton button_geri = findViewById(R.id.button_geri);
        button_geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        oyun = getIntent().getIntExtra(MainActivity.BAYRAK_KEY,MainActivity.BAYRAKLAR);
        genelListe = new ArrayList<String>();
        soruListesi = new ArrayList<String>();
        soruBankasi = findViewById(R.id.rl);
        butonBankasi = findViewById(R.id.buton_bankasi);
        ilerleme_tv = findViewById(R.id.ilerleme_tv);
        soru_tv = findViewById(R.id.soru_tv);
        zaman_tv = findViewById(R.id.zamanText);
        random = new Random();
        kacinciDeneme=1;

        for (int i=0;i<butonBankasi.getChildCount();i++){
            ImageButton imageButton = (ImageButton) butonBankasi.getChildAt(i);
            imageButton.setOnClickListener(butonDinleyici);
        }

        zaman=new Zaman(zaman_tv,10,this);

        ilerleme_tv.setText("1/"+TOPLAM_SORU_SAYISI);

        switchOyun();
        oyunTekrari();
    }

    private View.OnClickListener butonDinleyici = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Handler handler = new Handler();
            toplamCevapSayisi++;
            ImageButton imagebutton = (ImageButton) view;

            if(kontrol == view.getId()){
                zaman.StopTime();

                tebrikler(kacinciDeneme,zaman.getTime());
                toplamZaman += (9-zaman.getTime());
                kacinciDeneme=1;
                butunButonlarPasif();
                dogruCevapSayisi++;

                if(dogruCevapSayisi==TOPLAM_SORU_SAYISI){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zaman.ResetTextView();
                            dialog();
                        }
                    },1500);
                }else{
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zaman.ResetTextView();
                            sonrakiSoru();
                        }
                    },1500);
                }

                for (int i=0;i<butonBankasi.getChildCount();i++){
                    if(butonBankasi.getChildAt(i)!=imagebutton){
                        setColor((ImageButton) butonBankasi.getChildAt(i));
                    }
                }

            }else{
                kacinciDeneme++;

                setColor(imagebutton);
                Animasyon.titremeAnimasyonu(soruBankasi);
            }
        }
    };

    private void setColor(ImageButton imagebutton){
        imagebutton.setEnabled(false);
        imagebutton.setColorFilter(Color.parseColor("#AA606060"));

    }

    private void oyunTekrari() {
        toplamCevapSayisi = 0;
        dogruCevapSayisi = 0;
        soruListesi.clear();
        toplamPuan=0;
        toplamZaman=0;

        int soruSayisi = 1, listeElemanSayisi = genelListe.size();

        while (soruSayisi<=TOPLAM_SORU_SAYISI){/**genelListe den rastgele SoruSayısı kadar soru seçilip soruListesine ekleniyor*/
            int index = random.nextInt(listeElemanSayisi);
            String soru = genelListe.get(index);
            if(!soruListesi.contains(soru)){ /** Aynı soru yoksa eklesin*/
                soruListesi.add(soru);
                soruSayisi++;
            }
        }
        sonrakiSoru();
    }

    public void sonrakiSoru() {
        zaman.StartTime();
        zaman.ResetTime();

        ilerleme_tv.setText(dogruCevapSayisi+1+"/"+TOPLAM_SORU_SAYISI);

        String soru = soruListesi.remove(0);
        soru_tv.setText(getSoru(soru));
        String dogruCevap = getCevap(soru);

        /** ------ Butonlar ------ */

        Collections.shuffle(genelListe);
        int dogruCevapIndisi = genelListe.indexOf(soru);

        String uri;
        int imageResource;

        genelListe.add(genelListe.remove(dogruCevapIndisi)); //Doğru cevabı kesip liste sonuna ekliyor

        for ( int i=0;i<butonBankasi.getChildCount();i++){ /** cevaplar ekleniyor. fakat doğru cevap yok*/
            ImageButton imageButton = (ImageButton) butonBankasi.getChildAt(i);
            String secenek = genelListe.get(i);
            uri = getCevap(secenek);
            imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageButton.setImageDrawable(res);
            imageButton.setEnabled(true);
            imageButton.setColorFilter(Color.parseColor("#00FFFFFF"));
        }

        uri= dogruCevap;
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        int rastgele = random.nextInt(4);
        ((ImageButton)butonBankasi.getChildAt(rastgele)).setImageDrawable(res);
        kontrol = ((ImageButton)butonBankasi.getChildAt(rastgele)).getId();

        animasyonlar();

    }

    private String getSoru(String soru) {/** soruyu "-" işaretinden önce alıp kesiyor. */
        soru=soru.substring(0,soru.indexOf("-"));
        return soru;
    }

    private String getCevap(String soru) {/** doğru cevabı "-" işaretinden sonra alıp kesiyor. */
        soru=soru.substring(soru.indexOf("-")+1,soru.length());
        return soru;
    }

    private void animasyonlar() {
        List<View> butonlar = new ArrayList<View>();

        for (int i=0;i<butonBankasi.getChildCount();i++){
            ImageButton button= (ImageButton) butonBankasi.getChildAt(i);
            butonlar.add(button);
        }
        Animasyon.yaziAnimasyonu(soru_tv,random.nextInt(29));
        Animasyon.butonAnimasyonu(butonlar);
    }

    public void tebrikler(int kacinciDeneme,int zaman) {
        String tebrikMesaji="";
        switch (kacinciDeneme){
            case 0: tebrikMesaji="Süre Dolu";break;
            case 1: tebrikMesaji="Mükemmel"; toplamPuan+=4*zaman; break;
            case 2: tebrikMesaji="İyi"; toplamPuan+=3*zaman; break;
            case 3: tebrikMesaji="Fena Değil"; toplamPuan+=2*zaman; break;
            case 4: tebrikMesaji="Sonunda"; toplamPuan+=zaman; break;
        }
        soru_tv.setText(tebrikMesaji);
        soru_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        Animasyon.tebrikAnimasyonu(soru_tv);
    }

    private void butunButonlarPasif() {
        for (int i=0;i<butonBankasi.getChildCount();i++){
            ImageButton imageButton = (ImageButton) butonBankasi.getChildAt(i);
            imageButton.setEnabled(false);
        }
    }

    void dialog() {
        final ConstraintLayout dialogLayout = findViewById(R.id.dialogLayout);
        dialogLayout.setVisibility(View.VISIBLE);
        TextView puanText = findViewById(R.id.puanText);
        TextView zamanText = findViewById(R.id.sureText);
        TextView oranText = findViewById(R.id.oranText);
        puanText.setText(String.valueOf(toplamPuan)+"/"+String.valueOf(TOPLAM_SORU_SAYISI*40));
        zamanText.setText(String.valueOf(toplamZaman)+" sn");
        if(toplamCevapSayisi==0)
            oranText.setText("%0");
        else
            oranText.setText("%"+String.valueOf(TOPLAM_SORU_SAYISI*100/toplamCevapSayisi));
        FloatingActionButton yenileButton = findViewById(R.id.yenileButton);
        FloatingActionButton geriButton = findViewById(R.id.geriButton);
        yenileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oyunTekrari();
                dialogLayout.setVisibility(View.GONE);
            }
        });
        geriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        zaman.StopTime();
    }

    private void switchOyun(){
        TextView seviyeText = findViewById(R.id.seviyeText);
        TextView oyunBaslik = findViewById(R.id.oyunBaslik);

        switch (oyun){
            case Sorular.BES:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 5;
                imageButtonPadding(0,0);
                String[] dizi = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi){
                    genelListe.add(s);
                }
                break;
            case Sorular.ON:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 10;
                imageButtonPadding(0,0);
                String[] dizi2 = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi2){
                    genelListe.add(s);
                }
                break;
            case Sorular.ONBES:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 15;
                imageButtonPadding(0,0);
                String[] dizi3 = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi3){
                    genelListe.add(s);
                }
                break;
            case Sorular.YIRMI:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 20;
                imageButtonPadding(0,0);
                String[] dizi4 = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi4){
                    genelListe.add(s);
                }
                break;
            case Sorular.YIRMIBES:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 25;
                imageButtonPadding(0,0);
                String[] dizi5 = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi5){
                    genelListe.add(s);
                }
                break;
            case Sorular.OTUZ:
                seviyeText.setText("GENEL");
                TOPLAM_SORU_SAYISI = 30;
                imageButtonPadding(0,0);
                String[] dizi6 = getResources().getStringArray(R.array.bayraklar);
                for(String s: dizi6){
                    genelListe.add(s);
                }
                break;
            case MainActivity.PARALAR:
                oyunBaslik.setText("PARA BİRİMİ");
                seviyeText.setText("YARIŞI");
                imageButtonPadding(50,80);
                String[] dizi7 = getResources().getStringArray(R.array.paralar);
                for(String s: dizi7){
                    genelListe.add(s);
                }
                break;
            case MainActivity.BASKENTLER:
                oyunBaslik.setText("BAŞKENTLER");
                seviyeText.setText("YARIŞI");
                imageButtonPadding(0,0);
                String[] dizi8 = getResources().getStringArray(R.array.baskentler);
                for(String s: dizi8){
                    genelListe.add(s);
                }
                break;
            case Kitalar.AVRUPA:
                seviyeText.setText("AVRUPA KITASI");
                imageButtonPadding(0,0);
                String[] dizi9 = getResources().getStringArray(R.array.avrupa);
                for(String s: dizi9){
                    genelListe.add(s);
                }
                break;
            case Kitalar.AFRİKA:
                seviyeText.setText("AFRİKA KITASI");
                TOPLAM_SORU_SAYISI=20;
                imageButtonPadding(0,0);
                String[] dizi10 = getResources().getStringArray(R.array.afrika);
                for(String s: dizi10){
                    genelListe.add(s);
                }
                break;
            case Kitalar.ASYA:
                seviyeText.setText("ASYA KITASI");
                TOPLAM_SORU_SAYISI=10;
                imageButtonPadding(0,0);
                String[] dizi11 = getResources().getStringArray(R.array.asya);
                for(String s: dizi11){
                    genelListe.add(s);
                }
                break;
            case Kitalar.GUNEYAMERİKA:
                seviyeText.setText("GÜNEY AMERİKA KITASI");
                TOPLAM_SORU_SAYISI=10;
                imageButtonPadding(0,0);
                String[] dizi12 = getResources().getStringArray(R.array.gamerika);
                for(String s: dizi12){
                    genelListe.add(s);
                }
                break;
            case Kitalar.KUZEYAMERİKA:
                seviyeText.setText("KUZEY AMERİKA KITASI");
                TOPLAM_SORU_SAYISI=10;
                imageButtonPadding(0,0);
                String[] dizi13 = getResources().getStringArray(R.array.kamerika);
                for(String s: dizi13){
                    genelListe.add(s);
                }
                break;
        }
    }

    private void imageButtonPadding(int size, int size2){
        ImageButton imagebutton1 = findViewById(R.id.imageButton);
        ImageButton imagebutton2 = findViewById(R.id.imageButton2);
        ImageButton imagebutton3 = findViewById(R.id.imageButton3);
        ImageButton imagebutton4 = findViewById(R.id.imageButton4);

        imagebutton1.setPadding(size,size,size,size2);
        imagebutton2.setPadding(size,size,size,size2);
        imagebutton3.setPadding(size,size2,size,size);
        imagebutton4.setPadding(size,size2,size,size);
    }
}
