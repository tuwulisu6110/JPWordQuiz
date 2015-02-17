package FunctionalTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wordquiz.R;

/**
 * Created by tuwulisu on 2015/2/17.
 */
public class FunctionalTab extends Fragment
{
    private Button newTab;
    private View v;
    private FragmentTabHost mTabHost;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.functional_tab,container,false);
        newTab = (Button)v.findViewById(R.id.newTab);
        mTabHost = (FragmentTabHost)getActivity().findViewById(android.R.id.tabhost);
        newTab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
                        FragmentTest.class, null);
            }
        });
        return v;
    }
}
