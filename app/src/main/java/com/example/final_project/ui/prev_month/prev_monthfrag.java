package com.example.final_project.ui.prev_month;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.final_project.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;

public class prev_monthfrag extends Fragment {

    BarChart barChart;
    int differ,thismonth,prevmonth,chicken,candy,coffee,rice;
    TextView message1, message2;
    ImageView img;
    String red,blue;




    private prev_monthViewModel prevmonthViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

    SharedPreferences sp = getActivity().getSharedPreferences("this_month", Context.MODE_PRIVATE);
    thismonth = sp.getInt("totalset",0);

    SharedPreferences sp2 = getActivity().getSharedPreferences("prev_month",Context.MODE_PRIVATE);
    prevmonth = sp2.getInt("totalsetprev",0);

        prevmonthViewModel =
                ViewModelProviders.of(this).get(prev_monthViewModel.class);

        View root = inflater.inflate(R.layout.prev_month, container, false);

        message1 = (TextView)root.findViewById(R.id.message1);
        message2 = (TextView)root.findViewById(R.id.message2);
        img = (ImageView)root.findViewById(R.id.img);

        differ = prevmonth - thismonth;

        candy = differ / 300;
        coffee = differ / 2500;
        rice = differ / 6000;
        chicken = differ / 19000;
        red = "#C83E85";
        blue = "#1C90B3";

        message1.setText("????????? ?????? " + differ + "??? ???????????????");
        message1.setTextColor(Color.parseColor(blue));

        if(differ < 0) {
            message1.setText("????????? ?????? " + Math.abs(differ) + "??? ?????? ?????????");
            message1.setTextColor(Color.parseColor(red));
            message2.setText("???????????? ??? ????????? ??????????????? ??????");
            img.setImageResource(R.drawable.nomoney);
        }
        else if(0<=differ && differ<2000){
            img.setImageResource(R.drawable.candy);
            message2.setText("???????????? ?????? " + candy + "?????? ??? ????????? ??? ?????????!");
        }
        else if(2001<=differ && differ<8000){
            img.setImageResource(R.drawable.coffee);
            message2.setText("???????????? ?????? " + coffee + "?????? ??? ?????? ??? ?????????!");
        }
        else if(8001<=differ && differ<18999){
            img.setImageResource(R.drawable.rice);
            message2.setText("???????????? ??? " + rice + "?????? ??? ????????? ??? ?????????!");
        }
        else if(19000<=differ){
            img.setImageResource(R.drawable.chicken);
            message2.setText("???????????? ?????? "+chicken+"????????? ??? ???????????? ??? ?????????!");
        }


        return root;
    }
}