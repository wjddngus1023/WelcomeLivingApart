package com.example.final_project.ui.this_month;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.SharedPreferences;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.graphics.drawable.Drawable;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.text.style.ForegroundColorSpan;
        import android.text.style.RelativeSizeSpan;
        import android.text.style.StyleSpan;
        import android.view.LayoutInflater;
        import android.view.SurfaceHolder;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.EditText;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProviders;
        import com.example.final_project.R;
        import com.prolificinteractive.materialcalendarview.CalendarDay;
        import com.prolificinteractive.materialcalendarview.CalendarMode;
        import com.prolificinteractive.materialcalendarview.DayViewDecorator;
        import com.prolificinteractive.materialcalendarview.DayViewFacade;
        import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
        import com.prolificinteractive.materialcalendarview.spans.DotSpan;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Collection;
        import java.util.Date;
        import java.util.HashSet;
        import java.util.List;


public class this_monthfrag extends Fragment {

    MaterialCalendarView calendarView;
    TextView totalmoney, gasmoney, watermoney, managemoney, eletricmoney;
    int gas,water,manage,electric,total,gasprev,waterprev,manageprev,electricprev,totalprev;
    Button insertmoney,insertday,insertprev;
    EditText edtmanage,edtgas,edtelectric,edtwater,edtdaymanage,edtdaygas,edtdayelectric,edtdaywater,edtmanageprev,edtgasprev,edtelectricprev,edtwaterprev;
    String managestr,gasstr,electricstr,waterstr,managedaystr,gasdaystr,electricdaystr,waterdaystr,managestrprev,gasstrprev,electricstrprev,waterstrprev;
    View dlgView1,dlgView2,dlgView3;
    SQLiteDatabase sqlDB;




    private this_monthViewModel thismonthViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences("this_month", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        SharedPreferences sp2 = getActivity().getSharedPreferences("prev_month", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = sp2.edit();

        thismonthViewModel =
                ViewModelProviders.of(this).get(this_monthViewModel.class);

        final View root = inflater.inflate(R.layout.this_month, container, false);

        totalmoney = (TextView)root.findViewById(R.id.totalmoney);
        gasmoney = (TextView)root.findViewById(R.id.gasmoney);
        watermoney = (TextView)root.findViewById(R.id.watermoney);
        managemoney = (TextView)root.findViewById(R.id.managemoney);
        eletricmoney = (TextView)root.findViewById(R.id.electricmoney);
        insertday = (Button)root.findViewById(R.id.insertday);
        insertmoney = (Button)root.findViewById(R.id.insertmoney);
        insertprev = (Button)root.findViewById(R.id.insertprev);




        gas = sp.getInt("gasset",0);
        manage = sp.getInt("manageset",0);
        water = sp.getInt("waterset",0);
        electric = sp.getInt("electricset",0);
        total = sp.getInt("totalset",0);

        gasmoney.setText("가스비 : " + gas + "원");
        eletricmoney.setText("전기세 : " + electric + "원");
        managemoney.setText("관리비 : " + manage + "원");
        watermoney.setText("수도세 : " + water + "원");
        totalmoney.setText("이번달 납부액 합계 : " + total + "원");


        final myDBHelper dbHelper = new myDBHelper(getContext());


        calendarView = (MaterialCalendarView)root.findViewById(R.id.thiscalendar);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            ArrayList days = new ArrayList();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");




        insertmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dlgView1 = (View)View.inflate(getActivity(),R.layout.dialogview1,null);
                builder.setView(dlgView1);


                edtmanage = (EditText)dlgView1.findViewById(R.id.edtmanage);
                edtmanage.setHint(""+manage);
                edtgas = (EditText)dlgView1.findViewById(R.id.edtgas);
                edtgas.setHint(""+gas);
                edtelectric = (EditText)dlgView1.findViewById(R.id.edtelectric);
                edtelectric.setHint(""+electric);
                edtwater = (EditText)dlgView1.findViewById(R.id.edtwater);
                edtwater.setHint(""+water);


                builder.setTitle("납부 데이터 입력").setMessage("납부액을 입력하세요");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        managestr = edtmanage.getText().toString();
                        managemoney.setText("관리비 : "+ managestr+"원");
                        gasstr = edtgas.getText().toString();
                        gasmoney.setText("가스비 : " + gasstr + "원");
                        electricstr = edtelectric.getText().toString();
                        eletricmoney.setText("전기세 : " +  electricstr +"원");
                        waterstr = edtwater.getText().toString();
                        watermoney.setText("수도세 : " +waterstr+"원");

                        water = Integer.parseInt(waterstr);
                        electric = Integer.parseInt(electricstr);
                        gas = Integer.parseInt(gasstr);
                        manage = Integer.parseInt(managestr);
                        total = water+electric+gas+manage;

                        totalmoney.setText("이번달 납부액 합계 : " + total + "원");

                        editor.putInt("manageset",manage);
                        editor.putInt("gasset",gas);
                        editor.putInt("electricset",electric);
                        editor.putInt("waterset",water);
                        editor.putInt("totalset",total);
                        editor.apply();

                        sqlDB = dbHelper.getWritableDatabase();
                        //sqlDB.execSQL("INSERT INTO PayDB VALUES (total);");
                        // sqlDB.execSQL("UPDATE PayDB SET gNumber = ("+total+") WHERE gName = ('"+edtdaygas+"');");
                        sqlDB.close();

                        Toast.makeText(getActivity(),"반영되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"취소되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }

        });

        insertday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dlgView2 = (View)View.inflate(getActivity(),R.layout.dialogview2,null);
                builder.setView(dlgView2);

                edtdaymanage = (EditText)dlgView2.findViewById(R.id.edtdaymanage);
                edtdaygas = (EditText)dlgView2.findViewById(R.id.edtdaygas);
                edtdayelectric = (EditText)dlgView2.findViewById(R.id.edtdayelectric);
                edtdaywater = (EditText)dlgView2.findViewById(R.id.edtdaywater);

                builder.setTitle("납부 데이터 입력").setMessage("납부일을 숫자만 입력하세요");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        managedaystr = edtdaymanage.getText().toString();
                        gasdaystr = edtdaygas.getText().toString();
                        electricdaystr = edtdayelectric.getText().toString();
                        waterdaystr = edtdaywater.getText().toString();

                        Toast.makeText(getActivity(),"반영되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"취소되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        insertprev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dlgView3 = (View)View.inflate(getActivity(),R.layout.dialogview3,null);
                    builder.setView(dlgView3);


                    SharedPreferences sp2 = getActivity().getSharedPreferences("prev_month", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sp2.edit();

                    edtmanageprev = (EditText)dlgView3.findViewById(R.id.edtmanageprev);
                    edtgasprev = (EditText)dlgView3.findViewById(R.id.edtgasprev);
                    edtelectricprev = (EditText)dlgView3.findViewById(R.id.edtelectricprev);
                    edtwaterprev = (EditText)dlgView3.findViewById(R.id.edtwaterprev);


                    builder.setTitle("납부 데이터 입력").setMessage("이전 월의 납부액을 입력하세요");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            managestrprev = edtmanageprev.getText().toString();
                            gasstrprev = edtgasprev.getText().toString();
                            electricstrprev = edtelectricprev.getText().toString();
                            waterstrprev = edtwaterprev.getText().toString();


                            waterprev = Integer.parseInt(waterstrprev);
                            electricprev = Integer.parseInt(electricstrprev);
                            gasprev = Integer.parseInt(gasstrprev);
                            manageprev = Integer.parseInt(managestrprev);
                            totalprev = waterprev+electricprev+gasprev+manageprev;


                            editor2.putInt("managesetprev",manageprev);
                            editor2.putInt("gassetprev",gasprev);
                            editor2.putInt("electricsetprev",electricprev);
                            editor2.putInt("watersetprev",waterprev);
                            editor2.putInt("totalsetprev",totalprev);
                            editor2.apply();


                            Toast.makeText(getActivity(),"반영되었습니다",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"취소되었습니다",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }

            });


        return root;
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }



    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context ,"PayDB",null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE PayDB (pays INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS PayDB");
            onCreate(db);
        }
    }

}