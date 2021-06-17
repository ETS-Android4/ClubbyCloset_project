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
import android.widget.EditText;
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

public class imgView extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile, f;
    EditText edDesc, edLoc, edTime, edLink;
    TextView bsave;

    public static String id;

    private static final String FILE_USERVOTE ="uservote.txt";
    private static final String FILE_ALLUSERS = "allUsersData.txt";

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static final String FILE_USERIMG = "userimg.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view);

            Bundle Extra = getIntent().getExtras();
            String numb = Extra.getString("numb");
            id = Extra.getString("idProfile");

            bhome = (ImageView) this.findViewById(R.id.home);
            bsearch = (ImageView) this.findViewById(R.id.search);
            badd = (ImageView) this.findViewById(R.id.add);
            bvote = (ImageView) this.findViewById(R.id.vote);
            bprofile = (ImageView) this.findViewById(R.id.profile);
            f= (ImageView) this.findViewById(R.id.foto);
            edDesc = (EditText) this.findViewById(R.id.description);
            edLoc = (EditText) this.findViewById(R.id.location);
            edTime = (EditText) this.findViewById(R.id.time);
            edLink = (EditText) this.findViewById(R.id.link);
            bsave = (TextView) this.findViewById(R.id.save);

            EditText[] descri = {edDesc,edLoc,edTime,edLink};

            setLayout(FILE_ALLUSERS, f, descri, Integer.parseInt(numb));

            bhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent home = new Intent(imgView.this, home.class);
                    home.putExtra("idProfile", id);
                    startActivity(home);
                }
            });

            bsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent search = new Intent(imgView.this, search.class);
                    search.putExtra("idProfile", id);
                    startActivity(search);
                }
            });

            bprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profile = new Intent(imgView.this, profile.class);
                    profile.putExtra("type", "0");
                    profile.putExtra("idProfile", id);
                    startActivity(profile);
                }
            });

            bvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent vote = new Intent(imgView.this, vote.class);
                    vote.putExtra("idProfile", id);
                    startActivity(vote); // takes the user to the signup activity
                }

            });

            badd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(imgView.this, badd);
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

            bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String toAdd = ";descrizione:" + edDesc.getText().toString() + ":" + edLoc.getText().toString() + ":" + edTime.getText().toString() + ":"+edLink.getText().toString()+";;";
                    //save(FILE_USERIMG, load(FILE_USERIMG) + toAdd);
                    save(FILE_ALLUSERS, load(FILE_ALLUSERS) + toAdd);
                    //Toast.makeText(getApplicationContext(), "AFTER ADD:   " + load(FILE_USERIMG), Toast.LENGTH_SHORT).show();

                }catch (IOException e) {
                    e.printStackTrace();
                }
                Intent profile = new Intent(imgView.this, profile.class);
                profile.putExtra("idProfile", id);
                profile.putExtra("type", "0");
                startActivity(profile);
            }
        });


    }

    private void setLayout(String FILE_NAME, ImageView f, EditText[]desc, int numb) {
        String[] ret = load(FILE_NAME).split(";;");
        String imgSrc;
        if(numb == 0 ){
            imgSrc= ret[ret.length-1].split(";")[1].split(":")[1];
        }else{
            imgSrc= ret[ret.length-numb].split(";")[1].split(":")[1];
            String[] descSrc = ret[ret.length-numb].split(";")[2].split(":");
            for (int i = 0 ; i<desc.length; i++){
                desc[i].setEnabled(false);
                desc[i].setText(descSrc[i+1]);
            }
        }
        //Toast.makeText(getApplicationContext(), "IN FILE  " + imgSrc ,Toast.LENGTH_SHORT).show();
        Bitmap bm = BitmapFactory.decodeFile(imgSrc);
        Bitmap rotatedBitmap = null;
        try {
            rotatedBitmap = rotateImage(imgSrc, bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        f.setImageBitmap(rotatedBitmap);
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

    //to load img from gallery
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        verifyStoragePermissions(this);
        if (requestCode == RESULT_LOAD_IMAGE) {
            newImg(requestCode,resultCode,data);
        }else if(requestCode == RESULT_LOAD_VOTE) {
            try {
                newVote(requestCode,resultCode,data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newVote(int requestCode, int resultCode, Intent data) throws IOException {
        if (resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                String paths = "";
                int cout = data.getClipData().getItemCount();
                //Toast.makeText(getApplicationContext(), "SIZE  " + cout,Toast.LENGTH_SHORT).show();
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
                    saveVoteImg(paths, FILE_USERVOTE);
                    Intent voteView = new Intent(imgView.this, voteView.class);
                    voteView.putExtra("type", "0");
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

        }
    }

    private void saveVoteImg(String picPath, String FILE_NAME) throws IOException {
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
                save(FILE_USERIMG, load(FILE_USERIMG) +"imgSrc:" +  picturePath + ";");
                Intent imgVote = new Intent(imgView.this, imgView.class);
                imgVote.putExtra("type", "0");
                imgVote.putExtra("numb", "0");
                startActivity(imgVote);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursor.close();
        }
    }

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

}