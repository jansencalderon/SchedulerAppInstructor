package ph.edu.tip.schedulerappinstructor.ui.events.add;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.File;

import io.realm.Realm;
import ph.edu.tip.schedulerappinstructor.R;
import ph.edu.tip.schedulerappinstructor.databinding.ActivityEventAddBinding;
import ph.edu.tip.schedulerappinstructor.util.TagsManipulator;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * Created by Mark Jansen Calderon on 1/26/2017.
 */

public class EventAddActivity extends MvpActivity<EventAddView, EventAddPresenter> implements EventAddView {

    ActivityEventAddBinding binding;
    private String TAG = EventAddActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private int PLACE_PICKER_REQUEST = 1;
    private int IMAGE_REQUEST = 2;
    private Place pickedPlace;
    private File pickedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_add);
        binding.setView(getMvpView());
        presenter.onStart();

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        binding.locationCard.setVisibility(View.GONE);
        binding.addLocation.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public EventAddPresenter createPresenter() {
        return new EventAddPresenter();
    }


    @Override
    public void showAlert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhotoClicked() {
        EasyImage.openGallery(EventAddActivity.this, 0);
    }


    private void setImage(File imageFile) {
        Glide.with(this)
                .load(imageFile)
                .into(binding.eventImage);

        pickedImage = imageFile;
    }

    @Override
    public void startLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Creating event...");
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
    public void onPickLocation() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemoveLocation() {
        binding.locationCard.setVisibility(View.GONE);
        binding.addLocation.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                presenter.onCreateEvent(
                        binding.eventName.getText().toString(),
                        binding.eventDescription.getText().toString(),
                        binding.eventType.getText().toString(),
                        TagsManipulator.JoinTags(binding.tagGroup.getTags()),
                        pickedPlace,
                        pickedImage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Are you sure you want to cancel Event Creation?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                //set place data
                pickedPlace = place;
                binding.locationName.setText(place.getName());
                binding.locationAddress.setText(place.getAddress());
                binding.locationCard.setVisibility(View.VISIBLE);
                binding.addLocation.setVisibility(View.GONE);

            }
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    //Some error handling
                    e.printStackTrace();
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    setImage(imageFile);
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    //Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA) {
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(EventAddActivity.this);
                        if (photoFile != null) photoFile.delete();
                    }
                }
            });
        }

    }
}
