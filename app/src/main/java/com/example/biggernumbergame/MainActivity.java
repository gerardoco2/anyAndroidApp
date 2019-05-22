package com.example.biggernumbergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private static final String[] WORDS = {
            "abate", " to lessen to subside",
            "abeyance", "suspend action",
            "adjure", "promise or swear to give up",
            "adbogate", "repeal or annul by authority",
            "abstruse", "difficult to comprehend obscure",
            "agog", "eager/excited",
            "alloy", "to debase by mixin with something inferior"
    };

    private HashMap<String, String> dictionary;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fiveDefins;
    private String theWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionary = new HashMap<String, String>();

        list = new ArrayList<String>();

        for (int i= 0; i < WORDS.length; i+=2){
            list.add(WORDS[i]);
            dictionary.put(WORDS[i], WORDS[i +1]);
        }

        fiveDefins = new ArrayList<>();
        pickRandomWords();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString("theword", theWord);
        bundle.putStringArrayList("fivedefns", fiveDefins);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        if (bundle.containsKey("theword") && bundle.containsKey("fiveDefns")){
            theWord = bundle.getString("theword", /*default*/ "");
            fiveDefins = bundle.getStringArrayList("fiveDefns");
        }

        showWhatIPicked();

    }

    private void pickRandomWords() {

        ArrayList<String> fiveWords = new ArrayList<String>();
        Collections.shuffle(list);


        for (int i = 0; i < 5; i++) {
            fiveWords.add(list.get(i));
        }

        theWord = fiveWords.get(0);

        fiveDefins.clear();

        for (String word : fiveWords) {
            fiveDefins.add(dictionary.get(word));
        }
        Collections.shuffle(fiveDefins);

        showWhatIPicked();
    }

    private void showWhatIPicked(){

        TextView displayedWord = (TextView) findViewById(R.id.word_view);
        displayedWord.setText(theWord);

        // adapter for listview
        if (adapter == null) {
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, fiveDefins);
        } else {
            adapter.notifyDataSetChanged();
        }


        ListView listView = (ListView) findViewById(R.id.words_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defClicked = fiveDefins.get(position);
                String rightAnswer = dictionary.get(theWord);

                if (defClicked.equals(rightAnswer)){
                    Toast.makeText(MainActivity.this,"Right Answer!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Wrong!", Toast.LENGTH_SHORT).show();
                }
                pickRandomWords();
            }
        });
    }


}
