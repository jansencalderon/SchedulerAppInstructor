package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.File;

import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.Endpoints;
import ph.edu.tip.schedulerappinstructor.databinding.ActivityEventDetailBinding;
import ph.edu.tip.schedulerappinstructor.databinding.DialogAddCalendarBinding;
import ph.edu.tip.schedulerappinstructor.databinding.DialogAddSlotTypeBinding;
import ph.edu.tip.schedulerappinstructor.model.data.Calendar;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class EventDetailActivity extends MvpActivity<EventDetailView, EventDetailPresenter> implements EventDetailView {

    private ActivityEventDetailBinding binding;
    private SlotCategoryListAdapter slotAdapter;
    private File pickedImageSlotType;
    private ProgressDialog progressDialog;
    private Event event;
    private Dialog slotDialog;
    private DialogAddSlotTypeBinding slotDialogBinding;
    private Dialog scheduleDialog;
    private DialogAddCalendarBinding scheduleDialogBinding;
    private CalendarListAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        int id = i.getIntExtra(Constants.ID, -1);
        if (id == -1) {
            finish();
        }

        //set event
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
        calendarAdapter = new CalendarListAdapter(getMvpView());
        binding.recyclerViewCalendar.setAdapter(calendarAdapter);
        calendarAdapter.setCalendarList(event.getCalendar());


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
            progressDialog.setMessage("Adding Slot Type...");
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
                slotDialog.dismiss();
                break;
        }
    }

    //slot crud
    @Override
    public void onAddSlotType() {


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

        slotDialog.setCanceledOnTouchOutside(true);
        slotDialog.setContentView(slotDialogBinding.getRoot());
        slotDialog.show();
    }


    @Override
    public void onSlotCategoryDelete(SlotCategory slot) {
        showAlert(slot.getSlotName() + " DELETE");
    }

    @Override
    public void onSlotCategoryEdit(SlotCategory slot) {
        showAlert(slot.getSlotName() + " EDIT");

    }

    //calendar crud
    @Override
    public void onAddSchedule() {

        scheduleDialogBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.dialog_add_calendar,
                null,
                false);


        scheduleDialog = new Dialog(EventDetailActivity.this);


        scheduleDialogBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scheduleDialogBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleDialog.dismiss();
            }
        });

        scheduleDialog.setCanceledOnTouchOutside(true);
        scheduleDialog.setContentView(scheduleDialogBinding.getRoot());
        scheduleDialog.show();

    }

    @Override
    public void onCalendarDelete(Calendar cal) {
    }

    @Override
    public void onCalendarEdit(Calendar cal) {

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

