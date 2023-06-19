package src.localization;

import java.util.ListResourceBundle;

public class GUILabels_sv extends ListResourceBundle {

    private static final Object[][] contents =
    {
            {"log_in", "LOGGA IN"},
            {"login", "användarnamn"},
            {"password", "lösenord"},
            {"sign_up", "REGISTRERA DIG"},
            {"sign_in", "HAR DU ETT KONTO? LOGGA IN"},
            {"auth", "AUTENTISERING"},
            {"reg", "REGISTRERING"},
            {"become", "INGET KONTO? REGISTRERA DIG"},
            {"already", "Redan existerar"},
            {"invalid", "Ogiltig inmatning"},
            {"add", "LÄGG TILL"},
            {"exit", "AVSLUTA"},
            {"clear", "RADERA"},
            {"b_help", "HJÄLP"},
            {"info", "INFO"},
            {"map", "KARTA"},
            {"count", "RÄKNA EFTER TYP"},
            {"script", "KÖR SKRIPT"},
            {"remove_type", "TA BORT EFTER TYP"},
            {"remove_id", "TA BORT EFTER ID"},
            {"upd", "UPPDATERA EFTER ID"},
            {"username", "Användarnamn"},
            {"vehicle", "Fordon"},
            {"name", "Namn"},
            {"creator", "Skapare"},
            {"coords", "Koordinater"},
            {"creation_date", "Skapandedatum"},
            {"type", "Typ"},
            {"capacity", "Kapacitet"},
            {"ep", "Effekt"},
            {"fc", "Bränsleförbrukning"},
            {"all", "ALLA"},
            {"script_help", """
                    lägg_till {element} : lägg till en ny element i samlingen
                    kör_skript filnamn : läs och kör skript från angiven fil. Skriptet innehåller kommandon på samma sätt som de anges
                    radera : radera hela samlingen
                    uppdatera id : uppdatera värdet på ett element i samlingen med motsvarande id
                    ta_bort_efter_id id : ta bort ett element från samlingen med motsvarande id
                    ta_bort_alla_efter_typ typ : ta bort alla element från samlingen där typen motsvarar den angivna
                    """},
            {"help", """
                    lägg_till {element} : lägg till en ny element i samlingen
                    kör_skript filnamn : läs och kör skript från angiven fil. Skriptet innehåller kommandon på samma sätt som de anges
                    radera : radera hela samlingen
                    uppdatera id : uppdatera värdet på ett element i samlingen med motsvarande id
                    ta_bort_efter_id id : ta bort ett element från samlingen med motsvarande id
                    räkna_efter_typ typ : visa antalet element i samlingen där typen är större än det angivna värdet
                    hjälp : visa hjälp för tillgängliga kommandon
                    avsluta : avsluta programmet (utan att spara till fil)
                    ta_bort_alla_efter_typ typ : ta bort alla element från samlingen där typen motsvarar den angivna
                    info : visa information om samlingen (typ, initieringsdatum, antal element osv.) i standardutmatningen
                    """},
            {"separator", ","},
            {"date_format", "yyyy-MM-dd HH.mm.ss Z"},
            {"col_type", "Samlings typ"},
            {"init_date", "Initieringsdatum"},
            {"amount", "Antal element"},
            {"delete_ps", "Du kan bara ta bort fordon som du har skapat"},
            {"doesnt_exist", "Existerar inte"},
            {"not_ur", "Inte din"},
            {"deleted", "Antal borttagna fordon"},
            {"count_res", "Antal fordon med större typ än angiven"},
            {"table", "TABELL"}
    };

    public Object[][] getContents() {
        return contents;
    }

}
