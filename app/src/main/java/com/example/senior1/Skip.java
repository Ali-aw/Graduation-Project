package com.example.senior1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Skip extends AppCompatActivity {



    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tg", "888888888888888888onResume888888888888: ");

        SharedPreferences sp=getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        Log.i("tg", "onResumeskip: "+sp.getBoolean(prevStarted,false));
        if(!sp.getBoolean(prevStarted,false))
        {
            SharedPreferences.Editor e =sp.edit();
            e.putBoolean(prevStarted,Boolean.TRUE);
            e.apply();
        }
        else
        {
            Intent i= new Intent(getApplicationContext(),Welcome.class);
            startActivity(i);
        }
    }

    // creating variables for view pager,
    // liner layout, adapter and our array list.
    private ViewPager viewPager;
    private LinearLayout dotsLL;
    SliderAdapter adapter;
    private ArrayList<SliderModal> sliderModalArrayList;
    private TextView[] dots;
    int size,counter;
    String prevStarted="yes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        Log.i("tg", "888888888888888888onResume888888888888: ");

        System.out.println("********************2: " + counter);
        //sp = this.getSharedPreferences("count", Context.MODE_PRIVATE);

       // if(counter>0)
        //{
          //  Intent i= new Intent(getApplicationContext(),Welcome.class);
            //startActivity(i);
       // }
        //sp.edit().putInt("count",1);
      //  System.out.println("********************2: " + counter);
        //  Intent i= new Intent(getApplicationContext(),Welcome.class);
        //  startActivity(i);


        // initializing all our views.
        viewPager = findViewById(R.id.idViewPager);
        dotsLL = findViewById(R.id.idLLDots);


        // in below line we are creating a new array list.
        sliderModalArrayList = new ArrayList<>();

        // on below 3 lines we are adding data to our array list.
        sliderModalArrayList.add(new SliderModal("Let's Travel Together", "Travelling Together Is Fun ^_^ ", "https://assets.entrepreneur.com/content/3x2/2000/20160308205658-ridesharing-car-taxis-drive.jpeg?auto=webp&quality=95&crop=16:9&width=675", R.drawable.gradient_one));
        sliderModalArrayList.add(new SliderModal("Connect with drivers headed the same way ", "Finding A Ride Is Fast &Simple", "https://us.123rf.com/450wm/andreypopov/andreypopov1904/andreypopov190400962/121520012-group-of-happy-friends-having-fun-in-the-car.jpg?ver=6", R.drawable.gradient_one));
        sliderModalArrayList.add(new SliderModal("Schedule A Ride ", "Choose Source and Destination...", "https://previews.123rf.com/images/andreypopov/andreypopov1905/andreypopov190500056/121987146-smiling-young-man-talking-with-a-lady-sitting-inside-car.jpg", R.drawable.gradient_one));

        // below line is use to add our array list to adapter class.
        adapter = new SliderAdapter(Skip.this, sliderModalArrayList);

        // below line is use to set our
        // adapter to our view pager.

            viewPager.setAdapter(adapter);

            size = sliderModalArrayList.size();
            addDots(size, 0);

       // System.out.println("__________________________________" + count);
        viewPager.addOnPageChangeListener(viewListner);


    }


    private void addDots(int size, int pos)
    {

        // inside this method we are
        // creating a new text view.
        dots = new TextView[size];

        // below line is use to remove all
        // the views from the linear layout.
        dotsLL.removeAllViews();

        // running a for loop to add
        // number of dots to our slider.
        for (int i = 0; i < size; i++) {
            // below line is use to add the
            // dots and modify its color.
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("•"));
            dots[i].setTextSize(35);

            // below line is called when the dots are not selected.
            dots[i].setTextColor(getResources().getColor(R.color.black));
            dotsLL.addView(dots[i]);
        }
        if (dots.length > 0) {
            // this line is called when the dots
            // inside linear layout are selected
            dots[pos].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    // creating a method for view pager for on page change listener.
    int c=0;
    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if(position==2)
            {
                if(c>1)
                {

                      Intent i= new Intent(getApplicationContext(),Welcome.class);
                       startActivity(i);
                }
                c++;


            }

        }

        @Override
        public void onPageSelected(int position) {
            // we are calling our dots method to
            // change the position of selected dots.
            addDots(size, position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }


    };



    public void skip(View view)
    {


        Intent i= new Intent(getApplicationContext(), Welcome.class);
        startActivity(i);
    }










    /*
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pppppppppppppppppppppppppppppppppppp");
        Intent i= new Intent(getApplicationContext(),Welcome.class);
        startActivity(i);
    }

 */
}
