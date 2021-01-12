package sms;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sms.util.MathematicsTest;
import sms.util.VerificationsTest;

@RunWith(Suite.class)
@SuiteClasses({MathematicsTest.class, VerificationsTest.class})
public class AllTests {

}
