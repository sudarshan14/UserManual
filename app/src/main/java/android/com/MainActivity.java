package android.com;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.com.adapter.UserAdapter;
import android.com.model.UserData;
import android.com.usermanual.R;
import android.com.viewmodel.UserViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.onUpdateButtonListener, UserAdapter.onDeleteButtonListener{

    TextView responseText;

    private RecyclerView recyclerView;

    List<UserData> objUserList ;
    UserData objUserData;
    UserAdapter objUserAdapter;
    UserViewModel objUserModel;
    final Context objContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final UserAdapter objAdapter = new UserAdapter(this,this,this);

        recyclerView.setAdapter(objAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        objUserModel = ViewModelProviders.of(this).get(UserViewModel.class);

        objUserModel.getAllUsers().observe(this, new Observer<List<UserData>>() {
            @Override
            public void onChanged(@Nullable List<UserData> userData) {
                objAdapter.setUsers(userData);
            }
        });

        //responseText = (TextView) findViewById(R.id.responseText);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton objToolbarButton = (ImageButton)findViewById(R.id.toolSlection);
        objToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUserDialog(objContext,"","",null,"Add");
            }
        });


        setSupportActionBar(toolbar);

        initCollapsingToolbar();

    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void showAddUserDialog(Context objContext,String p_strFirstName,String p_strLastName,final UserData objUserData, final String btnName)
    {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(objContext);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(objContext);
        alertDialogBuilderUserInput.setView(mView);

        final EditText objFirstName = (EditText) mView.findViewById(R.id.firstName);
        final EditText objLastName = (EditText)mView.findViewById(R.id.lastName);

        if(!p_strFirstName.equals(""))
        {
            objFirstName.setText(p_strFirstName);
        }

        if(!p_strLastName.equals(""))
        {
            objLastName.setText(p_strLastName);
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(btnName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        InsertUpdateUser(objUserData,btnName,objFirstName.getText().toString(),objLastName.getText().toString());
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }


    @Override
    public void onUpdateButtonListener(UserData userData) {
        showAddUserDialog(objContext,userData.m_strFirstName,userData.m_strLastName,userData,"Update");
        objUserModel.UpdateUser(userData);
    }

    @Override
    public void onDeleteButtonListener(UserData userData) {
        objUserModel.DeleteUser(userData);
    }

    private void InsertUpdateUser(UserData userData, String btnTitle, String firstName, String lastName)
    {
        if(btnTitle.equals("Add"))
        {
            objUserModel.insertUser(new UserData(-1,firstName, lastName,""));
        }
        else
        {
            objUserModel.UpdateUser(new UserData(userData.m_iID,firstName, lastName,userData.m_strAvatar));
        }
    }
}
