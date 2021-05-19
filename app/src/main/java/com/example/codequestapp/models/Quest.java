package com.example.codequestapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quest implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("experience")
    @Expose
    private Integer experience;
    @SerializedName("imgPath")
    @Expose
    private String imgPath;
    @SerializedName("trophy")
    @Expose
    private String trophy;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("Category")
    @Expose
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTrophy() {
        return trophy;
    }

    public void setTrophy(String trophy) {
        this.trophy = trophy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    protected Quest(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        experience = in.readByte() == 0x00 ? null : in.readInt();
        imgPath = in.readString();
        trophy = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        categoryId = in.readByte() == 0x00 ? null : in.readInt();
        category = (Category) in.readValue(Category.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(title);
        if (experience == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(experience);
        }
        dest.writeString(imgPath);
        dest.writeString(trophy);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (categoryId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(categoryId);
        }
        dest.writeValue(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quest> CREATOR = new Parcelable.Creator<Quest>() {
        @Override
        public Quest createFromParcel(Parcel in) {
            return new Quest(in);
        }

        @Override
        public Quest[] newArray(int size) {
            return new Quest[size];
        }
    };
}