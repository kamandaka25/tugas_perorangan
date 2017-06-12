package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import id.sch.smktelkom_mlg.privateassignment.xirpl236.individu.DatabaseAccess;
public class EditActivity extends ActionBarActivity {
    private EditText etText;
    private Button btnSave;
    private Button btnCancel;
    private Noteview noteview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText = (EditText) findViewById(R.id.etText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            noteview = (Noteview) bundle.get("MEMO");
            if(noteview != null) {
                this.etText.setText(noteview.getText());
            }
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
    }

    public void onSaveClicked() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        if(noteview == null) {
            // Add new memo
            Noteview temp = new Noteview();
            temp.setText(etText.getText().toString());
            databaseAccess.save(temp);
        } else {
            // Update the memo
            noteview.setText(etText.getText().toString());
            databaseAccess.update(noteview);
        }
        databaseAccess.close();
        this.finish();
    }

    public void onCancelClicked() {
        this.finish();
    }
}