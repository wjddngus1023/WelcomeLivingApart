package com.example.final_project.ui.next_month;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.final_project.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class next_monthfrag extends Fragment {

    int thismanage,thiswater,thiselectric,thisgas,prevmanage,prevgas,prevwater,prevelectric,thistotal,prevtotal;
    double avgmanage,avgwater,avgelectric,avggas,avgtotal,differtotal,differgas,differmanage,differwater,differelectric;
    TextView avgmoney,managemoney,gasmoney,electricmoney,watermoney,totalmessage,managemessage,gasmessage,electricmessage,watermessage;
    String red,blue;




    private next_monthViewModel next_monthViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences("this_month", Context.MODE_PRIVATE);
        thismanage = sp.getInt("manageset",0);
        thisgas = sp.getInt("gasset",0);
        thiselectric = sp.getInt("electricset",0);
        thiswater = sp.getInt("waterset",0);
        thistotal = sp.getInt("totalset",0);
        SharedPreferences sp2 = getActivity().getSharedPreferences("prev_month", Context.MODE_PRIVATE);
        prevmanage = sp2.getInt("managesetprev",0);
        prevgas = sp2.getInt("gassetprev",0);
        prevelectric = sp2.getInt("electricsetprev",0);
        prevwater = sp2.getInt("watersetprev",0);
        prevtotal = sp2.getInt("totalsetprev",0);

        next_monthViewModel =
                ViewModelProviders.of(this).get(next_monthViewModel.class);

        View root = inflater.inflate(R.layout.next_month, container, false);

        avgmoney = (TextView)root.findViewById(R.id.nexttotal);
        managemoney = (TextView)root.findViewById(R.id.nextmanage);
        watermoney = (TextView)root.findViewById(R.id.nextwater);
        gasmoney = (TextView)root.findViewById(R.id.nextgas);
        electricmoney = (TextView)root.findViewById(R.id.nextelectric);

        totalmessage = (TextView)root.findViewById(R.id.totalmessage);
        managemessage = (TextView)root.findViewById(R.id.managemessage);
        watermessage = (TextView)root.findViewById(R.id.watermessage);
        gasmessage = (TextView)root.findViewById(R.id.gasmessage);
        electricmessage = (TextView)root.findViewById(R.id.electricmessage);


        avgmanage = (thismanage+prevmanage)/2;
        avgelectric = (thiselectric+prevelectric)/2;
        avggas = (thisgas+prevgas)/2;
        avgwater = (thiswater+prevwater)/2;
        avgtotal = (thistotal+prevtotal)/2;

        differtotal = avgtotal - thistotal;
        differelectric = avgelectric - thiselectric;
        differgas = avggas - thisgas;
        differmanage = avgmanage - thismanage;
        differwater = avgwater - thiswater;

        red = "#C83E85";
        blue = "#1C90B3";
        avgmoney.setText("다음달 예상 납부액 : " + avgtotal + "원");
        managemoney.setText("다음달 예상 관리비 : "+avgmanage+"원");
        watermoney.setText("다음달 예상 수도세 : "+avgwater+"원");
        electricmoney.setText("다음달 예상 전기세 : "+avgelectric+"원");
        gasmoney.setText("다음달 예상 가스비 : "+avggas+"원");

        if(differtotal < 0){
            totalmessage.setText("다음달엔 이번달보다 "+ Math.abs(differtotal)+"원 정도 더 내야해요!");
            totalmessage.setTextColor(Color.parseColor(red));
        }
        else{
            totalmessage.setText("다음달엔 이번달보다 " + Math.abs(differtotal)+"원 정도 덜 내도 될거에요!");
            totalmessage.setTextColor(Color.parseColor(blue));
        }
        if(differmanage <0){
            managemessage.setText("다음달엔 이번달보다 " +Math.abs(differmanage)+"원 정도 더 내야해요!");
            managemessage.setTextColor(Color.parseColor(red));
        }
        else {
            managemessage.setText("다음달엔 이번달보다 "+Math.abs(differmanage)+"원 정도 덜 내도 될거에요!");
            managemessage.setTextColor(Color.parseColor(blue));
        }
        if(differmanage <0){
            watermessage.setText("다음달엔 이번달보다 " +Math.abs(differwater)+"원 정도 더 내야해요!");
            watermessage.setTextColor(Color.parseColor(red));
        }
        else {
            watermessage.setText("다음달엔 이번달보다 "+Math.abs(differwater)+"원 정도 덜 내도 될거에요!");
            watermessage.setTextColor(Color.parseColor(blue));
        }
        if(differelectric <0){
           electricmessage.setText("다음달엔 이번달보다 " +Math.abs(differelectric)+"원 정도 더 내야해요!");
           electricmessage.setTextColor(Color.parseColor(red));
        }
        else {
            electricmessage.setText("다음달엔 이번달보다 "+Math.abs(differelectric)+"원 정도 덜 내도 될거에요!");
            electricmessage.setTextColor(Color.parseColor(blue));
        }
        if(differmanage <0){
            gasmessage.setText("다음달엔 이번달보다 " +Math.abs(differgas)+"원 정도 더 내야해요!");
            gasmessage.setTextColor(Color.parseColor(red));
        }
        else {
            gasmessage.setText("다음달엔 이번달보다 "+Math.abs(differgas)+"원 정도 덜 내도 될거에요!");
            gasmessage.setTextColor(Color.parseColor(blue));
        }




        return root;
    }
}