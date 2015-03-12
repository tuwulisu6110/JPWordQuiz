package structure;

import android.os.Bundle;

/**
 * Created by tuwulisu on 2015/3/12.
 */
public class cookieInfo
{
    private String username;
    private String serialNum;
    private String id;
    public cookieInfo(String u,String sn,String idd)
    {
        setCookie(u,sn,idd);
    }
    public void setCookie(String u,String sn,String idd)
    {
        username = u;
        serialNum = sn;
        id = idd;
    }
}
