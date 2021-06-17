package com.example.clubbbycloset;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private  static final String FILE_ALLVOTE = "allVote.txt";
    private static final String FILE_ALLUSERS = "allUsersData.txt";

    public static String id;
    public static String imgId;

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ImageView bhome, bsearch, badd, bvote, bprofile, blogout, bprofileImg;

    TextView tvusername;
    GridLayout gridLayout;
    LinearLayout linearLayout, hScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle Extra = getIntent().getExtras();
        String type = Extra.getString("type");
        id = Extra.getString("idProfile");

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);
        blogout = (ImageView)this.findViewById(R.id.logout);
        bprofileImg = (ImageView)this.findViewById(R.id.profile_img);

        tvusername= (TextView)this.findViewById(R.id.username);
        String name =null;
        try {
            String[] t =load(FILE_USER).split(";;");
            //Toast.makeText(getApplicationContext(), "Scritto   " + load(FILE_USER),Toast.LENGTH_SHORT).show();
            for (int i = 0; i< t.length; i++){
                String[] s = t[i].split(";");
                if (s[0].split(":")[1].equals(id)){
                    tvusername.setText(id);
                    name = id;
                    if(s[2].split(":").length > 1 ) {
                        imgId = s[2].split(":")[1];
                        //Toast.makeText(getApplicationContext(), "in profile img   " + s[1], Toast.LENGTH_SHORT).show();
                        Bitmap bm = BitmapFactory.decodeFile(s[2].split(":")[1]);
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                        Bitmap conv_bm = getRoundedRectBitmap(rotateImage(s[2].split(":")[1],resized), 200);
                        bprofileImg.setImageBitmap(conv_bm);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridLayout = (GridLayout) this.findViewById(R.id.grid);
        linearLayout = (LinearLayout) this.findViewById(R.id.linear);
        if (type.equals("0")){
            linearLayout.setVisibility(View.INVISIBLE);
            gridLayout.setVisibility(View.VISIBLE);
            gridLayout.removeAllViews();
            setPhotosGridLayout(FILE_ALLUSERS, gridLayout);
            //setPhotosGridLayout(FILE_USERIMG, gridLayout);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            gridLayout.setVisibility(View.INVISIBLE);
            setPhotosLinearLayuout(FILE_ALLUSERS, linearLayout);
        }

        hScroll = (LinearLayout) this.findViewById(R.id.horizScroll);
        try {
            setVoteBar(FILE_ALLVOTE, hScroll, name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bprofileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_PROFILE);
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(profile.this, home.class);
                home.putExtra("idProfile", id);
                startActivity(home); // takes the user to the signup activity
            }

        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(profile.this, search.class);
                search.putExtra("idProfile", id);
                startActivity(search); // takes the user to the signup activity
            }

        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(profile.this, profile.class);
                profile.putExtra("idProfile", id);
                startActivity(profile); // takes the user to the signup activity
            }

        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(profile.this, vote.class);
                vote.putExtra("idProfile", id);
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

    }

    private void setVoteBar(String fileUservote, LinearLayout hScroll, String username) throws IOException {
        String[] t =load(fileUservote).split(";;");
        for (int i = t.length-1; i>-1; i--) {
            if (t[i].split(";")[0].split(":")[1].equals(username)) {
                ImageView vimg = new ImageView(this);
                String[] s = t[i].split(";")[1].split(":");
                if (s.length > 2) {
                    //Toast.makeText(getApplicationContext(), "in profile img   " + s[1], Toast.LENGTH_SHORT).show();
                    Bitmap bm = BitmapFactory.decodeFile(s[(s.length - 1)]);
                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, false);
                    Bitmap conv_bm = getRoundedRectBitmap(rotateImage(s[(s.length - 1)], resized), 200);
                    vimg.setImageBitmap(conv_bm);
                }

                int finalI = i;
                vimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent voteView = new Intent(profile.this, voteView.class);
                        voteView.putExtra("numb", "1");
                        voteView.putExtra("imgSrc", t[finalI].split(";")[1]);
                        voteView.putExtra("descrSrc", t[finalI].split(";")[2]);
                        voteView.putExtra("votes", t[finalI].split(";")[3]);
                        voteView.putExtra("idProfile", id);

                        startActivity(voteView);
                    }

                });
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 2, 5, 2);
                vimg.setLayoutParams(lp);
                hScroll.addView(vimg);
            }
        }

    }

    private void setPhotosGridLayout(String FILE_NAME, GridLayout grid) {
        try {
            String[] res = load(FILE_NAME).split(";;");
            LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int buttons= res.length;//the number of bottons i have to put in GridLayout
            int buttonsForEveryRow = 2; // buttons i can put inside every single row
            int buttonsForEveryRowAlreadyAddedInTheRow =0; // count the buttons added in a single rows
            int columnIndex=0; //cols index to which i add the button
            int rowIndex=0; //row index to which i add the button
            for(int i=0; i < buttons;i++) {
                if (res[i].split(";")[0].split(":")[0].equals(id)) {
                    String imgSrc = res[i].split(";")[1].split(":")[1];
                    View view = inflater.inflate(R.layout.img_frame, null);
                    ImageView newi = (ImageView) view.findViewById(R.id.newImg);

                    Bitmap bm = BitmapFactory.decodeFile(imgSrc);
                    Bitmap rotatedBitmap = rotateImage(imgSrc, bm);
                    Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 550, 600, false);
                    newi.setImageBitmap(resized);

                    /*if numeroBottoniPerRigaInseriti equals numeroBottoniPerRiga i have to put the other buttons in a new row*/
                    if (buttonsForEveryRowAlreadyAddedInTheRow == buttonsForEveryRow) {
                        rowIndex++; //here i increase the row index
                        buttonsForEveryRowAlreadyAddedInTheRow = 0;
                        columnIndex = 0;
                    }

                    GridLayout.Spec row = GridLayout.spec(rowIndex, 1);
                    GridLayout.Spec colspan = GridLayout.spec(columnIndex, 1);
                    GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
                    LinearLayout f = (LinearLayout) view.findViewById(R.id.frame);
                    f.removeAllViews();
                    grid.addView(newi, gridLayoutParam);

                    buttonsForEveryRowAlreadyAddedInTheRow++;
                    columnIndex++;

                    newi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent profile = new Intent(profile.this, profile.class);
                            profile.putExtra("type", "1");
                            profile.putExtra("idProfile", id);
                            startActivity(profile); // takes the user to the signup activity
                        }

                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPhotosLinearLayuout(String FILE_NAME,  LinearLayout linearLayout) {
        try {
            String[] res = load(FILE_NAME).split(";;");
            LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for(int i=0; i < res.length;i++) {

                if (res[i].split(";")[0].split(":")[0].equals(id)) {
                    String imgSrc = res[i].split(";")[1].split(":")[1];
                    String descSrc[] = res[i].split(";")[2].split(":");
                    View lview = inflater.inflate(R.layout.img_desc_frame, null);

                    ImageView newi = (ImageView) lview.findViewById(R.id.foto);
                    Bitmap bm = BitmapFactory.decodeFile(imgSrc);
                    Bitmap rotatedBitmap = rotateImage(imgSrc, bm);
                    //Bitmap resized = Bitmap.createScaledBitmap(rotatedBitmap, 550, 600, false);
                    newi.setImageBitmap(rotatedBitmap);

                    TextView description = (TextView) lview.findViewById(R.id.description);
                    description.setText(descSrc[1]);
                    TextView location = (TextView) lview.findViewById(R.id.location);
                    location.setText(descSrc[2]);
                    TextView time = (TextView) lview.findViewById(R.id.time);
                    time.setText(descSrc[3]);
                    TextView link = (TextView) lview.findViewById(R.id.link);
                    link.setText(descSrc[4]);

                    newi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent profile = new Intent(profile.this, profile.class);
                            profile.putExtra("type", "0");
                            profile.putExtra("idProfile", id);
                            startActivity(profile); // takes the user to the signup activity
                        }

                    });

                    linearLayout.addView(lview);
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
            imgId = picturePath;
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
                    voteView.putExtra("idProfile", id);
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
                //save(FILE_USERIMG, load(FILE_USERIMG) +"imgSrc:" +  picturePath );
                save(FILE_ALLUSERS, load(FILE_ALLUSERS) + id + ":" + imgId + ";imgSrc:" +  picturePath );
                Intent imgVote = new Intent(profile.this, imgView.class);
                imgVote.putExtra("numb", "0");
                imgVote.putExtra("idProfile", id);
                startActivity(imgVote);
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

    public void save(String FILE_NAME, String text) throws IOException {
        FileOutputStream fos = null;
        fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
        fos.write(text.getBytes());
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeProfileImg(String picPath, String FILE_NAME) throws IOException {
        String t =load(FILE_NAME)+":"+picPath+";;";
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