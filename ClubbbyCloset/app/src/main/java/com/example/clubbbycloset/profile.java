package com.example.clubbbycloset;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class profile extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String FILE_USER = "userdata.txt";
    private static final String FILE_IMG = "images.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ImageView bhome, bsearch, badd, bvote, bprofile, blogout, bprofileImg, topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8;
    TextView tvusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        blogout = (ImageView)this.findViewById(R.id.logout);
        bprofileImg = (ImageView)this.findViewById(R.id.profile_img);

        tvusername= (TextView)this.findViewById(R.id.username);
        try {
            String[] t =load(FILE_USER).split(";");
            Toast.makeText(getApplicationContext(), "Scritto   " + load(FILE_USER),Toast.LENGTH_SHORT).show();
            for (int i = 0; i< t.length; i++){
                String[] s = t[i].split(":");
                if (s[0].equals("username")){
                    tvusername.setText(s[1]);
                }
                if(s[0].equals("profileImg")){
                    if(s.length > 1 ) {
                        Toast.makeText(getApplicationContext(), "in profile img   " + s[1], Toast.LENGTH_SHORT).show();
                        Bitmap bm = BitmapFactory.decodeFile(s[1]);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                        Bitmap conv_bm = getRoundedRectBitmap(resized, 200);
                        bprofileImg.setImageBitmap(conv_bm);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setImages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bprofileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(profile.this, home.class);
                startActivity(home); // takes the user to the signup activity
            }

        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(profile.this, search.class);
                startActivity(search); // takes the user to the signup activity
            }

        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(profile.this, profile.class);
                startActivity(profile); // takes the user to the signup activity
            }

        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(profile.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(profile.this, login.class);
                startActivity(login); // takes the user to the signup activity
            }

        });
    }

    public String load(String FILE_NAME) throws IOException {
        FileInputStream fis = null;
        fis = openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;
        while ((text = br.readLine()) != null) {
            if(text!= null){
                return text;
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void setImages() throws IOException {
        String resd = load(FILE_IMG).split(";")[0].split(":")[1];
        String resu = load(FILE_IMG).split(";")[1].split(":")[1];
        String aux = resd + "/" + resu;
        String[] imgSrc = aux.split("/");
        int size = imgSrc.length;
        if (imgSrc.length >=8){
            //Toast.makeText(getApplicationContext(), "imags" + imgSrc[0] + "topics " + topic[0], Toast.LENGTH_SHORT).show();
            topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
            int id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg1.setBackgroundResource(id);

            topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg2.setBackgroundResource(id);

            topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg3.setBackgroundResource(id);

            topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg4.setBackgroundResource(id);

            topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg5.setBackgroundResource(id);

            topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg6.setBackgroundResource(id);

            topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg7.setBackgroundResource(id);

            topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
            id = getResources().getIdentifier(imgSrc[new Random().nextInt(size)],"drawable", "com.example.clubbbycloset");
            topicImg8.setBackgroundResource(id);

        }

    }

    //to load img from gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            try {
                changeProfileImg(picturePath,FILE_USER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
            Bitmap conv_bm = getRoundedRectBitmap(resized, 200);
            bprofileImg.setImageBitmap(conv_bm);
        }
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 100, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    //to give the permission for load img
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void changeProfileImg(String picPath, String FILE_NAME) throws IOException {
        String t =load(FILE_USER)+picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}