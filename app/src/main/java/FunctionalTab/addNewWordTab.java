package FunctionalTab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.wordquiz.R;

import java.util.ArrayList;

import networkThread.AddWordTask;
import networkThread.NewSourceTask;
import networkThread.RefreshRadioGroupTask;

/**
 * Created by tuwulisu on 2015/2/18.
 */
public class AddNewWordTab extends Fragment
{
    private EditText wordET;
    private EditText readingET;
    private EditText meaningET;
    private EditText pageET;
    private RadioGroup sourceRadioGroup;
    private Button newSourceBtn;
    private Button submitButton;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
        RefreshRadioGroupTask task = new RefreshRadioGroupTask(getActivity(),getArguments(),sourceRadioGroup,newSourceBtn);
        task.execute();
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ArrayList<EditText> ets = new ArrayList<EditText>();
                ArrayList<String> wordInfo = new ArrayList<String>();
                ets.add(wordET);
                ets.add(readingET);
                ets.add(meaningET);
                ets.add(pageET);
                wordInfo.add("");
                wordInfo.add(wordET.getText().toString());
                wordInfo.add(readingET.getText().toString());
                wordInfo.add(meaningET.getText().toString());
                wordInfo.add(String.valueOf(sourceRadioGroup.getCheckedRadioButtonId()));//if no radioBtn is checked it will be -1
                wordInfo.add(pageET.getText().toString());

                AddWordTask task = new AddWordTask(getActivity(),getArguments(),ets,wordInfo);
                task.execute();
            }
        });
        newSourceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final EditText input = new EditText(getActivity());
                builder.setView(input);

                builder.setPositiveButton("OK",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //pass source name to NewSourceTask for network connection
                        String sourceName = input.getText().toString();
                        NewSourceTask task = new NewSourceTask(getActivity(),getArguments(),sourceName,sourceRadioGroup,newSourceBtn);
                        task.execute();
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        return v;
    }
}
