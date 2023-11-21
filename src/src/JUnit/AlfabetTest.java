package src.JUnit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

import src.domain.classes.*;
import src.exceptions.EntradaLlegidaMalament;
import src.exceptions.FormatDadesNoValid;
import src.exceptions.TipusDadesNoValid;
import java.io.FileNotFoundException;


public class AlfabetTest {
    String result1 = "{a=0.08207018075713365, b=0.015967827503884195, c=0.02152885672060328, d=0.046946644435181324, e=0.12102106938497101, f=0.020586229110614272, g=0.02303990298988215, h=0.06124237371632119, i=0.06945128652089885, j=7.863124786843003E-4, k=0.009862063738678995, l=0.03726931676077153, m=0.03324301792413505, n=0.06661866686877108, o=0.07848914320360756, p=0.016247300011368374, q=8.38417522452537E-4, r=0.057059759748380005, s=0.05975974838000682, t=0.09029804085035431, u=0.026881465762249424, v=0.00897154117245822, w=0.02566883928909773, x=0.0015347303800826101, y=0.02437095001705256, z=2.4631475235893744E-4}";
    String result2 = "{a=0.14347784385190868, b=0.013619796662190677, c=0.030188950700172645, d=0.037396892384423557, e=0.13808267792058315, f=0.010214847496643007, g=0.01286207558028007, h=0.006560521772491847, i=0.0743286015729906, j=0.003050067139842701, l=0.06705831574908881, m=0.03255802800690581, n=0.06754747746019567, o=0.053428927680798, p=0.02721081910608095, q=0.013830807596393631, r=0.06500575484366009, s=0.07859198158450029, t=0.053798196815653176, u=0.04497410320352964, v=0.019878189142528296, x=0.004148283138308076, y=0.0017887972376750432, z=3.980433531555726E-4}";
    String result3 = "{a=0.12474647887323943, b=0.01599530516431925, c=0.036253521126760564, d=0.05257276995305164, e=0.13827230046948358, f=0.005098591549295775, g=0.010849765258215963, h=0.011845070422535211, i=0.053028169014084504, j=0.006352112676056338, l=0.05724413145539906, m=0.02727699530516432, n=0.06788262910798122, o=0.09946948356807511, p=0.021215962441314552, q=0.01936619718309859, r=0.06066666666666667, s=0.07576056338028168, t=0.03787793427230047, u=0.04871830985915493, v=0.010835680751173709, x=1.3615023474178404E-4, y=0.014460093896713615, z=0.004075117370892019}";
    String result4 = "{a=0.06869890672707574, b=0.01733477789815818, c=0.0368856495617059, d=0.04983748645720477, e=0.126908302964641, f=0.017310154634098296, g=0.030409731113956465, h=0.02920319117502216, i=0.07517482517482517, j=0.0017974982763715158, k=0.010440263961390722, l=0.05126563577267803, m=0.024475524475524476, n=0.07246626612823796, o=0.06308480252142223, p=0.028021274500147738, q=0.0013050329951738403, r=0.07052102826750714, s=0.07512557864670541, t=0.06805870186151876, u=0.03154240126071112, v=0.01088348271446863, w=0.014429232739091894, x=0.004481434058898848, y=0.019378508815128533, z=9.603072983354673E-4}";
    String result5 = "{a=0.13284353358877912, b=0.014254578690195802, c=0.04255281752030091, d=0.03536401026470278, e=0.11756951523886526, f=0.012795725384047527, g=0.01880690406721271, h=0.003831686996871375, i=0.08533413013674553, j=0.0031637782542974654, l=0.05163989172847752, m=0.03221780855626252, n=0.07011284142440327, o=0.05710619749006925, p=0.02761275354167399, q=0.003585615354870461, r=0.08415650156431258, s=0.08115091222272999, t=0.06877702393925546, u=0.029809821773825008, v=0.01861356206278342, x=0.00548388230744894, y=0.0023201040531514745, z=8.964038387176153E-4}";
    String result6 = "{a=0.1395858378252608, b=0.019253887617791912, c=0.04473184162562532, d=0.05182844068716795, e=0.12077791135068057, f=0.008531430565788964, g=0.015783146546709582, h=0.009811145150657308, i=0.0686586264396789, j=0.006049559855741265, l=0.04250203590956684, m=0.029763058905650133, n=0.06648698956838717, o=0.09208128126575406, p=0.02336448598130841, q=0.004420832202272463, r=0.08133943459882886, s=0.07732578430992361, t=0.043917477798890915, u=0.030848877341296002, v=0.013921743514173808, x=4.847403730561911E-4, y=0.0023849226354364602, z=0.006146507930352503}";



    @Test
    public void test1() {
        String path1 = "test/exemples_input_alfabet/Text1.txt";
        Alfabet a1 = iniAlfabet("Alfabet1", "text", path1);
        Map<Character, Double> result = a1.getCharacter();
        assertEquals("Test 1: Correcte\n", result.toString(), result1);
    }

    @Test
    public void test2() {
        String path2 = "test/exemples_input_alfabet/Text2.txt";
        Alfabet a2 = iniAlfabet("Alfabet2", "text", path2);
        Map<Character, Double> result = a2.getCharacter();
        assertEquals("Test 2: Correcte\n", result.toString(), result2);        
    }

    @Test
    public void test3() {
        String path3 = "test/exemples_input_alfabet/Text3.txt";
        Alfabet a3 = iniAlfabet("Alfabet3", "text", path3);
        Map<Character, Double> result = a3.getCharacter();
        assertEquals("Test 3: Correcte\n", result.toString(), result3);
    }

    @Test
    public void test4() {
        String path4 = "test/exemples_input_alfabet/Words1.txt";
        Alfabet a4 = iniAlfabet("Alfabet4", "llista-paraules", path4);
        Map<Character, Double> result = a4.getCharacter();
        assertEquals("Test 4: Correcte\n", result.toString(), result4);
    }

    @Test
    public void test5() {
        String path5 = "test/exemples_input_alfabet/Words2.txt";
        Alfabet a5 = iniAlfabet("Alfabet5", "llista-paraules", path5);
        Map<Character, Double> result = a5.getCharacter();
        assertEquals("Test 5: Correcte\n", result.toString(), result5);
    }

    @Test
    public void test6() {
        String path6 = "test/exemples_input_alfabet/Words3.txt";
        Alfabet a6 = iniAlfabet("Alfabet6", "llista-paraules", path6);
        Map<Character, Double> result = a6.getCharacter();
        assertEquals("Test 6: Correcte\n", result.toString(), result6);
    }

    @Test
    public void test7() {
        String path7 = "test/exemples_input_alfabet/Text1.txt";
        Alfabet a7 = iniAlfabet("Alfabet7", "string-qualsevol", path7);
        assertNull("Test 7: Correcte\n", a7.getAbecedari());
        // Valor esperat EXC --> TipusDadesNoValid
        // "El tipus de dades (" + tipus + ") no és vàlid."
    }

    @Test
    public void test8() {
        String path8 = "test/exemples_input_alfabet/Text1.txt";
        Alfabet a8 = iniAlfabet("Alfabet8", "12345", path8);
        assertNull("Test 8: Correcte\n", a8.getAbecedari());
        // Valor esperat EXC --> TipusDadesNoValid
        // "El tipus de dades (" + tipus + ") no és vàlid."
    }

    @Test
    public void test9() {
        String path9 = "test/exemples_input_alfabet/Words1.txt";
        Alfabet a9 = iniAlfabet("Alfabet9", "text", path9);
        assertNull("Test 9: Correcte\n", a9.getAbecedari());
        // Valor esperat EXC --> FormatDadesNoValid
        // "El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus."
    }

    @Test
    public void test10() {
        String path10 = "test/exemples_input_alfabet/path-incorrecte.txt";
        Alfabet a10 = iniAlfabet("Alfabet10", "text", path10);
        assertNull("Test 10: Correcte\n", a10.getAbecedari());
        // Valor esperat EXC --> FileNotFoundException
        // "ERROR: El fitxer " + path + " no s'ha trobat"
    }

    @Test
    public void test11() {
        String path11 = "test/exemples_input_alfabet/Text1.txt";
        Alfabet a11 = iniAlfabet("Alfabet11", "llista-paraules", path11);
        assertNull("Test 11: Correcte\n", a11.getAbecedari());
        // Valor esperat EXC --> FormatDadesNoValid
        // "El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus."
    }

    @Test
    public void test12() {
        String path12 = "test/exemples_input_alfabet/path-incorrecte.txt";
        Alfabet a12 = iniAlfabet("llista-paraules", "text", path12);
        assertNull("Test 12: Correcte\n", a12.getAbecedari());
        // Valor esperat EXC --> FileNotFoundException
        // "ERROR: El fitxer " + path + " no s'ha trobat"
    }

    private Alfabet iniAlfabet (String nom, String tipus, String path) {
        Alfabet a = new Alfabet(nom);
        try {
            a.readInput(tipus, path);
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: El fitxer " + path + " no s'ha trobat");
        }
        catch (FormatDadesNoValid e) {
            System.out.println("El format de les dades del fitxer "+ path +" introduït no es correspon amb el seu tipus.");
        }
        catch (TipusDadesNoValid e) {
            System.out.println("El tipus de dades (" + tipus + ") no és vàlid.");
        }
        catch (EntradaLlegidaMalament e) {
            System.out.println("L'entrada no s'ha llegit correctament");
        }
        return a;
    }
}
