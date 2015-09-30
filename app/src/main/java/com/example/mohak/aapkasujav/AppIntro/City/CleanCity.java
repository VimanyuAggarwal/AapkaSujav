package com.example.mohak.aapkasujav.AppIntro.City;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohak.aapkasujav.AppIntro.MaterialTab.Tab1;
import com.example.mohak.aapkasujav.AppIntro.MaterialTab.Tab2;
import com.example.mohak.aapkasujav.AppIntro.PostImage;
import com.example.mohak.aapkasujav.AppIntro.RecyclerV.Recycler;
import com.example.mohak.aapkasujav.R;

import org.json.JSONException;
import org.json.JSONObject;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class CleanCity extends AppCompatActivity implements MaterialTabListener {

    static final int ACTIVITY_SELECT_IMAGE = 2;
    MaterialTabHost materialTab;
    String uid2;
    Toolbar toolbar;
    ViewPager viewPager;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_city);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // setting the navigation drawer for swipe
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialTab = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.pager);



        MypagerAdapter adapter = new MypagerAdapter(getSupportFragmentManager());

        //viewPager.setAdapter(new MypagerAdapter(getSupportFragmentManager()));
        // materialTab

        // viewPager.setAdapter(new MyAdapter(manager));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                materialTab.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            materialTab.addTab(
                    materialTab.newTab()
                            .setText(adapter.getText(i))
                            .setTabListener(this)
            );
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Networkalert();
        }

    }





    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

       @Override
    public void onBackPressed() {
        Intent intent = new Intent(CleanCity.this, Recycler.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clean_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.Comment) {
            createalert();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createalert() {

        if (isNetworkAvailable()) {

            Intent intent = new Intent(CleanCity.this, PostImage.class);
            startActivity(intent);
//            final String list[]={"Capture Image", "Choose from Gallery"};
//            LayoutInflater inflater = LayoutInflater.from(this);
//            View view =inflater.inflate(R.layout.alert_comment, null);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setView(view);
//            final EditText editText = (EditText)view.findViewById(R.id.editTextDialogUserInput);
//            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    String ok = editText.getText().toString();
//                    if(!ok.equals(""))
//                    {
//                        clean(ok,uid2);
//                        dialog();
//                    }else
//                    {
//                        Toast.makeText(CleanCity.this,"Cannot be left blank",Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//            }).setNegativeButton("Go Back",null).setItems(list, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                if(list[which].equals("Capture Image"))
//                {
//                    Intent intent = new Intent(CleanCity.this, PostImage.class);
//                    startActivity(intent);
////                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
//                }else if(list[which].equals("Choose from Gallery"))
//                {
//                    Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
//
//                }
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
        } else {
            Networkalert();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {


        } else if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

        }
    }


    public void Networkalert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unable to connect");
        builder.setPositiveButton("Check wifi settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    public class MypagerAdapter extends FragmentStatePagerAdapter {

        public static final int Tab_1 = 0;
        public static final int Tab_2 = 1;
        int icon[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        String ha[] = {"MyPost", "Others"};

        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case Tab_1:
                    frag = Tab1.newInstance("", "");
                    break;
                case Tab_2:
                    frag = Tab2.newInstance("", "");
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public android.graphics.drawable.Drawable getPageIcon(int position) {
            return ResourcesCompat.getDrawable(getResources(), icon[position], null);
        }

        public String getText(int position) {
            return ha[position];
        }
    }
}
