package selenium;

import junit.framework.Test;

public class TestSuite {

    public static Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite();

        suite.addTestSuite(LoginTest.class);
        suite.addTestSuite(ModifyPasswordTest.class);
        suite.addTestSuite(CreateProjectTest.class);
        suite.addTestSuite(ModifyProjectTest.class);
        suite.addTestSuite(ListProjectsTest.class);
        suite.addTestSuite(CreateBonusTest.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}