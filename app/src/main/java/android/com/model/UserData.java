package android.com.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "users")
public class UserData
{

    @PrimaryKey(autoGenerate = true)
    public int m_iID;
    public String m_strFirstName;
    public String m_strLastName;
    public String m_strAvatar;

    public UserData()
    {

    }

    public UserData(int m_iID, String p_strFirstName, String p_strLastName, String p_strAvatar) {
        if(m_iID != -1)
        {
            this.m_iID = m_iID;
        }
        this.m_strFirstName = p_strFirstName;
        this.m_strLastName = p_strLastName;
        this.m_strAvatar = p_strAvatar;
    }

    public int getId() {
        return m_iID;
    }

    public String getFirstName() {
        return m_strFirstName;
    }

    public String getLastName() {
        return m_strLastName;
    }

    public String getAvatar() {
        return m_strAvatar;
    }


    public void setFirstName(String p_strFirstName) {
        this.m_strFirstName = p_strFirstName;
    }

    public void setLastName(String p_strLastName) {
        this.m_strLastName = p_strLastName;
    }

    public void setAvatar(String p_strAvatar) {
        this.m_strAvatar = p_strAvatar;
    }

    public void setId(int p_iid) {
        this.m_iID = p_iid;
    }

}