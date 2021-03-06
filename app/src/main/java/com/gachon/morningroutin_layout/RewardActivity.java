package com.gachon.morningroutin_layout;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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
    static final int itemNum = 15;
    private static final String TAG = "rewardTag";
    // get instances for firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = firebaseDatabase.getReference();
    // array for image/textviews for drag-and-drop listener and database link
    int tree[] = new int [itemNum];
    TextView inv[] = new TextView[itemNum];
    ImageView item[] = new ImageView[itemNum];
    ImageView tile[][] = new ImageView[4][5];
    int del_target;
    int get_target;

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
        inv[9] = (TextView)findViewById(R.id.inventory10);
        inv[10] = (TextView)findViewById(R.id.inventory11);
        inv[11] = (TextView)findViewById(R.id.inventory12);
        inv[12] = (TextView)findViewById(R.id.inventory13);
        inv[13] = (TextView)findViewById(R.id.inventory14);
        inv[14] = (TextView)findViewById(R.id.inventory15);

        // set image for drag
        for (int i=0;i<itemNum; i++) {
            if(i<9) {
                int k = getResources().getIdentifier("item0" + (i+1), "id", getPackageName());
                item[i] = (ImageView) findViewById(k);
                item[i].setTag("ANDROID ICON");
            }
            else{
                int k = getResources().getIdentifier("item" + (i+1), "id", getPackageName());
                item[i] = (ImageView) findViewById(k);
                item[i].setTag("ANDROID ICON");
            }
            if (inv[i].getText().equals('0')==false) {
                item[i].setOnLongClickListener(this);
            }
        }

        //set table for switch
        for (int i=0;i<4;i++) {
            for (int j=0;j<5;j++) {
                int k = getResources().getIdentifier("imageView" + (i+1) + "_" + (j+1), "id", getPackageName());
                tile[i][j]=(ImageView) findViewById(k);
                tile[i][j].setOnDragListener(this);
                tile[i][j].setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View view) {
                        del_target=view.getId();
                        ImageView temp= findViewById(del_target);
                        if(temp.getDrawable()!=null){
                            showDialog();
                        }
                        return true;
                    }
                });
            }
        }

        // get inventory data from firebase, update whenever value changes
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
                tree[9]= inventory.getTree10();
                tree[10]= inventory.getTree11();
                tree[11]= inventory.getTree12();
                tree[12]= inventory.getTree13();
                tree[13]= inventory.getTree14();
                tree[14]= inventory.getTree15();

                for (int i=0; i<itemNum; i++){
                    inv[i].setText(String.valueOf(tree[i]));
                }
                for (int i=0; i<itemNum; i++) {
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

        // get village data from firebase, update whenever value changes
        DatabaseReference villageRef = firebaseDatabase.getReference();
        villageRef.child("village").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String villageList[] = new String[20];
                getVillage village = snapshot.getValue(getVillage.class);
                villageList = village.getVillageArray();
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 4; j++){
                        if(villageList[j * 5 + i].equals("tree00") == false){
                            int imageSrc = getResources().getIdentifier(villageList[j * 5 + i], "drawable", getPackageName());
                            tile[j][i].setImageResource(imageSrc);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // bottom menu
        // image button
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

    private void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(RewardActivity.this)
                .setTitle("????????? ??????")
                .setMessage("?????? ???????????? ???????????????. ?????? ???????????????????")
                .setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ImageView delete=findViewById(del_target);
                        DatabaseReference deleteTile = firebaseDatabase.getReference();
                        for (int k = 0; k<4; k++){
                            for(int j=0; j<5; j++){
                                if(del_target==tile[k][j].getId()){
                                    if(k * 5 + j>=9) {
                                        deleteTile.child("village").child("tile" + ((k * 5 + j)+1)).setValue("tree00");
                                    }
                                    else{
                                        deleteTile.child("village").child("tile0" + ((k * 5 + j)+1)).setValue("tree00");
                                    }
                                }
                            }
                        }
                        delete.setImageBitmap(null);
                        //Toast.makeText(RewardActivity.this, "??????", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(RewardActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
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
        int itemID[] = new int [itemNum];
        for (int i=0; i<itemNum; i++){
            itemID[i]=item[i].getId();
        }
        //int itemID = item[0].getId();
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

                //Toast.makeText(getApplicationContext(), String.valueOf(setting.getId()), Toast.LENGTH_LONG).show();

                // get tile ID on create
                int tileID = tile[0][0].getId();

                DatabaseReference villageRef = firebaseDatabase.getReference();
                for(int i = 1; i <= 20; i++) {
                    if (i < 6) {
                        if (setting.getId() == (tileID + i - 1)) {
                            for (int j = 1; j <= itemNum; j++) {
                                if (resource.getId() == (itemID[j - 1])) {
                                        if (j > 9)
                                            villageRef.child("village").child("tile0" + i).setValue("tree" + j);
                                        else
                                            villageRef.child("village").child("tile0" + i).setValue("tree0" + j);
                                }
                            }
                        }
                    }
                    else {
                        if (setting.getId() == (tileID + i)) {
                            for (int j = 1; j <= itemNum; j++) {
                                if (resource.getId() == (itemID[j - 1])) {
                                    if (i < 10) {
                                        if (j > 9)
                                            villageRef.child("village").child("tile0" + i).setValue("tree" + j);
                                        else
                                            villageRef.child("village").child("tile0" + i).setValue("tree0" + j);
                                    } else {
                                        if (j > 9)
                                            villageRef.child("village").child("tile" + i).setValue("tree" + j);
                                        else
                                            villageRef.child("village").child("tile" + i).setValue("tree0" + j);
                                    }
                                }
                            }
                        }
                    }
                }

                // when item is used, update database
                DatabaseReference itemRef = firebaseDatabase.getReference();
                for (int i=0; i<itemNum; i++){
                    if (resource.getId()== itemID[i]){
                        get_target=i;
                        if(i<9){
                            itemRef.child("inventory").child("tree0"+(i+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int tree1 = (int)snapshot.getValue(Integer.class);
                                    tree1 -= 1;
                                    itemRef.child("inventory").child("tree0"+(get_target+1)).setValue(tree1);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                        else{
                            itemRef.child("inventory").child("tree"+(i+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int tree1 = (int)snapshot.getValue(Integer.class);
                                    tree1 -= 1;
                                    itemRef.child("inventory").child("tree"+(get_target+1)).setValue(tree1);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
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