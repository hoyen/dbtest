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
    EditText edMaster;
    Button btn;
    Button btn1;
    UserItem uu;
    UserDAO userDAO;

    LockItem lockItem;
    LockDAO lockDAO;


    ListView lv;
    ListView ll;

    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, String>> arrayList;

    private SimpleAdapter simpleAdapter1;
    private ArrayList<HashMap<String, String>> arrayList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<HashMap<String, String>>();
        userDAO = new UserDAO(getApplicationContext());

        arrayList1 = new ArrayList<HashMap<String, String>>();
        lockDAO = new LockDAO(getApplicationContext());

        edAge = (EditText)findViewById(R.id.editText2);
        edName = (EditText)findViewById(R.id.editText);
        edMaster = (EditText)findViewById(R.id.editText3);
        btn = (Button)findViewById(R.id.button);
        btn1 = (Button)findViewById(R.id.button2);
        lv = (ListView)findViewById(R.id.listView1);
        ll = (ListView)findViewById(R.id.listView);

        btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                lockItem = new LockItem(edName.getText().toString());
                lockItem.setLockID(Integer.parseInt(edAge.getText().toString()));
                lockItem.setMasterCode(Integer.parseInt(edMaster.getText().toString()));

                //TODO: empty check

                lockDAO.insert(lockItem);
                updateLocklist();
            }
        });

        btn1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                uu = new UserItem(edName.getText().toString(), Integer.parseInt(edAge.getText().toString()));
                //TODO: empty check
                userDAO.insert(uu);
                updateUserList();
            }
        });


        updateUserList();
        updateLocklist();
    }

    private void updateLocklist(){

        int lockCount = 0;
        if(lockDAO.getCount() == 0)
            return;

        arrayList1.clear();
        lockCount = lockDAO.getCount();

        for (int i = 0; i < lockCount ; i++) {
            HashMap<String, String> item = new HashMap<String, String>();

            lockItem = lockDAO.get((long)i+1);
            item.put("Name",lockItem.getLockName());
            item.put("Age", Integer.toString(lockItem.getLockID()));// keyItem.keyType

            arrayList1.add(item);
        }

        simpleAdapter1 = new SimpleAdapter(this,
                arrayList1, R.layout.simple_adapter, new String[]{ "Name", "Age"},
                new int[]{R.id.nameL, R.id.ageL});

        ll.setAdapter(simpleAdapter1);
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
