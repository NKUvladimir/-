package com.example.administrator.huarongdao;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Resources resourse = this.getResources();
        String[] data = resourse.getStringArray(R.array.title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SelectActivity.this, android.R.layout.simple_list_item_1, data
        );
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        final Intent intent = new Intent(SelectActivity.this, MainActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                    case 9:
                        intent.putExtra("Mode",position);
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}
