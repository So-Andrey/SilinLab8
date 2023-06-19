package src.localization;

import java.util.ListResourceBundle;

public class GUILabels_et extends ListResourceBundle {

    private static final Object[][] contents =
    {
            {"log_in", "SISSE LOGIMA"},
            {"login", "kasutajanimi"},
            {"password", "parool"},
            {"sign_up", "REGISTREERU"},
            {"sign_in", "KASUTAJA OLEMAS? LOGI SISSE"},
            {"auth", "AUTENTIMINE"},
            {"reg", "REGISTREERIMINE"},
            {"become", "KASUTAJA PUUDUB? REGISTREERU"},
            {"already", "Juba olemas"},
            {"invalid", "Vigane sisend"},
            {"add", "LISA"},
            {"exit", "VÄLJU"},
            {"clear", "PUHASTA"},
            {"b_help", "ABI"},
            {"info", "INFO"},
            {"map", "KAART"},
            {"count", "LOE TÜÜBI JÄRGI"},
            {"script", "KÄIVITA SKRIPT"},
            {"remove_type", "EEMALDA TÜÜBI JÄRGI"},
            {"remove_id", "EEMALDA ID JÄRGI"},
            {"upd", "UUENDA ID JÄRGI"},
            {"username", "Kasutajanimi"},
            {"vehicle", "Sõiduk"},
            {"name", "Nimi"},
            {"creator", "Looja"},
            {"coords", "Koordinaadid"},
            {"creation_date", "Loomise kuupäev"},
            {"type", "Tüüp"},
            {"capacity", "Mahutavus"},
            {"ep", "Võimsus"},
            {"fc", "Kütusekulu"},
            {"all", "KÕIK"},
            {"script_help", """
                    lisa {element} : lisa uus element kogusse
                    käivita_skript faili_nimi : loe ja käivita skript antud failist. Skriptis on samasugused käsklused nagu sisestatakse
                    puhasta : puhasta kogu kogu
                    uuenda id : uuenda kogusse kuuluva elemendi väärtus, mille id on antud
                    eemalda_id id : eemalda element kogust, mille id on antud
                    eemalda_kõik_tüübi_järgi tüüp : eemalda kogust kõik elemendid, mille tüüp on antud
                    """},
            {"help", """
                    lisa {element} : lisa uus element kogusse
                    käivita_skript faili_nimi : loe ja käivita skript antud failist. Skriptis on samasugused käsklused nagu sisestatakse
                    puhasta : puhasta kogu kogu
                    uuenda id : uuenda kogusse kuuluva elemendi väärtus, mille id on antud
                    eemalda_id id : eemalda element kogust, mille id on antud
                    loe_tüübi_järgi_tüübi_järgi tüüp : väljasta kogu koguses olevate elementide arv, mille tüüp on suurem antud väärtusest
                    abi : väljasta juhend kättesaadavate käskude kohta
                    välju : lõpeta programm (salvestamata)
                    eemalda_kõik_tüübi_järgi tüüp : eemalda kogust kõik elemendid, mille tüüp on antud
                    info : väljasta standardväljundisse teave kogumise kohta (tüüp, alguskuupäev, elementide arv jne)
                    """},
            {"separator", ","},
            {"date_format", "yyyy-MM-dd HH:mm:ss Z"},
            {"col_type", "Kogumise tüüp"},
            {"init_date", "Alguskuupäev"},
            {"amount", "Elementide arv"},
            {"delete_ps", "Saate kustutada ainult neid sõidukeid, mille looja olete"},
            {"doesnt_exist", "Ei eksisteeri"},
            {"not_ur", "Ei kuulu teile"},
            {"deleted", "Kustutatud sõidukite arv"},
            {"count_res", "Tüübi järgi suurema arvuga sõidukite arv"},
            {"table", "TABEL"}
    };

    public Object[][] getContents() {
        return contents;
    }

}
