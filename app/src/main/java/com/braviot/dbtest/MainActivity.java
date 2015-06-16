package com.braviot.dbtest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    EditText edName;
    EditText edAge;
    Button btn;
    UserItem uu;
    UserDAO userDAO;

    ListView lv;

    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<HashMap<String, String>>();
        userDAO = new UserDAO(getApplicationContext());

        edAge = (EditText)findViewById(R.id.editText2);
        edName = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.button);
        lv = (ListView)findViewById(R.id.listView1);

        btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                uu = new UserItem(edName.getText().toString(), Integer.parseInt(edAge.getText().toString()));

                userDAO.insert(uu);
                updateUserList();
            }
        });


        updateUserList();
    }


    private void updateUserList(){

        int userCount = 0;
        if(userDAO.getCount() == 0)
            return;

        arrayList.clear();
        userCount = userDAO.getCount();

        for (int i = 0; i < userCount ; i++) {
            HashMap<String, String> item = new HashMap<String, String>();

            uu = userDAO.get((long)i+1);
            item.put("Name",uu.getName());
            item.put("Age", Integer.toString(uu.getAge()));// keyItem.keyType

            arrayList.add(item);
        }

        simpleAdapter = new SimpleAdapter(this,
                arrayList, R.layout.simple_adapter, new String[]{ "Name", "Age"},
                new int[]{R.id.nameL, R.id.ageL});

        lv.setAdapter(simpleAdapter);
    }


}
