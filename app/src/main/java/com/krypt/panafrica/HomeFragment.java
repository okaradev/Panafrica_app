package com.krypt.panafrica;

import static com.krypt.panafrica.utils.Urls.URL_ADD_CART;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.krypt.panafrica.databinding.FragmentHomeBinding;
import com.krypt.panafrica.utils.SessionHandler;
import com.krypt.panafrica.utils.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    public static final String TAG = "goods_adapter";


    private Button topay;
    private EditText nameofgoods, weight;
    private FragmentHomeBinding binding;
    private SessionHandler session;
    private UserModel user;
    ProgressDialog progressDialog;
    private String clientId = "";
    private String gweignt = "";
    private String gname = "";


    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        session = new SessionHandler(getContext());
        user = session.getUserDetails();
        clientId = user.getUserID();
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        nameofgoods = root.findViewById(R.id.edt_goodsnm);
        weight = root.findViewById(R.id.edt_weight);
        topay=root.findViewById(R.id.submitgood_btn);
        topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToPay();

            }
        });

        session = new SessionHandler(getContext());
        user = session.getUserDetails();


        return root;
    }


        public void addToPay() {
            final String sweight = weight.getText().toString().trim();
            final String sname = nameofgoods.getText().toString().trim();
            if(sweight.isEmpty()||sname.isEmpty()){
                Toast.makeText(getContext(), "You have to fill all the fields", Toast.LENGTH_SHORT).show();
            }else {
                progressShow();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_CART,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.i("tagconvertstradd", "[" + response + "]");
                                    Log.e("see", "[" + response + "]");
                                    Log.e("response ", response);
                                    String msg = jsonObject.getString("message");
                                    int status = jsonObject.getInt("status");
                                    if (status == 1) {
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "error1 " + msg);
                                        progressDialog.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "error" + e, Toast.LENGTH_SHORT).show();
                                   Log.e(TAG, "error2 " + e);
                                    progressDialog.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "error3 " + error);
                        progressDialog.dismiss();


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("clientID", clientId);

                        params.put("weight", sweight);
                        params.put("name", sname);


                        Log.e(TAG, "params is " + params);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        }
    public void progressShow() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Submitting your good details... ");
        progressDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}