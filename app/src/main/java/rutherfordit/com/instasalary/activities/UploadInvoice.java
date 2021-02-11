package rutherfordit.com.instasalary.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.sp.SPDocumentUploadActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class UploadInvoice extends AppCompatActivity implements ResponseHandler {

    View view, view1, view2;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    TextInputEditText enterAmount;
    RelativeLayout submitInvoice;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    boolean click = false;
    CardView loader_invoice;
    private int REQUEST_CODE_PERMISSIONS = 1000;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout upload_pdf, upload_from_camera, upload_from_gallery;
    String mSelectedDocFile, Pdf_name;
    private final int Request_Camera = 1;
    private final int Request_Gallery = 2;
    Uri imguri;
    String status,filename;
    private boolean invoice_uploaded = false;
    ImageView image_invoice;
    TextView text_invoice;
    CrystalPreloader loader_invoice_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_invoice);

        if (allPermissionsGranted()) {
            init();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


    }

    private void init() {

        loader_invoice_upload = findViewById(R.id.loader_invoice_upload);
        text_invoice = findViewById(R.id.text_invoice);
        image_invoice = findViewById(R.id.image_invoice);
        enterAmount = findViewById(R.id.enterAmount);
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        volleyRequest = new VolleyRequest();
        submitInvoice = findViewById(R.id.submitInvoice);
        loader_invoice = findViewById(R.id.loader_invoice);

        view = getLayoutInflater().inflate(R.layout.bottomsheetdialog_invoice, null);
        bottomSheetDialog = new BottomSheetDialog(UploadInvoice.this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(true);

        upload_pdf = view.findViewById(R.id.upload_pdf_invoice);
        upload_from_camera = view.findViewById(R.id.upload_from_camera_invoice);
        upload_from_gallery = view.findViewById(R.id.upload_from_gallery_invoice);
        view1 = view.findViewById(R.id.view1_invoice);
        view2 = view.findViewById(R.id.view2_invoice);

        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!enterAmount.getText().toString().equals("") && invoice_uploaded)
                {
                    submitInvoice.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitInvoice.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        image_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(UploadInvoice.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 1);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                bottomSheetDialog.cancel();
            }
        });

        upload_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE), Request_Camera);
                bottomSheetDialog.cancel();
            }
        });

        upload_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), Request_Gallery);
                bottomSheetDialog.cancel();
            }
        });

        submitInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (invoice_uploaded)
                {
                    loanApi();
                    loader_invoice.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {

            if (resultCode == RESULT_OK) {

                assert data != null;
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                assert list != null;
                NormalFile Url = (list.get(0));

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                mSelectedDocFile = Url.getPath();
                Pdf_name = Url.getName();

                uploadpdf();


            } else if (resultCode == RESULT_CANCELED) {
                Toasty.info(getApplicationContext(), "Cancelled..", Toast.LENGTH_SHORT).show();
            }
        }

        else if (requestCode == Request_Camera) {
            switch (resultCode) {
                case RESULT_OK:

                    if (data != null) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        assert bitmap != null;
                        getImageUri(bitmap);
                        uploadFile(imguri);
                    }
                    break;

                case RESULT_CANCELED:
                    break;
            }
        } else if (requestCode == Request_Gallery) {
            switch (resultCode) {
                case RESULT_OK:

                    if (data != null) {

                        imguri = data.getData();
                        assert imguri != null;
                        File file = new File(Objects.requireNonNull(imguri.getPath()));
                        String filename = file.getName();
                        uploadFile(imguri);
                        /*image_sp_pan.setImageURI(imguri);
                        text_sp_pan.setText(filename);*/
                    }
                    break;

                case RESULT_CANCELED:
                    break;
            }
        }
    }

    public void getImageUri(Bitmap inImage) {

        long tsLong = System.currentTimeMillis() / 1000;
        filename = Long.toString(tsLong);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, filename, null);
        imguri = Uri.parse(path);

    }

    public static void openPermissionSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                init();
            } else {
                Toasty.warning(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                openPermissionSettings(UploadInvoice.this);
            }
        }
    }

    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private String getRealPathFromURI(Uri captured_image) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, captured_image, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void loanApi() {
        JSONObject jsonObjectBody = new JSONObject();

        try {
            jsonObjectBody.put("amount", enterAmount.getText().toString());
            jsonObjectBody.put("days", "30");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequest.JsonObjRequestAuthorization(UploadInvoice.this,jsonObjectBody, Urls.LOAN_DETAILS, Constants.loan_details,sharedPrefsManager.getAccessToken());

    }

    @Override
    public void responseHandler(Object obj, int i) {

        if (i == Constants.loan_details){
            JSONObject response = (JSONObject) obj;
            Log.e("response", "responseHandlerLoan: " + response);
            if  (response!=null)
            {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                loader_invoice.setVisibility(View.GONE);
               /* new NordanAlertDialog.Builder(this)
                        .setAnimation(Animation.POP)
                        .isCancellable(false)
                        .setTitle("Application Successfull..")
                        .setMessage("Your application has been submitted to us. Please wait till we get back..")

                        .setPositiveBtnText("Great!")
                        .onPositiveClicked(this::gotonext)
                        .build().show();*/
            }
        }
    }

    public void gotonext()
    {

        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        }, 6000);


    }

    private void uploadFile(Uri uri) {

        loader_invoice_upload.setVisibility(View.VISIBLE);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        File file = new File(getRealPathFromURI(uri));

      //  Log.e("code", "uploadFile: " + code + file.getName() );

        try {
            builder.addFormDataPart("proof[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        builder.addFormDataPart("proof_type", "17");

        RequestBody requestBody = builder.build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(Urls.IMAGE_UPLOAD)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", sharedPrefsManager.getAccessToken())
                .post(requestBody)
                .build();


        OkHttpClient client = new OkHttpClient.Builder().build();
        okhttp3.Call call = client.newCall(request);
      //  String finalCode = code;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                Log.e("response", "onResponse: " + response.body().string() );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        invoice_uploaded = true;
                        image_invoice.setPadding(20, 20, 20, 20);
                        image_invoice.setScaleType(ImageView.ScaleType.FIT_XY);
                        image_invoice.setImageURI(imguri);
                        text_invoice.setText(filename + ".png");
                        loader_invoice_upload.setVisibility(View.GONE);
                        Toasty.info(getApplicationContext(), "Invoice Uploaded", Toast.LENGTH_SHORT).show();

                        if (!enterAmount.getText().toString().equals("") && invoice_uploaded)
                        {
                            submitInvoice.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click=true;
                        }
                        else {
                            submitInvoice.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click=false;
                        }
                    }
                });
            }
        });

    }

    private void uploadpdf() {

        loader_invoice_upload.setVisibility(View.VISIBLE);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (mSelectedDocFile != null) {

            File file = new File(mSelectedDocFile);

            try {
                final MediaType MEDIA_TYPE = MediaType.parse((mSelectedDocFile));
                builder.addFormDataPart("proof[]", file.getName(), RequestBody.create(MediaType.parse(String.valueOf(MEDIA_TYPE)), file));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        builder.addFormDataPart("proof_type", "17");

        RequestBody requestBody = builder.build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(Urls.IMAGE_UPLOAD)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", sharedPrefsManager.getAccessToken())
                .post(requestBody)
                .build();


        OkHttpClient client = new OkHttpClient.Builder().build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("httpresponse", "onFailure: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                assert response.body() != null;
                String body = response.body().string();
                Log.e("httpresponse", "onResponse: " + body);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        invoice_uploaded = true;
                        text_invoice.setText(Pdf_name);
                        image_invoice.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                        loader_invoice_upload.setVisibility(View.GONE);

                        if (!enterAmount.getText().toString().equals("") && invoice_uploaded)
                        {
                            submitInvoice.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click=true;
                        }
                        else {
                            submitInvoice.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click=false;
                        }
                    }
                });
            }
        });

    }
}