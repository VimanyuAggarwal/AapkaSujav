package com.example.mohak.aapkasujav.AppIntro.City;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohak.aapkasujav.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohak on 23/8/15.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.Cityholder> {

    ArrayList<SingleCity> data;
    LayoutInflater inflater;
    Context context;
    //List<SingleRow> refers to list of objects of SingleRow

    public CityAdapter(Context context, ArrayList<SingleCity> data)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public Cityholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Whenever new row is to be displayed this fn is called
        View view = inflater.inflate(R.layout.single_city, viewGroup ,false);
        Cityholder viewHolder = new Cityholder(view);
        View v = view.findViewById( R.id.view);
        v.setOnClickListener(clickListener);
        v.setTag(viewHolder);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(Cityholder viewHolder, int position) {
        //used to set data to be displayed int the recycler view and update it as per the position (i)

        SingleCity current = data.get(position);
        viewHolder.textView.setText(current.num);
        Log.d("Qwerty", current.num);
        viewHolder.textView2.setText(current.problem);
//        viewHolder.textView.setOnClickListener(clickListener);
//        viewHolder.textView2.setOnClickListener(clickListener);
//        viewHolder.textView.setTag(viewHolder);
//        viewHolder.textView2.setTag(viewHolder);
       // viewHolder.circleImageView.setImageResource(current.icon);



    }
    //Click on recycler view with unknown elements
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Cityholder holder = (Cityholder)v.getTag();
            int position = holder.getAdapterPosition();
            Toast.makeText(context,"Postion "+position,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Cityholder extends RecyclerView.ViewHolder
            //This class needs to be a sub class of the Adapter class
    {
        TextView textView;
        TextView textView2;

        public Cityholder(View itemView) {
            super(itemView);
           ;
            textView = (TextView) itemView.findViewById(R.id.number);
            textView2= (TextView) itemView.findViewById(R.id.prob);

        }
    }
}

