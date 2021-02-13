package rutherfordit.com.instasalary.activities.partnership;

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

public class PartnershipDocUploadActivity extends AppCompatActivity {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    SharedPrefsManager sharedPrefsManager;
    private int REQUEST_CODE_PERMISSIONS = 1000;
    String status,filename;
    RelativeLayout Submit_partner_docs;
    ImageView image_partner_bankstatement,image_partner_gst,image_partner_businessproof,image_partner_pan,
            image_partner_addressproof,image_partner_sla,image_partner_invoice;
    TextView text_partner_bankstatement,text_partner_gst,text_partner_businessproof,text_partner_pan,
            text_partner_addressproof,text_partner_sla,text_partner_invoice;
    CrystalPreloader loader_partner_bankstatement,loader_partner_gst,loader_partner_businessproof,loader_partner_pan
            ,loader_partner_addressproof,loader_partner_sla,loader_partner_invoice;
    View view, view1, view2;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout upload_pdf, upload_from_camera, upload_from_gallery;
    String mSelectedDocFile, Pdf_name;
    private final int Request_Camera = 1;
    private final int Request_Gallery = 2;
    Uri imguri;
    boolean bankstatement_uploaded ,registration_proof_uploaded ,pan_uploaded, invoices_uploaded,
            gstr_uploaded, addressproof_uploaded,sla_uploaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_doc_upload);

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
                openPermissionSettings(PartnershipDocUploadActivity.this);
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

        sharedPrefsManager = new SharedPrefsManager(PartnershipDocUploadActivity.this);
        view = getLayoutInflater().inflate(R.layout.bottomsheetdialog_partner, null);
        bottomSheetDialog = new BottomSheetDialog(PartnershipDocUploadActivity.this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(true);

        upload_pdf = view.findViewById(R.id.upload_pdf_partner);
        upload_from_camera = view.findViewById(R.id.upload_from_camera_partner);
        upload_from_gallery = view.findViewById(R.id.upload_from_gallery_partner);
        view1 = view.findViewById(R.id.view1_partner);
        view2 = view.findViewById(R.id.view2_partner);

        image_partner_bankstatement = findViewById(R.id.image_partner_bankstatement);
        text_partner_bankstatement = findViewById(R.id.text_partner_bankstatement);
        loader_partner_bankstatement = findViewById(R.id.loader_partner_bankstatement);

        image_partner_gst = findViewById(R.id.image_partner_gst);
        text_partner_gst = findViewById(R.id.text_partner_gst);
        loader_partner_gst = findViewById(R.id.loader_partner_gst);

        image_partner_businessproof = findViewById(R.id.image_partner_businessproof);
        text_partner_businessproof = findViewById(R.id.text_partner_businessproof);
        loader_partner_businessproof = findViewById(R.id.loader_partner_businessproof);

        image_partner_pan = findViewById(R.id.image_partner_pan);
        text_partner_pan = findViewById(R.id.text_partner_pan);
        loader_partner_pan = findViewById(R.id.loader_partner_pan);

        image_partner_addressproof = findViewById(R.id.image_partner_addressproof);
        text_partner_addressproof = findViewById(R.id.text_partner_addressproof);
        loader_partner_addressproof = findViewById(R.id.loader_partner_addressproof);

        image_partner_sla = findViewById(R.id.image_partner_sla);
        text_partner_sla = findViewById(R.id.text_partner_sla);
        loader_partner_sla = findViewById(R.id.loader_partner_sla);

        image_partner_invoice = findViewById(R.id.image_partner_invoice);
        text_partner_invoice = findViewById(R.id.text_partner_invoice);
        loader_partner_invoice = findViewById(R.id.loader_partner_invoice);

        Submit_partner_docs = findViewById(R.id.Submit_partner_docs);

        image_partner_pan.setOnClickListener(new View.OnClickListener() {
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

        image_partner_businessproof.setOnClickListener(new View.OnClickListener() {
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

        image_partner_bankstatement.setOnClickListener(new View.OnClickListener() {
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

        image_partner_gst.setOnClickListener(new View.OnClickListener() {
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

        image_partner_sla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "sla";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_partner_addressproof.setOnClickListener(new View.OnClickListener() {
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

        image_partner_invoice.setOnClickListener(new View.OnClickListener() {
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

        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(PartnershipDocUploadActivity.this, NormalFilePickActivity.class);
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

        Submit_partner_docs.setOnClickListener(new View.OnClickListener() {
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
                loader_partner_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "addressproof":
                loader_partner_addressproof.setVisibility(View.VISIBLE);
                code = "6";
                break;
            case "registration":
                loader_partner_businessproof.setVisibility(View.VISIBLE);
                code = "10";
                break;
            case "bankstatement":
                loader_partner_bankstatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                loader_partner_gst.setVisibility(View.VISIBLE);
                code = "12";
                break;
            case "invoice":
                loader_partner_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "sla":
                loader_partner_sla.setVisibility(View.VISIBLE);
                code = "13";
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
                                image_partner_pan.setPadding(20, 20, 20, 20);
                                image_partner_pan.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_pan.setImageURI(imguri);
                                text_partner_pan.setText(filename + ".png");
                                loader_partner_pan.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Pan Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "addressproof":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addressproof_uploaded = true;
                                image_partner_addressproof.setPadding(20, 20, 20, 20);
                                image_partner_addressproof.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_addressproof.setImageURI(imguri);
                                text_partner_addressproof.setText(filename + ".png");
                                loader_partner_addressproof.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Business Address Proof Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                image_partner_businessproof.setPadding(20, 20, 20, 20);
                                image_partner_businessproof.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_businessproof.setImageURI(imguri);
                                text_partner_businessproof.setText(filename + ".png");
                                loader_partner_businessproof.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Registration Proof Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                image_partner_bankstatement.setPadding(20, 20, 20, 20);
                                image_partner_bankstatement.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_bankstatement.setImageURI(imguri);
                                text_partner_bankstatement.setText(filename + ".png");
                                loader_partner_bankstatement.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Bank Statement Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                image_partner_gst.setPadding(20, 20, 20, 20);
                                image_partner_gst.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_gst.setImageURI(imguri);
                                text_partner_gst.setText(filename + ".png");
                                loader_partner_gst.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Gstr Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                image_partner_invoice.setPadding(20, 20, 20, 20);
                                image_partner_invoice.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_invoice.setImageURI(imguri);
                                text_partner_invoice.setText(filename + ".png");
                                loader_partner_invoice.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Invoice Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "sla":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sla_uploaded = true;
                                image_partner_sla.setPadding(20, 20, 20, 20);
                                image_partner_sla.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_partner_sla.setImageURI(imguri);
                                text_partner_sla.setText(filename + ".png");
                                loader_partner_sla.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Sla Uploaded", Toast.LENGTH_SHORT).show();

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
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
                loader_partner_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "addressproof":
                loader_partner_addressproof.setVisibility(View.VISIBLE);
                code = "6";
                break;
            case "registration":
                loader_partner_businessproof.setVisibility(View.VISIBLE);
                code = "10";
                break;
            case "bankstatement":
                loader_partner_bankstatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                loader_partner_gst.setVisibility(View.VISIBLE);
                code = "12";
                break;
            case "invoice":
                loader_partner_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "sla":
                loader_partner_sla.setVisibility(View.VISIBLE);
                code = "13";
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
                                text_partner_pan.setText(Pdf_name);
                                image_partner_pan.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_pan.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "addressproof":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addressproof_uploaded = true;
                                text_partner_addressproof.setText(Pdf_name);
                                image_partner_addressproof.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_addressproof.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                text_partner_businessproof.setText(Pdf_name);
                                image_partner_businessproof.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_businessproof.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                text_partner_bankstatement.setText(Pdf_name);
                                image_partner_bankstatement.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_bankstatement.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                text_partner_gst.setText(Pdf_name);
                                image_partner_gst.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_gst.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                text_partner_invoice.setText(Pdf_name);
                                image_partner_invoice.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_invoice.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "sla":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sla_uploaded = true;
                                text_partner_sla.setText(Pdf_name);
                                image_partner_sla.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_partner_sla.setVisibility(View.GONE);

                                if (pan_uploaded && addressproof_uploaded && registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && invoices_uploaded && sla_uploaded )
                                {
                                    Submit_partner_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_partner_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                }

            }
        });

    }
}