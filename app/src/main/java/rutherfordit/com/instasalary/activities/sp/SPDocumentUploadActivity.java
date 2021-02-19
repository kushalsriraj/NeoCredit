package rutherfordit.com.instasalary.activities.sp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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

public class SPDocumentUploadActivity extends AppCompatActivity {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    SharedPrefsManager sharedPrefsManager;
    private int REQUEST_CODE_PERMISSIONS = 1000;
    String status,filename;
    RelativeLayout Submit_sp_docs;
    ImageView image_sp_pan,image_sp_adhar,image_sp_registrationProof,image_sp_bankStatement,image_sp_gstr,image_sp_sla
                            ,image_sp_invoice,image_sp_rentAggrement,image_sp_itr,image_sp_current_address;
    TextView text_sp_pan,text_sp_adhar,text_sp_registrationProof,text_sp_bankStatement,text_sp_gstr,text_sp_sla
                            ,text_sp_invoice,text_sp_rentAggrement,text_sp_itr,text_sp_current_address;
    CrystalPreloader loader_sp_pan,loader_sp_adhar,loader_sp_registrationProof,loader_sp_bankStatement,loader_sp_gstr
                            ,loader_sp_sla,loader_sp_invoice,loader_sp_rentAggrement,loader_sp_itr,loader_sp_current_address;
    View view, view1, view2;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout upload_pdf, upload_from_camera, upload_from_gallery;
    String mSelectedDocFile, Pdf_name;
    private final int Request_Camera = 1;
    private final int Request_Gallery = 2;
    Uri imguri;
    boolean pan_uploaded, adhar_uploaded, itr_uploaded, gstr_uploaded,rental_uploaded, invoices_uploaded,sla_uploaded, bankstatement_uploaded, registration_proof_uploaded ,
            current_address = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_document_upload);

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
                openPermissionSettings(SPDocumentUploadActivity.this);
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

        sharedPrefsManager = new SharedPrefsManager(SPDocumentUploadActivity.this);
        view = getLayoutInflater().inflate(R.layout.bottomsheetdialog_sp, null);
        bottomSheetDialog = new BottomSheetDialog(SPDocumentUploadActivity.this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(true);

        upload_pdf = view.findViewById(R.id.upload_pdf_sp);
        upload_from_camera = view.findViewById(R.id.upload_from_camera_sp);
        upload_from_gallery = view.findViewById(R.id.upload_from_gallery_sp);
        view1 = view.findViewById(R.id.view1_sp);
        view2 = view.findViewById(R.id.view2_sp);

        image_sp_pan = findViewById(R.id.image_sp_pan);
        text_sp_pan = findViewById(R.id.text_sp_pan);
        loader_sp_pan = findViewById(R.id.loader_sp_pan);

        image_sp_adhar = findViewById(R.id.image_sp_adhar);
        text_sp_adhar = findViewById(R.id.text_sp_adhar);
        loader_sp_adhar = findViewById(R.id.loader_sp_adhar);

        image_sp_registrationProof = findViewById(R.id.image_sp_registrationProof);
        text_sp_registrationProof = findViewById(R.id.text_sp_registrationProof);
        loader_sp_registrationProof = findViewById(R.id.loader_sp_registrationProof);

        image_sp_bankStatement = findViewById(R.id.image_sp_bankStatement);
        text_sp_bankStatement = findViewById(R.id.text_sp_bankStatement);
        loader_sp_bankStatement = findViewById(R.id.loader_sp_bankStatement);

        image_sp_gstr = findViewById(R.id.image_sp_gstr);
        text_sp_gstr = findViewById(R.id.text_sp_gstr);
        loader_sp_gstr = findViewById(R.id.loader_sp_gstr);

        image_sp_sla = findViewById(R.id.image_sp_sla);
        text_sp_sla = findViewById(R.id.text_sp_sla);
        loader_sp_sla = findViewById(R.id.loader_sp_sla);

        image_sp_invoice = findViewById(R.id.image_sp_invoice);
        text_sp_invoice = findViewById(R.id.text_sp_invoice);
        loader_sp_invoice = findViewById(R.id.loader_sp_invoice);

        image_sp_rentAggrement = findViewById(R.id.image_sp_rentAggrement);
        text_sp_rentAggrement = findViewById(R.id.text_sp_rentAggrement);
        loader_sp_rentAggrement = findViewById(R.id.loader_sp_rentAggrement);

        image_sp_itr = findViewById(R.id.image_sp_itr);
        text_sp_itr = findViewById(R.id.text_sp_itr);
        loader_sp_itr = findViewById(R.id.loader_sp_itr);

        image_sp_itr = findViewById(R.id.image_sp_itr);
        text_sp_itr = findViewById(R.id.text_sp_itr);
        loader_sp_itr = findViewById(R.id.loader_sp_itr);

        image_sp_current_address = findViewById(R.id.image_sp_current_address);
        text_sp_current_address = findViewById(R.id.text_sp_current_address);
        loader_sp_current_address = findViewById(R.id.loader_sp_current_address);

        Submit_sp_docs = findViewById(R.id.Submit_sp_docs);

        sharedPrefsManager.setCHECK_PAGE("4");

        image_sp_pan.setOnClickListener(new View.OnClickListener() {
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

        image_sp_adhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "adhar";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_sp_registrationProof.setOnClickListener(new View.OnClickListener() {
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

        image_sp_bankStatement.setOnClickListener(new View.OnClickListener() {
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

        image_sp_gstr.setOnClickListener(new View.OnClickListener() {
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

        image_sp_sla.setOnClickListener(new View.OnClickListener() {
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

        image_sp_invoice.setOnClickListener(new View.OnClickListener() {
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

        image_sp_rentAggrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "rentaggrement";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                upload_from_camera.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                upload_from_gallery.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });

        image_sp_itr.setOnClickListener(new View.OnClickListener() {
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

        image_sp_current_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "current_address_proof";
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

                Intent intent4 = new Intent(SPDocumentUploadActivity.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 1);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
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

        Submit_sp_docs.setOnClickListener(new View.OnClickListener() {
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
                loader_sp_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "adhar":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_adhar.setVisibility(View.VISIBLE);
                code = "1";
                break;
            case "registration":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_registrationProof.setVisibility(View.VISIBLE);
                code = "22";
                break;
            case "bankstatement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_bankStatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_gstr.setVisibility(View.VISIBLE);
                code = "23";
                break;
            case "sla":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_sla.setVisibility(View.VISIBLE);
                code = "13";
                break;
            case "invoice":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "rentaggrement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_rentAggrement.setVisibility(View.VISIBLE);
                code = "24";
                break;
            case "itr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_itr.setVisibility(View.VISIBLE);
                code = "16";
                break;
            case "current_address_proof":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_current_address.setVisibility(View.VISIBLE);
                code = "3";
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
                    case "current_address_proof":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                current_address = true;
                                image_sp_current_address.setPadding(20, 20, 20, 20);
                                image_sp_current_address.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_current_address.setImageURI(imguri);
                                text_sp_current_address.setText(filename + ".png");
                                loader_sp_current_address.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Address Proof Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded && current_address)
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "pan":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pan_uploaded = true;
                                image_sp_pan.setPadding(20, 20, 20, 20);
                                image_sp_pan.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_pan.setImageURI(imguri);
                                text_sp_pan.setText(filename + ".png");
                                loader_sp_pan.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Pan Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                 && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "adhar":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adhar_uploaded = true;
                                image_sp_adhar.setPadding(20, 20, 20, 20);
                                image_sp_adhar.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_adhar.setImageURI(imguri);
                                text_sp_adhar.setText(filename + ".png");
                                loader_sp_adhar.setVisibility(View.GONE);
                                Toasty.success(getApplicationContext(), "Adhar Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }

                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                image_sp_registrationProof.setPadding(20, 20, 20, 20);
                                image_sp_registrationProof.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_registrationProof.setImageURI(imguri);
                                text_sp_registrationProof.setText(filename + ".png");
                                loader_sp_registrationProof.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Registration Proof Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                image_sp_bankStatement.setPadding(20, 20, 20, 20);
                                image_sp_bankStatement.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_bankStatement.setImageURI(imguri);
                                text_sp_bankStatement.setText(filename + ".png");
                                loader_sp_bankStatement.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Bank Statement Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                image_sp_gstr.setPadding(20, 20, 20, 20);
                                image_sp_gstr.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_gstr.setImageURI(imguri);
                                text_sp_gstr.setText(filename + ".png");
                                loader_sp_gstr.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Gstr Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "sla":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sla_uploaded = true;
                                image_sp_sla.setPadding(20, 20, 20, 20);
                                image_sp_sla.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_sla.setImageURI(imguri);
                                text_sp_sla.setText(filename + ".png");
                                loader_sp_sla.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Sla Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                image_sp_invoice.setPadding(20, 20, 20, 20);
                                image_sp_invoice.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_invoice.setImageURI(imguri);
                                text_sp_invoice.setText(filename + ".png");
                                loader_sp_invoice.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Invoice Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "rentaggrement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rental_uploaded = true;
                                image_sp_rentAggrement.setPadding(20, 20, 20, 20);
                                image_sp_rentAggrement.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_rentAggrement.setImageURI(imguri);
                                text_sp_rentAggrement.setText(filename + ".png");
                                loader_sp_rentAggrement.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Rent Agreement Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "itr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itr_uploaded = true;
                                image_sp_itr.setPadding(20, 20, 20, 20);
                                image_sp_itr.setScaleType(ImageView.ScaleType.FIT_XY);
                                image_sp_itr.setImageURI(imguri);
                                text_sp_itr.setText(filename + ".png");
                                loader_sp_itr.setVisibility(View.GONE);
                                Toasty.info(getApplicationContext(), "Itr Uploaded", Toast.LENGTH_SHORT).show();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
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
                loader_sp_pan.setVisibility(View.VISIBLE);
                code = "8";
                break;
            case "adhar":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_adhar.setVisibility(View.VISIBLE);
                code = "1";
                break;
            case "registration":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_registrationProof.setVisibility(View.VISIBLE);
                code = "22";
                break;
            case "bankstatement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_bankStatement.setVisibility(View.VISIBLE);
                code = "11";
                break;
            case "gstr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_gstr.setVisibility(View.VISIBLE);
                code = "23";
                break;
            case "sla":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_sla.setVisibility(View.VISIBLE);
                code = "13";
                break;
            case "invoice":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_invoice.setVisibility(View.VISIBLE);
                code = "14";
                break;
            case "rentaggrement":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_rentAggrement.setVisibility(View.VISIBLE);
                code = "24";
                break;
            case "itr":
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loader_sp_itr.setVisibility(View.VISIBLE);
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
                                text_sp_pan.setText(Pdf_name);
                                image_sp_pan.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_pan.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "adhar":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adhar_uploaded = true;
                                text_sp_adhar.setText(Pdf_name);
                                image_sp_adhar.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_adhar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "registration":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                registration_proof_uploaded = true;
                                text_sp_registrationProof.setText(Pdf_name);
                                image_sp_registrationProof.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_registrationProof.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "bankstatement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bankstatement_uploaded = true;
                                text_sp_bankStatement.setText(Pdf_name);
                                image_sp_bankStatement.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_bankStatement.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "gstr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gstr_uploaded = true;
                                text_sp_gstr.setText(Pdf_name);
                                image_sp_gstr.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_gstr.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "sla":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sla_uploaded = true;
                                text_sp_sla.setText(Pdf_name);
                                image_sp_sla.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_sla.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "invoice":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                invoices_uploaded = true;
                                text_sp_invoice.setText(Pdf_name);
                                image_sp_invoice.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_invoice.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "rentaggrement":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rental_uploaded = true;
                                text_sp_rentAggrement.setText(Pdf_name);
                                image_sp_rentAggrement.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_rentAggrement.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
                                }
                            }
                        });
                        break;
                    case "itr":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                itr_uploaded = true;
                                text_sp_itr.setText(Pdf_name);
                                image_sp_itr.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                                loader_sp_itr.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if ( registration_proof_uploaded && bankstatement_uploaded && gstr_uploaded
                                        && sla_uploaded && invoices_uploaded && rental_uploaded && itr_uploaded )
                                {
                                    Submit_sp_docs.setBackground(getDrawable(R.drawable.gradient_neocredit));
                                }
                                else
                                {
                                    Submit_sp_docs.setBackgroundColor(getResources().getColor(R.color.colorash));
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