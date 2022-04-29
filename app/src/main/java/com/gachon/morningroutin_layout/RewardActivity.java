package com.gachon.morningroutin_layout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardActivity  extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener {
    private static final String TAG = "rewardTag";
    // get instances for firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = firebaseDatabase.getReference();
    int tree01, tree02, tree03, tree04, tree05, tree06, tree07, tree08, tree09;
    TextView inv01, inv02, inv03, inv04, inv05, inv06, inv07, inv08, inv09;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        inv01 = (TextView)findViewById(R.id.inventory01);
        inv02 = (TextView)findViewById(R.id.inventory02);
        inv03 = (TextView)findViewById(R.id.inventory03);
        inv04 = (TextView)findViewById(R.id.inventory04);
        inv05 = (TextView)findViewById(R.id.inventory05);
        inv06 = (TextView)findViewById(R.id.inventory06);
        inv07 = (TextView)findViewById(R.id.inventory07);
        inv08 = (TextView)findViewById(R.id.inventory08);
        inv09 = (TextView)findViewById(R.id.inventory09);



        databaseRef.child("user").child("user_inventory").child("tree01").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tree01 = (int)snapshot.getValue(Integer.class);
                inv01.setText(tree01);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error connecting DB", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView item[]=new ImageView[9];
        //set image for drag
        for (int i=1;i<=9; i++) {
            int k = getResources().getIdentifier("item0" + i,"id", getPackageName());
            item[i-1] = (ImageView) findViewById(k);
            item[i-1].setTag("ANDROID ICON");
            item[i-1].setOnLongClickListener(this);
        }

        //set table for switch
        for (int i=1;i<=4;i++) {
            for (int j=1;j<=5;j++) {
                int k = getResources().getIdentifier("imageView" + i + "_" + j, "id", getPackageName());
                findViewById(k).setOnDragListener(this);
            }
        }

        // bottom menu
        //image button
        ImageButton main = findViewById(R.id.Plan);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        ImageButton option = findViewById(R.id.Options);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        ImageButton graph = findViewById(R.id.Graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        //text for button
        TextView txtMain = findViewById(R.id.textPlan);
        txtMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        TextView txtOption = findViewById(R.id.textOptions);
        txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        TextView txtGraph = findViewById(R.id.textGraph);
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        // Instantiates the drag shadow builder.
        View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(view);
        // Starts the drag
        view.startDrag(data        // data to be dragged
                , dragShadow   // drag shadow builder
                , view           // local data about the drag and drop operation
                , 0          // flags (not currently used, set to 0)
        );
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;
                //some events if we can use
            case DragEvent.ACTION_DRAG_ENTERED:
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                return true;

            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                String dragData = item.getText().toString();
                View vw = (View) event.getLocalState();
                ImageView setting= (ImageView)v;
                ImageView resource=(ImageView) vw;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) resource.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                setting.setImageBitmap(bitmap);
                //should be code change for count --
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw); //remove the dragged view
                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                return true;
            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}