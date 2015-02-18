package FunctionalTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wordquiz.R;

/**
 * Created by tuwulisu on 2015/2/13.
 */
public class FragmentTest extends Fragment
{
    private View v;
    private int count;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        count = savedInstanceState.getInt("count");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_test,container,false);
        Button b = (Button) v.findViewById(R.id.testButton);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentTabHost mTabHost = (FragmentTabHost)getActivity().findViewById(android.R.id.tabhost);
                int ima = mTabHost.getCurrentTab();
                //
                mTabHost.getTabWidget().removeViewAt(ima);
                mTabHost.requestLayout();
                mTabHost.invalidate();
                //mTabHost.getTabWidget().
                //mTabHost.clearAllTabs();
                //mTabHost.setCurrentTab(0);
                //mTabHost.removeViewAt(ima);

            }
        });
        return v;
    }
}
