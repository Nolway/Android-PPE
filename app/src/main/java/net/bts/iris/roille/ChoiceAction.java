package net.bts.iris.roille;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChoiceAction extends AppCompatActivity implements View.OnClickListener {

    private Button addInter, addMaterial, listMaterials;
    private String idTech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_action);

        Intent myIntent = getIntent(); // gets the previously created intent
        this.idTech = myIntent.getStringExtra("idTech");

        this.addInter = (Button) findViewById(R.id.b_addint);
        this.addInter.setOnClickListener(this);
        this.addMaterial = (Button) findViewById(R.id.b_addmat);
        this.addMaterial.setOnClickListener(this);
        this.listMaterials = (Button) findViewById(R.id.b_listmat);
        this.listMaterials.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent unIntent;
        switch(view.getId()){
            case R.id.b_addint:
                unIntent = new Intent(this, AddIntervention.class);
                unIntent.putExtra("idTech", this.idTech);
                startActivity(unIntent);
                break;
            case R.id.b_addmat:
                unIntent = new Intent(this, AddMaterial.class);
                unIntent.putExtra("idTech", this.idTech);
                startActivity(unIntent);
                break;
            case R.id.b_listmat:
                unIntent = new Intent(this, MaterialList.class);
                unIntent.putExtra("idTech", this.idTech);
                startActivity(unIntent);
                break;
        }
    }
}
