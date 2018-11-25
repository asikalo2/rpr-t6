package ba.unsa.etf.rpr.tutorijal06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ba/unsa/etf/rpr/tutorijal06/sample.fxml"));
        primaryStage.setTitle("Forma");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    /*otrebno je da napravite formular za unos studenta. Podaci o studentu su sljedeći:
Osnovni podaci:
ime (najviše 20 karaktera, ne smije biti prazno, mora sadržavati barem jedno slovo)
prezime (isto)
broj indeksa (pravila za validan broj indeksa)
Detaljni lični podaci:
JMBG (pravila za validan JMBG)
datum rođenja (ne smije biti u budućnosti, mora se poklapati sa odgovarajućim dijelovima JMBGa)
mjesto rođenja (ponuditi nekoliko najčešćih mjesta u BiH, ali korisnik mora moći unijeti mjesto po želji)
Kontakt podaci:
kontakt adresa (može se i ostaviti prazna)
kontakt telefon (validan telefonski broj ili prazno)
email adresa (validna email adresa, ne smije biti prazno)
Podaci o studiju:
odsjek (AE,EE,RI,TK)
godina studija (prva, druga, treća)
ciklus studija (bachelor, master, doktorski studij, stručni studij)
da li je student redovan ili redovan samofinansirajući?
da li student pripada posebnim boračkim kategorijama?


Polja formulara trebaju biti vizuelno grupisana po datim kategorijama. Sva polja moraju biti validirana:
prilikom kucanja (polje treba promijeniti boju u neku nijansu crvene ako je neispravno, a zelene ako je ispravno)
prilikom promjene fokusa (treba se pojaviti ili nestati pasivni indikator sa opisom greške)
prilikom klika na dugme Potvrdi (treba izbaciti dijaloški prozor da forma nije validna)
Ako je forma validna, klikom na dugme Potvrdi trebaju se ispisati svi podaci o studentu na standardni izlaz.
*/


    public static void main(String[] args) {
        launch(args);
    }
}
