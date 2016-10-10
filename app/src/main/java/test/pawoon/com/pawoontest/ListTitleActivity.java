package test.pawoon.com.pawoontest;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by franky on 10/10/16.
 */

public class ListTitleActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Title List");
        ArrayList<String> listTitle = getIntent().getExtras().getStringArrayList("titleList");

        if (!listTitle.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listTitle);

            setListAdapter(adapter);
            //
        }

    }
}
