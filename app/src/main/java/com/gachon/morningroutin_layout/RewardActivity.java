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
    int tree[] = new int [9];
    TextView inv[] = new TextView[9];
    ImageView item[]=new ImageView[9];
    final int itemID=2131296528;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        inv[0] = (TextView)findViewById(R.id.inventory01);
        inv[1] = (TextView)findViewById(R.id.inventory02);
        inv[2] = (TextView)findViewById(R.id.inventory03);
        inv[3] = (TextView)findViewById(R.id.inventory04);
        inv[4] = (TextView)findViewById(R.id.inventory05);
        inv[5] = (TextView)findViewById(R.id.inventory06);
        inv[6] = (TextView)findViewById(R.id.inventory07);
        inv[7] = (TextView)findViewById(R.id.inventory08);
        inv[8] = (TextView)findViewById(R.id.inventory09);

        //set image for drag
        for (int i=0;i<=8; i++) {
            int k = getResources().getIdentifier("item0" + (i+1),"id", getPackageName());
            item[i] = (ImageView) findViewById(k);
            item[i].setTag("ANDROID ICON");
            if (inv[i].getText().equals('0')==false) {
                item[i].setOnLongClickListener(this);
            }
        }

        //set table for switch
        for (int i=1;i<=4;i++) {
            for (int j=1;j<=5;j++) {
                int k = getResources().getIdentifier("imageView" + i + "_" + j, "id", getPackageName());
                findViewById(k).setOnDragListener(this);
            }
        }

        DatabaseReference inventoryRef = firebaseDatabase.getReference();
        inventoryRef.child("inventory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getInventory inventory = snapshot.getValue(getInventory.class);

                tree[0] = inventory.getTree01();
                tree[1] = inventory.getTree02();
                tree[2] = inventory.getTree03();
                tree[3] = inventory.getTree04();
                tree[4] = inventory.getTree05();
                tree[5] = inventory.getTree06();
                tree[6] = inventory.getTree07();
                tree[7] = inventory.getTree08();
                tree[8] = inventory.getTree09();

                for (int i=0; i<9; i++){
                    inv[i].setText(String.valueOf(tree[i]));
                }
                for (int i=0; i<9; i++) {
                    if (tree[i] == 0) {
                        item[i].setAlpha(50);
                        item[i].setEnabled(false);
                    } else {
                        item[i].setAlpha(255);
                        item[i].setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to connect DB", Toast.LENGTH_SHORT).show();
            }
        });

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
                //ViewGroup owner = (ViewGroup) vw.getParent();
                //owner.removeView(vw); //remove the dragged view
                // Returns true. DragEvent.getResult() will return true.
                // when item is used, update database
                DatabaseReference itemRef = firebaseDatabase.getReference();
                switch (resource.getId()){
                    case itemID:
                        itemRef.child("inventory").child("tree01").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree1 = (int)snapshot.getValue(Integer.class);
                                tree1 -= 1;
                                itemRef.child("inventory").child("tree01").setValue(tree1);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 1:
                        itemRef.child("inventory").child("tree02").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree2 = (int)snapshot.getValue(Integer.class);
                                tree2 -= 1;
                                itemRef.child("inventory").child("tree02").setValue(tree2);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 2:
                        itemRef.child("inventory").child("tree03").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree3 = (int)snapshot.getValue(Integer.class);
                                tree3 -= 1;
                                itemRef.child("inventory").child("tree03").setValue(tree3);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 3:
                        itemRef.child("inventory").child("tree04").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree4 = (int)snapshot.getValue(Integer.class);
                                tree4 -= 1;
                                itemRef.child("inventory").child("tree04").setValue(tree4);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 4:
                        itemRef.child("inventory").child("tree05").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree5 = (int)snapshot.getValue(Integer.class);
                                tree5 -= 1;
                                itemRef.child("inventory").child("tree05").setValue(tree5);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 5:
                        itemRef.child("inventory").child("tree06").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree6 = (int)snapshot.getValue(Integer.class);
                                tree6 -= 1;
                                itemRef.child("inventory").child("tree06").setValue(tree6);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 6:
                        itemRef.child("inventory").child("tree07").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree7 = (int)snapshot.getValue(Integer.class);
                                tree7 -= 1;
                                itemRef.child("inventory").child("tree07").setValue(tree7);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 7:
                        itemRef.child("inventory").child("tree08").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree8 = (int)snapshot.getValue(Integer.class);
                                tree8 -= 1;
                                itemRef.child("inventory").child("tree08").setValue(tree8);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    case itemID + 8:
                        itemRef.child("inventory").child("tree09").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int tree9 = (int)snapshot.getValue(Integer.class);
                                tree9 -= 1;
                                itemRef.child("inventory").child("tree09").setValue(tree9);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    default:
                        break;
                }
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