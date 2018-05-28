package bhumil.test.viewmyshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by bhumil on 28/5/18.
 */

public class movies_display  extends AppCompatActivity {

    private LinearLayout l;
    ProgressDialog prg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_display);

        final ViewPager viewPager  = findViewById(R.id.viewpager);
        l = (LinearLayout)findViewById(R.id.movies_layout);
        viewPager.setPageTransformer(true,new Animation());
        final SwipeAdapter swipeAdapter = new SwipeAdapter(this);

        prg=new ProgressDialog(this);
        prg.setMessage("Fetching Available Shows...");
        prg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setAdapter(swipeAdapter);
                prg.dismiss();
            }
        }, 10000);
    }
}