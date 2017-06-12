package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import id.sch.smktelkom_mlg.privateassignment.xirpl236.individu.DatabaseAccess;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView listView;
    private Button btnAdd;
    private DatabaseAccess databaseAccess;
    private List<Noteview> noteviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.databaseAccess = DatabaseAccess.getInstance(this);

        this.listView = (ListView) findViewById(R.id.listView);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Noteview memo = noteviews.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (memo.isFullDisplayed()) {
                    txtMemo.setText(memo.getShortText());
                    memo.setFullDisplayed(false);
                } else {
                    txtMemo.setText(memo.getText());
                    memo.setFullDisplayed(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        this.noteviews = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(this, noteviews);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(Noteview noteview) {
        databaseAccess.open();
        databaseAccess.delete(noteview);
        databaseAccess.close();

        ArrayAdapter<Noteview> adapter = (ArrayAdapter<Noteview>) listView.getAdapter();
        adapter.remove(noteview);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(Noteview noteview) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("MEMO", noteview);
        startActivity(intent);
    }

    private class MemoAdapter extends ArrayAdapter<Noteview> {


        public MemoAdapter(Context context, List<Noteview> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtMemo = (TextView) convertView.findViewById(R.id.txtMemo);

            final Noteview noteview = noteviews.get(position);
            noteview.setFullDisplayed(false);
            txtDate.setText(noteview.getDate());
            txtMemo.setText(noteview.getShortText());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(noteview);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(noteview);
                }
            });
            return convertView;
        }
    }
}