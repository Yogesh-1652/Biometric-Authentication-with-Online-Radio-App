package com.sub.dm.onlinefmradio;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;



import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.jackandphantom.blurimage.BlurImage;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment{

    public static SlidingUpPanelLayout slidingUpPanelLayout;
    public static ImageView PlayPauseButton;
    public static ImageView SmallImageView,BlurImageView,CloseWindow;
    public static TextView RadioName;
    public static RelativeLayout LowerRelativeLayout;
    public static View Divider;

    RelativeLayout upperrelativeLayout,lowerrelativeLayout;
    FrameLayout frameLayout;

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    ImageView volumeIcon;

    int[] radioimagesFirst = {R.drawable.radiomirchi};
    //
    String[] radiourlFirst = {"http://peridot.streamguys.com:7150/Mirchi"};
    //
    String[] radionamesFirst = {"Radio Mirchi"};

    int[] radioimagesSecond = {R.drawable.twoday,
                         R.drawable.radiowow,
                         R.drawable.airfmgold,
                         R.drawable.calmradio,
                         R.drawable.radiobollyfm,
                         R.drawable.radiocity
    };

    //
    String[] radiourlSecond = {"http://peridot.streamguys.com:7150/2Day",
                         "https://stream.zeno.fm/d22wrdbst5quv.mp3",
                         "http://peridot.streamguys.com:7150/Gold",
                         "https://drive.uber.radio/uber/calm/icecast.audio",
                         "http://stream.radiobollyfm.in:8201/;",
                         "https://prclive1.listenon.in/"};

    //
    String[] radionamesSecond = {"2 Day FM",
                           "Vividh Bharti",
                           "Air FM Gold",
                           "Air Live New 24x7",
                           "Radio Bolly FM",
                           "91.1 FM Radio City"

    };

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home,container,false);

        upperrelativeLayout = v.findViewById(R.id.upperRelativeLayout);
        lowerrelativeLayout = v.findViewById(R.id.lowerRelativeLayout);
        frameLayout = v.findViewById(R.id.homeframelayout);

        volumeIcon = v.findViewById(R.id.volumeicon);

        String themeValue = checkThemeValue(v);

        if(themeValue.equals("1"))
        {
            ChangeTheme(themeValue,v);
        }
        else
        {
            ChangeTheme(themeValue,v);
        }

        PlayPauseButton = v.findViewById(R.id.playpause);

        slidingUpPanelLayout = v.findViewById(R.id.sliding_layout);

        CloseWindow  =(ImageView)v.findViewById(R.id.closewindow);
        SmallImageView = (ImageView)v.findViewById(R.id.smallimageview);
        BlurImageView = (ImageView)v.findViewById(R.id.blurimageView);
        RadioName = (TextView)v.findViewById(R.id.radioname);
        LowerRelativeLayout = (RelativeLayout)v.findViewById(R.id.lowerRelativeLayout);

        Divider = (View)v.findViewById(R.id.divider);

        volumeSeekbar = (SeekBar)v.findViewById(R.id.volumeseekbar);

        //
        HorizontalAdapter firstAdapter = new HorizontalAdapter(radioimagesFirst,radionamesFirst,radiourlFirst);
        MultiSnapRecyclerView firstRecyclerView = v.findViewById(R.id.first_recycler_view);
        LinearLayoutManager firstManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        firstRecyclerView.setLayoutManager(firstManager);
        firstRecyclerView.setAdapter(firstAdapter);

        //
        HorizontalAdapter secondAdapter = new HorizontalAdapter(radioimagesSecond,radionamesSecond,radiourlSecond);
        MultiSnapRecyclerView secondRecyclerView =v.findViewById(R.id.second_recycler_view);
        LinearLayoutManager secondManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        secondRecyclerView.setLayoutManager(secondManager);
        secondRecyclerView.setAdapter(secondAdapter);

        checkAppStateDetails(v);

        CloseWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                CloseWindow.setVisibility(View.INVISIBLE);
            }
        });

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

        }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.toString().equals("EXPANDED"))
                {

                    slidingUpPanelLayout.setTouchEnabled(false);

                    Divider.setVisibility(View.INVISIBLE);

                    MainActivity.chipNavigationBar.setVisibility(View.INVISIBLE);

                    RelativeLayout.LayoutParams RelLayoutPlayPauseBtn = new RelativeLayout.LayoutParams(200, 200);
                    RelLayoutPlayPauseBtn.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    RelLayoutPlayPauseBtn.topMargin = 1100;

                    PlayPauseButton.setLayoutParams(RelLayoutPlayPauseBtn);

                    RelativeLayout.LayoutParams RelLayoutRadioName = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelLayoutRadioName.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    RelLayoutRadioName.topMargin = 1000;

                    RadioName.setLayoutParams(RelLayoutRadioName);


                    RelativeLayout.LayoutParams RelLayoutImage = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelLayoutImage.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    RelLayoutImage.topMargin = 300;

                    SmallImageView.setLayoutParams(RelLayoutImage);

                    CloseWindow.setVisibility(View.VISIBLE);

                    BlurImageView.setVisibility(View.VISIBLE);

                    volumeSeekbar.setVisibility(View.VISIBLE);

                    volumeIcon.setVisibility(View.VISIBLE);
                }
                else
                {

                    slidingUpPanelLayout.setTouchEnabled(true);


                    Divider.setVisibility(View.VISIBLE);


                    MainActivity.chipNavigationBar.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams RelLayoutPlayPauseBtn = new RelativeLayout.LayoutParams(170, 170);
                    RelLayoutPlayPauseBtn.addRule(RelativeLayout.ALIGN_PARENT_END);
                    RelLayoutPlayPauseBtn.rightMargin =85;

                    PlayPauseButton.setLayoutParams(RelLayoutPlayPauseBtn);

                    RelativeLayout.LayoutParams RelLayoutRadioName = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RelLayoutRadioName.leftMargin = 180;
                    RelLayoutRadioName.topMargin = 50;

                    RadioName.setLayoutParams(RelLayoutRadioName);

                    RelativeLayout.LayoutParams RelLayoutImage = new RelativeLayout.LayoutParams(130, 130);
                    RelLayoutImage.addRule(RelativeLayout.ALIGN_PARENT_START);
                    RelLayoutImage.leftMargin = 20;
                    RelLayoutImage.topMargin = 25;

                    SmallImageView.setLayoutParams(RelLayoutImage);

                    CloseWindow.setVisibility(View.INVISIBLE);

                    BlurImageView.setVisibility(View.INVISIBLE);

                    volumeSeekbar.setVisibility(View.INVISIBLE);

                    volumeIcon.setVisibility(View.INVISIBLE);
                }
            }
        });

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        try
        {
            audioManager = (AudioManager) getActivity().getSystemService(v.getContext().AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return v;
    }

    private void ChangeTheme(String themeValue,View view) {
        if(themeValue.equals("1"))
        {
            upperrelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme1));
            lowerrelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme1));
            frameLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme1));
        }
        else
        {
            upperrelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme2));
            lowerrelativeLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme2));
            frameLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.theme2));
        }
    }

    private String checkThemeValue(View view) {
        @SuppressLint("WrongConstant")
        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref", MODE_APPEND);

        String value = sh.getString("themeValue", "");
        return value;

    }

    private void checkAppStateDetails(View v) {
        @SuppressLint("WrongConstant")
        SharedPreferences sh = v.getContext().getSharedPreferences("MySharedPref", MODE_APPEND);

        String s1 = sh.getString("state", "");
        String radioname = sh.getString("radioname","");
        String radioRunning = sh.getString("radioRunning","");
        int image = sh.getInt("image",0);

        if(s1.equals("1"))
        {
            SmallImageView.setVisibility(View.VISIBLE);
            RadioName.setVisibility(View.VISIBLE);
            Divider.setVisibility(View.VISIBLE);
            PlayPauseButton.setVisibility(View.VISIBLE);

            SmallImageView.setImageResource(image);
            BlurImage.with(v.getContext()).load(image).intensity(20).Async(true).into(BlurImageView);
            RadioName.setText(radioname);

            PlayPauseButton.setImageResource(R.drawable.ic_play);
            HorizontalAdapter.PlaySavedRadio = true;

        }
        else
        {
            SmallImageView.setVisibility(View.INVISIBLE);
            RadioName.setVisibility(View.INVISIBLE);
            Divider.setVisibility(View.INVISIBLE);
            PlayPauseButton.setVisibility(View.INVISIBLE);
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }

    }

}
