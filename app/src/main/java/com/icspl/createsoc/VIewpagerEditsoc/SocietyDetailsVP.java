package com.icspl.createsoc.VIewpagerEditsoc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.icspl.createsoc.Adaptor.SocVPAdaptor;
import com.icspl.createsoc.BuildConfig;
import com.icspl.createsoc.DbConstant.DbConstant;
import com.icspl.createsoc.DbHelper.DbHelper;
import com.icspl.createsoc.Model.ServerResponseModel;
import com.icspl.createsoc.Model.SocVPModel;
import com.icspl.createsoc.R;
import com.icspl.createsoc.callback.ApiService;
import com.icspl.createsoc.utils.FileManager;
import com.icspl.kartik.myapplication.utils.Common;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static java.sql.DriverManager.println;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SocietyDetailsVP.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocietyDetailsVP#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocietyDetailsVP extends Fragment implements SocVPAdaptor.Photoclickhandler{

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    public TextView tv_socname;
    public EditText et_socadd,et_socregno,et_socmeter,et_wmeter,et_tax,et_addrows;
    public Button btn_namedoc,btn_namecam,btn_socadddoc,btn_socaadcam,btn_socphotodoc,btn_socphotocam,btn_socregdoc,
            btn_socregcam,btn_meterdoc,btn_socmetercam,btn_wmeterdoc,btn_socwmetercam,btn_taxdoc,btn_socptaxcam,btn_Savesocdetails;

public SocVPAdaptor vpAdaptor;
public List<SocVPModel> list;
public RecyclerView rv;
public Context mContext;
    private Uri imageToUploadUri;
    private int IMAGE_REQUEST_CODE = 2103;
    private int DOCCUMENT_REQUEST_CODE = 2102;
    File imageBtnFile;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Button test;
    private OnFragmentInteractionListener mListener;
    Hashtable<Integer, String> camera = new Hashtable<Integer, String>();
    Hashtable<Integer, String> document = new Hashtable<Integer, String>();
    Hashtable<Integer,String>titles=new Hashtable<Integer, String>();
    Hashtable<Integer,String>values=new Hashtable<Integer, String>();
    //database
    DbHelper dbHelper;
    SQLiteDatabase mDatabase;
    public Integer num;
    public SocietyDetailsVP() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocietyDetailsVP.
     */
    // TODO: Rename and change types and number of parameters
    public static SocietyDetailsVP newInstance(String param1, String param2) {
        SocietyDetailsVP fragment = new SocietyDetailsVP();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_society_details_v, container, false);

        // Inflate the layout for this fragment
        //Find view of buttons
        pref= getActivity().getSharedPreferences("MyPref", 0);
        edit=pref.edit();
        btn_namedoc = v.findViewById(R.id.btn_namedoc);
        btn_namecam = v.findViewById(R.id.btn_namecam);
        btn_socadddoc = v.findViewById(R.id.btn_socadddoc);
        btn_socaadcam = v.findViewById(R.id.btn_socaadcam);
        btn_socphotodoc = v.findViewById(R.id.btn_socphotodoc);
        btn_socphotocam = v.findViewById(R.id.btn_socphotocam);
        btn_socregdoc = v.findViewById(R.id.btn_socregdoc);
        btn_socregcam = v.findViewById(R.id.btn_socregcam);
        btn_meterdoc = v.findViewById(R.id.btn_meterdoc);
        btn_socmetercam = v.findViewById(R.id.btn_metercam);
        btn_wmeterdoc = v.findViewById(R.id.btn_wmeterdoc);
        btn_socwmetercam = v.findViewById(R.id.btn_wmetercam);
        btn_taxdoc = v.findViewById(R.id.btn_taxdoc);
        btn_socptaxcam = v.findViewById(R.id.btn_taxcam);

        //findview of edittext
        et_socadd=v.findViewById(R.id.et_socadd);
        et_socregno=v.findViewById(R.id.et_socregno);
        et_socmeter=v.findViewById(R.id.et_socmeter);
        et_wmeter=v.findViewById(R.id.et_wmeter);
        et_tax=v.findViewById(R.id.et_tax);
        et_addrows=v.findViewById(R.id.et_addrows);
        rv=v.findViewById(R.id.rv);
        list= new ArrayList<>();
        dbHelper = new DbHelper(getActivity());
        mDatabase = dbHelper.getWritableDatabase();
        //submit button
        btn_Savesocdetails=v.findViewById(R.id.btn_Savesocdetails);

        et_addrows.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
               if(charSequence.toString().equals(""))
               {
                   list.clear();
                   Toast.makeText(getContext(), "cannot be null", Toast.LENGTH_SHORT).show();
               }
               else{
                   int number=Integer.parseInt(et_addrows.getText().toString());
                   list.clear();
                   for (int j = 0; j < number; j++) {
                       SocVPModel socVPModel = new SocVPModel("", "");
                       list.add(socVPModel);
                   }

                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                   rv.setLayoutManager(linearLayoutManager);
                   vpAdaptor = new SocVPAdaptor(getActivity(), list,SocietyDetailsVP.this);
                   rv.setAdapter(vpAdaptor);

               }
            }

            @Override
            public void afterTextChanged(Editable editable) {
              //  String num=et_addrows.getText().toString();

}

        });


        //findview for text view



        tv_socname=v.findViewById(R.id.tv_socname);
        btn_namecam.setOnClickListener(new View.OnClickListener()   {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        test=btn_namecam;
        pickimage();
        num=1;

    }
});
    btn_socaadcam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socaadcam;
            pickimage();
            num=2;
        }
    });
    btn_socphotocam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socphotocam;
            pickimage();
            num=3;
        }
    });
    btn_socregcam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socregcam;
            pickimage();
            num=4;
        }
    });
    btn_socmetercam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socmetercam;
            pickimage();
            num=5;
        }
    });
    btn_socwmetercam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socwmetercam;
            pickimage();
            num=6;
        }
    });
    btn_socptaxcam.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            test=btn_socptaxcam;
            pickimage();
            num=7;
        }
    });
    btn_namedoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_namedoc;
            num=1;
            picDocuments();
        }
    });
    btn_socadddoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_socadddoc;
            num=2;
            picDocuments();
        }
    });
    btn_socphotodoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_socphotodoc;
            num=3;
            picDocuments();
        }
    });
    btn_socregdoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_socregdoc;
            num=4;
            picDocuments();
        }
    });
    btn_meterdoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_meterdoc;
            num=5;
            picDocuments();
        }
    });
    btn_wmeterdoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test =btn_wmeterdoc;
            num=6;
            picDocuments();
        }
    });
    btn_taxdoc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test=btn_taxdoc;
            num=7;
            picDocuments();
        }
    });
        btn_Savesocdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_socadd.getText().toString().equals(""))
                {
                    et_socadd.setError("Address field is compulsory");
                    return;
                }
                if(et_socregno.getText().toString().equals(""))
                {
                    et_socregno.setError("Please enter registor number");
                    return;
                }
                if(et_socmeter.getText().toString().equals(""))
                {
                    et_socmeter.setError("Please enter electric meter number");
                    return;
                }
                if(et_wmeter.getText().toString().equals(""))
                {
                    et_wmeter.setError("Please enter water meter number");
                    return;
                }
                if(et_tax.getText().toString().equals(""))
                {
                    et_tax.setError("Please enter property tax number");
                    return;
                }

//                values.put(1,pref.getString("Societyname",""));
                values.put(1,"hell");
                values.put(2,et_socadd.getText().toString());
                values.put(3,"photo");
                values.put(4,et_socregno.getText().toString());
                values.put(5,et_socmeter.getText().toString());
                values.put(6,et_wmeter.getText().toString());
                values.put(7,et_tax.getText().toString());
                titles.put(1,"Society name");
                titles.put(2,"Address");
                titles.put(3,"Photo");
                titles.put(4,"Register no");
                titles.put(5,"Electric meter no");
                titles.put(6,"Water meter no");
                titles.put(7,"property taxn no");
                int number=7;
                if(!et_addrows.getText().toString().equals(""))
                {
                     number=Integer.parseInt(et_addrows.getText().toString());
                    number=number+7;
                    if(values.size()!=number&&titles.size()!=number)
                    {
                        Toast.makeText(getActivity(),"Please Fill All Extra details",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
//                for (int i = 1; i <=number ; i++) {
//                    if(titles.containsKey(i))
//                    {
//                        title.add(titles.get(i));
//                    }
//                    else {
//                        title.add("");
//                    }
//                    if(values.containsKey(i))
//                    {
//                        description.add(values.get(i));
//                    }
//                    else {
//                        description.add("");
//                    }
//                    if(camera.containsKey(i))
//                    {
//                        photo.add(camera.get(i));
//                    }
//                    else
//                    {
//                        photo.add("");
//                    }
//                    if(document.contains(i))
//                    {
//                        documents.add(document.get(i));
//                    }
//                    else {
//                        documents.add("");
//                    }
//                }
                String prefvalsid=  pref.getString("S_id","");
                for (int i = 1; i <= number; i++) {
                    MultipartBody.Part sendDocument;
                    MultipartBody.Part sendImage;
                    if(document.containsKey(i)&&camera.containsKey(i))
                    {
                        File doc= new File(document.get(i));
                        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),doc);
                        sendDocument=MultipartBody.Part.createFormData("sdoc",doc.getName(),requestBody);
                        File cam= new File(camera.get(i));
                        RequestBody requestBody1= RequestBody.create(MediaType.parse("multipart/form-data"),cam);
                        sendImage=MultipartBody.Part.createFormData("simage",cam.getName(),requestBody1);
                        senddata(prefvalsid,titles.get(i),values.get(i),i,sendDocument,sendImage,document.get(i),camera.get(i));


                    }
                    else if(document.containsKey(i))
                    {
                        File doc= new File(document.get(i));
                        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),doc);
                        sendDocument=MultipartBody.Part.createFormData("sdoc",doc.getName(),requestBody);
                        RequestBody cam=RequestBody.create(MultipartBody.FORM,"");
                        sendImage=MultipartBody.Part.createFormData("simage","",cam);
                        senddata(prefvalsid,titles.get(i),values.get(i),i,sendDocument,sendImage,document.get(i),camera.get(i));

                    }
                    else if(camera.containsKey(i))
                    {
                        RequestBody doc=RequestBody.create(MultipartBody.FORM,"");
                        sendDocument=MultipartBody.Part.createFormData("sdoc","",doc);
                        File cam= new File(camera.get(i));
                        RequestBody requestBody1= RequestBody.create(MediaType.parse("multipart/form-data"),cam);
                        sendImage=MultipartBody.Part.createFormData("simage",cam.getName().toString(),requestBody1);
                        senddata(prefvalsid,titles.get(i),values.get(i),i,sendDocument,sendImage,document.get(i),camera.get(i));
                    }
                    else
                    {
                        RequestBody cam=RequestBody.create(MultipartBody.FORM,"");
                        sendImage=MultipartBody.Part.createFormData("simage","",cam);
                        RequestBody doc=RequestBody.create(MultipartBody.FORM,"");
                        sendDocument=MultipartBody.Part.createFormData("sdoc","",doc);
                        senddata(prefvalsid,titles.get(i),values.get(i),i,sendDocument,sendImage,document.get(i),camera.get(i));

                    }
                }
                Toast.makeText(getActivity(),"done",Toast.LENGTH_LONG).show();


            }
        });
  return v;

    }

    private void senddata(final String prefvalsid, final String title, final String value, final int i, MultipartBody.Part sendDocument, MultipartBody.Part sendImage, final String docpath, final String campath) {
        Common common= new Common();
        common.getAPI().addsocietydetails(prefvalsid,title,value,i,sendDocument,sendImage).enqueue(new Callback<List<ServerResponseModel>>()
        {
            @Override
            public void onResponse(@NotNull Call<List<ServerResponseModel>> call, @NotNull Response<List<ServerResponseModel>> response)
            {
                if (response.isSuccessful()&&response.body().get(0).getServerResponse()>0)
                Toast.makeText(getActivity(),"document successfully both",Toast.LENGTH_LONG).show();
                else
                {
                    Toast.makeText(getActivity(),"Something went  wrong ",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ServerResponseModel>> call, Throwable t) {
                //insert into sqlite
                ContentValues values = new ContentValues();
                values.put(DbConstant.SocietyDocuments.TITLE,title);
                values.put(DbConstant.SocietyDocuments.DESCRIPTION,value);
                values.put(DbConstant.SocietyDocuments.NUMBER,i);
                values.put(DbConstant.SocietyDocuments.PHOTO,campath);
                values.put(DbConstant.SocietyDocuments.DOCUMENT,docpath);
                values.put(DbConstant.SocietyDocuments.SOCIETY_ID,prefvalsid);
                mDatabase.insert(DbConstant.SocietyDocuments.TABLE_SOCIETY_DOCUMENT,null,values);
                Toast.makeText(getActivity(),"Network error while uploading please Sync the resend the documents ",Toast.LENGTH_LONG).show();
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
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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


        imageToUploadUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", imageBtnFile);
//        File impfile=new File(context.getFilesDir(),"");
//        Uri imageToUploadUri=getUriForFile()

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageToUploadUri);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
               cameraIntent.setClipData(ClipData.newRawUri("", imageToUploadUri));

            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(cameraIntent, IMAGE_REQUEST_CODE);

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
            documentResultHandler(resultCode, data, test);
        }

    }
    public void documentResultHandler(int resultCode, Intent data,Button fileButton) {
        if (resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String picturePath = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    picturePath = FileManager.getPath(getContext(),selectedImage);
                }
                //Log.i(TAG, "documentResultHandler: " + picturePath);
                try {
                    File file = new File(picturePath);
                    if (file.exists()) {
                        if (file.length() / 1024 >= 5120) { //15 mb
                            Toast.makeText(mContext, "File Size limit is upto 5MB", Toast.LENGTH_LONG)
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
                    document.put(num,picturePath);
                    test.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    Toast.makeText(getActivity(), "fle attached", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "not attached", Toast.LENGTH_SHORT).show();
                }

            }

        } else
            Toast.makeText(getActivity(), getString(R.string.error_file_executong), Toast.LENGTH_LONG).show();


    }

    @SuppressLint("StaticFieldLeak")
    public void imageActivityResult() {
        try {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    //Activity activity= new Activity();
                    String path = compressImage(imageBtnFile.toString(),getActivity());
                    Log.i(TAG, "doInBackground: path: " + path);
                    return path;
                }


                @Override
                protected void onPostExecute(String path) {
                    super.onPostExecute(path);
                    try {
                        if (path != null) {
                            camera.put(num,path);
                            test.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            Toast.makeText(getActivity(), "fle attached", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "not attached", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to Attach File", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(mContext, "Failed to Attach File", Toast.LENGTH_SHORT).show();
        }


    }

    public static String compressImage(String imageUri,Activity activity) {
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void photoclickcallback(Integer pos, View v,Button rvbtn_cam) {
        num=pos+8;
        test=rvbtn_cam;
        pickimage();
    }

    @Override
    public void documentclickcallback(Integer pos, View v, Button rvbtn_file) {
        num=pos+8;
        test=rvbtn_file;
        picDocuments();
    }


    @Override
    public void valuecallback(Integer pos, String title,String value) {
        if(!value.equals(""))
        {
                num=pos+8;
                titles.put(num,title);
                values.put(num,value);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
