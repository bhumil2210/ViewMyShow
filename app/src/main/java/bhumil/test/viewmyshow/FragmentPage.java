package bhumil.test.viewmyshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by bhumil on 28/5/18.
 */

public class FragmentPage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        String[] movie = bundle.getStringArray("movie");
        view = inflater.inflate(R.layout.fragment_layout,container,false);
        TextView movie_name = view.findViewById(R.id.movie_name);
        TextView movie_desc = view.findViewById(R.id.movie_description);
        Log.e("name-------------",""+movie[0]);
        Log.e("desc-------------",""+movie[1]);
        movie_name.setText(movie[0]);
        movie_desc.setText(movie[1]);

        return view;
    }
}
