package com.openclassrooms.realestatemanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Adapter.GalleryRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private TextView area;
    private TextView rooms;
    private TextView bedrooms;
    private TextView bathrooms;
    private TextView pointOfInterest;
    private TextView address;
    private TextView description;
    private TextView label;

    private List<Illustration> gallery = new ArrayList<>();
    private List<Illustration> galleryToDisplay = new ArrayList<>();
    private List<House> houseList = MainFragment.houseList;
    // Smartphone
    private House house;

    private RecyclerView recyclerView;
    private GalleryRecyclerAdapter adapter;

    // Required empty public constructor
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        gallery.add(new Illustration(1,1, "Salon", "https://v.seloger.com/s/cdn/x/visuels/0/m/2/v/0m2v0q1zbvnr9zhz2zpadwxwn2h2s4wc800plrsw0.jpg"));
        gallery.add(new Illustration(2,1, "Cuisine", "https://v.seloger.com/s/cdn/x/visuels/2/9/8/v/298vujqnf17in31ceg5xe0af165tmlf6f6c9zjncw.jpg"));
        gallery.add(new Illustration(3,1, "Suite parentale", "https://designmag.fr/wp-content/uploads/2015/09/decoration-interieur-moderne-suite-parentale-de-reve.jpg"));
        gallery.add(new Illustration(4,1, "Chambre enfant", "https://camif.twic.pics/is/image/matelsom/2A_CHAMBRE_LIT_BLANC_INTERNATIONAL%20DESI_IVANOE?$RCamifFicheProduitPrincipal6$"));
        gallery.add(new Illustration(5,1, "Salle de bain", "http://www.immobilier-miami-floride-usa.com/medias/images/appartement-a-vendre-sunny-isles-miami-beach-salle-de-bain.jpg?fx=r_400_267"));
        gallery.add(new Illustration(6,1, "Salle d'eau", "https://v.seloger.com/s/cdn/x/visuels/1/x/9/i/1x9ixdusggwsnd6gdkq3vezjx2rrop547rd1nhce8.jpg"));

        gallery.add(new Illustration(7,2, "Salon", "https://v.seloger.com/s/cdn/x/visuels/1/u/2/e/1u2em36fwbs8b4oe16mr54bpyxewzlty01t1wjxr4.jpg"));
        gallery.add(new Illustration(8,2, "Cuisine", "https://v.seloger.com/s/cdn/x/visuels/0/l/c/c/0lccrrlmiknz1wqpwx0e7xnkyc7ultp71buygvoc8.jpg"));
        gallery.add(new Illustration(9,2, "Patio", "https://v.seloger.com/s/cdn/x/visuels/1/w/v/u/1wvukylsd4imndf2g5clepeijkt0bei20r3yuwx40.jpg"));
        gallery.add(new Illustration(10,2, "Chambre", "https://v.seloger.com/s/cdn/x/visuels/0/o/8/y/0o8ywjtsj6jj62o73v7hq12jhk0qopbj6uztxz4bk.jpg"));
        gallery.add(new Illustration(11,2, "Chambre enfant", "https://v.seloger.com/s/cdn/x/visuels/2/0/b/3/20b3e0llpw289kj4o6aw563w06umqugpm6n16dl0g.jpg"));
        gallery.add(new Illustration(12,2, "Salle de bain", "https://v.seloger.com/s/cdn/x/visuels/1/u/9/9/1u9929lkcg6n8obckei1g528oig9tak7s0tug2pi8.jpg"));


        gallery.add(new Illustration(13,3, "Salon", "https://v.seloger.com/s/cdn/x/visuels/0/c/j/v/0cjvola8clg6zdnjeca93f9jcr6on3d2xwyw53619.jpg"));
        gallery.add(new Illustration(14,3, "Cuisine", "https://v.seloger.com/s/cdn/x/visuels/0/y/p/f/0ypfv90b5izj7jecyfiexda8vgsd4xjjo8935elhi.jpg"));
        gallery.add(new Illustration(15,3, "Chambre", "https://v.seloger.com/s/cdn/x/visuels/0/d/h/c/0dhcfz11x1qd0je6yvsubu8kpfuqfs15v2k76wbwt.jpg"));
        gallery.add(new Illustration(16,3, "Salle de bain", "https://v.seloger.com/s/cdn/x/visuels/1/n/4/u/1n4ue5j0y5adxbjijghr1qo4y5xmvm5tvqfn7yces.jpg"));
        gallery.add(new Illustration(17,3, "Piscine", "https://v.seloger.com/s/cdn/x/visuels/0/t/b/i/0tbiwnei8e459phso5kyqzf68ymiqg1p9h86tzdxp.jpg"));


        area = view.findViewById(R.id.tv_fragment_detail_surface_value);
        rooms = view.findViewById(R.id.tv_fragment_detail_rooms_value);
        bedrooms = view.findViewById(R.id.tv_fragment_detail_bedrooms_value);
        bathrooms = view.findViewById(R.id.tv_fragment_detail_bathrooms_value);
        pointOfInterest = view.findViewById(R.id.tv_fragment_detail_poi_value);
        address = view.findViewById(R.id.tv_fragment_detail_address_value);
        description = view.findViewById(R.id.tv_fragment_detail_description_value);
        label = view.findViewById(R.id.lbl_no_house);
        recyclerView = view.findViewById(R.id.rv_fragment_detail);

        return view;
    }

    @Override
    public void onStart() {
        //Smartphone display
        if (house != null) {
            updateHouse(house);
            configureRecyclerView(getIllustrationByHouseId(house.getId()));
        }
        super.onStart();
    }

    public void configureRecyclerView(List<Illustration> galleryToDisplay) {
        this.adapter = new GalleryRecyclerAdapter(galleryToDisplay);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public House getHouseById(int houseId) {
        for (House house : houseList) {
            if (house.getId() == houseId)
                return house;
        }
        return null;
    }

    public List<Illustration> getIllustrationByHouseId(long houseId) {
        Log.e("Test", "gallery :" + gallery.size());
        List<Illustration> galleryToDisplay = new ArrayList<>();
        for (Illustration illustration : gallery) {
            if (illustration.getHouseId() == houseId) {
                galleryToDisplay.add(illustration);
            }
        }
        Log.e("Test", "galleryToDisplay :" + galleryToDisplay.size());
        return galleryToDisplay;
    }

    //Tablet display
    public void updateDisplayTablet(House house) {
        updateHouse(house);
        configureRecyclerView(getIllustrationByHouseId(house.getId()));
    }

    public void updateHouse(House house) {
        area.setText(String.valueOf(house.getArea()));
        rooms.setText(String.valueOf(house.getNumberOfRooms()));
        bedrooms.setText(String.valueOf(house.getNumberOfBedrooms()));
        bathrooms.setText(String.valueOf(house.getNumberOfBathrooms()));
        pointOfInterest.setText(house.getPointOfInterest());
        address.setText(house.getAddress());
        description.setText(house.getDescription());
    }

    //Listener
    public void onHouseClick(House house) {
        this.house = house;
    }

    @Override
    public void onDestroy() {
        MainFragment mainFragment = new MainFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frame_layout, mainFragment)
                .commit();
        super.onDestroy();
    }
}