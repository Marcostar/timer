package com.example.dzeko.timer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.dzeko.timer.MainActivity.FINAL_CYCLE_NUMBER;
import static com.example.dzeko.timer.MainActivity.FINAL_SET_NUMBER;
import static com.example.dzeko.timer.MainActivity.MyPREFERENCES;
import static com.example.dzeko.timer.MainActivity.REST_BETWEEN_CYCLE;
import static com.example.dzeko.timer.MainActivity.REST_TIME;
import static com.example.dzeko.timer.MainActivity.WARM_UP_TIME;
import static com.example.dzeko.timer.MainActivity.WORKOUT_TIME;

public class Settings extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView setsLabel, sets, warmUpTimeTextLabel, warmUpTimeText, restTimeTextLabel,
            restTimeText, workout_time_textLabel, workout_time_text, cycle_text_Label, cycle_text,
            sofa_textLabel, sofa_text, exerciseButtonLabel;

    private ImageButton exerciseButton;

    private LinearLayout setsLayout, warmUpLayout, restLayout, workoutLayout, cycleLayout,
            restBetweenCycleLayout, initialExcerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initialiseLayout();

        clickListner();
    }

    private void initialiseLayout() {
        setsLabel = (TextView) findViewById(R.id.setsLabel);
        sets = (TextView) findViewById(R.id.sets);
        warmUpTimeTextLabel = (TextView) findViewById(R.id.warmUpTimeTextLabel);
        warmUpTimeText = (TextView) findViewById(R.id.warmUpTimeText);
        restTimeTextLabel = (TextView) findViewById(R.id.restTimeTextLabel);
        restTimeText = (TextView) findViewById(R.id.restTimeText);
        workout_time_textLabel = (TextView) findViewById(R.id.workout_time_textLabel);
        workout_time_text = (TextView) findViewById(R.id.workout_time_text);
        cycle_text_Label = (TextView) findViewById(R.id.cycle_text_Label);
        cycle_text = (TextView) findViewById(R.id.cycle_text);
        sofa_textLabel = (TextView) findViewById(R.id.sofa_textLabel);
        sofa_text = (TextView) findViewById(R.id.sofa_text);
        exerciseButtonLabel = (TextView) findViewById(R.id.exerciseButtonLabel);
        exerciseButton = (ImageButton) findViewById(R.id.exerciseButton);
        initialExcerciseButton = (LinearLayout) findViewById(R.id.initialExcerciseButton);
        setsLayout = (LinearLayout) findViewById(R.id.setsLayout);
        warmUpLayout = (LinearLayout) findViewById(R.id.warmUpLayout);
        restLayout = (LinearLayout) findViewById(R.id.restLayout);
        workoutLayout = (LinearLayout) findViewById(R.id.workoutLayout);
        cycleLayout = (LinearLayout) findViewById(R.id.cycleLayout);
        restBetweenCycleLayout = (LinearLayout) findViewById(R.id.restBetweenCycleLayout);

        sets.setText(sharedPreferences.getInt(MainActivity.FINAL_SET_NUMBER,0)+"");
        warmUpTimeText.setText(sharedPreferences.getInt(MainActivity.WARM_UP_TIME,0)+"");
        restTimeText.setText(sharedPreferences.getInt(MainActivity.REST_TIME,0)+"");
        workout_time_text.setText(sharedPreferences.getInt(MainActivity.WORKOUT_TIME, 0)+"");
        cycle_text.setText(sharedPreferences.getInt(MainActivity.FINAL_CYCLE_NUMBER,0)+"");
        sofa_text.setText(sharedPreferences.getInt(MainActivity.REST_BETWEEN_CYCLE,0)+"");
    }


    private void clickListner() {

        exerciseButtonLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialExcerciseButton.performClick();
            }
        });

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialExcerciseButton.performClick();
            }
        });

        initialExcerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        setsLayout();
        warmUpLayouts();
        restLayouts();
        workoutLayouts();
        cycleLayouts();
        restBetweenCycleLayouts();

    }

    private void restBetweenCycleLayouts() {
        sofa_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restBetweenCycleLayout.performClick();
            }
        });

        sofa_textLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restBetweenCycleLayout.performClick();
            }
        });

        restBetweenCycleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(getString(R.string.rest_between_cycle), "Rest between cycles in seconds", R.mipmap.ic_sofa);
            }
        });

    }

    private void cycleLayouts() {
        cycle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cycleLayout.performClick();
            }
        });

        cycle_text_Label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cycleLayout.performClick();
            }
        });

        cycleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(getString(R.string.cycles), "Number of cycles", R.mipmap.ic_cycles);
            }
        });
    }

    private void workoutLayouts() {
        workout_time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutLayout.performClick();
            }
        });

        workout_time_textLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutLayout.performClick();
            }
        });

        workoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(getString(R.string.workout_time), "Workout time in seconds", R.mipmap.ic_fitness);
            }
        });
    }

    private void restLayouts() {
        restTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restLayout.performClick();
            }
        });

        restTimeTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restLayout.performClick();
            }
        });

        restLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(getString(R.string.rest_time), "Rest time between sets", R.mipmap.ic_man_rest);
            }
        });
    }

    private void warmUpLayouts() {
        warmUpTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warmUpLayout.performClick();
            }
        });

        warmUpTimeTextLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warmUpLayout.performClick();
            }
        });

        warmUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(getString(R.string.warm_up_time), "Preparation time in seconds", R.mipmap.ic_man_running);
            }
        });
    }

    private void setsLayout() {

        sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setsLayout.performClick();
            }
        });

        setsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setsLayout.performClick();
            }
        });

        setsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add sets
                createDialog(getString(R.string.sets), "Number of sets", R.mipmap.ic_replay);
            }
        });

    }

    private void createDialog(String title, String message, final int iconID) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.dialogEditText);

        dialogBuilder.setTitle(title);
        dialogBuilder.setIcon(iconID);
        dialogBuilder.setMessage(message);

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                edt.getText().toString();
                if (edt.getText().toString().matches(""))
                {
                    switch (iconID)
                    {
                        case R.mipmap.ic_replay:
                            edt.setText(sharedPreferences.getInt(MainActivity.FINAL_SET_NUMBER,0)+"");
                            break;
                        case R.mipmap.ic_man_running:
                            edt.setText(sharedPreferences.getInt(MainActivity.WARM_UP_TIME,0)+"");
                            break;
                        case R.mipmap.ic_man_rest:
                            edt.setText(sharedPreferences.getInt(MainActivity.REST_TIME,0)+"");
                            break;
                        case R.mipmap.ic_fitness:
                            edt.setText(sharedPreferences.getInt(MainActivity.WORKOUT_TIME,0)+"");
                            break;
                        case R.mipmap.ic_cycles:
                            edt.setText(sharedPreferences.getInt(MainActivity.FINAL_CYCLE_NUMBER,0)+"");
                            break;
                        case R.mipmap.ic_sofa:
                            edt.setText(sharedPreferences.getInt(MainActivity.REST_BETWEEN_CYCLE,0)+"");
                            break;
                    }
                }
                else
                {
                    switch (iconID)
                    {
                        case R.mipmap.ic_replay:
                            editor.putInt(FINAL_SET_NUMBER,Integer.parseInt(edt.getText().toString())).apply();
                            sets.setText(edt.getText().toString());
                            break;
                        case R.mipmap.ic_man_running:
                            editor.putInt(WARM_UP_TIME,Integer.parseInt(edt.getText().toString())).apply();
                            warmUpTimeText.setText(edt.getText().toString());
                            break;
                        case R.mipmap.ic_man_rest:
                            editor.putInt(REST_TIME,Integer.parseInt(edt.getText().toString())).apply();
                            restTimeText.setText(sharedPreferences.getInt(MainActivity.REST_TIME,0)+"");
                            break;
                        case R.mipmap.ic_fitness:
                            editor.putInt(WORKOUT_TIME,Integer.parseInt(edt.getText().toString())).apply();
                            workout_time_text.setText(sharedPreferences.getInt(MainActivity.WORKOUT_TIME,0)+"");
                            break;
                        case R.mipmap.ic_cycles:
                            editor.putInt(FINAL_CYCLE_NUMBER,Integer.parseInt(edt.getText().toString())).apply();
                            cycle_text.setText(sharedPreferences.getInt(MainActivity.FINAL_CYCLE_NUMBER,0)+"");
                            break;
                        case R.mipmap.ic_sofa:
                            editor.putInt(REST_BETWEEN_CYCLE,Integer.parseInt(edt.getText().toString())).apply();
                            sofa_text.setText(sharedPreferences.getInt(MainActivity.REST_BETWEEN_CYCLE,0)+"");
                            break;
                    }
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}
