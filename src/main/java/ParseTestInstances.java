
import org.apache.log4j.Logger;

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
    private final Logger log  = Logger.getLogger(ParseTestInstances.class);

    /**
     * Parses inc.txt into SLRLInstance Objects taken from quaida.com
     * @return List of SLRL Test instances
     */
    public List<SLRLInstance> parse(){
        boolean beginningSeparator = true;
        try {
            Scanner sc = new Scanner(new File("inc.txt"));
            while(sc.hasNext()){
                String curWord = sc.next();
                //comment
                if(curWord.contains("#")){
                    sc.nextLine();
                }
                else{

                    if(curWord.contains("::")){
                        if(beginningSeparator == true){
                            curWord = sc.next();
                            String barboneName = "";
                            while(!curWord.contains("::") && !curWord.contains("(") ){
                                barboneName+=" " + curWord;
                                curWord = sc.next();
                            }
                            System.out.println(barboneName);
                            beginningSeparator = false;

                        }
                        else {
                            beginningSeparator = true;
                        }

                    }

                }




            }
        } catch (FileNotFoundException e) {
            log.error("File inc.txt  was not found");
        }
        return null;
    }
}
