package android.com.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Users {


    @SerializedName("page")
    @Expose
    public Integer m_iPage;

    @SerializedName("per_page")
    @Expose
    public Integer m_iperPage;

    @SerializedName("total")
    @Expose
    public Integer m_iTotal;

    @SerializedName("total_pages")
    @Expose
    public Integer m_iTotalPages;

    @SerializedName("data")
    @Expose
    public List<Datum> m_objLData = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        public Integer m_iID;

        @SerializedName("first_name")
        public String m_strFirstName;

        @SerializedName("last_name")
        public String m_strLastName;

        @SerializedName("avatar")
        public String m_strAvatar;

    }
}

