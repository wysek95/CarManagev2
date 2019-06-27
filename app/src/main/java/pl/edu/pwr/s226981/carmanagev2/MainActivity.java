package pl.edu.pwr.s226981.carmanagev2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final int CAR_CODE = 12345;
    public static final int TANK_CODE = 1234;
    public static final String ADD_DATA = "ADD_DATA";

    private Button goToTankButton, goToAddCarButton;;
    private Spinner autoChooser;
    private ArrayList<AutoInformation> autoList;
    private ArrayAdapter<AutoInformation> arrayAdapter;
    private TextView burningTextViewEdit;
    private AutoInformation autoInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initialization();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // burningTextViewEdit.setText(calculate());
    }

    private void initialization() {
        goToTankButton = (Button) findViewById(R.id.go_to_tank_buttton);
        goToAddCarButton = (Button) findViewById(R.id.add_car_button);
        autoChooser = (Spinner) findViewById(R.id.auto_choose_spinner);

        burningTextViewEdit = findViewById(R.id.burning_Text_View_Edit);
        burningTextViewEdit.setText("0.00L / 100km");

        initAutoList();
        initArrayAdapter();
        autoChooser.setAdapter(arrayAdapter);

        goToTankButton.setOnClickListener(goToTankingActivity());
        goToAddCarButton.setOnClickListener(goToAddCarActivity());
    }

    private String calculate()
    {
        if(autoInformation.getTankingRecord().size() >= 2)
        {
            Float consumption = 0F;
            List<TankingRecord> tankingRecord = autoInformation.getTankingRecord();
            for(int i = 0; i < tankingRecord.size() - 1; i++)
            {
                consumption += tankingRecord.get(i).getLiters();
            }
            return String.format( consumption / getMileageDiff() + " L/100km");
        }
        else
        {
            return "N/A";
        }

    }

    private long getMileageDiff()
    {
        long mileageDiff = 0;
        if(autoInformation.getTankingRecord().size() >= 2)
        {
            Collections.sort(autoInformation.getTankingRecord(), (o1, o2) -> o1.getTankUpDate().compareTo(o2.getTankUpDate()));
            mileageDiff = autoInformation.getTankingRecord().get(autoInformation.getTankingRecord().size() - 1).getMileage() - autoInformation.getTankingRecord().get(0).getMileage();
        }
        return mileageDiff;
    }


    private View.OnClickListener goToAddCarActivity()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
                startActivityForResult(intent, CAR_CODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
       if(requestCode == CAR_CODE)
       {
           if(resultCode == Activity.RESULT_OK)
           {
               if(data != null)
               {
                   AutoInformation newAutodata = (AutoInformation) data.getExtras().get(AddCarActivity.AUTO_DATA_NEW_CAR);
                   Boolean isNewCarMasterCar = (Boolean) data.getExtras().get(AddCarActivity.IS_NEW_CAR_MASTER_CAR);
                   if (isNewCarMasterCar != null && isNewCarMasterCar)   //Sprawdza czy jest zaznaczony przekaźnik czy główny samochód i czy w ogóle wpisana jest jakaś wartość
                   {
                       autoList.add(0, newAutodata);
                   }
                   else
                   {
                       autoList.add(newAutodata);
                   }
               }
           }
       }
        if(requestCode == TANK_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data != null)
                {
                    TankingRecord newTankUp = (TankingRecord) data.getExtras().get(TankingActivity.NEW_TANKUP_RECORD);
                    if (newTankUp != null)
                    {
                        getCurrentAuto().getTankingRecord().add(newTankUp);
                    }
                }
            }
        }
    }

    private View.OnClickListener goToTankingActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, TankingActivity.class);
                intent.putExtra(ADD_DATA, getCurrentAuto());
                startActivityForResult(intent, TANK_CODE);
            }
        };
    }

    private void initArrayAdapter() {
        arrayAdapter = new ArrayAdapter<AutoInformation>(this, android.R.layout.simple_spinner_item, autoList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initAutoList() {
        autoList = new ArrayList<AutoInformation>();
        autoList.add(new AutoInformation("Bravo", "Fiat", "Bordowy"));
    }

    private AutoInformation getCurrentAuto() {
        return (AutoInformation) autoChooser.getSelectedItem();
    }
}
