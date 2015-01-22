package selenium;

import junit.framework.Test;

public class TestSuite {

    public static Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite();

        suite.addTestSuite(LoginTest.class);
        suite.addTestSuite(ModifyPasswordTest.class);
        suite.addTestSuite(CreateProjectTest.class);

        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}