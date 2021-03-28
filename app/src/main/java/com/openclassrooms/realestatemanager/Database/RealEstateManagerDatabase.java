package com.openclassrooms.realestatemanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.Database.Dao.HouseDao;
import com.openclassrooms.realestatemanager.Database.Dao.IllustrationDao;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;

@Database(entities = {House.class, Illustration.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    //Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    //Dao
    public abstract HouseDao houseDao();

    public abstract IllustrationDao illustrationDao();

    //Instance
    public static RealEstateManagerDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues houseOne = new ContentValues();
                houseOne.put("id", 1);
                houseOne.put("category", "Maison");
                houseOne.put("district", "Hourton");
                houseOne.put("price", 741000);
                houseOne.put("isEuro", true);
                houseOne.put("area", 380);
                houseOne.put("numberOfRooms", 11);
                houseOne.put("numberOfBathrooms", 2);
                houseOne.put("numberOfBedrooms", 6);
                houseOne.put("pointOfInterest", "Commerce, école, métro");
                houseOne.put("description", "Magnifique villa à Saint Aubin de Médoc située dans un quartier recherché. Vous découvrirez une entrée, une belle pièce de vie avec salon et salle à manger, un deuxième salon séparé par une cheminée centrale, une cuisine fermée donnant sur la cuisine d'été, une buanderie, une salle de jeu, une cave. Le rez-de-chaussée est complété par une chambre avec salle d'eau, un bureau et une salle de sport avec sauna et salle d'eau. A l'étage, vous accédez à une suite parentale avec une chambre donnant sur une terrasse, une salle de bains avec baignoire et douche, un double dressing, puis trois chambres avec dressing et une salle de bains avec baignoire et douche. L'ensemble sur un jardin paysagé d'environ 1400 m² dispose d'une grande terrasse en bois, d'une cuisine d'été, d'une piscine au sel chauffée. Très belles prestations pour cette maison d'exception à quelques minutes du centre de Saint Aubin de Médoc, des écoles, collège et lycée. Situation idéale à 15 kms de Bordeaux, 35 kms de Lacanau, 7 kms d'un parcours de golf. Un coin de paradis à découvrir sans tarder.");
                houseOne.put("illustration", "");
                houseOne.put("address", "15 route de Hourton, 33160 Saint-Aubin-de-Médoc");
                houseOne.put("available", true);
                houseOne.put("dateOfEntry", "20/01/2021");
                houseOne.put("dateOfSale", "null");
                houseOne.put("realEstateAgent", "Eddy");

                db.insert("House", OnConflictStrategy.IGNORE, houseOne);

                ContentValues houseTwo = new ContentValues();
                houseTwo.put("id", 2);
                houseTwo.put("category", "Maison");
                houseTwo.put("district", "Estève");
                houseTwo.put("price", 749000);
                houseTwo.put("isEuro", true);
                houseTwo.put("area", 214);
                houseTwo.put("numberOfRooms", 6);
                houseTwo.put("numberOfBathrooms", 2);
                houseTwo.put("numberOfBedrooms", 5);
                houseTwo.put("pointOfInterest", "Commerce, bureau, école");
                houseTwo.put("description", "Nichée au fond d'une impasse, construite en 2015 sur une parcelle clôturée et piscinable de 1000 m² environ, très belle maison de plain pied ossature bois avec vue sur les champs. Située à 35mn en voiture de Bordeaux et des plages de Lacanau, cette maison vous charmera par sa luminosité grâce au patio central, et par la qualité de vie qu'elle propose. L'entrée dessert d'un côté la partie jour avec une vaste pièce de vie et cuisine ouverte, une buanderie, un bureau et une grande suite parentale avec dressing et salle de douche. De l'autre côté se trouve la partie enfants avec 4 chambres supplémentaires, une grande salle de bain, un WC séparé et une salle de jeu. Toute la maison tourne autour d'un joli patio de 80 m² environ, végétalisé et terrassé. L'ensemble est complété d'un garage de 15 m², de 3 places de stationnement devant la maison, une terrasse à l'ombre et un grand jardin. Arrêt de bus et ramassage scolaire au bout de la rue. 5mn en voiture de l'école élémentaire Molière et du Groupe Scolaire Jean de la Fontaine. Proximité sites aéronautiques DASSAULT et THALES.");
                houseTwo.put("illustration", "");
                houseTwo.put("address", "68 ter Route de Loustaou Vieil, 33160 Saint-Aubin-de-Médoc");
                houseTwo.put("available", true);
                houseTwo.put("dateOfEntry", "15/12/2021");
                houseTwo.put("dateOfSale", "null");
                houseTwo.put("realEstateAgent", "Charlotte");

                db.insert("House", OnConflictStrategy.IGNORE, houseTwo);

                ContentValues houseThree = new ContentValues();
                houseThree.put("id", 3);
                houseThree.put("category", "Villa");
                houseThree.put("district", "Centre ville");
                houseThree.put("price", 668000);
                houseThree.put("isEuro", true);
                houseThree.put("area", 195);
                houseThree.put("numberOfRooms", 7);
                houseThree.put("numberOfBathrooms", 3);
                houseThree.put("numberOfBedrooms", 5);
                houseThree.put("pointOfInterest", "Commerce, école, tram, parc");
                houseThree.put("description", "Maison de 195 m² environ comprenant 5 belles chambres, toutes en parquet bois naturel. Vous serez séduit par le volume de sa chambre parentale, qui comprend un dressing équipé et sa pièce d'eau attenante disposant d'une baignoire, d'une douche à l'italienne, d'un toilette et d'une double vasque. Un ensemble de 37 m² environ entièrement pour vous. Les 4 autres chambres en parquet bois disposent toutes d'un placard. L'une d'entre elle, attenante à la pièce de vie pourra aisément se transformer en bureau afin de vous permettre d'être au calme et indépendant. Que dire de la pièce de vie de plus de 67 m², lumineuse grâce à sa double exposition Est/Ouest et ses grandes baies vitrées (5m d'un côté et 3m de l'autre), qui n'attend que vous pour se transformer en différents espaces: salle à manger, salon, coin cheminée, coin lecture.. Faites vous plaisir. La cuisine de près de 19 m² avec un îlot central et équipée, vous séduira par sa convivialité et sa luminosité grâce à un puit de lumière situé au dessus de l'îlot. Un cellier avec un accès sur le garage complète cette cuisine. Côté extérieur vous serez séduit par son terrain de plus de 1600 m² et ses arbres qui vous donnent une impression de vivre en forêt. La piscine de 4X10 exposée Ouest ajoute encore à cette quiétude. Un double garage isolé de 40 m² complète cette villa qui n'attend plus que vous me contactiez pour organiser une visite. Chauffage au sol, au gaz avec chaudière Viesmann de 2018, puit et arrosage automatique.");
                houseThree.put("illustration", "");
                houseThree.put("address", "22 Allée des Châtaigniers, 33160 Saint-Aubin-de-Médoc");
                houseThree.put("available", true);
                houseThree.put("dateOfEntry", "10/11/2020");
                houseThree.put("dateOfSale", "null");
                houseThree.put("realEstateAgent", "Eddy");

                db.insert("House", OnConflictStrategy.IGNORE, houseThree);

                ContentValues photoA = new ContentValues();

                photoA.put("id", 1);
                photoA.put("houseId", 1);
                photoA.put("description", "Salon");
                photoA.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoA);

                ContentValues photoB = new ContentValues();

                photoB.put("id", 2);
                photoB.put("houseId", 1);
                photoB.put("description", "Cuisine");
                photoB.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoB);

                ContentValues photoC = new ContentValues();

                photoC.put("id", 3);
                photoC.put("houseId", 1);
                photoC.put("description", "Suite parentale");
                photoC.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoC);

                ContentValues photoD = new ContentValues();

                photoD.put("id", 4);
                photoD.put("houseId", 1);
                photoD.put("description", "Chambre enfant");
                photoD.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoD);

                ContentValues photoE = new ContentValues();

                photoE.put("id", 5);
                photoE.put("houseId", 1);
                photoE.put("description", "Salle de bain");
                photoE.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoE);

                ContentValues photoF = new ContentValues();

                photoF.put("id", 6);
                photoF.put("houseId", 2);
                photoF.put("description", "Salon");
                photoF.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoF);

                ContentValues photoG = new ContentValues();

                photoG.put("id", 7);
                photoG.put("houseId", 2);
                photoG.put("description", "Cuisine");
                photoG.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoG);

                ContentValues photoH = new ContentValues();

                photoH.put("id", 8);
                photoH.put("houseId", 2);
                photoH.put("description", "Patio");
                photoH.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoH);

                ContentValues photoI = new ContentValues();

                photoI.put("id", 9);
                photoI.put("houseId", 2);
                photoI.put("description", "Chambre");
                photoI.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoI);

                ContentValues photoJ = new ContentValues();

                photoJ.put("id", 10);
                photoJ.put("houseId", 2);
                photoJ.put("description", "Chambre enfant");
                photoJ.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoJ);

                ContentValues photoK = new ContentValues();

                photoK.put("id", 11);
                photoK.put("houseId", 2);
                photoK.put("description", "Salle de bain");
                photoK.put("picture", "");
                db.insert("Illustration", OnConflictStrategy.IGNORE, photoK);

                ContentValues photoL = new ContentValues();

                photoL.put("id", 12);
                photoL.put("houseId", 3);
                photoL.put("description", "Salon");
                photoL.put("picture", "");
                db.insert("Illustration", OnConflictStrategy.IGNORE, photoL);

                ContentValues photoM = new ContentValues();

                photoM.put("id", 13);
                photoM.put("houseId", 3);
                photoM.put("description", "Cuisine");
                photoM.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoM);

                ContentValues photoN = new ContentValues();

                photoN.put("id", 14);
                photoN.put("houseId", 3);
                photoN.put("description", "Chambre");
                photoN.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoN);

                ContentValues photoO = new ContentValues();

                photoO.put("id", 15);
                photoO.put("houseId", 3);
                photoO.put("description", "Salle de bain");
                photoO.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoO);
                ContentValues photoP = new ContentValues();

                photoP.put("id", 16);
                photoP.put("houseId", 3);
                photoP.put("description", "Piscine");
                photoP.put("picture", "");

                db.insert("Illustration", OnConflictStrategy.IGNORE, photoP);

            }
        };
    }
}
