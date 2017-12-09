package com.bond.android.familymap;

        import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bond.android.familymap.model.Event;
        import com.bond.android.familymap.model.FamilyInfo;
        import com.bond.android.familymap.model.Person;
        import com.joanzapata.iconify.IconDrawable;
        import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class PersonActivity extends AppCompatActivity {

    private Person mPerson;
    private TextView mFirstNameView;
    private TextView mLastNameView;
    private TextView mGenderView;
    private RecyclerView mEventList;
    private Adapter mAdapter;
    private Event[] mEvents;
    Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        String personID = getIntent().getExtras().getString("personID");
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        mPerson = familyInfo.getPersonFromID(personID);
        mEvents = familyInfo.getEventsOfPerson(personID);
        String fullName = mPerson.getFirstName() + " " + mPerson.getLastName();

        mFirstNameView = (TextView) findViewById(R.id.PA_first_name);
        mFirstNameView.setText(mPerson.getFirstName());

        mLastNameView = (TextView) findViewById(R.id.PA_last_name);
        mLastNameView.setText(mPerson.getLastName());

        mGenderView = (TextView) findViewById(R.id.PA_gender);
        mGenderView.setText(mPerson.getGender());

        mEventList = (RecyclerView) findViewById(R.id.PA_event_list);
        mEventList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(this, mEvents, fullName);
        mEventList.setAdapter(mAdapter);

        icon = new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(25);

    }

    class Adapter extends RecyclerView.Adapter<Holder> {

        private Event[] events;
        private LayoutInflater inflater;
        private String name;

        public Adapter(Context context, Event[] personEvents, String name)
        {
            this.events = personEvents;
            this.name = name;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.event_list_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Event event = events[position];
            holder.bind(event, name);
        }

        @Override
        public int getItemCount() {
            return events.length;
        }


    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView eventListItemView;
        private Event event;
        private String eventInfo;
        private String fullName;

        public Holder (View view)
        {
            super(view);
            eventListItemView = (TextView) view.findViewById(R.id.PA_event_info);
            eventListItemView.setOnClickListener(this);
        }

        void bind(Event e, String name)
        {
            this.event = e;
            this.eventInfo = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
            this.fullName = name;
            eventListItemView.setText(eventInfo + "\n" + fullName);
            eventListItemView.setCompoundDrawables(icon, null, null, null);
        }

        @Override
        public void onClick(View view) {
            //create map activity with map zoomed and centered on location of the clicked-on event
        }
    }

}
