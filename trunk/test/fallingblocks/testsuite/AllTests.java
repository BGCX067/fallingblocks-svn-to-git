package fallingblocks.testsuite;

import fallingblocks.model.GameBoardTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for fallingblocks.testsuite");
		//$JUnit-BEGIN$
		suite.addTestSuite(GameBoardTest.class);
		//$JUnit-END$
		return suite;
	}

}
