package ph.edu.tip.schedulerappinstructor.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ph.edu.tip.schedulerappinstructor.model.data.Admin;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RealmQuery {


    public static Admin getUser(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Admin.class).findFirst();
    }




}
