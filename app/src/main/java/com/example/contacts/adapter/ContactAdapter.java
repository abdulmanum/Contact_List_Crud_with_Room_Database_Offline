package com.example.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.Contact;
import com.example.contacts.R;

import java.util.List;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final LayoutInflater mInflater;
    private List<Contact> mContacts;
    private OnContactClickListener mContactClickListener;

    public ContactAdapter(Context context, OnContactClickListener contactClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mContactClickListener = contactClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            holder.textViewName.setText(current.getName());
            holder.textViewEmail.setText(current.getEmail());
            holder.textViewPhone.setText(current.getPhoneNumber());
        } else {
            // If data is not ready yet
            holder.textViewName.setText("No contact");
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactClickListener.onDeleteClick(mContacts.get(position));
            }
        });
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactClickListener.onEditClick(mContacts.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }
    public interface OnContactClickListener {
        void onEditClick(Contact contact);

        void onDeleteClick(Contact contact);
    }

    public void setContacts(List<Contact> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewEmail;
        private final TextView textViewPhone;
        private final ImageView img_edit,img_delete;

        private ContactViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }



}


