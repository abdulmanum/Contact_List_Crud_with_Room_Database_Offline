package com.example.contacts;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact{

        @PrimaryKey(autoGenerate = true)
        Long id  = 0L;
        String name;
        String email;
        String phoneNumber;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public Contact(Long id, String name, String email, String phoneNumber) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.phoneNumber = phoneNumber;
        }
}