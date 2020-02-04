package com.example.medicinelist.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.medicinelist.R;
//import com.example.medicine.controller.Controller;
import com.example.medicinelist.entity.Patients;
import com.example.medicinelist.entity.Therapies;
import com.example.medicinelist.entity.Therapyphotos;
//import com.example.medicinelist.entity.Therapyphotos;


public class TherapyEdit extends AppCompatActivity  implements View.OnClickListener {
    final String LOG_TAG = "myLogs";
    private static final int ADD_PROCESS = 1;
    private static final int EDIT_PROCESS = 2;
    public static final int MY_REQUEST_READ_GALLERY = 13;
    public static final int MY_REQUEST_WRITE_GALLERY = 14;
    public static final int MY_REQUEST_GALLERY = 15;
    long therapy_Id, pat_id;
    int ACTION_PROCESS;
    Button btnSaveTher,btnFVDate,btnPhoto, btnPhotoGallery;
    EditText etText, etAnalis, etDateConsult;
    TableLayout tableLayout;
    LinearLayout linearLayout6;
    private List<Therapyphotos> therapyPhotosList = new ArrayList<>();
    private RelativeLayout relativeLayout;
    //Controller controller;
    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 01;
    int myDay = 01;
    Calendar dateAndTime=Calendar.getInstance();
    private final int Pick_image = 1;

    /**
     * Cursor used to access the results from querying for images on the SD card.
     */
    private Cursor cursor;
    /*
     * Column index for the Thumbnails Image IDs.
     */
    private int columnIndex;

    File directory;
    final int TYPE_PHOTO = 1;
    final int TYPE_VIDEO = 2;

    final int REQUEST_CODE_PHOTO = 1;
    final int REQUEST_CODE_VIDEO = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    SurfaceView sfView;
    final String TAG = "myLogs";

    ImageView ivPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_edit);

        //controller =  new Controller(this);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(this);

        btnPhotoGallery = (Button) findViewById(R.id.btnPhotoGallery);
        btnPhotoGallery.setOnClickListener(this);




        btnFVDate = (Button) findViewById(R.id.btnFVDate);

        etText = (EditText) findViewById(R.id.etText);
        etAnalis = (EditText) findViewById(R.id.etAnalis);
        etAnalis.setOnClickListener(this);
        Intent intent = getIntent();
        therapy_Id = intent.getExtras().getLong("therapy_Id");
        pat_id = intent.getExtras().getLong("pat_id");

        //ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        //ivPhoto.setOnClickListener(this);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //linearLayout6 = (LinearLayout) findViewById(R.id.linearLayout6);
        //relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        ACTION_PROCESS = ADD_PROCESS;
        if (therapy_Id!=0) {
            ACTION_PROCESS = EDIT_PROCESS;
            try {
                onLoadInfo(therapy_Id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setEditTextEnabled(false);
        }
        createDirectory();
        /*try {
            getOutputImgFromDbs();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            try {
                addOrEditPation(ACTION_PROCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
        if (id == R.id.menu_edit) {
            setEditTextEnabled(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEditTextEnabled(boolean flag){
        etText.setEnabled(flag);
        etAnalis.setEnabled(flag);
        btnFVDate.setEnabled(flag);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPhoto:
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
                //startActivityForResult(intent, REQUEST_CODE_PHOTO);
                //dispatchTakePictureIntent();
                break;
           //case R.id.ivPhoto:
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(etAnalis.getText().toString())));
                //ivPhoto.setImageDrawable(Drawable.createFromPath(etAnalis.getText().toString()));
               // break;
            case R.id.btnPhotoGallery:
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
                //startActivityForResult(intent, REQUEST_CODE_PHOTO);
                selectPhotoFromGallery();
                break;
        }
    }

    private void selectPhotoFromGallery(){
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(photoPickerIntent, Pick_image);
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        Therapyphotos therapyPhotos = new Therapyphotos(therapy_Id, currentPhotoPath);
        therapyPhotosList.add(therapyPhotos);
        TableRow tr = new TableRow(this);



        tr.addView(getImgFromPath(currentPhotoPath));
        //ivPhoto.setImageDrawable(Drawable.createFromPath(currentPhotoPath));
        //etAnalis.setText(currentPhotoPath);
        tableLayout.addView(tr);
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");


                        //therapyPhotosList.add(therapyPhotos);
                        if (obj instanceof Bitmap) {

                            Bitmap bitmap = (Bitmap) obj;


                            /*Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);

                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }

        /*if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Video uri: " + intent.getData());
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }*/

    private ImageView getImgFromPath(String path){
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        imgView.setImageDrawable(Drawable.createFromPath(path));

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imgView.getLayoutParams();
        layoutParams.width = 120;
        layoutParams.height = 120;
        imgView.setTag(path);
        imgView.setLayoutParams(layoutParams);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onClick - " +v.getTag().toString());
                Intent intent = new Intent(TherapyEdit.this, WebViewActivity.class);
                intent.putExtra("fileName", v.getTag().toString());
                startActivity(intent);
            }
        });


        return imgView;
    }

    //Обрабатываем результат выбора в галерее:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Therapies therapies = Therapies.findById(Therapies.class, therapy_Id);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        Log.d(LOG_TAG,"Intent.ACTION_SEND_MULTIPLE - "+Intent.ACTION_SEND_MULTIPLE);
                        Log.d(LOG_TAG,"imageReturnedIntent.getAction() - "+imageReturnedIntent.getAction());
                        Log.d(LOG_TAG,"Intent.EXTRA_STREAM - "+imageReturnedIntent.hasExtra(Intent.EXTRA_STREAM));
                        ClipData clipData = imageReturnedIntent.getClipData();
                        TableRow tr = new TableRow(this);
                        File source;
                        if (clipData==null){
                            Uri uri = imageReturnedIntent.getData();
                            source = new File(getRealPathFromURI_API11to18(this, uri));
                            Therapyphotos therapyPhotos = new Therapyphotos(source.getAbsolutePath(), therapies);
                            therapyPhotos.save();

                            tr.addView(getImgViewFromUri(uri));
                        }else if (clipData.getItemCount()>0) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                source = new File(getRealPathFromURI_API11to18(this, imageUri));
                                File dest = createImageFile();
                                Log.d(LOG_TAG, "dest - " + dest.getAbsolutePath());
                                Log.d(LOG_TAG, "source - " + source.getAbsolutePath());
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                /*}else {
                                    copyFile(source, dest);
                                }*/
                                }
                                Therapyphotos therapyPhotos = new Therapyphotos(source.getAbsolutePath(), therapies);
                                therapyPhotos.save();
                                //therapyPhotosList.add(therapyPhotos);

                                tr.addView(getImgViewFromUri(imageUri));
                            }

                            Log.d(LOG_TAG, "clipData.getItemCount() - " + clipData.getItemCount());
                        }
                        tableLayout.addView(tr);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }
    private ImageView getImgViewFromUri(Uri imageUri) throws IOException {
        Bitmap selectedImage = getBitmapFromUri(imageUri);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        imgView.setImageBitmap(selectedImage);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imgView.getLayoutParams();
        layoutParams.width = 105;
        layoutParams.height = 105;
        imgView.setLayoutParams(layoutParams);
        return imgView;
    }

    private void getOutputImgFromDbs() throws IOException {

        //String str = "/storage/emulated/0/viber/media/Viber Images/IMG-a355c8e449a0f81a0596a5d08600b31b-V.jpg";
        TableRow tr = new TableRow(this);
        //tr.addView(getImgFromPath(str));
        boolean rows = true;
            for (int i = 0; i < therapyPhotosList.size(); i++) {
                //if ((i % 5 == 0 && i>0) || i+1 >= therapyPhotosList.size()) {
                if (i % 5 == 0 && i>0) {
                    tableLayout.addView(tr);
                    tr = new TableRow(this);
                }
                Log.d(LOG_TAG, "therapyPhotosList.get(i).getPhoto() - " + therapyPhotosList.get(i).getPath());
                tr.addView(getImgFromPath(therapyPhotosList.get(i).getPath()),100,150);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tr.setTag(therapyPhotosList.get(i).getPath());
            }
        tableLayout.addView(tr);

    }





    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhoto.setImageBitmap(imageBitmap);
        }
    }
    */
    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            case TYPE_VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".mp4");
                break;
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    public void setDateFV(View v) {
        new DatePickerDialog(TherapyEdit.this, dFV,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener dFV=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear+1;
            myDay = dayOfMonth;
            btnFVDate.setText("" + myDay + "." + myMonth + "." + myYear);
            //setInitialDateTime();
        }
    };



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
/*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d(TAG, "storageDir = " + storageDir);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String datetodbformat(Date date) {
        String retval = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy"); //dlya sortirovki
        if (date == null) {
            return retval;
        }
        retval = sdf.format(date);
        return retval;
    }

    private void addOrEditPation(int flag) throws IOException {
        Log.d(LOG_TAG,"Фото - "+currentPhotoPath);
        String therapyText, analyses, dateConsult, memo;
        Patients patient = Patients.findById(Patients.class, pat_id);
        Calendar calendar;
        Date dateCons;
        therapyText = etText.getText().toString();
        analyses = currentPhotoPath;
        memo = etAnalis.getText().toString();
        dateConsult = btnFVDate.getText().toString().replace(".","/");

        calendar = new GregorianCalendar(Integer.parseInt(dateConsult.split("/")[2]),
                Integer.parseInt(dateConsult.split("/")[1])-1,Integer.parseInt(dateConsult.split("/")[0]));
        dateCons = calendar.getTime();

        switch (flag) {
            case ADD_PROCESS:
                Therapies therapies = new Therapies(therapyText, dateConsult, patient);
                therapies.setMemo(memo);
                therapies.setPhoto(analyses);
                therapies.save();
                //controller.addTherapyIntoPatient(pat_id, new Therapy(dateCons, therapyText, analyses, memo));
                break;
            case EDIT_PROCESS:
                //Therapies therapy = controller.getTherapy(therapy_Id);
                Therapies therapy = Therapies.findById(Therapies.class, therapy_Id);
                therapy.setDateConsult(dateConsult);
                therapy.setTherapy(therapyText);
                therapy.setPhoto(currentPhotoPath);
                therapy.setMemo(memo);
                therapy.save();
                //therapy.setTherapyPhotos(therapyPhotosList);
                //getOutputImgFromDbs();
                Log.d(LOG_TAG,"-------Edit Therapy------------");
                //controller.updateTherapy(therapy);
                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(LOG_TAG,"requestCode = " + requestCode);
        switch (requestCode) {
            case 14:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    /*try {
                        //getOutputImgFromDbs();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                } else {
                    // permission denied
                }
                return;
        }
    }
    private void onLoadInfo(long therapy_Id) throws IOException {
        Log.d(LOG_TAG,"-------Edit Therapy------------");
        Therapies therapy = Therapies.findById(Therapies.class, therapy_Id);
        Log.d(LOG_TAG,therapy.toString());
        etText.setText(therapy.getTherapy());
        etAnalis.setText(therapy.getMemo());
        currentPhotoPath  = therapy.getPhoto();
        therapyPhotosList = therapy.getTherapyphotos();
        //therapyPhotosList = controller.getFullListTherapyPhotos(therapy_Id);
       // Log.d(LOG_TAG,"therapyPhotosList = " + therapyPhotosList.toString() );
       // if (currentPhotoPath != null && !currentPhotoPath.equals(""))
       //     ivPhoto.setImageDrawable(Drawable.createFromPath(currentPhotoPath));
        //etDateConsult.setText(datetodbformat(therapy.getDateConsult()));
       // int permissionCheck = ContextCompat.checkSelfPermission(TherapyEdit.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
       // Log.d(LOG_TAG,"permissionCheck = " + permissionCheck );
       /// Log.d(LOG_TAG,"PackageManager.PERMISSION_GRANTED = " + PackageManager.PERMISSION_GRANTED );
       // if (permissionCheck!=PackageManager.PERMISSION_GRANTED){
         //   ActivityCompat.requestPermissions(TherapyEdit.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_WRITE_GALLERY);
       // }
        //else{
            getOutputImgFromDbs();
       // }
        /*if (ActivityCompat.checkSelfPermission(TherapyEdit.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TherapyEdit.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else{
            getOutputImgFromDbs();
        }
*/

        btnFVDate.setText(therapy.getDateConsult());
        Log.d(LOG_TAG,"-------Edit Therapy end------------");
    }
}
