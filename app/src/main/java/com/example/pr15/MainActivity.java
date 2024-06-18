package com.example.pr15;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        fileName = Environment.getExternalStorageDirectory(Environment.DIRECTORY_DOCUMENTS) + "/records.3gpp";
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void releaseRecorder(){
        if(mediaRecorder != null){
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    public void recordStart(View view){
        try{
            releaseRecorder();
            File outFile = new File(fileName);
            if(outFile.exists()) outFile.delete();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);
            mediaRecorder.prepare();
            mediaRecorder.start();
            button.setEnabled(true);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void recordStop(View view){
        if(mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }
    private void releasePlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void playStart(View view){
        try{
        releasePlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(fileName);
        mediaPlayer.prepare();
        mediaPlayer.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void playStop(View view){
        if(mediaPlayer != null)
            mediaPlayer.stop();
    }

}