package com.example.johannes.hellokawan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.MediaController;

import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    static final int IJIN_PAKE_CAMERA = 1;
    ImageView gambarku;
    Button kameraku;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set video
        final VideoView videoku = (VideoView) findViewById(R.id.videoku);
        videoku.setVideoPath("https://www.thenewboston.com/forum/project_files/006_testVideo.mp4");

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoku);
        videoku.setMediaController(mediaController);

        videoku.start();

        //set gambar
        kameraku = (Button) findViewById(R.id.kameraku);
        gambarku = (ImageView) findViewById(R.id.gambarku);


        //ilangin tombol kalo ga punya kamera
        if(!punyaKamera()){
            kameraku.setEnabled(true);
        }

    }

    //cek kameranya ada apa ga
    private boolean punyaKamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void bukaKamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //simpan hasil ke ActivityResult
        startActivityForResult(intent, IJIN_PAKE_CAMERA);
    }

    //untuk dapat return dari hasil kamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == IJIN_PAKE_CAMERA && resultCode == RESULT_OK){
            //get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            Bitmap filter = BitmapFactory.decodeResource(getResources(), R.drawable.oldschool);

            Drawable[] layers = new Drawable[2];
            layers[0] = new BitmapDrawable(getResources(), photo);
            layers[1] = new BitmapDrawable(getResources(), filter);
            LayerDrawable layerDrawable = new LayerDrawable(layers);

            gambarku.setImageDrawable(layerDrawable);

        }
    }
}