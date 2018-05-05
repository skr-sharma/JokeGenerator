package com.icndb.joke;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Model.JokesBean;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.helper.AppSingleton;
import com.icndb.Adapter.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JokeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtFirstName, edtLastName;
    private TextView btnSubmit;
    private LinearLayout layoutLoader;
    public static ArrayList<JokesBean> JokeArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initXmlViews();
    }

    private void initXmlViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        layoutLoader = (LinearLayout) findViewById(R.id.layoutLoader);
        layoutLoader.setVisibility(View.GONE);

    }

    public void randomJokesApiTask(String firstNameStr, String lastNameStr) {
        layoutLoader.setVisibility(View.VISIBLE);
        String url = AppSingleton.baseUrl;
        String jokesCount = AppSingleton.jokesCount;
        String completeUrlJokes = url + jokesCount + "firstName=" + firstNameStr + "&" + "lastName=" + lastNameStr;
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, completeUrlJokes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    layoutLoader.setVisibility(View.GONE);
                    Log.e("response ", response + "");
                    if (response != null) {
                        JokeArrayList = new ArrayList<>();
                        JSONObject jMainObj = new JSONObject(response);
                        String typeStr = jMainObj.getString("type");
                        if (typeStr.equalsIgnoreCase("success")) {
                            JSONArray jValueAry = jMainObj.getJSONArray("value");
                            for (int i = 0; i < jValueAry.length(); i++) {
                                try {
                                    JSONObject jObj = jValueAry.getJSONObject(i);
                                    JokesBean jokesBean = new JokesBean();
                                    String id = jObj.optString("id");
                                    String joke = jObj.optString("joke");
                                    jokesBean.setId(id);
                                    jokesBean.setJokes(joke);
                                    JokeArrayList.add(jokesBean);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (JokeArrayList.size() > 0) {
                            startActivity(new Intent(getApplicationContext(), JokeDetailsActivity.class));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                layoutLoader.setVisibility(View.GONE);
                VolleyLog.d("Error: " + error.getMessage());
            }
        });

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq, "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (edtFirstName != null) {
            edtFirstName.setText("");
            edtLastName.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                validateInputFields();
                break;
        }
    }

    private void validateInputFields() {

        hideKeyboard();
        String firstNameStr = edtFirstName.getText().toString().trim();
        String lastNameStr = edtLastName.getText().toString().trim();
        if (TextUtils.isEmpty(firstNameStr)) {
            edtFirstName.setError("Please enter first name");
            return;
        }

        if (TextUtils.isEmpty(lastNameStr)) {
            edtLastName.setError("Please enter last name");
            return;
        }
        randomJokesApiTask(firstNameStr, lastNameStr);
    }

    private void hideKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}