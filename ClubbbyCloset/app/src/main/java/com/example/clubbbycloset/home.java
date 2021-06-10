package com.example.clubbbycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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


public class home extends AppCompatActivity {
    ImageView bhome, bsearch, badd, bvote, bprofile;
    TextView tv1,tv2,tv3;
    private static final String FILE_ALLUSERS = "allUsersData.txt";

    private static final String FILE_USERVOTE ="uservote.txt";
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VOTE = 2;
    private static final String FILE_USERIMG = "userimg.txt";

    private String picturePath = "";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ArrayList<Uri> mArrayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String res = load(FILE_ALLUSERS);
        String[] l = res.split(";");
        String[] users =new String[l.length];
        mArrayUri = new ArrayList<Uri>();

        for(int i = 0; i<l.length; i++){
            users[i] = l[i].split(":")[0];
            //Toast.makeText(getApplicationContext(), users[i], Toast.LENGTH_SHORT).show();
        }

        bhome = (ImageView) this.findViewById(R.id.home);
        bsearch = (ImageView) this.findViewById(R.id.search);
        badd = (ImageView) this.findViewById(R.id.add);
        bvote = (ImageView) this.findViewById(R.id.vote);
        bprofile = (ImageView) this.findViewById(R.id.profile);

        tv1 = (TextView)this.findViewById(R.id.home1);
        tv1.setText(users[new Random().nextInt(l.length)]);

        tv2 = (TextView)this.findViewById(R.id.home2);
        tv2.setText(users[new Random().nextInt(l.length)]);

        tv3 = (TextView)this.findViewById(R.id.home3);
        tv3.setText(users[new Random().nextInt(l.length)]);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(home.this, usersProfile.class);
                profilo.putExtra("user", tv1.getText());
                startActivity(profilo);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(home.this, usersProfile.class);
                profilo.putExtra("user", tv2.getText());
                startActivity(profilo);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profilo = new Intent(home.this, usersProfile.class);
                profilo.putExtra("user", tv3.getText());
                startActivity(profilo);
            }
        });

        bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(home.this, home.class);
                startActivity(home);
            }
        });

        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(home.this, search.class);
                startActivity(search);
            }
        });

        bprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(home.this, profile.class);
                profile.putExtra("type", "0");
                startActivity(profile);
            }
        });

        bvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vote = new Intent(home.this, vote.class);
                startActivity(vote); // takes the user to the signup activity
            }

        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(home.this, badd);
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
                    Intent voteView = new Intent(home.this, voteView.class);
                    voteView.putExtra("numb", "0");
                    startActivity(voteView);
                }
            }

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
                save(FILE_USERIMG, load(FILE_USERIMG) +"imgSrc:" +  picturePath );
                Intent imgVote = new Intent(home.this, imgView.class);
                imgVote.putExtra("numb", "0");
                startActivity(imgVote);
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
