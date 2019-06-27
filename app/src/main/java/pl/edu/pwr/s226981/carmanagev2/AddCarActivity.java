package pl.edu.pwr.s226981.carmanagev2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddCarActivity extends AppCompatActivity
{
    public static final String AUTO_DATA_NEW_CAR = "AUTO_DATA_NEW_CAR";
    public static final String IS_NEW_CAR_MASTER_CAR = "IS_NEW_CAR_MASTER_CAR";
    private EditText brand, color, model;
    private Switch masterCarSwitch;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_layout);

        brand = (EditText) findViewById(R.id.brandEditText);
        model = (EditText) findViewById(R.id.modelEditText);
        color = (EditText) findViewById(R.id.colorEditText);
        masterCarSwitch = (Switch) findViewById(R.id.master_car_switch);
        confirmButton = (Button) findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoInformation autoInformation = new AutoInformation(model.getText().toString(), brand.getText().toString(), color.getText().toString());
                Boolean isMasterCar = masterCarSwitch.isChecked();
                Intent intent = new Intent();
                intent.putExtra(AUTO_DATA_NEW_CAR, autoInformation);
                intent.putExtra(IS_NEW_CAR_MASTER_CAR, isMasterCar);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
