package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.badoualy.datepicker.DatePickerTimeline;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.File;

import io.realm.Realm;
import io.realm.Sort;
import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.databinding.ActivityEventDetailBinding;
import ph.edu.tip.schedulerappinstructor.databinding.DialogAddScheduleBinding;
import ph.edu.tip.schedulerappinstructor.databinding.DialogAddSlotTypeBinding;
import ph.edu.tip.schedulerappinstructor.databinding.DialogChooseInstructorBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;
import ph.edu.tip.schedulerappinstructor.ui.login.LoginActivity;
import ph.edu.tip.schedulerappinstructor.util.DateTimeUtils;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//TODO: Update Loading Labels
public class EventDetailActivity extends MvpActivity<EventDetailView, EventDetailPresenter> implements EventDetailView {

    private ActivityEventDetailBinding binding;
    private SlotCategoryListAdapter slotAdapter;
    private File pickedImageSlotType;
    private ProgressDialog progressDialog;
    private Event event;
    private Dialog slotDialog;
    private DialogAddSlotTypeBinding slotDialogBinding;
    private Dialog scheduleDialog;
    private DialogAddScheduleBinding scheduleDialogBinding;
    private SchedListAdapter calendarAdapter;
    private String scheduleTimeStartPicked, scheduleTimeEndPicked;
    private int id;
    private String schedulePickedDate;
    private DialogChooseInstructorBinding dialogChooseInstructorBinding;
    private Dialog dialogInstructorChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(this);
        presenter.onStart();
        binding = DataBindingUtil.setContentView(EventDetailActivity.this, R.layout.activity_event_detail);
        binding.setView(getMvpView());

        //set toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get id
        Intent i = getIntent();
        id = i.getIntExtra(Constants.ID, -1);
        if (id == -1) {
            finish();
        }

        //set event
        refreshLocalData();


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                @Override
                public void permissionRefused() {
                    finish();
                }
            });
        }

        EasyImage.configuration(this)
                .setImagesFolderName("My app images") // images folder name, default is "EasyImage"
                .saveInAppExternalFilesDir() // if you want to use root internal memory for storying images
                .saveInRootPicturesDirectory(); // if you want to use internal memory for storying images - default

        binding.statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onChangeEventStatus(id,event.getStatus());
            }
        });

    }

    @Override
    public void showError(String message) {

    }


    @Override
    public void showAlert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(String type) {
        switch (type) {
            case "SlotType":
                refreshLocalData();
                if (slotDialog != null && slotDialog.isShowing())
                    slotDialog.dismiss();
                break;
            case "SchedType":
                refreshLocalData();
                if (scheduleDialog != null && scheduleDialog.isShowing())
                    scheduleDialog.dismiss();
                break;
            case "InstructorListType":
                showInstructorChooser();
                break;
            case "AdminType":
                refreshLocalData();
                if(dialogInstructorChooser != null && dialogInstructorChooser.isShowing())
                    dialogInstructorChooser.dismiss();
                break;
            case "StatusType":
                refreshLocalData();

        }
    }

    private void refreshLocalData() {
        event = presenter.event(id);
        binding.setEvent(event);
        Glide.with(this)
                .load(Endpoints.EVENT_URL_IMAGE + event.getScheduledEventImage())
                .into(binding.eventImage);

        //set slot data
        binding.recyclerViewSlots.setLayoutManager(new LinearLayoutManager(this));
        slotAdapter = new SlotCategoryListAdapter(getMvpView());
        binding.recyclerViewSlots.setAdapter(slotAdapter);
        slotAdapter.setSlotCategoryList(event.getCategories());

        //set calendar data
        binding.recyclerViewCalendar.setLayoutManager(new LinearLayoutManager(this));
        calendarAdapter = new SchedListAdapter(getMvpView());
        binding.recyclerViewCalendar.setAdapter(calendarAdapter);
        calendarAdapter.setList(event.getCalendar().sort("date", Sort.ASCENDING));

        //set admins
        binding.recyclerViewAdmins.setLayoutManager(new LinearLayoutManager(this));
        InstructorListAdapter instructorListAdapter= new InstructorListAdapter(getMvpView());
        binding.recyclerViewAdmins.setAdapter(instructorListAdapter);
        instructorListAdapter.setList(event.getAdmins());

        //set status
        if(event.getStatus().equals("O")){
            binding.status.setText("Posted");
            binding.statusPanel.setBackgroundColor(ContextCompat.getColor(this, R.color.greenSuccess));
            binding.statusSwitch.setChecked(false);
        }
        if(event.getStatus().equals("P")){
            binding.status.setText("Posted");
            binding.statusPanel.setBackgroundColor(ContextCompat.getColor(this, R.color.redFailed));
            binding.statusSwitch.setChecked(true);
        }
    }

    //slot crud
    @Override
    public void onAddSlotType() {
        pickedImageSlotType = null;
        slotDialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_slot_type,
                null,
                false);

        slotDialogBinding.slotTypeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(EventDetailActivity.this, 0);
            }
        });


        slotDialog = new Dialog(EventDetailActivity.this);


        slotDialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddSlotCategory(event.getScheduledEventId(),
                        slotDialogBinding.slotName.getText().toString(),
                        slotDialogBinding.numberOfSlot.getText().toString(),
                        slotDialogBinding.slotPrice.getText().toString(),
                        slotDialogBinding.switchSelectNumber.isChecked(),
                        pickedImageSlotType);
            }
        });

        slotDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotDialog.dismiss();
            }
        });

        slotDialog.setContentView(slotDialogBinding.getRoot());
        slotDialog.show();
    }

    @Override
    public void onSlotCategoryEdit(final SlotCategory slot) {
        pickedImageSlotType = null;
        slotDialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_slot_type,
                null,
                false);

        slotDialogBinding.slotTypeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(EventDetailActivity.this, 0);
            }
        });

        //set existing data
        slotDialogBinding.send.setText("UPDATE");
        slotDialogBinding.slotName.setText(slot.getSlotName());
        slotDialogBinding.slotPrice.setText(slot.getSlotPrice());
        slotDialogBinding.numberOfSlot.setText(slot.getSlotAlloted());
        Glide.with(this).load(Endpoints.EVENT_URL_IMAGE+slot.getSlotImage()).into(slotDialogBinding.slotTypeImage);
        if(slot.getIsSeatTrue().equals("true")){
            slotDialogBinding.switchSelectNumber.setChecked(true);
        }
        //end

        slotDialog = new Dialog(EventDetailActivity.this);


        slotDialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUpdateSlotCategory(
                        id,
                        slot.getSlotCategoryId()+"",
                        slotDialogBinding.slotName.getText().toString(),
                        slotDialogBinding.numberOfSlot.getText().toString(),
                        slotDialogBinding.slotPrice.getText().toString(),
                        slotDialogBinding.switchSelectNumber.isChecked(),
                        pickedImageSlotType);
            }
        });

        slotDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotDialog.dismiss();
            }
        });

        slotDialog.setContentView(slotDialogBinding.getRoot());
        slotDialog.show();

    }

    @Override
    public void onSlotCategoryDelete(final SlotCategory slot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + slot.getSlotName() + "?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                presenter.onDeleteSlotCategory(slot.getSlotCategoryId());
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    //calendar crud
    @Override
    public void onAddSchedule() {
        final Calendar c = Calendar.getInstance();
        scheduleDialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_schedule,
                null,
                false);
        scheduleDialogBinding.setView(getMvpView());
        scheduleDialogBinding.schedDate.setFirstVisibleDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        scheduleDialogBinding.schedDate.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                schedulePickedDate = year + "-" + (month + 1) + "-" + day;
                scheduleDialogBinding.schedDateReadable.setText(DateTimeUtils.toReadable(schedulePickedDate));
                Log.e("pickedDate: ", schedulePickedDate);
            }
        });

        scheduleDialog = new Dialog(EventDetailActivity.this);


        scheduleDialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddSchedule(schedulePickedDate, scheduleTimeStartPicked, scheduleTimeEndPicked, id + "");
            }
        });

        scheduleDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleDialog.dismiss();

                //reset values
                schedulePickedDate = "";
                scheduleTimeStartPicked = "";
                scheduleTimeEndPicked = "";
            }
        });

        scheduleDialog.setContentView(scheduleDialogBinding.getRoot());
        scheduleDialog.show();
    }


    @Override
    public void onSchedTimeStart() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EventDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                scheduleTimeStartPicked = selectedHour + ":" + selectedMinute + ":00";
                scheduleDialogBinding.schedTimeStart.setText(DateTimeUtils.stringToAMorPM(scheduleTimeStartPicked));
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Time Start");
        mTimePicker.show();
    }

    @Override
    public void onSchedTimeEnd() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EventDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                scheduleTimeEndPicked = selectedHour + ":" + selectedMinute + ":00";
                scheduleDialogBinding.schedTimeEnd.setText(DateTimeUtils.stringToAMorPM(scheduleTimeEndPicked));
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Time End");
        mTimePicker.show();

    }

    @Override
    public void onSchedDelete(final Schedule sched) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + DateTimeUtils.dateToReadable(sched.getDate()) + "?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                presenter.onDeleteSchedule(sched.getCalendarId());
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSchedEdit(final Schedule sched) {

        final Calendar c = Calendar.getInstance();
        scheduleDialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_schedule,
                null,
                false);
        scheduleDialogBinding.setView(getMvpView());
        scheduleDialogBinding.schedDate.setFirstVisibleDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        scheduleDialogBinding.schedDate.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                schedulePickedDate = year + "-" + (month + 1) + "-" + day;
                Log.e("pickedDate: ", schedulePickedDate);
                scheduleDialogBinding.schedDateReadable.setText(DateTimeUtils.toReadable(schedulePickedDate));
            }
        });

        //set sched data
        scheduleDialogBinding.send.setText("UPDATE");
        scheduleDialogBinding.schedTimeStart.setText(DateTimeUtils.stringToAMorPM(sched.getTimeStart()));
        scheduleDialogBinding.schedTimeEnd.setText(DateTimeUtils.stringToAMorPM(sched.getTimeEnd()));
        scheduleDialogBinding.schedDateReadable.setText(DateTimeUtils.dateToReadable(sched.getDate()));

        scheduleTimeStartPicked = sched.getTimeStart();
        scheduleTimeEndPicked = sched.getTimeEnd();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        schedulePickedDate = sdf.format(sched.getDate());
        //end

        scheduleDialog = new Dialog(EventDetailActivity.this);


        scheduleDialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onUpdateSchedule(schedulePickedDate, scheduleTimeStartPicked, scheduleTimeEndPicked, id + "",sched.getCalendarId()+"");
            }
        });

        scheduleDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleDialog.dismiss();
                //reset values
                schedulePickedDate = "";
                scheduleTimeStartPicked = "";
                scheduleTimeEndPicked = "";
            }
        });

        scheduleDialog.setContentView(scheduleDialogBinding.getRoot());
        scheduleDialog.show();
    }

    @Override
    public void onAddInstructor() {
        presenter.getInstructors();
    }

    public void showInstructorChooser(){
        final Calendar c = Calendar.getInstance();
        dialogChooseInstructorBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_choose_instructor,
                null,
                false);

        dialogChooseInstructorBinding.setView(getMvpView());
        //set slot data
        dialogChooseInstructorBinding.recyclerViewChooseInstructor.setLayoutManager(new LinearLayoutManager(this));
        ChooseInstructorListAdapter listAdapter = new ChooseInstructorListAdapter(getMvpView());
        dialogChooseInstructorBinding.recyclerViewChooseInstructor.setAdapter(listAdapter);
        listAdapter.setList(presenter.getInstructorFromRealm());

        dialogInstructorChooser = new Dialog(EventDetailActivity.this);
        dialogInstructorChooser.setContentView(dialogChooseInstructorBinding.getRoot());
        dialogInstructorChooser.show();

    }

    @Override
    public void onEditInstructor(ScheduleEventAdmin eventAdmin) {

    }

    @Override
    public void onDeleteInstructor(ScheduleEventAdmin eventAdmin) {

    }

    @Override
    public void onChooseInstructor(ScheduleEventAdmin eventAdmin) {
        if(event.getAdmins().contains(eventAdmin)){
            showAlert("Admin is already added");
        }else{
            presenter.addInstructor(id+"",eventAdmin);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Glide.with(EventDetailActivity.this).load(imageFile).into(slotDialogBinding.slotTypeImage);
                pickedImageSlotType = imageFile;
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(EventDetailActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

    }


    @NonNull
    @Override
    public EventDetailPresenter createPresenter() {
        return new EventDetailPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}

