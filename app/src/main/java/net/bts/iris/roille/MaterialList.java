package net.bts.iris.roille;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MaterialList extends AppCompatActivity {

    private ArrayList<String> materialListString;
    private ArrayList<String> materialListMontantString;
    private ListView materialsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        this.materialListString = new ArrayList<String>();
        this.materialListMontantString = new ArrayList<String>();

        this.materialsListView = (ListView) findViewById(R.id.listMat);
        ArrayAdapter<String> itemsAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.materialListString);
        ArrayAdapter<String> itemsAdapter2 =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, this.materialListMontantString);
        this.materialsListView.setAdapter(itemsAdapter);
        //this.materialsListView.setAdapter(itemsAdapter2);

        final MaterialList leThis = this;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String content = HttpRequest.get("http://192.168.0.24:3000/materiels").body();
                JSONArray materialsJson =null;
                try{
                    materialsJson = new JSONArray(content);
                    for(int i = 0; i < materialsJson.length(); i++){
                        JSONObject materialJson = materialsJson.getJSONObject(i);
                        leThis.getMaterialListString().add((String) materialJson.get("libelle"));
                        leThis.getMaterialListMontantString().add("Prix Caution : " + materialJson.get("montantCaution"));
                    }
                }catch (JSONException e){

                }
            }
        });
    }

    public ArrayList<String> getMaterialListString() {
        return materialListString;
    }

    public void setMaterialListString(ArrayList<String> materialListString) {
        this.materialListString = materialListString;
    }

    public ArrayList<String> getMaterialListMontantString() {
        return materialListMontantString;
    }

    public void setMaterialListMontantString(ArrayList<String> materialListMontantString) {
        this.materialListMontantString = materialListMontantString;
    }
}
