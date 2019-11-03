/**
 * 
 */
package ca.mcgill.ecse223.quoridor.features;
import ca.mcgill.ecse223.quoridor.features.CucumberStepDefinitions;
///ca.mcgill.ecse223.quoridor/src/test/java/ca/mcgill/ecse223/quoridor/features/CucumberStepDefinitions.java
/**
 * @author FSharp4
 *
 */
public class LoadSavedGameTester {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		CucumberStepDefinitions cSD = new CucumberStepDefinitions();
		cSD.theGameIsNotRunning();
		cSD.iInitiateToLoadASavedGame("quoridor_test_game_1.dat");
		System.out.println("Yay");
	}

}
