package com.heartmeetsoul.visionboard;

import android.app.Activity;
import android.content.Intent;



public class themeUtils {
   static Intent[]intents=new Intent[]{new Intent (new DreamActivity().getBaseContext(),new DreamActivity().getClass()),new Intent (new DreamGrid().getBaseContext(),new DreamGrid().getClass()),new Intent (new DreamList().getBaseContext(),new DreamList().getClass()),new Intent (new AddDream().getBaseContext(),new AddDream().getClass()),new Intent (new EditDreamActivity().getBaseContext(),new EditDreamActivity().getClass()),new Intent(new ShowDreamActivity().getBaseContext(),new ShowDreamActivity().getClass())};
static Activity[] activities=new Activity[]{new DreamActivity(),new DreamGrid(),new DreamList(),new AddDream(),new ShowDreamActivity(),new EditDreamActivity()};

    private static int cTheme;

    public final static int BLACK = 1;

    public final static int BLUE = 0;

    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;
        for(int i=0;i<activities.length;i++) {
            activities[i].finish();

        }


        activity.startActivities(intents);


    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:

            case BLACK:


                activity.setTheme(R.style.DreActivityThemeDark);

                break;

            case BLUE:

                activity.setTheme(R.style.DreActivityThemeLight);


                break;

        }

    }

}

