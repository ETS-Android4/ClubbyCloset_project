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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class profile extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static int RESULT_LOAD_IMAGE_PROFILE = 3;
    private static final String FILE_USER = "userdata.txt";
    private static final String FILE_USERIMG = "userimg.txt";
    private static final String FILE_USERVOTE ="uservote.txt";


    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ArrayList<Uri> mArrayUri;

    ImageView bhome, bsearch, badd, bvote, bprofile, blogout, bprofileImg,topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8, v1,v2,v3,v4,v5;

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

        topicImg1 = (ImageView) this.findViewById(R.id.topicimg1);
        topicImg2 = (ImageView) this.findViewById(R.id.topicimg2);
        topicImg3 = (ImageView) this.findViewById(R.id.topicimg3);
        topicImg4 = (ImageView) this.findViewById(R.id.topicimg4);
        topicImg5 = (ImageView) this.findViewById(R.id.topicimg5);
        topicImg6 = (ImageView) this.findViewById(R.id.topicimg6);
        topicImg7 = (ImageView) this.findViewById(R.id.topicimg7);
        topicImg8 = (ImageView) this.findViewById(R.id.topicimg8);
        v1 = (ImageView) this.findViewById(R.id.vote1);
        v2 = (ImageView) this.findViewById(R.id.vote2);
        v3 = (ImageView) this.findViewById(R.id.vote3);
        v4 = (ImageView) this.findViewById(R.id.vote4);
        v5 = (ImageView) this.findViewById(R.id.vote5);

        tvusername= (TextView)this.findViewById(R.id.username);

        ImageView[] imgs = {topicImg1,topicImg2, topicImg3, topicImg4, topicImg5, topicImg6, topicImg7, topicImg8};
        ImageView[] votes = {v1,v2,v3,v4,v5};

        try {
            String[] t =load(FILE_USER).split(";");
            //Toast.makeText(getApplicationContext(), "Scritto   " + load(FILE_USER),Toast.LENGTH_SHORT).show();
            for (int i = 0; i< t.length; i++){
                String[] s = t[i].split(":");
                if (s[0].equals("username")){
                    tvusername.setText(s[1]);
                }
                if(s[0].equals("profileImg")){
                    if(s.length > 1 ) {
                        //Toast.makeText(getApplicationContext(), "in profile img   " + s[1], Toast.LENGTH_SHORT).show();
                        Bitmap bm = BitmapFactory.decodeFile(s[(s.length-1)]);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                        Bitmap conv_bm = getRoundedRectBitmap(rotateImage(s[(s.length-1)],resized), 200);
                        bprofileImg.setImageBitmap(conv_bm);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String[] t =load(FILE_USERVOTE).split(";");
            //Toast.makeText(getApplicationContext(), "Scritto   " + load(FILE_USER),Toast.LENGTH_SHORT).show();
            int j = 0;
            for (int i = t.length-1; i>-1; i--){
                String[] s = t[i].split(":");
                if(s[0].equals("voteSrc")){
                    if(s.length > 2 &&  j<votes.length) {
                        //Toast.makeText(getApplicationContext(), "in profile img   " + s[1], Toast.LENGTH_SHORT).show();
                        Bitmap bm = BitmapFactory.decodeFile(s[(s.length-1)]);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                        Bitmap conv_bm = getRoundedRectBitmap(rotateImage(s[(s.length-1)],resized), 200);
                        votes[j].setImageBitmap(conv_bm);
                        j++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPhotos(FILE_USERIMG, imgs);

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

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(profile.this, badd);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(home.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Add new img")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        } if (item.getTitle().equals("Add new vote")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(i, RESULT_LOAD_VOTE);
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

        for(int i = 0; i<votes.length; i++){
            int j=i+1;
            votes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent voteView = new Intent(profile.this, voteView.class);
                    voteView.putExtra("numb", Integer.toString(j));
                    startActivity(voteView);
                }

            });
        }

    }

    private void setPhotos(String FILE_NAME, ImageView[] imgs) {
        try {
            String[] res = load(FILE_NAME).split(";")[1].split(":");
            for(int i =1 ; i<res.length; i++){
                if(i<imgs.length) {
                    Bitmap bm = BitmapFactory.decodeFile(res[i]);
                    Bitmap rotatedBitmap = rotateImage(res[i], bm);
                    imgs[i-1].setImageBitmap(rotatedBitmap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap rotateImage(String path, Bitmap source) throws IOException {
        Float angle = null;
        ExifInterface ei = new ExifInterface(path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = Float.valueOf(90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = Float.valueOf(180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = Float.valueOf(270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                angle = Float.valueOf(0);
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
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

    //to load img from gallery for img profile
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE_PROFILE ) {
            newProfileImg(resultCode,data);
        }
        else  if (requestCode == RESULT_LOAD_IMAGE) {
            newImg(requestCode,resultCode,data);
        }else if(requestCode == RESULT_LOAD_VOTE) {
            try {
                newVote(requestCode,resultCode,data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newProfileImg(int resultCode, Intent data){
        if (resultCode == RESULT_OK && null != data) {
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
            Bitmap rotatedBitmap=null;
            try {
                rotatedBitmap = rotateImage(picturePath, bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 200, 200, false);
            Bitmap conv_bm = getRoundedRectBitmap(resized, 200);
            bprofileImg.setImageBitmap(conv_bm);
        }

    }

    private void newVote(int requestCode, int resultCode, Intent data) throws IOException {
        if (resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                String paths = "";
                int cout = data.getClipData().getItemCount();
                Toast.makeText(getApplicationContext(), "SIZE  " + cout,Toast.LENGTH_SHORT).show();
                if(cout <= 4) {
                    for (int i = 0; i < cout; i++) {
                        // adding imageuri in array
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
                        paths = paths + picturePath + ":";
                        //Toast.makeText(getApplicationContext(), "LETTO  " + paths,Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                    loadVoteImg(paths, FILE_USERVOTE);
                    Intent voteView = new Intent(profile.this, voteView.class);
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

        }
    }

    public void newImg(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            try {
                changeProfileImg(picturePath, FILE_USERIMG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
        }
    }

    private void loadVoteImg(String picPath, String FILE_NAME) throws IOException {
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
        String t = load(FILE_NAME) + ";voteSrc:" + picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String t =load(FILE_NAME)+":"+picPath;
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(t.getBytes());
        //Toast.makeText(getApplicationContext(), "Scritto   " + t,Toast.LENGTH_SHORT).show();
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}