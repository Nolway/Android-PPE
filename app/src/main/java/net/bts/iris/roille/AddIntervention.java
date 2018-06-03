package net.bts.iris.roille;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddIntervention extends AppCompatActivity implements View.OnClickListener {

    private String idTech;
    private EditText idContrat, dtInter, hInter, durInter, comInter;
    private ArrayList<String> origines;
    private Spinner orgInter;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        Intent myIntent = getIntent(); // gets the previously created intent
        this.idTech = myIntent.getStringExtra("idTech");

        this.idContrat = (EditText) findViewById(R.id.et_idContrat);
        this.dtInter = (EditText) findViewById(R.id.et_dateInter);
        this.hInter = (EditText) findViewById(R.id.et_heureInter);
        this.durInter = (EditText) findViewById(R.id.et_dureeInter);
        this.comInter = (EditText) findViewById(R.id.et_comInter);
        this.orgInter = (Spinner) findViewById(R.id.sp_orgInter);

        this.save = (Button) findViewById(R.id.b_enrInter);
        this.save.setOnClickListener(this);

        this.origines = new ArrayList<String>();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.origines);
        this.orgInter.setAdapter(spinnerArrayAdapter);


        final AddIntervention leThis = this;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String content = HttpRequest.get("http://192.168.0.24:3000/origines").body();
                JSONArray originesJson =null;
                try{
                    originesJson = new JSONArray(content);
                    for(int i = 0; i < originesJson.length(); i++){
                        JSONObject origineJson = originesJson.getJSONObject(i);
                        leThis.getOrigines().add((String) origineJson.get("libelle"));
                    }
                }catch (JSONException e){

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_enrInter:
                final AddIntervention leThis = this;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int contentType = HttpRequest.post("http://192.168.0.24:3000/interventions/").send(
                                                "dateHeureIntervention=" + leThis.dtInter.getText().toString().replace("/", "-") + " " + leThis.hInter.getText().toString() +
                                                "&tempsHeures=" + leThis.durInter.getText().toString() +
                                                "&commentaire=" + leThis.comInter.getText().toString() +
                                                "&idTechnicien=" + leThis.idTech +
                                                "&idContrat=" + leThis.idContrat.getText().toString() +
                                                "&idOrigine=" + (leThis.orgInter.getSelectedItemPosition()+1)).code();
                        if (contentType == 200) {
                            Intent unIntent = new Intent(leThis, ChoiceAction.class);
                            unIntent.putExtra("idTech", leThis.idTech);
                            startActivity(unIntent);
                        }else{
                            // Toast.makeText(leThis, "Les informations de connexion sont incorrectes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    public ArrayList<String> getOrigines() {
        return origines;
    }

    public void setOrigines(ArrayList<String> origines) {
        this.origines = origines;
    }
}
