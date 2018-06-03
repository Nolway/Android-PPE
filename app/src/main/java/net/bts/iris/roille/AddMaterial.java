package net.bts.iris.roille;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMaterial extends AppCompatActivity implements View.OnClickListener {

    private EditText montantMat, nomMat;
    private Spinner typeMat;
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
        this.typeMat = (Spinner) findViewById(R.id.sp_typeMat);

        this.typesMat = new ArrayList<String>();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, this.typesMat);
        this.typeMat.setAdapter(spinnerArrayAdapter);


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
                                        "&idTypeMateriel=" + (leThis.typeMat.getSelectedItemPosition()+1) +
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
}
