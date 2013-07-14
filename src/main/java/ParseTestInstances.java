
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        List<SLRLInstance> instances  = new ArrayList<SLRLInstance>();
        boolean beginningSeparator = true;
        SLRLInstance curInstance = new SLRLInstance();
        boolean firstParse = true;
        try {
            Scanner sc = new Scanner(new File("inc.txt"));
            while(sc.hasNext()){
                String curWord = sc.next();
                //comment
                if(curWord.contains("#")){
                    sc.nextLine();
                }
                else{
                    //log.error(curWord);

                    if(curWord.contains("::")){
                        if(beginningSeparator == true){
                            if(!firstParse)
                                instances.add(curInstance);
                            else
                                firstParse=false;
                            curWord = sc.next();
                            String barboneName = "";
                            while(!curWord.contains("::") && !curWord.contains("(") ){
                                barboneName+=" " + curWord;
                                curWord = sc.next();
                            }
                            //new name acknowledged
                            curInstance = new SLRLInstance();
                            curInstance.setTestInstanceName(barboneName);


                            //special case if there is no update date
                            if(curWord.contains("::")){
                                beginningSeparator = true;
                            }else{
                                beginningSeparator = false;
                            }

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

        instances.add(curInstance);
        return instances;
    }
}
