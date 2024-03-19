package com.GameWFriends;

import android.view.View;

import java.util.List;

/**
 * This class is used to store useful functions for fragments and activities.
 */
public class UsefulFragmentandActivityTools {

    /**
     * hideUiElements is a method that hides the UI elements of the LoginActivity
     * @param views the views to hide
     */
    public static void hideUiElements(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * showUiElements is a method that shows the UI elements of the main LoginActivity
     * @param views the views to show
     */
    public static void showUiElements(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
