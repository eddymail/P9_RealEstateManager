package com.openclassrooms.realestatemanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Adapter.ListHouseAdapter;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<House> houseList = new ArrayList<>();
    private ListHouseAdapter adapter;
    private TextView lblNoHouse;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.houseList = new ArrayList<>();
        houseList.add(new House("Flat","Manhattan", "50 388 600 $", "944 m2", "10", "7", "5",
                "SOMPTUEUX GRAND PENTHOUSE DE CINQ CHAMBRES ET PLEIN ÉTAGE AVEC 360 DEGRÉS ET VUE SUR LA VILLE AVEC UNE TERRASSE PRIVÉE FACE À L'EST.\n" +
                "\n" +
                "Entrez dans ce remarquable penthouse de 10 171 pieds carrés qui englobe tout le 90e étage par ascenseur privé.\n" +
                "La résidence soigneusement planifiée dispose d'un hall d'entrée gracieux, de plafonds de 14 pieds et de somptueux planchers de chêne français de 7,5 pouces de large.\n" +
                "La grande chambre d'angle expansive offre une vue sud s'étendant sur des kilomètres vers le centre-ville de Manhattan, la Freedom Tower et au-delà. " +
                "Adjacent à la salle à manger, la résidence dispose d'une terrasse de 454 pieds carrés avec vue sur l'horizon à l'est et au sud. " +
                "La cuisine séparée avec fenêtre a été équipée d'armoires luxueuses Smallbone of Devizes, d'un îlot en marbre blanc opale, de comptoirs et de dosserets, " +
                        "ainsi que d'une suite complète d'appareils Gaggenau. La suite parentale d'angle comprend un dressing et un bar, des salles de bains principales à " +
                        "double fenêtre enveloppées dans un magnifique quartzite iceberg, avec une double vanité avec des panneaux Kinon, une baignoire profonde et un dressing.\n" +
                "Les quatre chambres supplémentaires, la salle de remise en forme, la salle multimédia, six salles de bain attenantes, une belle salle d'eau revêtue d'Onyx et une buanderie / " +
                        "buanderie avec laveuse et sécheuse côte à côte en font une maison vraiment spéciale. Aucun petit détail n'est négligé.",
                "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-13a300000000056000015ed9558b-90219283.jpg", "160 E 25th St, New York, NY 10010, États-Unis", true, "17/02/2021", null, "Eddy"));

        houseList.add(new House("Penthouse", "Financial District", "20 348 300 $", "313 m2", "6", "3","3",
                "Massive maison Demi-étage Penthouse avec 176 carrés terrasse au pied dispose d'un mur de fenêtres en verre incurvées offrant une vue panoramique imprenable " +
                        "sur la rivière Hudson, NY Harbor, Statue de la Liberté, World Trade Center et Manhattan Skyline. " +
                        "La série L, une collection de 50 West Streets penthouse le plus remarquable, pleine et résidences demi-de-chaussée, " +
                        "offre un éventail impressionnant de fonctionnalités supplémentaires. Les cuisines, aménagées avec des comptoirs de dalles en pierre et dosserets," +
                        " sont équipées d'un réfrigérateur extra-spacieuses et congélateurs, un poêle à six brûleurs, et un réfrigérateur à vin pleine hauteur. " +
                        "Grandes dalles de pierre de marbre, une baignoire autoportante trempage, une douche à vapeur et un sauna benched améliorer l'élégante salle de bains principale. " +
                        "Ouest, une tour résidentielle de 64 étages situé dans le centre du New Downtown, offre une vue imprenable sur le port de New York, " +
                        "l'Hudson et East Rivers, la Statue de la Liberté et Ellis Island. architecte de renommée internationale Helmut Jahn a conçu le environ 780" +
                        " 'gratte-ciel à présenter des fenêtres en verre incurvées du sol au plafond. Les aménagements intérieurs vastes, allant de un à cinq chambres à coucher " +
                        "et disposant d'un réseau de duplex et double hauteur des espaces, ont été conçus et finis par Thomas Juul-Hansen. Quatre étages de la tour sont consacrés " +
                        "à des équipements state-of-the-art: un immense centre de remise en forme, le Club eau magnifiquement aménagées, des équipements childrens uniques, " +
                        "et L'observatoire de la rue de l'Ouest, un 64ème étage spectaculaire espace de divertissement en plein air avec apparemment vues infinies de New York et au-delà. penthouse.",
                "https://pic.le-cdn.com/thumbs/1024x768/04/10/properties/Property-b2660000000001e2000a57b5fd0a-31614642.jpg","New York, État de New York 10004",true,
                "15/02/2021", null, "Eddy"));
        houseList.add(new House("Penthouse", "Central Park", "51 048 800 $", "769 m2", "10","7", "5",
                "Penthouse le plus sophistiqué, le plus étonnant et le plus élégant de New York\n" +
                "Magnifique vue sur Central Park depuis cet appartement de 6 chambres gracieusement combiné. Vues magiques de chaque pièce - Central Park, Hudson River et ville ouverte " +
                        "- l'appartement est entouré de lumière et de vert, ainsi que d'eau pétillante et de bateaux qui passent pendant la journée et des lumières de la ville scintillantes " +
                        "tout autour la nuit. Le plan d'étage soigneusement conçu crée deux ailes de chambre séparées et un espace de divertissement qui peut sembler ouvert et loft ou être " +
                        "élégamment divisé en pièces plus discrètes pour des réceptions formelles. La résidence est en triple état neuf et est joliment décorée. Il est également tout à fait unique" +
                        " dans le bâtiment.\n" + "\n" + "Cette env. Cet appartement de 769 mètres carrés est une combinaison rare de grand espace, de lumière incroyable, de vues spectaculaires et" +
                        " d'excellence dans la conception et l'exécution.\n" + "Le niveau de finitions personnalisées est inégalé, en commençant par les sols en pierre incrustés de l'entrée " +
                        "de l'ascenseur privé et en continuant dans le salon spectaculaire de 42 pieds de long avec des fenêtres du sol au plafond donnant sur Central Park, des cheminées doubles," +
                        " des portes et des planchers en bois Bubinga et des poutres et moulures en bois de Padouk. À côté du salon se trouve un beau coin salon lambrissé avec cheminée." +
                        " À côté de la tanière se trouve une salle à manger spectaculaire de 22 pieds avec de beaux sols en marbre, des murs en peau texturée et une vue imprenable sur la " +
                        "ville et Central Park.\n" + "\n" + "La cuisine du chef professionnel a un îlot central avec évier et table de cuisson à gaz, des appareils haut de gamme," +
                        " des comptoirs en marbre et des armoires en sycomore personnalisées . Un coin petit-déjeuner confortable à côté de la cuisine mène à une salle multimédia orientée " +
                        "au nord-ouest avec vue sur la rivière Hudson. Une chambre du personnel avec salle de bain attenante est également située dans cette aile de l'appartement.",
                "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-431f0000000005d200145f9be395-97656643.jpg","New York, NY 10007", true, "10/12/2020",null, "Nath"));

        recyclerView = view.findViewById(R.id.fragment_main_recyclerview);
        lblNoHouse = view.findViewById(R.id.lbl_no_task);

        this.configureRecyclerView();
        this.updateList();

        return view;
    }

    private void configureRecyclerView() {

        this.adapter = new ListHouseAdapter(this.houseList);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void updateList() {
        if (houseList.size() == 0) {
            lblNoHouse.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            lblNoHouse.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
       adapter.updateList(houseList);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.updateList();
    }
}