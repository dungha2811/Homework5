/**
 * Dung Ha
 * CIS472
 * MainActivity
 * Dr. Tian
 * Assignment 5
 */
package edu.fontbonne.assignmen5;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    //initialize value
    ImageView image;
    Button play;
    Button stop;
    Button pause;

    Toolbar toolbar;
    MusicService service;
    boolean isBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //handle toolbar
        toolbar = (Toolbar)findViewById(R.id.layout_toolbar);



        //handle the value
        image = (ImageView)findViewById(R.id.im_image);
        play = (Button)findViewById(R.id.btn_play);
        stop = (Button)findViewById(R.id.btn_stop);
        pause = (Button)findViewById(R.id.btn_pause);

        //disable button
        stop.setEnabled(false);
        pause.setEnabled(false);

        //set image for button and imageview
        play.setBackgroundResource(R.drawable.play);
        stop.setBackgroundResource(R.drawable.stop);
        pause.setBackgroundResource(R.drawable.pause);
        image.setImageResource(R.drawable.generic);

        //handle event for play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic();
            }
        });

        //handle event for stop button
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMusic();
            }
        });

        //handle event for pause button
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic();
            }
        });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Log.d("Dung","3");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //switch for the toolbar option menu
        switch (item.getItemId()){
            case R.id.play:
                playMusic();
                break;

            case R.id.pause:
                pauseMusic();
                break;

            case R.id.stop:
                stopMusic();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    //variable to connect the service
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;

            service =binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if(isBound == true){
                isBound =false;
            }

        }
    };


    /**
     * The playMusic() method play music from service, change the image to music image and disable pause and stop button
     */
    public void playMusic(){
        service.playMusic();
        play.setEnabled(false);
        stop.setEnabled(true);
        pause.setEnabled(true);
        image.setImageResource(R.drawable.vietnam);
    }

    /**
     * The stopMusic() method stop the music and disable stop and pause button, it also changes image to generic image
     */
    public void stopMusic(){
        service.stopMusic();
        play.setEnabled(true);
        stop.setEnabled(false);
        pause.setEnabled(false);
        image.setImageResource(R.drawable.generic);
    }

    /**
     * The pauseMusic() method pause the music and disable
     */
    public void pauseMusic(){
        service.pauseMusic();
        play.setEnabled(true);
        stop.setEnabled(true);
        pause.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //bind the service
        Intent intent = new Intent(getApplicationContext(),MusicService.class);
        bindService(intent,myConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unbind service
        if(isBound == true){
            unbindService(myConnection);
            isBound = false;
        }
    }
}
