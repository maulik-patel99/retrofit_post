package com.example.retrofit_post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
List<model> models=new ArrayList<model>();
RecyclerView recyclerView;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycleview);
        Retrofit retro=new Retrofit.Builder().
                baseUrl("https://mutable-villages.000webhostapp.com/").
                addConverterFactory(ScalarsConverterFactory.create()).build();

        RetrofitInterface retrofitInterface=retro.create(RetrofitInterface.class);
        Call<String> stringCall=retrofitInterface.data("dev");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("response", response.body());
                try {

                    JSONObject jsonObject=new JSONObject(response.body());
                    JSONArray array=jsonObject.getJSONArray("user");
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                   model model=new model();
                   model.setName(object.getString("name"));
                   model.setEmail(object.getString("email"));
                   models.add(model);
                    }
RecycleviewAdapter recycleviewAdapter=new RecycleviewAdapter(models);
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
recyclerView.setAdapter(recycleviewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
Log.d("error", String.valueOf(t));
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
