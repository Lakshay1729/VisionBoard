package com.heartmeetsoul.visionboard.EXpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> idea = new ArrayList<String>();
        idea.add("The basic idea behind this app is to visualize the ideas, dreams and memories that are special to people. People usually like to put together photos of moments that were memorable or moments that they wish to come true, one day.");
//        cricket.add("Pakistan");
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");

        List<String> lao = new ArrayList<String>();
        lao.add("Law of attraction is “We become ,what we believe”.\n" +
                "This idea is based upon the fact that in our daily life we attract things that we give most importance to. When we rent our head to certain idea, it definitely pays off. Our actions are mere reflection of our thought process  if we improve our thought process and train our minds to think what is useful then we automatically proceed towards our goals.\n" +
                "And after all the explanation if you don’t believe me, King Khan has also said the obvious “Jis cheez ko tum shiddat se chaho poori kaayenat use tumse milane me lag jati hain”.\n");


        List<String> approle = new ArrayList<String>();
        approle.add("  There is a famous quote in hindi that goes like this “Boond boond se hi ghada bharta hain. Let’s see what does it mean ,it says that we start with the small things and make big. It is the fundamental rule of nature, we use bricks to make houses ,every living organism is made up of small cells etc. We  set goals for short terms that can be kept in check by this app, it improves state of mind and motivate us to proceed further. These small achievements keep us happy\n" +
                "and stress away. It helps concentrate and focus on things that matter and are productive.   \n");


        expandableListDetail.put("The idea of Vision Board", idea);
        expandableListDetail.put("What is law of attraction?", lao);
        expandableListDetail.put("How does app help in  making us happy or help us achieve our goals?", approle);
        return expandableListDetail;
    }
}
