package com.example.quanlisachpn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisachpn.ChaoActivity;
import com.example.quanlisachpn.QlNguoiDungActivity;
import com.example.quanlisachpn.model.User;
import com.example.quanlisachpn.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class RecyclerUser extends RecyclerView.Adapter<RecyclerUser.ViewHolder> {
    List<User> userList;
    Context context;
    int layout;

    public static boolean flag = false;

    public RecyclerUser(List<User> userList, Context context, int layout) {
        this.userList = userList;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textUser.setText("Username: "+userList.get(position).getName());
        holder.textSdt.setText("Phone number: "+userList.get(position).getPhoneNumber());
        if (flag == true) {
            holder.btn.setText(userList.get(position).textButton);
            holder.btn.setVisibility(userList.get(position).visibility);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(holder.btn.getText());
                final int i = holder.getAdapterPosition();
                final String usename = userList.get(i).getName();
                if (text.equalsIgnoreCase("edit")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Sửa người dùng");
                    View view = LayoutInflater.from(context).inflate(R.layout.sua_user, null);
                    builder.setView(view);
                    final TextInputEditText textUser, textSdt, textPass;
                    textUser = view.findViewById(R.id.txtUserSua);
                    textSdt = view.findViewById(R.id.txtSdtSua);
                    textPass = view.findViewById(R.id.txtPassSua);
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String user = textUser.getText().toString().trim();
                            String sdt = textSdt.getText().toString().trim();
                            String pass = textPass.getText().toString().trim();
                            ChaoActivity.userDao.update(new User(user, sdt, pass), usename);
                            ChaoActivity.userList.remove(i);
                            ChaoActivity.userList.add(i,new User(user, sdt, pass));
                            userList.get(i).textButton = "Edit";
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            notifyItemChanged(i);
                        }
                    });
                    builder.show();
                } else {
                    ChaoActivity.userDao.delete(usename);
                    ChaoActivity.userList.remove(i);
                    notifyItemRemoved(i);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        TextView textUser, textSdt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUser = itemView.findViewById(R.id.textUser);
            textSdt = itemView.findViewById(R.id.text2);
            btn = itemView.findViewById(R.id.btnRcvDK);

        }
    }
}
