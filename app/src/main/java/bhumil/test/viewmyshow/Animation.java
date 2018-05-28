package bhumil.test.viewmyshow;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by bhumil on 28/5/18.
 */

public class Animation implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    @Override
    public void transformPage(View page, float position) {
        int pagewidth = page.getWidth();

        if(position<-1){
            page.setAlpha(0);
        }
        else if (position<=0){
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        }
        else if(position<=1){
            page.setAlpha(1-position);
            page.setTranslationX(pagewidth * -position);
            float scalefactor = MIN_SCALE + (1-MIN_SCALE)*(1-Math.abs(position));
            page.setScaleX(scalefactor);
            page.setScaleY(scalefactor);
        }
        else {
            page.setAlpha(0);
        }
    }
}
