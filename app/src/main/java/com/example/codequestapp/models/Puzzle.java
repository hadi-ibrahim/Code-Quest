package com.example.codequestapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Puzzle implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("options")
    @Expose
    private List<PuzzleOption> options = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<PuzzleOption> getOptions() {
        return options;
    }

    public void setOptions(List<PuzzleOption> options) {
        this.options = options;
    }

    protected Puzzle(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        prompt = in.readString();
        if (in.readByte() == 0x01) {
            options = new ArrayList<PuzzleOption>();
            in.readList(options, PuzzleOption.class.getClassLoader());
        } else {
            options = null;
        }
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
        dest.writeString(prompt);
        if (options == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(options);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Puzzle> CREATOR = new Parcelable.Creator<Puzzle>() {
        @Override
        public Puzzle createFromParcel(Parcel in) {
            return new Puzzle(in);
        }

        @Override
        public Puzzle[] newArray(int size) {
            return new Puzzle[size];
        }
    };
}