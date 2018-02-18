package tr.com.alicolalaker.ulkebilgisi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String BAYRAK_KEY="key";
    public static final int BAYRAKLAR = 0;
    public static final int PARALAR = 1;
    public static final int BASKENTLER = 2;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tiklaButon(View view){

        Intent intent = new Intent(getApplicationContext(),Oyun.class);
        Intent intent2 = new Intent(getApplicationContext(),Kitalar.class);

        switch (view.getId()){
            case R.id.bayrak_buton:
                startActivity(intent2);
                break;
            case R.id.para_buton:
                intent.putExtra(BAYRAK_KEY, PARALAR);
                startActivity(intent);
                break;
            case R.id.plaka_buton:
                intent.putExtra(BAYRAK_KEY, BASKENTLER);
                startActivity(intent);
                break;
        }
    }
}
