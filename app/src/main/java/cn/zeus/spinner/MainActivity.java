package cn.zeus.spinner;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private NormalSpinner spinner;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (NormalSpinner) findViewById(R.id.spinner);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("spinner question-" + i);
        }
        bindSpinner(list);
    }

    public void bindSpinner(List<String> list) {
        //设置下拉选择框的样式
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView)view.findViewById(android.R.id.text1)).setTextSize(DeviceUtil.getFontSize(getContext()) + 2);
                return view;
            }
        };
        spinner.setAdapter(adapter);
        spinner.setText(list.get(0).toString());
    }


}
