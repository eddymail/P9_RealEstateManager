package com.openclassrooms.realestatemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Activities.MainActivity;
import com.openclassrooms.realestatemanager.Adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements HouseRecyclerAdapter.OnHouseListener {

    private RecyclerView recyclerView;
    private List<House> houseList = new ArrayList<>();
    private HouseRecyclerAdapter adapter;
    private TextView lblNoHouse;
    private HouseViewModel houseViewModel;

    private static final long HOUSE_ID = 1;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

       /* houseList.add(new House(1, "Maison", "Hourton", 741000, 380, 11, 2, 6, "Commerce, école, métro","Magnifique villa à Saint Aubin de Médoc située dans un quartier recherché. Vous découvrirez une entrée, une belle pièce de vie avec salon et salle à manger, un deuxième salon séparé par une cheminée centrale, une cuisine fermée donnant sur la cuisine d'été, une buanderie, une salle de jeu, une cave. Le rez-de-chaussée est complété par une chambre avec salle d'eau, un bureau et une salle de sport avec sauna et salle d'eau. A l'étage, vous accédez à une suite parentale avec une chambre donnant sur une terrasse, une salle de bains avec baignoire et douche, un double dressing, puis trois chambres avec dressing et une salle de bains avec baignoire et douche. L'ensemble sur un jardin paysagé d'environ 1400 m² dispose d'une grande terrasse en bois, d'une cuisine d'été, d'une piscine au sel chauffée. Très belles prestations pour cette maison d'exception à quelques minutes du centre de Saint Aubin de Médoc, des écoles, collège et lycée. Situation idéale à 15 kms de Bordeaux, 35 kms de Lacanau, 7 kms d'un parcours de golf. Un coin de paradis à découvrir sans tarder."
                , "https://v.seloger.com/s/width/861/visuels/0/r/i/1/0ri18eygqgnn0yj8yzyiwv9mx1f5x0yku96bf1a0w.jpg","15 route de Hourton, 33160 Saint-Aubin-de-Médoc",
               true ,"20/01/2021" , null,"Eddy"));

        houseList.add(new House(2, "Maison", "Estève", 749000, 214, 6, 2, 5,
                "Commerce, bureau, école", "Nichée au fond d'une impasse, construite en 2015 sur une parcelle clôturée et piscinable de 1000 m² environ, très belle maison de plain pied ossature bois avec vue sur les champs. Située à 35mn en voiture de Bordeaux et des plages de Lacanau, cette maison vous charmera par sa luminosité grâce au patio central, et par la qualité de vie qu'elle propose. L'entrée dessert d'un côté la partie jour avec une vaste pièce de vie et cuisine ouverte, une buanderie, un bureau et une grande suite parentale avec dressing et salle de douche. De l'autre côté se trouve la partie enfants avec 4 chambres supplémentaires, une grande salle de bain, un WC séparé et une salle de jeu. Toute la maison tourne autour d'un joli patio de 80 m² environ, végétalisé et terrassé. L'ensemble est complété d'un garage de 15 m², de 3 places de stationnement devant la maison, une terrasse à l'ombre et un grand jardin. Arrêt de bus et ramassage scolaire au bout de la rue. 5mn en voiture de l'école élémentaire Molière et du Groupe Scolaire Jean de la Fontaine. Proximité sites aéronautiques DASSAULT et THALES",
                "https://v.seloger.com/s/cdn/x/visuels/0/w/v/7/0wv7p12j5lugnffry96jkhoghwf43g5iqal3q1bc0.jpg", "68 ter Route de Loustaou Vieil, 33160 Saint-Aubin-de-Médoc", true,
                "15/12/2020", null, "Eddy"));
        houseList.add(new House(3, "Villa", "Centre ville", 668000, 195, 7, 3, 5,
                "Commerce, école, tram, parc", "Maison de 195 m² environ comprenant 5 belles chambres, toutes en parquet bois naturel. Vous serez séduit par le volume de sa chambre parentale, qui comprend un dressing équipé et sa pièce d'eau attenante disposant d'une baignoire, d'une douche à l'italienne, d'un toilette et d'une double vasque. Un ensemble de 37 m² environ entièrement pour vous. Les 4 autres chambres en parquet bois disposent toutes d'un placard. L'une d'entre elle, attenante à la pièce de vie pourra aisément se transformer en bureau afin de vous permettre d'être au calme et indépendant. Que dire de la pièce de vie de plus de 67 m², lumineuse grâce à sa double exposition Est/Ouest et ses grandes baies vitrées (5m d'un côté et 3m de l'autre), qui n'attend que vous pour se transformer en différents espaces: salle à manger, salon, coin cheminée, coin lecture.. Faites vous plaisir. La cuisine de près de 19 m² avec un îlot central et équipée, vous séduira par sa convivialité et sa luminosité grâce à un puit de lumière situé au dessus de l'îlot. Un cellier avec un accès sur le garage complète cette cuisine. Côté extérieur vous serez séduit par son terrain de plus de 1600 m² et ses arbres qui vous donnent une impression de vivre en forêt. La piscine de 4X10 exposée Ouest ajoute encore à cette quiétude. Un double garage isolé de 40 m² complète cette villa qui n'attend plus que vous me contactiez pour organiser une visite. Chauffage au sol, au gaz avec chaudière Viesmann de 2018, puit et arrosage automatique.",
                "https://v.seloger.com/s/width/1613/visuels/1/g/2/z/1g2zw26el8kcz92rd2ixkbrnaujnduu8ods1scbpp.jpg", "15 Allée Saint-Julien, 33160 Saint-Aubin-de-Médoc", true, "10/12/2020", null, "Nath"));*/

        recyclerView = view.findViewById(R.id.fragment_main_recyclerview);
        lblNoHouse = view.findViewById(R.id.lbl_no_house);

        this.configureViewModel();
        this.configureRecyclerView();

        this.getAllHousesFromDatabase();

        return view;
    }

    private void configureRecyclerView() {

        this.houseList = new ArrayList<>();
        this.adapter = new HouseRecyclerAdapter(this.houseList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);
    }

    private void updateDisplayList() {
        if (houseList.size() == 0) {
            lblNoHouse.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            lblNoHouse.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void getAllHousesFromDatabase() {
        this.houseViewModel.getAll().observe(this, this::updateList);
    }

    private void updateList(List<House> houses) {
        houseList.addAll(houses);
        adapter.notifyDataSetChanged();
        this.updateDisplayList();
    }

    @Override
    public void onHouseClick(int position) {
        // add with Gaethan
        ((MainActivity) getActivity()).onHouseClick(houseList.get(position));

        Log.e("Test", "MAIN FRAGMENT onHouseClick house Id = " + houseList.get(position).getId());

    }
}