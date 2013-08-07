import Parsing.ParseTestInstances;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/13/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main (String args[]) {
        final Logger log = Logger.getLogger(Application.class);

        ParseTestInstances parseTestInstances = new ParseTestInstances();
        List<SLRLInstance> instanceList = parseTestInstances.parse();
        log.debug("Instance Size: "+instanceList.size());
        for(SLRLInstance slrlInstance: instanceList){
            log.debug(slrlInstance.toString());
            log.debug("------------------------------");
        }
    }
}
