package com.cu.mae;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    private List<PagerData> pagerData;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(List<PagerData> pagerData, Context context) {
        this.pagerData = pagerData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pagerData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item,container,false);
        ImageView imageView;
        final TextView title;
        imageView=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        imageView.setImageResource(pagerData.get(position).getImage());
        title.setText(pagerData.get(position).getTitle());
        ConstraintLayout category=view.findViewById(R.id.category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                try {
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra("Category", title.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                 */
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
