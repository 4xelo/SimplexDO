/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexdo;

/**
 *
 * @author Misho
 */
public class matica
{
    private int m;        // aktualny pocet podmienok v modeli (bez pomocneho riadku)
    private int n;        // aktualny pocet stlpcov matice - pocet prem ennych (bez stlpca pravych stran a stlpca x0)
    private double [][] a;  // simplexova tabulka - hodnoty  (m+1 riadkov, n+2 stlpcov)
    private double [] x;     // vysledok - krajny bod
    private int [] B;       // baza - su tu ulozene indexy stlpcov, kde sa nachadza pivotovy prvok pre prislusny riadok 
    private int r;          // pivotovy riadok
    private int s;          // pivotovy stlpec
    private boolean NemaOpt;      //false = nasli sme optimalne riesenie, true = este sme optimum nenasli
    private boolean NemaRies;     //false = nasli sme pivota, true = nenasli sme pivota = koncime



    public matica(int paPocRia, int paPocStl)  // parametre - maximalny pocet riadkov a stlpcov matice
    {
        m = paPocRia;                   // inicializuje pocet riadkov modelu (strukt.podmienky)
        n = paPocStl;                   // inicializuje pocet stlpcov (premennych - bez stlpca x0 a pravej strany)
        a = new double [m+1][n+2];      // vytvori maticu a
        x = new double [m+1];           // vytvori pole pre riesenie
        B = new int [m+1];              // vytvori bazu - indexy bazicnych premennych
    }




    /**
     * Zobrazenie matice v terminalovom okne
     */
    public void zobraz()
    {
        System.out.println("Simplexova tabulka: ");
        for (int i=0; i<m+1; i++)    //pre vsetky riadky
          {  
          for (int j=0; j<n+1; j++)  //pre vsetky stlpce
            System.out.printf("%8.2f",a[i][j]);
          System.out.print(" | ");
          System.out.printf("%8.2f",a[i][n+1]);
          System.out.println();
          }
        
        
        System.out.println("Hodnota UF: "+a[0][n+1]);
        System.out.print(" | ");
        for (int i=1; i<m+1; i++) {
            System.out.print("x["+B[i]+"] = ");
            System.out.printf("%8.2f",a[i][n+1]);
            System.out.print(" | ");
        }
          System.out.println();
          System.out.println();
        
    }


 
    public void nacitajZoSuboru(String nazovSuboru) throws java.io.FileNotFoundException
    {
        // vytvorenie novej instancie triedy Scanner pre citanie z textoveho suboru
        java.util.Scanner citac = new java.util.Scanner(new java.io.File(nazovSuboru));
        m = citac.nextInt();             
        n = citac.nextInt();
        for (int i=0; i<m+1; i++) { //pre vsetky riadky
          a[i][0]=0;        
          for (int j=1; j<n+2; j++)      //pre vsetky stlpce
            a[i][j] = citac.nextDouble();  // nacitanie hodnoty prvku zo suboru
        }
        a[0][0]=1;    //nastavenie hodnoty v pomocnom riadku v stlpci x0
        B[0]=0;       //nastavenie bazy v pomocnom riadku
       
        for (int i=1; i<m+1; i++)      //pre vsetky riadky nacita bazu
            B[i] = citac.nextInt();
        
        citac.close();
    }

    
   public void SimpexovaMetoda(){
       //hlavny cyklus simplexovej metody
       while (NemaOpt == false) {
           NemaOpt = StlpcovePravidlo();
           if (NemaOpt!=true) 
                {
                    NemaRies = RiadkovePravidlo();
                    if (NemaRies!=true) PivotovaTransformacia();   
                    zobraz();
                }
           if (NemaRies)
           {
               System.out.println("Uloha nema riesenie z dovodu neohranicenosti!");
               NemaOpt = true;
           }
       }
  

           
   } 

   public boolean StlpcovePravidlo(){
       //SEM DOPLNTE KOD - STLPCOVE PRAVIDLO
       // navratova hodnota: TRUE - ak sa nenasiel pivotovy stlpec, inak FALSE

       s = 0;
       for (int j = 1; j < n; j++) {
           /*if (a[0][j] > 0) {
               s = j; // index 1 cisla vacsieho ako 0 priradim do pivotoveho stlpca
           }*/
           if (a[0][j] > a[0][s]) { //porovnavam hodnoty na indexe j s tym co mam oznacene ako
               s = j;          //pivotovy stlpec, ak je ta hodnota mensia upravim pivotovy stlpec
           }
       }
       return s <= 0; //ak sa pivotovy stlpec nezmenil vratim true, inak false
       
   } 
   
   public boolean RiadkovePravidlo(){
       //SEM DOPLNTE KOD - RIADKOVE PRAVIDLO
       // navratova hodnota: TRUE - ak sa nenasiel pivotovy riadok, inak FALSE
       r=0;
       double hodnota = 100000000.00;

       for (int i = 1; i < m+1; i++) {
           double hodnotaVCykle = (a[i][n+1]/a[i][s]);
           if (a[i][s] > 0.0 && hodnotaVCykle < hodnota) {
               r = i;
               hodnota = (a[i][n+1]/a[i][s]);
           }
       }
       return r <= 0;
   } 
   
   public void PivotovaTransformacia(){
       //SEM DOPLNTE KOD - PIVOTOVA TRANSFORMACIA

        double koef = a[r][s];
        for (int j = 0; j < n+2;j++) {
            a[r][j] = a[r][j]/koef;
        }
        for (int i =0; i < m+1; i++)
        {
                koef = a[i][s];
                for (int j = 0; j < n+2; j++)
                {
                    if (i == r) {
                    break;
                }
                    a[i][j] = a[i][j] - (koef * a[r][j]);
                }
        }
        B[r] =s;
   } 

}