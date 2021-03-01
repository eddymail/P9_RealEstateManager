package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IllustrationDaoTest {
    //For data
    private RealEstateManagerDatabase database;

    //Data set for Test
    private static long HOUSE_ID = 1;
    private static House HOUSE_DEMO = new House(HOUSE_ID, "Maison", "Hourton", 741000, 380, 11, 2, 6, "Commerce, école, métro", "Magnifique villa à Saint Aubin de Médoc située dans un quartier recherché. Vous découvrirez une entrée, une belle pièce de vie avec salon et salle à manger, un deuxième salon séparé par une cheminée centrale, une cuisine fermée donnant sur la cuisine d'été, une buanderie, une salle de jeu, une cave. Le rez-de-chaussée est complété par une chambre avec salle d'eau, un bureau et une salle de sport avec sauna et salle d'eau. A l'étage, vous accédez à une suite parentale avec une chambre donnant sur une terrasse, une salle de bains avec baignoire et douche, un double dressing, puis trois chambres avec dressing et une salle de bains avec baignoire et douche. L'ensemble sur un jardin paysagé d'environ 1400 m² dispose d'une grande terrasse en bois, d'une cuisine d'été, d'une piscine au sel chauffée. Très belles prestations pour cette maison d'exception à quelques minutes du centre de Saint Aubin de Médoc, des écoles, collège et lycée. Situation idéale à 15 kms de Bordeaux, 35 kms de Lacanau, 7 kms d'un parcours de golf. Un coin de paradis à découvrir sans tarder."
            , "https://v.seloger.com/s/width/861/visuels/0/r/i/1/0ri18eygqgnn0yj8yzyiwv9mx1f5x0yku96bf1a0w.jpg", "15 route de Hourton, 33160 Saint-Aubin-de-Médoc",
            true, "20/01/2021", null, "Eddy");
    private static Illustration ILLUSTRATION_SALON = new Illustration(1, HOUSE_ID, "Salon", "https://v.seloger.com/s/cdn/x/visuels/0/m/2/v/0m2v0q1zbvnr9zhz2zpadwxwn2h2s4wc800plrsw0.jpg");
    private static Illustration ILLUSTRATION_CUISINE = new Illustration(2, HOUSE_ID, "Cuisine", "https://v.seloger.com/s/cdn/x/visuels/2/9/8/v/298vujqnf17in31ceg5xe0af165tmlf6f6c9zjncw.jpg");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.clearAllTables();
    }

    @Test
    public void insertAndGetHouse() throws InterruptedException {
        //Before: Adding demo house
        this.database.houseDao().createHouse(HOUSE_DEMO);
        //Test
        House house = LiveDataTestUtil.getValue(this.database.houseDao().getHouse(HOUSE_ID));
        assertTrue(house.getAddress().equals(HOUSE_DEMO.getAddress()) && house.getId() == HOUSE_ID);
    }

    @Test
    public void insertAndGetIllustration() throws InterruptedException {
        //Before: Adding demo house and demo illustration
        this.database.houseDao().createHouse(HOUSE_DEMO);
        this.database.illustrationDao().insertIllustration(ILLUSTRATION_SALON);
        this.database.illustrationDao().insertIllustration(ILLUSTRATION_CUISINE);

        //Test
        List<Illustration> gallery = LiveDataTestUtil.getValue(this.database.illustrationDao().getGallery(HOUSE_ID));
        Assert.assertEquals(2, gallery.size());

    }
}
