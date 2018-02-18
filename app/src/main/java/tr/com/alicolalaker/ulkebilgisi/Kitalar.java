package tr.com.alicolalaker.ulkebilgisi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Kitalar extends AppCompatActivity {

    public static final String DUNYA_KEY="key";
    public static final int AVRUPA = 3;
    public static final int ASYA = 4;
    public static final int AFRİKA = 5;
    public static final int KUZEYAMERİKA = 6;
    public static final int GUNEYAMERİKA = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitalar);
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
        Intent intent2 = new Intent(getApplicationContext(),Sorular.class);

        switch (view.getId()){
            case R.id.dunya_buton:
                startActivity(intent2);
                break;
            case R.id.avrupa_buton:
                intent.putExtra(DUNYA_KEY, AVRUPA);
                startActivity(intent);
                break;
            case R.id.asya_buton:
                intent.putExtra(DUNYA_KEY, ASYA);
                startActivity(intent);
                break;
            case R.id.afrika_buton:
                intent.putExtra(DUNYA_KEY, AFRİKA);
                startActivity(intent);
                break;
            case R.id.kamerika_buton:
                intent.putExtra(DUNYA_KEY, KUZEYAMERİKA);
                startActivity(intent);
                break;
            case R.id.gamerika_buton:
                intent.putExtra(DUNYA_KEY, GUNEYAMERİKA);
                startActivity(intent);
                break;
        }
    }

}
