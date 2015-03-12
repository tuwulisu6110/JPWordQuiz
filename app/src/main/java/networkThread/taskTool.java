package networkThread;

import android.content.Context;
import android.content.Intent;

import com.example.wordquiz.LoginActivity;

/**
 * Created by tuwulisu on 2015/3/12.
 */
public class taskTool
{
    public static boolean checkStatusAndReturnLogin(Context c, String status)
    {
        if(status.equals("success")!=true)
        {
            Intent returnIntent = new Intent(c,com.example.wordquiz.LoginActivity.class);
            c.startActivity(returnIntent);
            return true;
        }
        return false;
    }
}
