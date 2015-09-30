package com.example.mohak.aapkasujav.AppIntro.MaterialTab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohak.aapkasujav.AppIntro.City.CityAdapter;
import com.example.mohak.aapkasujav.AppIntro.City.SingleCity;
import com.example.mohak.aapkasujav.AppIntro.VolleySingelton;
import com.example.mohak.aapkasujav.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentActivity myContext;
    VolleySingelton volleySingelton;
    RecyclerView mycycle;
    File folder;
    File myfile;
    ProgressBar progressBar;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mycycle = (RecyclerView) view.findViewById(R.id.rec);
//        FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4

        if (isNetworkAvailable()) {
            getdata();

        } else {
            progressBar.setVisibility(View.INVISIBLE);

        }

        return view;
    }

    public void getdata() {

        progressBar.setVisibility(View.VISIBLE);
        volleySingelton = VolleySingelton.getInstance();
        RequestQueue q = volleySingelton.getrequestQueue();
        String url2 = "http://myrestapiish.meteor.com/publications/aapka1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                mycycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                CityAdapter adapter = new CityAdapter(getActivity(), json(response));
                SlideInBottomAnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(adapter);
                animationAdapter.setFirstOnly(false);
                animationAdapter.setDuration(500);
                animationAdapter.setInterpolator(new FastOutLinearInInterpolator());
                mycycle.setAdapter(animationAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                Log.d("Error", "" + error);
            }
        });

        q.add(request);

    }

    private ArrayList<SingleCity> json(JSONObject response) {
        ArrayList<SingleCity> data = new ArrayList<>();
        try {
            JSONArray array = response.getJSONArray("aapka1");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String problem = object.getString("problem");
                Log.d("problem", problem);
                String num = object.getString("uid");

                if (!num.equals(read())) {
                    SingleCity city = new SingleCity(problem, num);
                    data.add(city);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return data;
    }


    private String read() {
        Boolean isSDPresent = Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED);
        if (isSDPresent) {
            folder = getActivity().getExternalFilesDir("AapkaSujav");
            myfile = new File(folder, "id.txt");
            return readingFromSd(myfile);
        } else {
            return readFromInternal();
        }

    }

    private String readFromInternal() {

        FileInputStream inputStream = null;
        try {
            inputStream = getActivity().openFileInput("uid.txt");
            int read=-1;
            StringBuffer buffer = new StringBuffer();
            while ((read = inputStream.read()) != -1) {
                buffer.append((char) read);

            }
            String ok = buffer.toString();
            return  ok;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readingFromSd(File myfile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(myfile);
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = inputStream.read()) != -1) {
                buffer.append((char) read);

            }
            String ab = buffer.toString();
            return ab;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
