package pro.sunriseforest.sunriseforestapp_client.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;



public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

        this.clicklistener = clicklistener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
//                View child = recycleView.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && clicklistener != null) {
//                    clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
//                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
            clicklistener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
