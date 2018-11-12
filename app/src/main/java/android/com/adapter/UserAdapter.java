package android.com.adapter;

import android.com.model.UserData;
import android.com.usermanual.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*****
 * Lija -
 * Adapter class to access the Recycler view contents
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    public List<UserData>objUserList = new ArrayList<>();
    Context objContext;
    private static onUpdateButtonListener onUpdaTEButtonListener;
    private onDeleteButtonListener onDeleteButtonListener;
    private LayoutInflater layoutInflater;

    public interface onUpdateButtonListener {
        void onUpdateButtonListener(UserData userData);
    }

    public interface onDeleteButtonListener{
        void onDeleteButtonListener(UserData userData);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new UserHolder(itemView);
    }

    public UserAdapter(Context context,onUpdateButtonListener objListener, onDeleteButtonListener objDeleteListener) {
        this.objUserList = new ArrayList<>();
        this.objContext = context;
        this.onUpdaTEButtonListener = objListener;
        this.onDeleteButtonListener = objDeleteListener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        final UserData objUserData = objUserList.get(position);
        holder.firstName.setText(objUserData.getFirstName());
        holder.lastName.setText(objUserData.getLastName());

        if(!objUserData.m_strAvatar.equals(""))
        {
            Picasso.with(holder.thumbnail.getContext())
                    .load(objUserData.getAvatar())
                    .fit()
                    .into(holder.thumbnail);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("lija","Delete called");
                if (onDeleteButtonListener != null)
                    onDeleteButtonListener.onDeleteButtonListener(objUserData);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("lija","Update called");
                if (onUpdaTEButtonListener != null)
                    onUpdaTEButtonListener.onUpdateButtonListener(objUserData);
            }
        });
    }

    class UserHolder extends RecyclerView.ViewHolder{

        public TextView firstName, lastName;
        public ImageView thumbnail, delete, edit;

        public UserHolder(View view)
        {
            super(view);

            firstName = (TextView) view.findViewById(R.id.firstName);
            lastName = (TextView) view.findViewById(R.id.lastName);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            delete = (ImageView) view.findViewById(R.id.delete);
            edit = (ImageView) view.findViewById(R.id.edit) ;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public void setUsers(List<UserData> p_objUserList)
    {
        this.objUserList = p_objUserList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return objUserList.size();
    }
}
