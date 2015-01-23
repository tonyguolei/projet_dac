package selenium;

import junit.framework.Test;
import mybeans.Comment;

public class TestSuite {

    public static Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite();
        suite.addTestSuite(CreateAccountTest.class);
        suite.addTestSuite(LoginTest.class);
        suite.addTestSuite(ModifyPasswordTest.class);
        suite.addTestSuite(CreateProjectTest.class);
        suite.addTestSuite(ModifyProjectTest.class);
        suite.addTestSuite(ListProjectsTest.class);
        suite.addTestSuite(CreateBonusTest.class);
        suite.addTestSuite(CommentTest.class);
        suite.addTestSuite(SaveProjectTest.class);
        suite.addTestSuite(FundProjectTest.class);
        suite.addTestSuite(ConsultBonusOwnerTest.class);
        suite.addTestSuite(AugmentContributionTest.class);
        suite.addTestSuite(NotificationTest.class);
        suite.addTestSuite(ReportProjectTest.class);
        suite.addTestSuite(ListUsersTest.class);

        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}