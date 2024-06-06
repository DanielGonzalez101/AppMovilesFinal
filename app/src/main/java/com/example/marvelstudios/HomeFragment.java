package com.example.marvelstudios;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marvelstudios.adaptadores.SuperheroAdaptador;
import com.example.marvelstudios.clases.Superhero;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SuperheroAdaptador.OnItemClickListener{

    List<Superhero> superheroList = new ArrayList<>();
    RecyclerView rcvSuperhero;
    SuperheroAdaptador adaptador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvSuperhero = view.findViewById(R.id.rcvSuperhero);

        rcvSuperhero.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new SuperheroAdaptador(superheroList);
        rcvSuperhero.setAdapter(adaptador);
        adaptador.setOnItemClickListener(this);

        loadInfo();
        return view;
    }
    private void loadInfo() {
        String url="https://gateway.marvel.com:443/v1/public/characters?ts=1&apikey=95de3fa89516f96fbb4ff8b6e89880b9&hash=e20fcd1090dd331853efd97dd122f323";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    receiveResponse(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(requireContext());
        rq.add(myRequest);
    }

    private void receiveResponse(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject("data");
            for (int i =0; i<data.getJSONArray("results").length(); i++){
                String name = data.getJSONArray("results").getJSONObject(i).getString("name");
                String id = data.getJSONArray("results").getJSONObject(i).getString("id");
                String image = data.getJSONArray("results").getJSONObject(i).getJSONObject("thumbnail").getString("path") + "." + data.getJSONArray("results").getJSONObject(i).getJSONObject("thumbnail").getString("extension");
                Superhero s = new Superhero(name, id, image);
                superheroList.add(s);
            }
            adaptador.notifyDataSetChanged();
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onItemClick(Superhero selectedSuperhero) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("name", selectedSuperhero.getName());
        i.putExtra("id", selectedSuperhero.getId());
        i.putExtra("image", selectedSuperhero.getImage());
        startActivity(i);
    }
}