package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

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
import rutherfordit.com.instasalary.activities.BankDetailsActivity;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;

public class PrivateLimitedDocUploadActivity extends AppCompatActivity {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    SharedPrefsManager sharedPrefsManager;
    private int REQUEST_CODE_PERMISSIONS = 1000;
    String status,filename;
    RelativeLayout Submit_pl_docs;
    ImageView image_pvt_bankstatement,image_pvt_gst,image_pvt_itreturns,image_pvt_businessproof,image_pvt_pan,image_pvt_addressproof
            ,image_pvt_aoa,image_pvt_moa,image_pvt_invoice;
    TextView text_pvt_bankstatement,text_pvt_gst,text_pvt_itreturns,text_pvt_businessproof,text_pvt_pan,text_pvt_addressproof
            ,text_pvt_aoa,text_pvt_moa,text_pvt_invoice;
    CrystalPreloader loader_pvt_bankstatement,loader_pvt_gst,loader_pvt_itreturns,loader_pvt_businessproof,loader_pvt_pan
            ,loader_pvt_addressproof,loader_pvt_aoa,loader_pvt_moa,loader_pvt_invoice;
    View view, view1, view2;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout upload_pdf, upload_from_camera, upload_from_gallery;
    String mSelectedDocFile, Pdf_name;
    private final int Request_Camera = 1;
    private final int Request_Gallery = 2;
    Uri imguri;
    boolean bankstatement_uploaded ,registration_proof_uploaded ,pan_uploaded, invoices_uploaded,
            itr_uploaded, gstr_uploaded, addressproof_uploaded,aoa_uploaded, moa_uploaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_doc_upload);

        if (allPermissionsGranted()) {
            init();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
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
                openPermissionSettings(PrivateLimitedDocUploadActivity.this);
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

    private void init() {

        sharedPrefsManager = new SharedPrefsManager(PrivateLimitedDocUploadActivity.this);
        view = getLayoutInflater().inflate(R.layout.bottomsheetdialog_pvt, null);
        bottomSheetDialog = new BottomSheetDialog(PrivateLimitedDocUploadActivity.this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(true);

        upload_pdf = view.findViewById(R.id.upload_pdf_pvt);
        upload_from_camera = view.findViewById(R.id.upload_from_camera_pvt);
        upload_from_gallery = view.findViewById(R.id.upload_from_gallery_pvt);
        view1 = view.findViewById(R.id.view1_pvt);
        view2 = view.findViewById(R.id.view2_pvt);

        image_pvt_bankstatement = findViewById(R.id.image_pvt_bankstatement);
        text_pvt_bankstatement = findViewById(R.id.text_pvt_bankstatement);
        loader_pvt_bankstatement = findViewById(R.id.loader_pvt_bankstatement);

        image_pvt_gst = findViewById(R.id.image_pvt_gst);
        text_pvt_gst = findViewById(R.id.text_pvt_gst);
        loader_pvt_gst = findViewById(R.id.loader_pvt_gst);

        image_pvt_itreturns = findViewById(R.id.image_pvt_itreturns);
        text_pvt_itreturns = findViewById(R.id.text_pvt_itreturns);
        loader_pvt_itreturns = findViewById(R.id.loader_pvt_itreturns);

        image_pvt_businessproof = findViewById(R.id.image_pvt_businessproof);
        text_pvt_businessproof = findViewById(R.id.text_pvt_businessproof);
        loader_pvt_businessproof = findViewById(R.id.loader_pvt_businessproof);

        image_pvt_pan = findViewById(R.id.image_pvt_pan);
        text_pvt_pan = findViewById(R.id.text_pvt_pan);
        loader_pvt_pan = findViewById(R.id.loader_pvt_pan);

        image_pvt_addressproof = findViewById(R.id.image_pvt_addressproof);
        text_pvt_addressproof = findViewById(R.id.text_pvt_addressproof);
        loader_pvt_addressproof = findViewById(R.id.loader_pvt_addressproof);

        image_pvt_aoa = findViewById(R.id.image_pvt_aoa);
        text_pvt_aoa = findViewById(R.id.text_pvt_aoa);
        loader_pvt_aoa = findViewById(R.id.loader_pvt_aoa);

        image_pvt_moa = findViewById(R.id.image_pvt_moa);
        text_pvt_moa = findViewById(R.id.text_pvt_moa);
        loader_pvt_moa = findViewById(R.id.loader_pvt_moa);

        image_pvt_invoice = findViewById(R.id.image_pvt_invoice);
        text_pvt_invoice = findViewById(R.id.text_pvt_invoice);
        loader_pvt_invoice = findViewById(R.id.loader_pvt_invoice);

        sharedPrefsManager.setCHECK_PAGE("5");


        Submit_pl_docs = findViewById(R.id.Submit_pl_docs);

        image_pvt_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "pan";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_businessproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "registration";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_bankstatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "bankstatement";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "gstr";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "invoice";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_itreturns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "itr";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();

            }
        });

        image_pvt_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "addressproof";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_aoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "aoa";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_pvt_moa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "moa";
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

                Intent intent4 = new Intent(PrivateLimitedDocUploadActivity.this, NormalFilePickActivity.class);
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

        Submit_pl_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BankDetailsActivity.class);
                startActivity(i);
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

    private void uploadFile(Uri uri) {

        String code = "";

        switch (status) {
            case "pan":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "addressproof":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_addressproof.setVisibility(View.VISIBLE);
                code = "6";
                break;
            case "registration":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_businessproof.setVisibility(View.VISIBLE);
                code = "10";
                break;
            case "bankstatement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_bankstatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_gst.setVisibility(View.VISIBLE);
                code = "12";
                break;
            case "moa":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_moa.setVisibility(View.VISIBLE);
                code = "18";
                break;
            case "invoice":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "aoa":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_aoa.setVisibility(View.VISIBLE);
                code = "19";
                break;
            case "itr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_itreturns.setVisibility(View.VISIBLE);
                code = "16";
                break;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        File file = new File(getRealPathFromURI(uri));

        Log.e("code", "uploadFile: " + code + file.getName() );

        try {
            builder.addFormDataPart("proof[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        builder.addFormDataPart("proof_type", code);
        builder.addFormDataPart("company_id", sharedPrefsManager.getCOMPANY_ID());

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
        String finalCode = code;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                Log.e("response", "onResponse: " + response.body().string() );

                switch (status) {
                    case "pan":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pan_uploaded = true;
                                image_pvt_pan.setPadding(20, 20, 20, 20);
                                image_pvt_pan.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_pan.setImageURI(imguri);
                                text_pvt_pan.setText(filename + ".png");
                                loader_pvt_pan.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Pan Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "addressproof":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addressproof_uploaded = true;
                                image_pvt_addressproof.setPadding(20, 20, 20, 20);
                                image_pvt_addressproof.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_addressproof.setImageURI(imguri);
                                text_pvt_addressproof.setText(filename + ".png");
                                loader_pvt_addressproof.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Business Address Proof Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                image_pvt_businessproof.setPadding(20, 20, 20, 20);
                                image_pvt_businessproof.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_businessproof.setImageURI(imguri);
                                text_pvt_businessproof.setText(filename + ".png");
                                loader_pvt_businessproof.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Registration Proof Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                image_pvt_bankstatement.setPadding(20, 20, 20, 20);
                                image_pvt_bankstatement.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_bankstatement.setImageURI(imguri);
                                text_pvt_bankstatement.setText(filename + ".png");
                                loader_pvt_bankstatement.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Bank Statement Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                image_pvt_gst.setPadding(20, 20, 20, 20);
                                image_pvt_gst.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_gst.setImageURI(imguri);
                                text_pvt_gst.setText(filename + ".png");
                                loader_pvt_gst.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Gstr Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "moa":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                moa_uploaded = true;
                                image_pvt_moa.setPadding(20, 20, 20, 20);
                                image_pvt_moa.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_moa.setImageURI(imguri);
                                text_pvt_moa.setText(filename + ".png");
                                loader_pvt_moa.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Moa Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                image_pvt_invoice.setPadding(20, 20, 20, 20);
                                image_pvt_invoice.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_invoice.setImageURI(imguri);
                                text_pvt_invoice.setText(filename + ".png");
                                loader_pvt_invoice.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Invoice Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "aoa":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                aoa_uploaded = true;
                                image_pvt_aoa.setPadding(20, 20, 20, 20);
                                image_pvt_aoa.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_aoa.setImageURI(imguri);
                                text_pvt_aoa.setText(filename + ".png");
                                loader_pvt_aoa.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Aoa Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "itr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itr_uploaded = true;
                                image_pvt_itreturns.setPadding(20, 20, 20, 20);
                                image_pvt_itreturns.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_pvt_itreturns.setImageURI(imguri);
                                text_pvt_itreturns.setText(filename + ".png");
                                loader_pvt_itreturns.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Itr Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                }


            }
        });

    }

    private void uploadpdf() {

        String code = "";

        switch (status) {
            case "pan":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "addressproof":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_addressproof.setVisibility(View.VISIBLE);
                code = "6";
                break;
            case "registration":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_businessproof.setVisibility(View.VISIBLE);
                code = "10";
                break;
            case "bankstatement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_bankstatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_gst.setVisibility(View.VISIBLE);
                code = "12";
                break;
            case "moa":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_moa.setVisibility(View.VISIBLE);
                code = "18";
                break;
            case "invoice":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "aoa":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_aoa.setVisibility(View.VISIBLE);
                code = "19";
                break;
            case "itr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_pvt_itreturns.setVisibility(View.VISIBLE);
                code = "16";
                break;
        }


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

        builder.addFormDataPart("proof_type", code);
        builder.addFormDataPart("company_id", sharedPrefsManager.getCOMPANY_ID());

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

                switch (status) {
                    case "pan":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pan_uploaded = true;
                                text_pvt_pan.setText(Pdf_name);
                                image_pvt_pan.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_pan.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "addressproof":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addressproof_uploaded = true;
                                text_pvt_addressproof.setText(Pdf_name);
                                image_pvt_addressproof.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_addressproof.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                text_pvt_businessproof.setText(Pdf_name);
                                image_pvt_businessproof.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_businessproof.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                text_pvt_bankstatement.setText(Pdf_name);
                                image_pvt_bankstatement.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_bankstatement.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                text_pvt_gst.setText(Pdf_name);
                                image_pvt_gst.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_gst.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "moa":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                moa_uploaded = true;
                                text_pvt_moa.setText(Pdf_name);
                                image_pvt_moa.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_moa.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                text_pvt_invoice.setText(Pdf_name);
                                image_pvt_invoice.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_invoice.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "aoa":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                aoa_uploaded = true;
                                text_pvt_aoa.setText(Pdf_name);
                                image_pvt_aoa.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_aoa.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "itr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itr_uploaded = true;
                                text_pvt_itreturns.setText(Pdf_name);
                                image_pvt_itreturns.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_pvt_itreturns.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && moa_uploaded && invoices_uploaded && aoa_uploaded && itr_uploaded )
                                {
                                    Submit_pl_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_pl_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Action Denied.", Toast.LENGTH_SHORT).show();
        // super.onBackPressed();
    }
}