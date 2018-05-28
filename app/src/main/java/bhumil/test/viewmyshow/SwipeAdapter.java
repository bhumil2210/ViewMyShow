package bhumil.test.viewmyshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by bhumil on 28/5/18.
 */

public class SwipeAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    public String[] Name = new String[7];
    public String[] desc = new String[7];
    public String[] url = new String[7];
    public String[] release_date = new String[7];
    public static int[] random_string = new int[7];
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();
    private static int i=1;

    public SwipeAdapter(Context context) {
        this.context = context;
        for(int j=1;j<=7;j++) {
            reference.child("Movies").child("movie" + j).addValueEventListener(new ValueEventListener() {

                //int i = 0;
                Random r = new Random();
                int random = r.nextInt(5);

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("Name").getValue(String.class);
                    String description = dataSnapshot.child("Description").getValue(String.class);
                    String poster = dataSnapshot.child("poster").getValue(String.class);
                    String rel_date = dataSnapshot.child("release_date").getValue(String.class);
                    Log.w("name", "" + name);
                    Log.w("desc", "" + description);
                    random = (random*random)%6;
                    Name[random] = name;
                    desc[random] = description;
                    url[random] = poster;
                    release_date[random]=rel_date;
                    i++;
                    Log.e("i====",""+i);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


        @Override
        public int getCount () {
            return 5;
        }

        @Override
        public boolean isViewFromObject (View view, Object object){
            return view == (RelativeLayout) object;
        }

        @Override
        public Object instantiateItem (ViewGroup container,int position){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fragment_layout, container, false);

            final TextView movie_name = view.findViewById(R.id.movie_name);
            final TextView movie_desc = view.findViewById(R.id.movie_description);
            final TextView movie_date = view.findViewById(R.id.release_date);
            final ImageView movie_poster = view.findViewById(R.id.poster);
            Log.e("down_name",""+Name[0]+""+Name[1]);
            Log.e("down_desc",""+desc[0]+""+desc[1]);
            movie_name.setText(Name[position]);
            movie_desc.setText(desc[position]);
            movie_date.setText(release_date[position]);
            Picasso.get().load(url[position]).into(movie_poster);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem (ViewGroup container,int position, Object object){
            container.removeView((RelativeLayout)object);
        }
    }
