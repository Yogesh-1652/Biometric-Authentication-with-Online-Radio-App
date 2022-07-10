package com.sub.dm.onlinefmradio;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    RelativeLayout settingRelativeLayout;
    ImageView ThemeImageView;


    String themeValue="0";

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_setting,container,false);

        settingRelativeLayout = view.findViewById(R.id.settingRL);
        ThemeImageView = view.findViewById(R.id.theme);

        themeValue = checkThemeValue(view);

        if(themeValue.equals("1"))
        {
            ThemeImageView.setImageResource(R.drawable.ic_toggle_on);
            ChangeTheme(themeValue,view);
        }
        else
        {
            ThemeImageView.setImageResource(R.drawable.ic_toggle_off);
            ChangeTheme(themeValue,view);
        }

        ThemeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(themeValue.equals("1"))
                {
                    ThemeImageView.setImageResource(R.drawable.ic_toggle_off);
                    themeValue = "0";
                    ChangeTheme(themeValue,view);
                    saveThemeValue(themeValue,view);
                }
                else
                {
                    ThemeImageView.setImageResource(R.drawable.ic_toggle_on);
                    themeValue = "1";
                    ChangeTheme(themeValue,view);
                    saveThemeValue(themeValue,view);
                }
            }
        });

        //



        return view;
    }


    private void ChangeTheme(String themeValue,View view) {
        if(themeValue.equals("1"))
        {
            settingRelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme1));
        }
        else
        {
            settingRelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme2));
        }
    }

    private String checkThemeValue(View view) {
        @SuppressLint("WrongConstant")
        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref", MODE_APPEND);

        String value = sh.getString("themeValue", "");
        return value;

    }

    private void saveThemeValue(String themeValue, View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("themeValue",themeValue);
        myEdit.commit();

    }


}
