package com.example.codequestapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("hint")
    @Expose
    private String hint;

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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    protected Question(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        prompt = in.readString();
        solution = in.readString();
        hint = in.readString();
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
        dest.writeString(solution);
        dest.writeString(hint);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}