package com.sub.dm.onlinefmradio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {


    //
    public static ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        chipNavigationBar = (ChipNavigationBar)findViewById(R.id.bottom_nav);

        //

        if (savedInstanceState == null)
        {
            chipNavigationBar.setItemSelected(R.id.home,true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,homeFragment)
                    .commit();
        }


        //
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i)
                {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.settings:
                        fragment = new SettingFragment();
                        break;

                }
                if (fragment!=null)
                {
                    //no
                    StopRadio();

                    //yea
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .commit();
                }
            }
        });
    }

    private void StopRadio() {

        if(HorizontalAdapter.isPlaying)
        {
            HorizontalAdapter.player.setPlayWhenReady(false);
        }

    }

    @Override
    public void onBackPressed() {

    }
}
