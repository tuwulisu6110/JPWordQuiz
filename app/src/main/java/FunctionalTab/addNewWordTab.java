package FunctionalTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.wordquiz.R;

/**
 * Created by tuwulisu on 2015/2/18.
 */
public class AddNewWordTab extends Fragment
{
    private EditText wordET;
    private EditText readingET;
    private EditText meaningET;
    private EditText pageET;
    private String username;
    private RadioGroup sourceRadioGroup;
    private Button newSourceBtn;
    private Button submitButton;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("username");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.add_word_tab,container,false);
        submitButton = (Button)v.findViewById(R.id.submitButton);
        wordET = (EditText)v.findViewById(R.id.wordText);
        readingET = (EditText)v.findViewById(R.id.readingText);
        meaningET = (EditText)v.findViewById(R.id.meaningText);
        pageET = (EditText)v.findViewById(R.id.pageText);
        sourceRadioGroup = (RadioGroup)v.findViewById(R.id.sourceRadioGroup);
        newSourceBtn = (Button)v.findViewById(R.id.newSourceButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        newSourceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        return v;
    }
}