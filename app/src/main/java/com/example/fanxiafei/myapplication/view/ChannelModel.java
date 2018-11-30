package com.example.fanxiafei.myapplication.view;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class ChannelModel implements Parcelable {
    private String name;
    private String code;
    private boolean lock = true;

    public ChannelModel() {

    }

    public ChannelModel(String name, String code) {
        this.name = name;
        this.code = code;
    }

    protected ChannelModel(Parcel in) {
        name = in.readString();
        code = in.readString();
        lock = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChannelModel) {
            return Objects.equals(name, ((ChannelModel) obj).name)
                    && Objects.equals(code, ((ChannelModel) obj).code)
                    && lock == ((ChannelModel) obj).lock;
        }
        return false;
    }

    public static final Creator<ChannelModel> CREATOR = new Creator<ChannelModel>() {
        @Override
        public ChannelModel createFromParcel(Parcel in) {
            return new ChannelModel(in);
        }

        @Override
        public ChannelModel[] newArray(int size) {
            return new ChannelModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
        dest.writeByte((byte) (lock ? 1 : 0));
    }
}
