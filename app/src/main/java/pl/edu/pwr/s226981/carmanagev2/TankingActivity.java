package pl.edu.pwr.s226981.carmanagev2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TankingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    public static final String NEW_TANKUP_RECORD = "NEW_TANKUP_RECORD";
    private EditText dateET, mileageET, litersET, costET;
    private TextView mileageTV, litersTV, costTV;

    private Button confirmButton;
    private AutoInformation autoInformation;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tankinglayout);

        autoInformation = (AutoInformation) getIntent().getExtras().getSerializable(MainActivity.ADD_DATA);

        initialization();
        setTitle("Tanking");
    }

    private void initialization() {
        dateET = findViewById(R.id.dateTank);
        mileageET = findViewById(R.id.mileage);
        litersET = findViewById(R.id.liters);
        costET = findViewById(R.id.cost);
        confirmButton = findViewById(R.id.confirm);
        mileageTV = findViewById(R.id.mileageView);
        litersTV = findViewById(R.id.litersView);
        costTV = findViewById(R.id.costView);

        dateET.setText(getTodayDate());
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TankingActivity.this, TankingActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (correctMileage()&& correctCost() && correctLiters())
                {
                    TankingRecord tankRecord = new TankingRecord(getDateEditTextDate(), getMileage(), getLiters(), getCost());
                    autoInformation.getTankingRecord().add(tankRecord);
                    Intent intent = new Intent();
                    intent.putExtra(NEW_TANKUP_RECORD, tankRecord);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        mileageET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus == false)
                {
                    correctMileage();
                }
            }
        });

        costET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    correctCost();
                }
            }
        });

        litersET.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == false)
                {
                    correctLiters();
                }
            }
        });
    }

    private String getTodayDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        Calendar calendar = new GregorianCalendar(year, month, day);
        dateET.setText(dateFormat.format(calendar.getTime()));
    }

    private boolean correctMileage() {
        int size = autoInformation.getTankingRecord().size();
        if (TextUtils.isEmpty(mileageET.getText().toString()))
        {
            mileageTV.setText("The value must be entered");
            mileageTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        if (Integer.valueOf(mileageET.getText().toString()) <= 0)
        {
            mileageTV.setText("The value must be positive");
            mileageTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        if (autoInformation.getTankingRecord().size() != 0)
        {
            Integer newMileage = Integer.valueOf(mileageET.getText().toString());
            Integer oldMileage = autoInformation.getTankingRecord().get(size - 1).getMileage();
            if(newMileage <= oldMileage)
            {
                mileageTV.setText("The mileage is less than or equal to the last one");
                mileageTV.setTextColor(getResources().getColor(R.color.red));
                return false;
            } else
            {
                mileageTV.setText(getResources().getString(R.string.mileage));
                mileageTV.setTextColor(getResources().getColor(R.color.colorAccent));
                return true;
            }
        }
        return true;
    }

    private boolean correctLiters()
    {
        if (TextUtils.isEmpty(litersET.getText().toString()))
        {
            litersTV.setText("The value must be entered");
            litersTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else if (Integer.valueOf(litersET.getText().toString()) <= 0)
        {
            litersTV.setText("The value must be positive");
            litersTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else
        {
            litersTV.setText("Liters");
            litersTV.setTextColor(getResources().getColor(R.color.colorAccent));
            return true;
        }
    }

    private boolean correctCost()
    {
        if (TextUtils.isEmpty(costET.getText().toString()))
        {
            costTV.setText("The value must be entered");
            costTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else if (Integer.valueOf(costET.getText().toString()) <= 0)
        {
            costTV.setText("The value must be positive");
            costTV.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else
        {
            costTV.setText("Cost");
            costTV.setTextColor(getResources().getColor(R.color.colorAccent));
            return true;
        }
    }

    private Date getDateEditTextDate() {

        try {
            return dateFormat.parse(dateET.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return date;
    }

    private Integer getCost() {
        return Integer.valueOf(costET.getText().toString());
    }

    private Integer getLiters() {
        return Integer.valueOf(litersET.getText().toString());
    }

    private Integer getMileage() {
        return Integer.valueOf(mileageET.getText().toString());
    }


}
