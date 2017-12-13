package com.bond.android.familymap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bond.android.familymap.model.Event;
import com.bond.android.familymap.model.FamilyInfo;
import com.bond.android.familymap.model.Person;
import com.bond.android.familymap.model.SearchResultItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView searchResultsList;
    private Adapter adapter;
    private List<SearchResultItem> searchResultItems;
    private Drawable maleIcon;
    private Drawable femaleIcon;
    private Drawable markericon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        maleIcon = new IconDrawable(this, FontAwesomeIcons.fa_male).colorRes(R.color.BLUE).sizeDp(25);
        femaleIcon = new IconDrawable(this, FontAwesomeIcons.fa_female).colorRes(R.color.PINK).sizeDp(25);
        markericon = new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(25);
        searchResultItems = new ArrayList<>();

        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    search(s);
                    adapter = new Adapter(SearchActivity.this, searchResultItems);
                    searchResultsList.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchResultsList = (RecyclerView) findViewById(R.id.search_results_list);
        searchResultsList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, new ArrayList<SearchResultItem>());
        searchResultsList.setAdapter(adapter);
    }

    private void search(CharSequence s)
    {
        searchResultItems.clear();
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        //search people for matching first or last name
        Person[] people = familyInfo.getPeople();
        for (Person p : people)
        {
            if (p.getFirstName().toLowerCase().contains(s) || p.getLastName().toLowerCase().contains(s))
            {
                Drawable genderIcon;
                if (p.getGender().toLowerCase().equals("male") || p.getGender().toLowerCase().equals("m"))
                    genderIcon = maleIcon;
                else
                    genderIcon = femaleIcon;
                String fullName = p.getFirstName() + " " + p.getLastName();
                searchResultItems.add(new SearchResultItem(genderIcon, fullName, p));
            }
        }

        //search events for matching country, city, description, or year
        Event[] events = familyInfo.getFilteredEvents();
        for (Event e : events)
        {
            if (e.getCountry().toLowerCase().contains(s) || e.getCity().toLowerCase().contains(s) ||
                    e.getEventType().toLowerCase().contains(s) || e.getYear().toLowerCase().contains(s))
            {
                String lineOne = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")\n";
                Person person = familyInfo.getPersonFromEvent(e);
                String lineTwo = person.getFirstName() + " " + person.getLastName();
                searchResultItems.add(new SearchResultItem(markericon, lineOne + lineTwo, e));
            }
        }

    }

    class Adapter extends RecyclerView.Adapter<Holder> {
        private List<SearchResultItem> searchResultItems;
        private LayoutInflater inflater;

        public Adapter(Context context, List<SearchResultItem> searchResults)
        {
            this.searchResultItems = searchResults;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.search_result_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            SearchResultItem searchResultItem = searchResultItems.get(position);
            holder.bind(searchResultItem);
        }

        @Override
        public int getItemCount() {
            return searchResultItems.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView searchResultTextView;
        private SearchResultItem searchResultItem;

        public Holder(View view) {
            super(view);
            this.searchResultTextView = (TextView) view.findViewById(R.id.search_result_item);
            this.searchResultTextView.setOnClickListener(this);
        }

        void bind(SearchResultItem searchResult) {
            this.searchResultItem = searchResult;
            searchResultTextView.setText(searchResultItem.getInfo());
            searchResultTextView.setCompoundDrawables(searchResult.getIcon(), null, null, null);
        }

        @Override
        public void onClick(View view) {
            //create a person activity or map activity centered on event, depending on what's clicked
            if (searchResultItem.getPerson() == null) //clicked on event
            {
                Intent intent = new Intent(SearchActivity.this, MapActivity.class);
                intent.putExtra("eventID", searchResultItem.getEvent().getEventID());
                startActivity(intent);
            } else {
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("personID", searchResultItem.getPerson().getPersonID());
                startActivity(intent);
            }
        }
    }

}
