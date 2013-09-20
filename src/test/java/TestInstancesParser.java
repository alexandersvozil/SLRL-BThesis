import Parsing.ParserAlternative;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/29/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestInstancesParser extends AbstractInstancesTest {
    private static Logger log = Logger.getLogger(TestInstancesParser.class);
//    private List<Parsing.SLRLInstance> instanceList;// = parseTestInstances.parse();

    @BeforeClass
    public static void prepare(){
        ParserAlternative parser = new ParserAlternative();
        instanceList = parser.parse();

    }


}
