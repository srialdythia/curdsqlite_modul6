package com.example.nameandhobby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    private List<DataModel> dataList;
    EditText namaEt,hobbyEt, updateNamaEt, updateHobbyEt;
    Button addBtn, updateBtn, cancelBtn;
    RecyclerView recyclerView;
    DataItemAdapter dataItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaEt = findViewById(R.id.nama_et);
        hobbyEt = findViewById(R.id.hobby_et);
        addBtn = findViewById(R.id.add_btn);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

        dataList = databaseHelper.getEveryone();
        dataItemAdapter = new DataItemAdapter(MainActivity.this, dataList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataItemAdapter);

        dataItemAdapter.setOnItemClickListener(new DataItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View myView = inflater.inflate(R.layout.input_layout,null);
                myDialog.setView(myView);

                final AlertDialog dialog = myDialog.create();
                dialog.setCancelable(false);

                updateBtn = myView.findViewById(R.id.save_btn);
                cancelBtn = myView.findViewById(R.id.cancel_btn);
                updateNamaEt = myView.findViewById(R.id.updateNama_et);
                updateHobbyEt = myView.findViewById(R.id.updateHobby_et);

                updateNamaEt.setText(dataList.get(position).getNama());
                updateHobbyEt.setText(dataList.get(position).getHobby());

                updateBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        DataModel data = dataList.get(position);
                        boolean result = databaseHelper.updateData(Integer.toString(data.getId()),updateNamaEt.getText().toString(),updateHobbyEt.getText().toString());
                        Toast.makeText(MainActivity.this, "Update" + Boolean.toString(result), Toast.LENGTH_SHORT).show();
                        dataList.get(position).changeString(updateNamaEt.getText().toString(),updateHobbyEt.getText().toString());
                        dataItemAdapter.notifyItemChanged(position);

                        dialog.dismiss();
                    }
                });
//                Cancel Button
                cancelBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

            @Override
            public void onDeleteClick(int position) {
                System.out.println("delete btn");
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                DataModel datamodel = databaseHelper.getEveryone().get(position);
                boolean result = databaseHelper.deleteOne(datamodel);
                dataList.remove(position);
                dataItemAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataModel dataModel;
                try{
                    dataModel = new DataModel(1,namaEt.getText().toString(),hobbyEt.getText().toString());

                }catch (Exception e){
                    dataModel = new DataModel(-1,"","");
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean result = databaseHelper.addOne(dataModel);
                String resultString =  result == true ? namaEt.getText().toString()+" berhasil ditambahkan":"Failed";
                if(result){
                    dataModel = databaseHelper.getEveryone().get(dataList.size());
                    dataList.add(databaseHelper.getEveryone().size() - 1,dataModel);
                    dataItemAdapter.notifyItemInserted(dataList.size());
                    Toast.makeText(MainActivity.this,resultString,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}