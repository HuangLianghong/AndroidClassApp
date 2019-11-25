package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout view =(FrameLayout)inflater.inflate(R.layout.fragment_game, container, false);
        GameView gameView=new GameView(getContext());
        view.addView(gameView);
        gameView.setMinimumHeight(view.getHeight());
        gameView.setMinimumWidth(view.getWidth());
        return view;
    }

}
