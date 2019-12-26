package com.icspl.createsoc.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.BuildConfig;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.createsoc.utils.FileManager;
import com.icspl.kartik.myapplication.utils.Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static java.sql.DriverManager.println;

public class Notice extends AppCompatActivity {
    public EditText et_notice;
    public ImageView btn_noticedoc,btn_noticecam,btn_sendnotice;
    private Uri imageToUploadUri;
    private int IMAGE_REQUEST_CODE = 2103;
    private int DOCCUMENT_REQUEST_CODE = 2102;
    private String document,camera;
    File imageBtnFile;
    Toolbar toolbar;
    TextView back_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        toolbar=findViewById(R.id.toolbar_notice);
        back_textview=findViewById(R.id.back_arrow_notice);
        setSupportActionBar(toolbar);
        back_textview.setText(toolbar.getTitle());
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_sendnotice=findViewById(R.id.btn_sendnotice);
        btn_noticecam=findViewById(R.id.btn_noticecam);
        btn_noticedoc=findViewById(R.id.btn_noticedoc);
        et_notice=findViewById(R.id.et_notice);
        btn_noticecam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                pickimage();
            }
        });
        btn_noticedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picDocuments();
            }
        });
        btn_sendnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_notice.getText().toString().equals(""))
                {
                    Toast.makeText(Notice.this, "Please Enter some value", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(et_notice.getText().toString().matches("[0-9]+"))
//                {
                    Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Common.Companion.getBASE_URL()).addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit= builder.build();

                    ApiService apiService= retrofit.create(ApiService.class);

                    //fid is static
                   // apiService.addnotice(fid,et_notice.getText().toString()).enqueue(new Callback<List<ServerResponseModel>>() {
                apiService.addnotice(1,et_notice.getText().toString()).enqueue(new Callback<List<ServerResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ServerResponseModel>> call, Response<List<ServerResponseModel>> response) {
                            if(response.isSuccessful()&&response.body().size()>0)
                            {
                                Toast.makeText(Notice.this, "notice added ", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Notice.this, "response error ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ServerResponseModel>> call, Throwable t) {
                            Toast.makeText(Notice.this, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    });


//                }
//                else {
//                    Toast.makeText(Notice.this, "Enter proper number", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }
    public void picDocuments() {

        try {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            //                        Uri uri = Uri.parse(Environment.getRootDirectory().getAbsolutePath());
            //                        fileIntent.setData(uri);
            fileIntent.setType("application/pdf")  ;
            String[] extraMimeTypes =new String[]
                    {
                            "audio/*",
                            "video/*",
                            "application/*",
                            "application/pdf",
                            "application/msword",
                            "application/vnd.ms-powerpoint",
                            "application/vnd.ms-excel",
                            "application/zip",
                            "audio/x-wav|text/plain"
                    };
            fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes);
            startActivityForResult(fileIntent, DOCCUMENT_REQUEST_CODE);
        } catch ( Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void pickimage() {
        String dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ICS SMS/Images/";
        File newdir = new File(dir);
        newdir.mkdirs();

        String file = dir + "SMS_" + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";

        imageBtnFile = new File(file);
        try {
            imageBtnFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imageToUploadUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", imageBtnFile);
//        File impfile=new File(context.getFilesDir(),"");
//        Uri imageToUploadUri=getUriForFile()
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                cameraIntent.setClipData(ClipData.newRawUri("", imageToUploadUri));

                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(cameraIntent, IMAGE_REQUEST_CODE);
        } catch ( Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST_CODE)
        {
            imageActivityResult();
        }
        else if(requestCode==DOCCUMENT_REQUEST_CODE)
        {
            documentResultHandler(resultCode, data, btn_noticedoc);
        }

    }
    public void documentResultHandler(int resultCode, Intent data, ImageView fileButton) {
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String picturePath = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    picturePath = FileManager.getPath(getApplicationContext(),selectedImage);
                }
                //Log.i(TAG, "documentResultHandler: " + picturePath);
                try {
                    File file = new File(picturePath);
                    if (file.exists()) {
                        if (file.length() / 1024 >= 5120) { //15 mb
                            Toast.makeText(getApplicationContext(), "File Size limit is upto 5MB", Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }
                    }
                } catch ( java.lang.Exception e) {
                    e.printStackTrace();
                }

                println("picturePath +" + picturePath);  //path of sdcard
                if(picturePath != null)
                {
                    document=picturePath;
//                    document.add(picturePath);
                    btn_noticedoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Toast.makeText(getApplicationContext(), "fle attached", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "not attached", Toast.LENGTH_SHORT).show();
                }

            }

        } else

            Toast.makeText(getApplicationContext(), getString(R.string.error_file_executong), Toast.LENGTH_LONG).show();


    }

    @SuppressLint("StaticFieldLeak")
    public void imageActivityResult() {
        try {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    //Activity activity= new Activity();
                    String path = compressImage(imageBtnFile.toString(),Notice.this);
                    Log.i(TAG, "doInBackground: path: " + path);
                    return path;
                }


                @Override
                protected void onPostExecute(String path) {
                    super.onPostExecute(path);
                    try {
                        if (path != "") {
                            camera=path;
                            btn_noticecam.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            Toast.makeText(getApplicationContext(), "fle attached", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "not attached", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed to Attach File", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to Attach File", Toast.LENGTH_SHORT).show();
        }


    }

    public static String compressImage(String imageUri, Activity activity) {
        String filename = "";
        try {
            String filePath = getRealPathFromURI(imageUri,activity);
            Bitmap scaledBitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas;
            if (scaledBitmap != null) {
                canvas = new Canvas(scaledBitmap);
                canvas.setMatrix(scaleMatrix);
                canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
            }


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);

                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);

                } else if (orientation == 3) {
                    matrix.postRotate(180);

                } else if (orientation == 8) {
                    matrix.postRotate(270);

                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream out;
            filename = getFilename();
            try {
                out = new FileOutputStream(filename);
                if (scaledBitmap != null) {
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filename;
    }

    private static String getFilename() {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), "ICS SMS/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + "/" + "SMS_" + UUID.randomUUID().toString() + "_IMG_" + System.currentTimeMillis() + ".jpg";

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private static String getRealPathFromURI(String contentURI,Activity activity) {
        Uri contentUri = Uri.parse(contentURI);

        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
