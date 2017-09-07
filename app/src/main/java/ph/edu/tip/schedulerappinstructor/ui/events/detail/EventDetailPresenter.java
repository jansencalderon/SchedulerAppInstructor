package ph.edu.tip.schedulerappinstructor.ui.events.detail;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ph.edu.tip.schedulerappinstructor.app.App;
import ph.edu.tip.schedulerappinstructor.app.Constants;
import ph.edu.tip.schedulerappinstructor.app.RealmQuery;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import ph.edu.tip.schedulerappinstructor.model.data.Event;
import ph.edu.tip.schedulerappinstructor.model.data.Schedule;
import ph.edu.tip.schedulerappinstructor.model.data.ScheduleEventAdmin;
import ph.edu.tip.schedulerappinstructor.model.data.SlotCategory;
import ph.edu.tip.schedulerappinstructor.model.response.ScheduleEventAdminResponse;
import ph.edu.tip.schedulerappinstructor.model.response.BasicResponse;
import ph.edu.tip.schedulerappinstructor.model.response.ScheduleResponse;
import ph.edu.tip.schedulerappinstructor.model.response.SlotCategoryResponse;
import ph.edu.tip.schedulerappinstructor.util.RequestBodyCreate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailPresenter extends MvpNullObjectBasePresenter<EventDetailView> {
    private Realm realm;

    public void onStart() {
        realm = Realm.getDefaultInstance();
    }

    public void onStop() {
        realm.close();
    }

    public Event event(int id) {
        return realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, id).findFirst();
    }


    public void onChangeEventStatus(final int eventId, final String eventStatus) {
        getView().startLoading();
        App.getInstance().getApiInterface().changeEventStatus(Constants.BEARER + RealmQuery.getUser().getApiToken(), eventId + ""
                , Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, final Response<BasicResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Event event = realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, eventId).findFirst();
                                if (eventStatus.equals("P")) {
                                    event.setStatus("O");
                                } else {
                                    event.setStatus("P");
                                }
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().onSuccess("StatusType");
                                getView().showAlert(response.body().getMessage());
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                error.printStackTrace();
                                getView().showAlert(error.getLocalizedMessage());
                            }
                        });
                    } else {
                        getView().showAlert(response.body().getMessage());
                    }
                } else {
                    getView().showError("Oops, something went wrong\nPlease try again");
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                getView().showError("Oops, something went wrong\nPlease try again");
            }
        });
    }


    public void onAddSlotCategory(final int eventId, String slotName, String numberOfSlot, String price, boolean selectSeatNumber, File image) {
        String select = "off";
        if (selectSeatNumber) {
            select = "on";
        }
        if (image == null) {
            getView().showAlert("Choose Image First");
        } else if (slotName.equals("") || numberOfSlot.equals("") || price.equals("")) {
            getView().showAlert("Please complete all details");
        } else {
            getView().startLoading();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body = MultipartBody.Part.createFormData(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_IMAGE
                    , image.getName(), requestFile);
            App.getInstance().getApiInterface().createEventSlotCategory(
                    Constants.BEARER + RealmQuery.getUser().getApiToken(),
                    RequestBodyCreate.createPartFromString(eventId + ""),
                    RequestBodyCreate.createPartFromString(slotName),
                    RequestBodyCreate.createPartFromString(numberOfSlot),
                    RequestBodyCreate.createPartFromString(price),
                    RequestBodyCreate.createPartFromString(select),
                    body,
                    Constants.APPJSON).enqueue(new Callback<SlotCategoryResponse>() {
                @Override
                public void onResponse(Call<SlotCategoryResponse> call, final Response<SlotCategoryResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccess()) {
                                final Realm realm = Realm.getDefaultInstance();
                                realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Event event = realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, eventId).findFirst();
                                        updateEventSlotCategory(event, realm, response.body().getSlotCategory());
                                    }
                                }, new Realm.Transaction.OnSuccess() {
                                    @Override
                                    public void onSuccess() {
                                        realm.close();
                                        getView().onSuccess("SlotType");
                                        getView().showAlert(response.body().getMessage());
                                    }
                                }, new Realm.Transaction.OnError() {
                                    @Override
                                    public void onError(Throwable error) {
                                        realm.close();
                                        error.printStackTrace();
                                        getView().showAlert(error.getLocalizedMessage());
                                    }
                                });
                            } else {
                                getView().showAlert(response.body().getMessage());
                            }
                        } else {
                            getView().showAlert("Server Error");
                        }
                    } else {
                        getView().showAlert("Server Error");
                    }
                }

                @Override
                public void onFailure(Call<SlotCategoryResponse> call, Throwable t) {
                    t.printStackTrace();
                    getView().stopLoading();
                    getView().showAlert(t.getLocalizedMessage());
                }
            });
        }

    }

    public void onUpdateSlotCategory(final int eventId, String slotId, String slotName, String numberOfSlot, String price, boolean selectSeatNumber, File image) {
        String select = "off";

        if (selectSeatNumber) {
            select = "on";
        } else if (slotName.equals("") || numberOfSlot.equals("") || price.equals("")) {
            getView().showAlert("Please complete all details");
        } else {
            getView().startLoading();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body = MultipartBody.Part.createFormData(Constants.ApiParameters.ScheduledEvents.SCHEDULED_EVENT_IMAGE
                    , image.getName(), requestFile);
            App.getInstance().getApiInterface().updateSlotCategory(
                    Constants.BEARER + RealmQuery.getUser().getApiToken(),
                    slotId,
                    RequestBodyCreate.createPartFromString(slotName),
                    RequestBodyCreate.createPartFromString(numberOfSlot),
                    RequestBodyCreate.createPartFromString(price),
                    RequestBodyCreate.createPartFromString(select),
                    body,
                    Constants.APPJSON).enqueue(new Callback<SlotCategoryResponse>() {
                @Override
                public void onResponse(Call<SlotCategoryResponse> call, final Response<SlotCategoryResponse> response) {
                    getView().stopLoading();

                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Event event = realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, eventId).findFirst();
                                    updateEventSlotCategory(event, realm, response.body().getSlotCategory());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().onSuccess("SlotType");
                                    getView().showAlert(response.body().getMessage());
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    getView().showAlert(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    } else {
                        getView().showError("Oops, something went wrong\nPlease try again");
                    }

                }

                @Override
                public void onFailure(Call<SlotCategoryResponse> call, Throwable t) {
                    t.printStackTrace();
                    getView().stopLoading();
                    getView().showError("Oops, something went wrong\nPlease try again");
                }
            });
        }

    }

    public void onDeleteSlotCategory(final int slotCategoryId) {
        getView().startLoading();
        App.getInstance().getApiInterface().deleteSlotCategory(Constants.BEARER + RealmQuery.getUser().getApiToken(),
                slotCategoryId + "",
                Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, final Response<BasicResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                SlotCategory slotCategory = realm.where(SlotCategory.class).equalTo(Constants.Realm.SLOT_CATEGORY_ID, slotCategoryId).findFirst();
                                slotCategory.deleteFromRealm();
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().showAlert(response.body().getMessage());
                                getView().onSuccess("SlotType");
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                error.printStackTrace();
                                getView().showAlert(error.getLocalizedMessage());
                            }
                        });
                    } else {
                        getView().showAlert(response.body().getMessage());
                    }
                } else {
                    getView().showAlert("Server Error");
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                getView().stopLoading();
                getView().showError("Oops, something went wrong\nPlease try again");
            }
        });
    }

    public void onAddSchedule(String date, String timeStart, String timeEnd, final String scheduledEventId) {
        if (date == null || date.equals("") || timeStart == null || timeStart.equals("") || timeEnd == null || timeEnd.equals("") || scheduledEventId.equals("")) {
            getView().showAlert("Please Complete All Details");
        } else {
            getView().startLoading();
            Map<String, String> params = new HashMap<>();
            params.put(Constants.ApiParameters.ScheduledEventSchedule.DATE, date);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.TIME_START, timeStart);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.TIME_END, timeEnd);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.SCHEDULED_EVENT_ID, scheduledEventId);
            App.getInstance().getApiInterface().createEventSched(
                    Constants.BEARER + RealmQuery.getUser().getApiToken(),
                    params,
                    Constants.APPJSON).enqueue(new Callback<ScheduleResponse>() {
                @Override
                public void onResponse(Call<ScheduleResponse> call, final Response<ScheduleResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {

                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Event event = realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, Integer.parseInt(scheduledEventId)).findFirst();
                                    updateEventSched(event, realm, response.body().getSched());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().showAlert(response.body().getMessage());
                                    getView().onSuccess("SchedType");
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    getView().showAlert(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    } else {
                        getView().showAlert("Server Error");
                    }
                }

                @Override
                public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                    t.printStackTrace();
                    getView().stopLoading();
                    getView().showError("Oops, something went wrong\nPlease try again");
                }
            });
        }
    }

    public void onDeleteSchedule(final int scheduleId) {
        getView().startLoading();
        App.getInstance().getApiInterface().deleteEventSched(Constants.BEARER + RealmQuery.getUser().getApiToken(),
                scheduleId + "",
                Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, final Response<BasicResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Schedule sched = realm.where(Schedule.class).equalTo(Constants.Realm.CALENDAR_ID, scheduleId).findFirst();
                                sched.deleteFromRealm();
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().showAlert(response.body().getMessage());
                                getView().onSuccess("SchedType");
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                error.printStackTrace();
                                getView().showAlert(error.getLocalizedMessage());
                            }
                        });
                    } else {
                        getView().showAlert(response.body().getMessage());
                    }
                } else {
                    getView().showAlert("Server Error");
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                getView().stopLoading();
                getView().showError("Oops, something went wrong\nPlease try again");
            }
        });
    }

    public void onUpdateSchedule(String date, String timeStart, String timeEnd, final String scheduledEventId, String scheduleId) {
        if (date == null || date.equals("") || timeStart == null || timeStart.equals("") || timeEnd == null || timeEnd.equals("") || scheduledEventId.equals("")) {
            getView().showAlert("Please Complete All Details");
        } else {
            getView().startLoading();
            Map<String, String> params = new HashMap<>();
            params.put(Constants.ApiParameters.ScheduledEventSchedule.DATE, date);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.TIME_START, timeStart);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.TIME_END, timeEnd);
            params.put(Constants.ApiParameters.ScheduledEventSchedule.SCHEDULED_EVENT_ID, scheduledEventId);
            App.getInstance().getApiInterface().updateEventSched(
                    Constants.BEARER + RealmQuery.getUser().getApiToken(),
                    scheduleId,
                    params,
                    Constants.APPJSON).enqueue(new Callback<ScheduleResponse>() {
                @Override
                public void onResponse(Call<ScheduleResponse> call, final Response<ScheduleResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {

                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.copyToRealmOrUpdate(response.body().getSched());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().showAlert(response.body().getMessage());
                                    getView().onSuccess("SchedType");
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    getView().showAlert(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    } else {
                        getView().showAlert("Server Error");
                    }
                }

                @Override
                public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                    t.printStackTrace();
                    getView().stopLoading();
                    getView().showAlert(t.getLocalizedMessage());
                }
            });
        }
    }


    public void getInstructors() {
        getView().startLoading();
        App.getInstance().getApiInterface().getInstructors(Constants.BEARER + RealmQuery.getUser().getApiToken(), Constants.APPJSON).enqueue(new Callback<List<ScheduleEventAdmin>>() {
            @Override
            public void onResponse(Call<List<ScheduleEventAdmin>> call, final Response<List<ScheduleEventAdmin>> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(response.body());
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().onSuccess("InstructorListType");
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                error.printStackTrace();
                                getView().showAlert(error.getLocalizedMessage());
                            }
                        });
                    } else {
                        getView().showAlert("Server Error");
                    }
                } else {
                    getView().showAlert("Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleEventAdmin>> call, Throwable t) {
                t.printStackTrace();
                getView().stopLoading();
                getView().showError("Oops, something went wrong\nPlease try again");
            }
        });
    }

    public List<ScheduleEventAdmin> getInstructorFromRealm() {
        return realm.where(ScheduleEventAdmin.class).findAll();
    }


    //TODO: Dynamic Data on Event Admin
    public void addInstructor(final String eventId, ScheduleEventAdmin admin) {
        getView().startLoading();
        Map<String, String> params = new HashMap<>();
        params.put(Constants.ApiParameters.ScheduledEventAdmin.POSITION, "Instructor");
        params.put(Constants.ApiParameters.ScheduledEventAdmin.DESCRIPTION, "Sample Description");
        params.put(Constants.ApiParameters.ScheduledEventAdmin.ADMIN_ID, admin.getAdminId() + "");
        params.put(Constants.ApiParameters.ScheduledEventAdmin.SCHEDULED_EVENT_ID, eventId);

        App.getInstance().getApiInterface().addInstructor(Constants.BEARER + RealmQuery.getUser().getApiToken(),
                params
                , Constants.APPJSON).enqueue(new Callback<ScheduleEventAdminResponse>() {
            @Override
            public void onResponse(Call<ScheduleEventAdminResponse> call, final Response<ScheduleEventAdminResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        if (response.body().isSuccess()) {
                            final Realm realm = Realm.getDefaultInstance();
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Event event = realm.where(Event.class).equalTo(Constants.Realm.EVENT_ID, Integer.parseInt(eventId)).findFirst();
                                    updateEventAdmins(event, realm, response.body().getScheduleEventAdmin());
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    realm.close();
                                    getView().onSuccess("AdminType");
                                    getView().showAlert(response.body().getMessage());
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    realm.close();
                                    error.printStackTrace();
                                    getView().showAlert(error.getLocalizedMessage());
                                }
                            });
                        } else {
                            getView().showAlert(response.body().getMessage());
                        }
                    } else {
                        getView().showError("Oops, something went wrong\nPlease try again");
                    }
                } else {
                    getView().showError("Oops, something went wrong\nPlease try again");
                }
            }

            @Override
            public void onFailure(Call<ScheduleEventAdminResponse> call, Throwable t) {
                t.printStackTrace();
                getView().stopLoading();
                getView().showAlert(t.getLocalizedMessage());
            }
        });
    }

    public static void updateEventSched(Event event, Realm realm, Schedule schedule) {
        //do update stuff
        Schedule update = realm.copyToRealmOrUpdate(schedule);
        event.getCalendar().add(update);

    }

    public static void updateEventSlotCategory(Event event, Realm realm, SlotCategory slot) {
        //do update stuff
        SlotCategory update = realm.copyToRealmOrUpdate(slot);
        event.getCategories().add(update);

    }

    public static void updateEventAdmins(Event event, Realm realm, ScheduleEventAdmin admin) {
        //do update stuff
        ScheduleEventAdmin update = realm.copyToRealmOrUpdate(admin);
        event.getAdmins().add(update);

    }

    public void onDeleteInstructor(final int eventAdminId) {
        getView().startLoading();
        App.getInstance().getApiInterface().deleteInstructor(Constants.BEARER + RealmQuery.getUser().getApiToken(),
                eventAdminId + ""
                , Constants.APPJSON).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, final Response<BasicResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ScheduleEventAdmin admin = realm.where(ScheduleEventAdmin.class).equalTo(Constants.Realm.SCHEDULED_EVENT_ADMIN_ID, eventAdminId).findFirst();
                                admin.deleteFromRealm();
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().onSuccess("InstructorType");
                                getView().showAlert(response.body().getMessage());
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                error.printStackTrace();
                                getView().showAlert(error.getLocalizedMessage());
                            }
                        });
                    } else {
                        getView().showAlert(response.body().getMessage());
                    }
                } else {
                    getView().showError("Oops, something went wrong\nPlease try again");
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                getView().stopLoading();
                getView().showError("Oops, something went wrong\nPlease try again");
            }
        });
    }
}
