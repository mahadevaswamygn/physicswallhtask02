package com.example.physicswallah;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    Button addButton;
    ItemModel country;
    ArrayList<ItemModel> itemList = new ArrayList<ItemModel>();
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addItem);
        displayListView();
        removeButtonClick();
    }

    private void displayListView() {
        dataAdapter = new MyCustomAdapter(this,
                R.layout.activity_list_view_with_checkbox_item, itemList);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ItemModel country = (ItemModel) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView inputName = findViewById(R.id.textViewInput);

                country = new ItemModel(inputName.getText().toString(), false);
                itemList.add(country);
                dataAdapter = new MyCustomAdapter(getBaseContext(),
                        R.layout.activity_list_view_with_checkbox_item, itemList);
                listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();
                inputName.setText("");
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<ItemModel> {

        private ArrayList<ItemModel> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<ItemModel> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<ItemModel>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.activity_list_view_with_checkbox_item, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textItem);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        ItemModel country = (ItemModel) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ItemModel country = countryList.get(position);
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);
            return convertView;
        }
    }

    private void removeButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemModel> countryList = dataAdapter.countryList;
                if(country.isSelected()){
                    for (int i = 0; i < countryList.size(); i++) {
                        ItemModel country = countryList.get(i);
                        if (country.isSelected()) {
                            countryList.remove(i);
                        }
                    }
                }

                dataAdapter = new MyCustomAdapter(getBaseContext(),
                        R.layout.activity_list_view_with_checkbox_item, countryList);
                listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();
            }
        });
    }
}