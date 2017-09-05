package ph.edu.tip.schedulerappinstructor.util;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by itsodeveloper on 05/09/2017.
 */

public class RequestBodyCreate {

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("text/plain"), descriptionString);
    }

    @NonNull
    private RequestBody createPartFromImage(File file) {
        return RequestBody.create(
                MediaType.parse("image/*"), file);
    }
}
