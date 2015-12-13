package ca.qc.bdeb.p45.runningtracker.View;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ca.qc.bdeb.p45.runningtracker.BD.DBHelper;
import ca.qc.bdeb.p45.runningtracker.Modele.Objectif;
import ca.qc.bdeb.p45.runningtracker.R;

public class Nouvel_Objectif extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    Switch objectifType;
    TextView distanceInitial;
    TextView distanceFinal;
    Button sauvegarder;
    DBHelper db = DBHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvel_objectif);
        distanceInitial = (TextView) this.findViewById(R.id.NouvelObjectif_TextView_DistanceInitial);
        distanceFinal = (TextView) this.findViewById(R.id.NouvelObjectif_TextView_DistanceFinal);
        objectifType = (Switch) this.findViewById(R.id.NouvelObjectif_Switch_Evolutif);
        objectifType.setChecked(true);
        sauvegarder = (Button) this.findViewById(R.id.NouvelObjectf_Bouton_Save);

        objectifType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                distanceInitial.setEnabled(isChecked);
            }
        });

        this.findViewById(R.id.NouvelObjectif_boutton_PickDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Nouvel_Objectif.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objectif objectif = new Objectif();
                try {
                    objectif.setObjectifDateCommencement(new Date());
                    objectif.setObjectifDateFinal(myCalendar.getTime());
                    if (objectifType.isChecked()) {
                        objectif.setObjectifInitiale(Integer.parseInt(distanceInitial.getText().toString()));
                        objectif.setObjectifFinale(Integer.parseInt(distanceFinal.getText().toString()));
                    } else {
                        objectif.setObjectifInitiale(Integer.parseInt(distanceFinal.getText().toString()));
                        objectif.setObjectifFinale(Integer.parseInt(distanceFinal.getText().toString()));
                    }
                    db.ajouterObjectif(objectif);
                    finish();
                } catch (Exception e){
                    Toast.makeText(getApplication(), getString(R.string.Erreur), Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateLabel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nouvel__objectif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ((TextView)this.findViewById(R.id.NouvelObjectf_textView_Date)).setText(sdf.format(myCalendar.getTime()));
    }

}
