package git.rahulparihar.materialdesignshowcase.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.transition.Fade;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import git.rahulparihar.materialdesignshowcase.R;


public class GUIUtils {

    public static final int SCALE_FACTOR = 30;

    public static void configureWindowEnterExitExplodeTransition(Window w) {

        Fade ex = new Fade();
        ex.setInterpolator(new PathInterpolator(0.4f, 0, 1, 1));
        ex.setDuration(5000);
        w.setExitTransition(ex);
        w.setEnterTransition(ex);
    }

    public static void configureFab(View fabButton) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabButton.setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int fabSize = view.getContext().getResources().getDimensionPixelSize(R.dimen.fab_size);
                    outline.setOval(0, 0, fabSize, fabSize);
                }
            });
        } else {
            ((ImageButton) fabButton).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    public static int getStatusBarHeight(Context c) {

        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void hideRevealEffect(final View v, int centerX, int centerY, int initialRadius) {

        v.setVisibility(View.VISIBLE);

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, initialRadius, 0);

        anim.setDuration(350);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    public static void showRevealEffect(final View v, int centerX, int centerY, Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int height = v.getHeight();

        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, height);

        anim.setDuration(350);

        anim.addListener(lis);
        anim.start();
    }


    public static void hideViewByScale(View view) {

        ViewPropertyAnimator propertyAnimator = view.animate().setStartDelay(SCALE_FACTOR)
                .scaleX(0).scaleY(0);

        propertyAnimator.start();
    }

    public static void showViewByScale(View view) {

        ViewPropertyAnimator propertyAnimator = view.animate().setStartDelay(SCALE_FACTOR)
                .scaleX(1).scaleY(1);

        propertyAnimator.start();
    }


}
