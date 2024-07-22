package com.example.lost_and_found_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;

public class SeeAllLostItemsActivity extends AppCompatActivity {

    ArrayAdapter itemArrayAdapter;
    List<LostAndFoundModel> allPosts;
    ListView itemList;
    DbHelper dataBaseHelper;
    Button deletePostBtn;
    Boolean isDeleteBtnVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_see_all_lost_items);
        itemList = findViewById(R.id.idListView);
        deletePostBtn = findViewById(R.id.idDeletePostBtn);

        dataBaseHelper = new DbHelper(SeeAllLostItemsActivity.this);
        allPosts = dataBaseHelper.getAllItemRecords();

        itemArrayAdapter = new ArrayAdapter<LostAndFoundModel>(SeeAllLostItemsActivity.this, android.R.layout.simple_list_item_1, allPosts);
        itemList.setAdapter(itemArrayAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isDeleteBtnVisible) {
                    deletePostBtn.setVisibility(View.VISIBLE);
                    isDeleteBtnVisible = true;
                    deletePostBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LostAndFoundModel clickedPostModel = (LostAndFoundModel) adapterView.getItemAtPosition(i);
                            dataBaseHelper.deleteLostOrFoundItemRecord(clickedPostModel);
                            Toast.makeText(SeeAllLostItemsActivity.this, "Post Successfully Deleted", Toast.LENGTH_SHORT).show();
                            deletePostBtn.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    isDeleteBtnVisible = false;
                    deletePostBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}