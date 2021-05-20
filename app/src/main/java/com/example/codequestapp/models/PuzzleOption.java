package com.example.codequestapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PuzzleOption implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("isCorrect")
    @Expose
    private Boolean isCorrect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public PuzzleOption(String option, Boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }

    protected PuzzleOption(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        option = in.readString();
        byte isCorrectVal = in.readByte();
        isCorrect = isCorrectVal == 0x02 ? null : isCorrectVal != 0x00;
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
        dest.writeString(option);
        if (isCorrect == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isCorrect ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PuzzleOption> CREATOR = new Parcelable.Creator<PuzzleOption>() {
        @Override
        public PuzzleOption createFromParcel(Parcel in) {
            return new PuzzleOption(in);
        }

        @Override
        public PuzzleOption[] newArray(int size) {
            return new PuzzleOption[size];
        }
    };
}