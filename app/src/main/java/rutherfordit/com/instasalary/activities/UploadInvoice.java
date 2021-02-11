package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class UploadInvoice extends AppCompatActivity implements ResponseHandler {

    TextInputEditText enterAmount;
    RelativeLayout submitInvoice;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    boolean click = false;
    CardView loader_invoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_invoice);

        enterAmount = findViewById(R.id.enterAmount);
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        volleyRequest = new VolleyRequest();
        submitInvoice = findViewById(R.id.submitInvoice);
        loader_invoice = findViewById(R.id.loader_invoice);

        textWatcher();

        submitInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loanApi();
                loader_invoice.setVisibility(View.VISIBLE);
            }
        });
    }

    private void textWatcher() {
        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!enterAmount.getText().toString().equals(""))
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
            }
        }
    }
}