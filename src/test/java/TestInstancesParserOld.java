import Parsing.ParseTestInstances;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

/**
 * User: svozil
 * Date: 8/16/13
 * Time: 5:01 PM
 */
public class TestInstancesParserOld extends AbstractInstancesTest {
    private static Logger log = Logger.getLogger(TestInstancesParserOld.class);
//    private List<Parsing.SLRLInstance> instanceList;// = parseTestInstances.parse();

    @BeforeClass
    public static void prepare(){
        ParseTestInstances parseTestInstances = new ParseTestInstances();
        instanceList = parseTestInstances.parse();

    }
}
