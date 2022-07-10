package com.sub.dm.onlinefmradio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.jackandphantom.blurimage.BlurImage;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;

import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder>{

    private int[] images;

    //
    private String[] radiourls;
    private String[] radionames;
    public Context context;

    //
    public DataSource.Factory dataSourceFactory;
    public ExtractorsFactory extractorsFactory;
    public static SimpleExoPlayer player;

    public static boolean isPlaying=false;
    public static boolean PlaySavedRadio = false;


    //
    public HorizontalAdapter(int[] images,String[] radionames,String[] radiourls) {
        this.images = images;

        //
        this.radionames = radionames;
        this.radiourls = radiourls;
    }

    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal, viewGroup, false);

        //
        context = viewGroup.getContext();

        return new HorizontalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalAdapter.ViewHolder holder, int position) {

        final int image = images[position];

        //
        final String url = radiourls[position];
        final String radioname = radionames[position];



        holder.imageView.setImageResource(image);


        HomeFragment.PlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckPlayPauseButton();

            }
        });



        //
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playRadio(image, radioname,url);

            }
        });
    }

    private void CheckPlayPauseButton() {
        if(PlaySavedRadio)
        {
            @SuppressLint("WrongConstant")
            SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_APPEND);

            String radioname = sh.getString("radioname","");
            String url = sh.getString("url","");
            int image = sh.getInt("image",0);

            PlaySavedRadio = false;
            isPlaying = true;
            HomeFragment.PlayPauseButton.setImageResource(R.drawable.ic_pause);

            playRadio(image,radioname,url);

        }
        else
        {
            if(isPlaying)
            {
                isPlaying = false;
                HomeFragment.PlayPauseButton.setImageResource(R.drawable.ic_play);
                player.setPlayWhenReady(false);
            }
            else
            {
                isPlaying = true;
                HomeFragment.PlayPauseButton.setImageResource(R.drawable.ic_pause);
                player.setPlayWhenReady(true);
            }
        }

    }

    //
    public void playRadio(int image,String radioname, String url) {
        if(player !=null)
        {
            player.setPlayWhenReady(false);
            player.stop();
            player.seekTo(0);
        }

        //
        player = new SimpleExoPlayer.Builder(context).build();
        dataSourceFactory = new DefaultDataSourceFactory(context.getApplicationContext(), "Exoplayer");
        extractorsFactory = new DefaultExtractorsFactory();

        //
        final MediaSource audioSource = new ProgressiveMediaSource.Factory(dataSourceFactory,extractorsFactory).createMediaSource(Uri.parse(url));
        player.prepare(audioSource);
        player.setPlayWhenReady(true);


        HomeFragment.PlayPauseButton.setImageResource(R.drawable.ic_pause);
        HomeFragment.SmallImageView.setImageResource(image);
        BlurImage.with(context).load(image).intensity(20).Async(true).into(HomeFragment.BlurImageView);
        HomeFragment.RadioName.setText(radioname);

        HomeFragment.SmallImageView.setVisibility(View.VISIBLE);
        HomeFragment.RadioName.setVisibility(View.VISIBLE);
        HomeFragment.Divider.setVisibility(View.VISIBLE);
        HomeFragment.PlayPauseButton.setVisibility(View.VISIBLE);

        HomeFragment.slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        isPlaying = true;

        saveRadioDetails(radioname,url,image);

    }

    private void saveRadioDetails(String radioname, String url, int image) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("state", "1");
        myEdit.putString("radioname", radioname);
        myEdit.putString("url", url);
        myEdit.putInt("image", image);

        myEdit.commit();

    }
    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ViewHolder(final View itemView) {
            super(itemView);
            this.imageView = (ImageView)itemView.findViewById(R.id.imageview);
        }
    }
}