package edu.neu.recipehub.utils;


import android.content.Context;
import android.widget.Toast;

/**
 *  A class help the application play some special movements in UI.
 */
public class UIUtils {

    static Toast mToast;

    public static void showToast(Context context, String toast){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,toast,Toast.LENGTH_SHORT);
        mToast.show();
    }
}
