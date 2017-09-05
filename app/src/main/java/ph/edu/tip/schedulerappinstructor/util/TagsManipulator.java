package ph.edu.tip.schedulerappinstructor.util;

import android.text.TextUtils;

/**
 * Created by itsodeveloper on 04/08/2017.
 */

public class TagsManipulator {

    public static String SplitTag(String tag)
    {
        String outputTag="";
        String[] items = tag.split(",");
        for (String item : items)
        {
            outputTag += "#"+item+" ";

        }
        return outputTag;
    }

    public static String JoinTags(String[] tags)
    {
        return TextUtils.join(",", tags).trim();
    }

    public static String ReservationStatus(String res)
    {

        String outputRes="";
       if(res.equalsIgnoreCase("O"))
            outputRes = "Ongoing";

        else if (res.equalsIgnoreCase("D"))
           outputRes = "Done";  ;

        return "Status: "+outputRes;
    }

}
