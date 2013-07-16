
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
        boolean firstString = true;
        boolean beginningSeparator = true;
        SLRLInstance curInstance = new SLRLInstance();
        Map<Node,Vector<Node>> curNodeMap = new HashMap<Node, Vector<Node>>();

        boolean firstParse = true;
        try {
            Scanner sc = new Scanner(new File("test.txt"));
            while(sc.hasNext()){
                String curWord = sc.next();
                //comment
                if(curWord.contains("#")){
                    sc.nextLine();
                    firstString=true;
                }
                //not a comment
                else{
                    //log.error(curWord);

                    if(curWord.contains("::")){
                        if(beginningSeparator == true){

                            if(!firstParse){
                                curInstance.setGraph(curNodeMap);
                                instances.add(curInstance);
                            }else{
                                firstParse=false;
                            }

                            curWord = sc.next();
                            String barboneName = "";
                            while(!curWord.contains("::") && !curWord.contains("(") ){
                                barboneName+=" " + curWord;
                                curWord = sc.next();
                            }
                            //new name acknowledged
                            curInstance = new SLRLInstance();
                            curNodeMap = new HashMap<Node,Vector<Node>>();

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
                    //create nodes
                    if(sc.hasNextInt()|| curWord.matches("-?\\d+(\\.\\d+)?")){
                        //speed is not needed yet
                        curWord = sc.next();
                        Node curNode = new Node();
                        String curNodeName = "";
                        if(firstString){
                            curNodeName += curWord+ " ";
                        }
                        curWord = sc.next();

                        boolean jump = false;
                        while(true){
                            if(!curNodeName.contains(",")){
                            jump = true;
                            curNodeName +=curWord +" ";
                            }

                            if(curWord.contains(",")){
                                break;
                            }

                            curWord = sc.next();
                        }

                        //connections
                        curNode.setName(curNodeName);
                        //state
                        sc.next();
                        //country
                        curWord = sc.next();

                        //needed if the city has a name like "los angeles"
                        if(jump)
                        curWord = sc.next();

                        Vector<Node> nodeVectorN = new Vector<Node>();
                        log.debug(curWord);

                        //second point of connection (right side of the file)
                        while(!curWord.contains("#") && !sc.hasNextInt() && !sc.hasNextDouble() && !sc.hasNext("-?\\d+(\\.\\d+)?") ){

                       /*curWord = sc.next();
                            while(true){
                                curNodeName += " " + curWord;
                                if(curWord.contains(","))
                                    break;
                                curWord = sc.next();
                            }
                            Node n = new Node();
                            n.setName(curNodeName);
                            log.debug("added adj.node:"+ n);
                            nodeVectorN.add(n);

                            //state
                            curWord=sc.next();
                            //log.debug("state:"+curWord);
                            //country
                             curWord=sc.next();
                             //log.debug("country:"+ curWord);
                            */
                            curWord=sc.next();
                        }
                        firstString=true;

                        log.debug ("start "+ curNode);
                        if(!curNodeMap.containsKey(curNode))
                        {
                            curNodeMap.put(curNode,null);//nodeVectorN);
                        }
                        else{
                            //node already in map
                            //log.debug("bing");
                        }


                    }else{
                        //log.debug("shit:"+ curWord);
                    }

                }




            }
        } catch (FileNotFoundException e) {
            log.error("File inc.txt  was not found");
        }
        curInstance.setGraph(curNodeMap);
        instances.add(curInstance);
        return instances;
    }
}
