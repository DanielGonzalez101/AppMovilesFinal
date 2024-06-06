package com.example.marvelstudios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    public static final String dataUser = "dataUser";
    private static final int modoPrivate = Context.MODE_PRIVATE;
    TextView txtName, txtEmail, txtDate;
    Button btnLogout;
    String name, email, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDate = view.findViewById(R.id.txtDate);
        btnLogout = view.findViewById(R.id.btnLogout);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        email = sharedPreferences.getString("email", "");
        date = sharedPreferences.getString("date", "");
        txtName.setText(name);
        txtEmail.setText(email);
        txtDate.setText(date);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences configuration = getActivity().getSharedPreferences(dataUser, modoPrivate);
                configuration.edit().clear().commit();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }
}