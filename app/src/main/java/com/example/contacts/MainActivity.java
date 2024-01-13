package com.example.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.adapter.ContactAdapter;
import com.example.contacts.roomdb.ContactDao;
import com.example.contacts.roomdb.ContactDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactClickListener {

    private ContactAdapter mAdapter;
    private ContactDao mContactDao;
    FloatingActionButton floatingActionButton;
    TextView tv_noContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
         floatingActionButton = findViewById(R.id.fab);
        tv_noContact = findViewById(R.id.tv_noContact);
        mAdapter = new ContactAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initializing database and DAO
        ContactDatabase db = ContactDatabase.getDatabase(getApplicationContext());
        mContactDao = db.contactDao();

        // Observe changes in the contact list and update RecyclerView
        mContactDao.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                if(contacts.size() > 0) {
                    mAdapter.setContacts(contacts);
                    tv_noContact.setVisibility(View.GONE);
                }else {
                    mAdapter.setContacts(new ArrayList<>());
                    tv_noContact.setVisibility(View.VISIBLE);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactDialog("Add",new Contact(null,"","",""));
            }
        });
    }

    @Override
    public void onEditClick(Contact contact) {
       showContactDialog("edit",contact);
    }

    @Override
    public void onDeleteClick(Contact contact) {
        // Implement delete action here, e.g., delete the contact from the database
        showYesNoDialoge(contact);

    }

    private void showYesNoDialoge(Contact contact) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Contacts");
        alertDialogBuilder.setMessage("Are you sure you want to delete this contact?");

// Set positive button - Yes
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mContactDao.deleteContact(contact);
                dialog.dismiss(); // Dismiss the dialog
            }
        });

// Set negative button - No
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle "No" button click action here
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void showContactDialog(String type,Contact contact) {
        String typeNew = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if(type.equals("edit")){
            typeNew = "Edit";
            dialogBuilder.setTitle("Edit Contact");

        }else{
            typeNew = "Add";
            dialogBuilder.setTitle("Add Contact");

        }

        // Inflate and set the custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);

        if(type.equals("edit")){
            editTextName.setText(contact.name);
            editTextEmail.setText(contact.email);
            editTextPhone.setText(contact.phoneNumber);
        }else{
            editTextName.setText("");
            editTextEmail.setText("");
            editTextPhone.setText("");
        }

        // Set up the buttons and their actions
        // Inside showAddContactDialog() method after inserting the contact
        dialogBuilder.setPositiveButton(typeNew, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                // Create a Contact object and save it to the Room database

                if(type.equals("edit")){
                    Contact newContact = new Contact(contact.id,name, email, phone);
                    mContactDao.updateContact(newContact);
                }else {
                    Contact newContact = new Contact(null,name, email, phone);
                    mContactDao.insertContact(newContact);
                }
                dialog.dismiss();

            }
        });


        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                // Canceled.
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
