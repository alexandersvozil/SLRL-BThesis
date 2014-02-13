import Graph.Node;
import Graph.NodeNotFoundException;
import Parsing.ParseTestInstances;
import Parsing.SLRLInstance;
import ParsingData.Parser;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

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
        Parser parser = new Parser();
        instanceList = parser.parse();

    }


}
