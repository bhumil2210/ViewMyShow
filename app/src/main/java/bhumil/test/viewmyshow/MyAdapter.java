package bhumil.test.viewmyshow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by bhumil on 27/5/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

private List<ListItem> listItems;
private Context context;

public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.cardview,parent,false);
        return new ViewHolder(v);
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);
        holder.head.setText(listItem.getHead());
        holder.desc.setText(listItem.getDesc());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,listItem.getHead(),Toast.LENGTH_LONG).show();
                Intent i =new Intent(context,movies_display.class);
                i.putExtra("name",listItem.getHead());
                i.putExtra("description",listItem.getDesc());
                context.startActivity(i);
            }
        });
        }

@Override
public int getItemCount() {
        return listItems.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView head;
    public TextView desc;
    public RelativeLayout relativeLayout;
    public ViewHolder(View itemView) {
        super(itemView);

        head = itemView.findViewById(R.id.places_name);
        desc = itemView.findViewById(R.id.places_address);
        relativeLayout = itemView.findViewById(R.id.places_list);
        }
    }
}
