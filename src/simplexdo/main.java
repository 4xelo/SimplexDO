package simplexdo;

/**
 *
 * @author Misho
 */
public class main {

   public static void main(String [] args) throws java.io.FileNotFoundException
	{

	matica mat;
	mat = new matica(50,50);            //   vytvori novu nulovu maticu 20x20
	System.out.print('\f');             //   vymaze terminalove okno
//      mat.nacitajZKlavesnice();           //   citanie matice z klavesnice - NacitajMaticu(A,PocRia,PocStl);}
        mat.nacitajZoSuboru("aaa.txt");    //   nacitanie suboru zo suboru
        
        System.out.println("Matica po nacitani:");
        mat.zobraz();
        
        mat.SimpexovaMetoda();

       
  
        
        
	}
}
