package tr.com.alicolalaker.ulkebilgisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class Sorular extends AppCompatActivity {

    public static final String DUNYA_KEY="key";
    public static final int BES = 8;
    public static final int ON = 9;
    public static final int ONBES = 10;
    public static final int YIRMI = 11;
    public static final int YIRMIBES= 12;
    public static final int OTUZ= 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorular);
        ImageButton button_geri = findViewById(R.id.button_geri);
        button_geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void tiklaButon(View view){

        Intent intent = new Intent(getApplicationContext(),Oyun.class);

        switch (view.getId()){
            case R.id.bes_buton:
                intent.putExtra(DUNYA_KEY, BES);
                startActivity(intent);
                break;
            case R.id.on_buton:
                intent.putExtra(DUNYA_KEY, ON);
                startActivity(intent);
                break;
            case R.id.onbes_buton:
                intent.putExtra(DUNYA_KEY, ONBES);
                startActivity(intent);
                break;
            case R.id.yirmi_buton:
                intent.putExtra(DUNYA_KEY, YIRMI);
                startActivity(intent);
                break;
            case R.id.yirmibes_buton:
                intent.putExtra(DUNYA_KEY, YIRMIBES);
                startActivity(intent);
                break;
            case R.id.otuz_buton:
                intent.putExtra(DUNYA_KEY, OTUZ);
                startActivity(intent);
                break;
        }
    }

}
