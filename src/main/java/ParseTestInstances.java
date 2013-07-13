import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseTestInstances {

    /**
     * Parses inc.txt taken from quaida.com
     * @return List of SLRL Test instances
     */
    public List<SLRLInstance> parse(){
        try {
            Scanner sc = new Scanner(new File("inc.txt"));
        } catch (FileNotFoundException e) {
            log.error
        }
        return null;
    }
}
