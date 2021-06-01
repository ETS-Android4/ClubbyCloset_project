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
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class voteView extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile , f1, f2;

    private static final String FILE_VOTE ="uservote.txt";

    private static int RESULT_LOAD_IMAGE = 1;
    private static final String FILE_USERIMG = "userimg.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view);


        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        f1= (ImageView) this.findViewById(R.id.foto1);
        f2= (ImageView) this.findViewById(R.id.foto2);
        ImageView[] imgs = {f1, f2};
        setPhotosVote(FILE_VOTE, imgs);
        /*String[] imgSrc = load(FILE_VOTE).split(";");
        Toast.makeText(getApplicationContext(), "LETTO 0  " + imgSrc[0],Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "LETTO 0 : 1  " + imgSrc[0].split(":")[1],Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "LETTO 0 : 2  " + imgSrc[0].split(":")[2],Toast.LENGTH_SHORT).show();*/



        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(voteView.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(voteView.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(voteView.this, profile.class);
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(voteView.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(voteView.this, badd);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(home.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Add new img")){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

    }

    public String load(String FILE_NAME) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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

    private void setPhotosVote(String FILE_NAME, ImageView[] imgs) {
        try {
            String[] res = load(FILE_NAME).split(";")[1].split(":");
            /*Toast.makeText(getApplicationContext(), "LETTO 1  " + res[1],Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "LETTO 2  " + res[2],Toast.LENGTH_SHORT).show();
            Bitmap bm = BitmapFactory.decodeFile(res[1]);
            Bitmap rotatedBitmap = rotateImage(res[1], bm);
            imgs[0].setImageBitmap(rotatedBitmap);

            bm = BitmapFactory.decodeFile(res[2]);
            rotatedBitmap = rotateImage(res[2], bm);
            imgs[1].setImageBitmap(rotatedBitmap);*/

            for(int i = 1 ; i<res.length; i++){
                if(i<=imgs.length) {
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

    /*to load img from gallery
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
                changeProfileImg(picturePath,FILE_USERIMG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
        }
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
        //Toast.makeText(getApplicationContext(), "LETTO  " + load(FILE_NAME),Toast.LENGTH_SHORT).show();
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
    }*/

}
