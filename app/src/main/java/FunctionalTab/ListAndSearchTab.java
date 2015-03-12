package FunctionalTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wordquiz.R;

import networkThread.FetchAndSearchTask;

/**
 * Created by tuwulisu on 2015/3/3.
 */
public class ListAndSearchTab extends Fragment
{
    private Button searchBtn;
    private ListView wordList;
    private TextView searchTV;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.list_and_search_tab,container,false);
        searchBtn = (Button)v.findViewById(R.id.searchButton);
        wordList = (ListView)v.findViewById(R.id.wordList);
        searchTV = (TextView)v.findViewById(R.id.searchText);
        FetchAndSearchTask listTask = new FetchAndSearchTask(this.getActivity(),getArguments(),wordList,"");
        listTask.execute();
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FetchAndSearchTask searchTask = new FetchAndSearchTask(getActivity(),getArguments(),wordList,searchTV.getText().toString());
                searchTask.execute();
            }
        });

        return v;
    }

}
