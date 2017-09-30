package com.example.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

public class DummyModel implements Parcelable {
	
	   private final String id;
	    private final String title;
	    private final String detail;

	    public DummyModel(String id, String title, String detail) {
	        this.id = id;
	        this.title = title;
	        this.detail = detail;
	    }

	    public String getId() {
	        return id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public String getDetail() {
	        return detail;
	    }


	    // parceleable

	    protected DummyModel(Parcel in) {
	        id = in.readString();
	        title = in.readString();
	        detail = in.readString();
	    }

	    @Override
	    public void writeToParcel(Parcel dest, int flags) {
	        dest.writeString(id);
	        dest.writeString(title);
	        dest.writeString(detail);
	    }
	    public static final Creator<DummyModel> CREATOR = new Creator<DummyModel>() {
	        @Override
	        public DummyModel createFromParcel(Parcel in) {
	            return new DummyModel(in);
	        }

	        @Override
	        public DummyModel[] newArray(int size) {
	            return new DummyModel[size];
	        }
	    };

	    @Override
	    public int describeContents() {
	        return 0;
	    }



}
