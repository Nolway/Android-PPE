package net.bts.iris.roille;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMaterial extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText montantMat, nomMat;
    private Spinner typeMatSpin;
    private int typeMat;
    private String idTech;
    private ArrayList<String> typesMat;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        this.montantMat = (EditText) findViewById(R.id.et_montantMat);
        this.nomMat = (EditText) findViewById(R.id.et_nomMat);
        this.save = (Button) findViewById(R.id.b_enrMat);
        this.save.setOnClickListener(this);
        this.typeMatSpin = (Spinner) findViewById(R.id.sp_typeMat);
        this.typeMatSpin.setOnItemSelectedListener(this);

        this.typesMat = new ArrayList<String>();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, this.typesMat);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.typeMatSpin.setAdapter(spinnerArrayAdapter);


        final AddMaterial leThis = this;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String content = HttpRequest.get("http://192.168.0.24:3000/typeMateriel").body();
                JSONArray originesJson =null;
                try{
                    originesJson = new JSONArray(content);
                    for(int i = 0; i < originesJson.length(); i++){
                        JSONObject origineJson = originesJson.getJSONObject(i);
                        leThis.getTypesMat().add((String) origineJson.get("designation"));
                    }
                }catch (JSONException e){

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_enrMat:
                final AddMaterial leThis = this;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int contentType = HttpRequest.post("http://192.168.0.24:3000/materiels/").send(
                                "montantCaution=" + leThis.montantMat.getText().toString() +
                                        "&idTypeMateriel=" + leThis.typeMat +
                                        "&libelle=" + leThis.nomMat.getText().toString()).code();
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

    public ArrayList<String> getTypesMat() {
        return typesMat;
    }

    public void setTypesMat(ArrayList<String> typesMat) {
        this.typesMat = typesMat;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.typeMat = 1;
    }
}
