package com.satyam.dbmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
EditText et_name,et_age;
Button bt_viewAll,bt_add;
Switch sw_ActiveCustomer;
ListView lv_CustomerList;
DatabaseHelper databaseHelper;
ArrayAdapter arrayAdapter;
    CustomerModel customerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name=findViewById(R.id.et_name);
        et_age=findViewById(R.id.et_age);
        bt_viewAll=findViewById(R.id.btn_viewAll);
        bt_add=findViewById(R.id.btn_add);
        lv_CustomerList=findViewById(R.id.list_view);
        sw_ActiveCustomer=findViewById(R.id.sw_active);
        databaseHelper=new DatabaseHelper(MainActivity.this);
        AddallInListView(databaseHelper);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
               customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_ActiveCustomer.isChecked());
                    DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
                    boolean sucess = databaseHelper.addOne(customerModel);
                    Toast.makeText(MainActivity.this,"Successfully added ",Toast.LENGTH_SHORT).show();

                    AddallInListView(databaseHelper);
           }
           catch (Exception e){
               Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
               customerModel=new CustomerModel(-1,"error",0,false);
           }

                
            }
        });
        bt_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddallInListView(databaseHelper);
            }
        });
        lv_CustomerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel customerModel= (CustomerModel) parent.getItemAtPosition(position);
                databaseHelper.deleteOne(customerModel);
                AddallInListView(databaseHelper);
                Toast.makeText(MainActivity.this,"DELETED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    public void AddallInListView(DatabaseHelper databaseHelper2) {
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper2.getEveryone());
        lv_CustomerList.setAdapter(arrayAdapter);
    }

}