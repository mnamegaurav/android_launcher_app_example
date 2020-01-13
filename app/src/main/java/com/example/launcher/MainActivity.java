package com.example.launcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    Button callButton,smsButton,emailButton,cameraButton,photosButton;
    ImageView img;
    private static final int CAPTURED_ID = 11;
    private static final int SELECTED_ID = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callButton = (Button)findViewById(R.id.call);
        smsButton = (Button)findViewById(R.id.sms);
        emailButton = (Button)findViewById(R.id.email);
        cameraButton = (Button)findViewById(R.id.camera);
        photosButton = (Button)findViewById(R.id.photos);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri num = Uri.parse("tel:");
                Intent callIntent = new Intent(Intent.ACTION_DIAL,num);
                if(callIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(callIntent);
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no App",Toast.LENGTH_LONG).show();
                }
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("sms:"));
//                smsIntent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                if(smsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(smsIntent);
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no App to launch",Toast.LENGTH_LONG).show();
                }
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"));
//                emailIntent.addCategory(Intent.CATEGORY_APP_EMAIL);
                if(emailIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(emailIntent);
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no App to launch",Toast.LENGTH_LONG).show();
                }
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(cameraIntent,CAPTURED_ID);
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no App to launch",Toast.LENGTH_LONG).show();
                }
            }
        });

        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photosIntent = new Intent(Intent.ACTION_PICK);
                photosIntent.setType("image/*");
                if (photosIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(photosIntent,SELECTED_ID);
                }
                else{
                    Toast.makeText(MainActivity.this,"There is no App to launch",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        img =  findViewById(R.id.imageView);
        if (requestCode == CAPTURED_ID && resultCode==RESULT_OK) {
            Bitmap photo_captured = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo_captured);
        }
        else if (requestCode == SELECTED_ID && resultCode==RESULT_OK) {
            Uri uri = data.getData();
            img.setImageURI(uri);
        }
        else{
            img.setImageBitmap(null);
        }
    }

}
