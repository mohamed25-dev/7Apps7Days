package com.example.moham.todo;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ListView listTask;
    private List<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        listTask = findViewById(R.id.tasksList);

        listTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String task = tasks.get(i);
                dbHelper.deleteTask(task);

                loadTasksList();

                return true;
            }
        });

        loadTasksList();

    }

    private void loadTasksList () {
        tasks = dbHelper.getTasks();
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.textView, tasks);
            listTask.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(tasks);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menue, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddTask:
                final EditText editText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add Task")
                        .setMessage("Write Your Next Task")
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task = editText.getText().toString();
                                dbHelper.insertTask(task);
                                loadTasksList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
