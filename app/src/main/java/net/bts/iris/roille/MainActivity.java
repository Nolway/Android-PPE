package net.bts.iris.roille;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText pass;
    private Button connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.email = (EditText) findViewById(R.id.et_email);
        this.pass = (EditText) findViewById(R.id.et_password);
        this.connection = (Button) findViewById(R.id.b_connection);

        this.connection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_connection:
                final MainActivity leThis = this;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int contentType = HttpRequest.post("http://192.168.0.24:3000/techniciens/connexion").send("email=" + leThis.email.getText().toString() + "&mdp=" + leThis.pass.getText().toString()).code();
                        if (contentType == 200) {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    String content = HttpRequest.get("http://192.168.0.24:3000/techniciens/email/"+leThis.email.getText().toString()).body();
                                    JSONArray techniciensJson =null;
                                    String idTech = "0";
                                    try{
                                        techniciensJson = new JSONArray(content);
                                        for(int i = 0; i < techniciensJson.length(); i++){
                                            JSONObject technicienJson = techniciensJson.getJSONObject(i);
                                            idTech = (int) technicienJson.get("idTechnicien")+"";
                                            break;
                                        }
                                    }catch (JSONException e){

                                    }
                                    Intent unIntent = new Intent(leThis, ChoiceAction.class);
                                    unIntent.putExtra("idTech", idTech);
                                    startActivity(unIntent);
                                }
                            });
                        }else{
                           // Toast.makeText(leThis, "Les informations de connexion sont incorrectes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
