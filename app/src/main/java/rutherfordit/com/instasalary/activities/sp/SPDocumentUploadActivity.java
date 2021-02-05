package rutherfordit.com.instasalary.activities.sp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;

public class SPDocumentUploadActivity extends AppCompatActivity {

    String status;
    RelativeLayout Submit_sp_docs;
    ImageView image_sp_pan,image_sp_adhar,image_sp_registrationProof,image_sp_bankStatement,image_sp_gstr,image_sp_sla
                            ,image_sp_invoice,image_sp_rentAggrement,image_sp_itr;
    TextView text_sp_pan,text_sp_adhar,text_sp_registrationProof,text_sp_bankStatement,text_sp_gstr,text_sp_sla
                            ,text_sp_invoice,text_sp_rentAggrement,text_sp_itr;
    CrystalPreloader loader_sp_pan,loader_sp_adhar,loader_sp_registrationProof,loader_sp_bankStatement,loader_sp_gstr
                            ,loader_sp_sla,loader_sp_invoice,loader_sp_rentAggrement,loader_sp_itr;
    View view, view1, view2;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout upload_pdf, upload_from_camera, upload_from_gallery;
    String mSelectedDocFile, Pdf_name;
    private final int Request_Camera = 1;
    private final int Request_Gallery = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_document_upload);

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

        Submit_sp_docs = findViewById(R.id.Submit_sp_docs);

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
                view1.setVisibility(View.GONE);
                upload_from_camera.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                upload_from_gallery.setVisibility(View.GONE);
                bottomSheetDialog.show();
            }
        });

        image_sp_bankStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "bankstatement";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                upload_from_camera.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                upload_from_gallery.setVisibility(View.GONE);
                bottomSheetDialog.show();
            }
        });

        image_sp_gstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "gstr";
                upload_pdf.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                upload_from_camera.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                upload_from_gallery.setVisibility(View.GONE);
                bottomSheetDialog.show();
            }
        });

        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(SPDocumentUploadActivity.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 1);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
                startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                bottomSheetDialog.cancel();
            }
        });

        upload_from_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Request_Camera);
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
                Intent i = new Intent(getApplicationContext(),SpCreditRequirementsActivity.class);
                startActivity(i);
            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {
            if (resultCode == RESULT_OK) {

                assert data != null;
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                assert list != null;
                NormalFile Url = (list.get(0));
                mSelectedDocFile = Url.getPath();
                Pdf_name = Url.getName();

                text_sp_pan.setText(Pdf_name);
                image_sp_pan.setImageDrawable(getResources().getDrawable(R.drawable.pdfseticon));
                loader_sp_pan.setVisibility(View.GONE);

                Toasty.success(getApplicationContext(), "PAN Uploaded", Toast.LENGTH_SHORT).show();


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
                        image_sp_pan.setImageBitmap(bitmap);
                    }
                    break;

                case RESULT_CANCELED:
                    break;
            }
        } else if (requestCode == Request_Gallery) {
            switch (resultCode) {
                case RESULT_OK:

                    if (data != null) {

                        Uri selectedImage = data.getData();
                        assert selectedImage != null;
                        File file = new File(Objects.requireNonNull(selectedImage.getPath()));
                        String filename = file.getName();
                        image_sp_pan.setImageURI(selectedImage);
                        text_sp_pan.setText(filename);
                    }
                    break;

                case RESULT_CANCELED:
                    break;
            }
        }

    }

}