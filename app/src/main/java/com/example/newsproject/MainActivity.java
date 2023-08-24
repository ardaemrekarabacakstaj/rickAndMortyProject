package com.example.newsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ListView listView;
    private ArrayList<String> statuS;
    private ArrayList<String> nameS;
    private ArrayList<String> imageS;
    private ArrayList<String> typeS;

    EditText txt;
    Button btn;

    private final int TOTAL_PAGES = 10;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        nameS = new ArrayList<>();
        imageS = new ArrayList<>();
        statuS = new ArrayList<>();
        typeS = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        txt = findViewById(R.id.editTextTextPersonName);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> {
            aramaSorgu();
        });
        verileriAl();
    }
    private void aramaSorgu() {
        String searchTerm = txt.getText().toString().trim();
        if (searchTerm.isEmpty()) {
            verileriAl();
        } else {
            String url = "https://rickandmortyapi.com/api/character/?name=" + searchTerm;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        nameS.clear();
                        imageS.clear();
                        statuS.clear();
                        typeS.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject resultsObject = jsonArray.getJSONObject(i);
                            String name = resultsObject.getString("name");
                            String status = resultsObject.getString("status");
                            String image = resultsObject.getString("image");
                            String type = resultsObject.getString("type");

                            nameS.add(name);
                            imageS.add(image);
                            statuS.add(status);
                            typeS.add(type);
                        }

                        Adapter adapter = new Adapter(getApplicationContext(), nameS, imageS, statuS);
                        listView.setAdapter(adapter);
                        clickCharacter(listView);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, Throwable::printStackTrace);
            requestQueue.add(jsonObjectRequest);
        }
    }
    private void verileriAl() {
        if (currentPage > TOTAL_PAGES) {
            Adapter adapter = new Adapter(getApplicationContext(), nameS, imageS, statuS);
            listView.setAdapter(adapter);
            clickCharacter(listView);
            return;
        }

        String url = "https://rickandmortyapi.com/api/character/?page=" + currentPage;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject resultsObject = jsonArray.getJSONObject(i);
                        String name = resultsObject.getString("name");
                        String status = resultsObject.getString("status");
                        String image = resultsObject.getString("image");
                        String type = resultsObject.getString("type");

                        nameS.add(name);
                        imageS.add(image);
                        statuS.add(status);
                        typeS.add(type);
                    }

                    currentPage++;
                    verileriAl();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, Throwable::printStackTrace);
        requestQueue.add(jsonObjectRequest);
    }
    private void clickCharacter(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickCname = nameS.get(position);
                String clickCstatus = statuS.get(position);
                String clickCimage = imageS.get(position);
                String clickCtype = typeS.get(position);

                Intent intent = new Intent(MainActivity.this,IntentIncele.class);
                intent.putExtra("name",clickCname);
                intent.putExtra("status",clickCstatus);
                intent.putExtra("image",clickCimage);
                intent.putExtra("type",clickCtype);
                startActivity(intent);
            }
        });
    }
}