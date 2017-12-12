package com.bond.android.familymap;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.support.annotation.NonNull;
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
        import com.bond.android.familymap.model.FamilyMember;
        import com.bond.android.familymap.model.Person;
        import com.joanzapata.iconify.IconDrawable;
        import com.joanzapata.iconify.fonts.FontAwesomeIcons;

        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.Iterator;
        import java.util.List;
        import java.util.ListIterator;

public class PersonActivity extends AppCompatActivity {

    private Person mPerson;
    private TextView mFirstNameView;
    private TextView mLastNameView;
    private TextView mGenderView;
    private RecyclerView mEventList;
    private RecyclerView mFamilyList;
    private Adapter mAdapter;
    private AdapterFamily mAdapterFamily;
    private Event[] mEvents;
    private List<FamilyMember> mFamilyMembers;
    Drawable icon;
    Drawable maleIcon;
    Drawable femaleIcon;
    Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        String personID = getIntent().getExtras().getString("personID");
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        mPerson = familyInfo.getPersonFromID(personID);
        mEvents = familyInfo.getEventsOfPerson(personID);
        String fullName = mPerson.getFirstName() + " " + mPerson.getLastName();

        //get family members
        mFamilyMembers = generateFamilyList(personID);

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

        mFamilyList = (RecyclerView) findViewById(R.id.PA_family_list);
        mFamilyList.setLayoutManager(new LinearLayoutManager(this));
        mAdapterFamily = new AdapterFamily(this, mFamilyMembers);
        mFamilyList.setAdapter(mAdapterFamily);

        icon = new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(25);
        maleIcon = new IconDrawable(this, FontAwesomeIcons.fa_male).colorRes(R.color.BLUE).sizeDp(25);
        femaleIcon = new IconDrawable(this, FontAwesomeIcons.fa_female).colorRes(R.color.PINK).sizeDp(25);
        thisActivity = this;

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

    class AdapterFamily extends RecyclerView.Adapter<HolderFamily> {
        private List<FamilyMember> familyMembers;
        private LayoutInflater inflater;

        public AdapterFamily(Context context, List<FamilyMember> familyMembers)
        {
            this.familyMembers = familyMembers;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public HolderFamily onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.family_member, parent, false);
            return new HolderFamily(view);
        }

        @Override
        public void onBindViewHolder(HolderFamily holder, int position) {
            FamilyMember familyMember = familyMembers.get(position);
            holder.bind(familyMember);
        }

        @Override
        public int getItemCount() {
            return familyMembers.size();
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
            Intent intent = new Intent(thisActivity, MapActivity.class);
            intent.putExtra("eventID", event.getEventID());
            startActivity(intent);
        }
    }

    class HolderFamily extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView familyMemberItemView;
        private FamilyMember familyMember;
        private String fullName;
        private String relation;
        private String gender;

        public HolderFamily(View view)
        {
            super(view);
            familyMemberItemView = (TextView) view.findViewById(R.id.PA_family_member);
            familyMemberItemView.setOnClickListener(this);
        }

        void bind(FamilyMember familyMember)
        {
            this.familyMember = familyMember;
            this.fullName = familyMember.getFullName();
            this.relation = familyMember.getRelation();
            this.gender = familyMember.getGender();
            familyMemberItemView.setText(fullName + "\n" + relation);
            Drawable genderIcon = (gender.toLowerCase().equals("male") || gender.toLowerCase().equals("m")) ? maleIcon : femaleIcon;
            familyMemberItemView.setCompoundDrawables(genderIcon, null, null, null);
        }

        @Override
        public void onClick(View view) {
            //create new person activity for the person clicked on
            Intent intent = new Intent(thisActivity, PersonActivity.class);
            intent.putExtra("personID", familyMember.getPersonID());
            startActivity(intent);
        }

    }

    private List<FamilyMember> generateFamilyList(String personID)
    {
        List<FamilyMember> family = new ArrayList<>();
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        Person person = familyInfo.getPersonFromID(personID);

        //find father
        Person father = familyInfo.getPersonFromID(person.getFather());
        if (father != null)
            family.add(new FamilyMember(father, "Father"));

        //find mother
        Person mother = familyInfo.getPersonFromID(person.getMother());
        if (mother != null)
            family.add(new FamilyMember(mother, "Mother"));

        //find spouse
        Person spouse = familyInfo.findSpouse(personID);
        if (spouse != null)
            family.add(new FamilyMember(spouse, "Spouse"));

        //find children
        List<Person> children = familyInfo.findChildren(personID);
        List<FamilyMember> kids = new ArrayList<>();
        for (Person child : children)
        {
            String kidLabel = (child.getGender().toLowerCase().equals("male") || child.getGender().toLowerCase().equals("m")) ?
                    "Son" : "Daughter";
            family.add(new FamilyMember(child, kidLabel));
        }

        return family;
    }

}
