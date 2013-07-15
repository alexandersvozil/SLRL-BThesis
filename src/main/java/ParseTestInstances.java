
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
                    if(sc.hasNextInt()){
                        //speed is not needed yet
                        sc.next();
                        Node curNode = new Node();
                        String curNodeName = "";
                        curWord = sc.next();

                        while(true){
                            curNodeName +=" " + curWord;

                            if(curWord.contains(","))
                                break;

                            curWord = sc.next();
                        }
                        //connections
                        curNode.setName(curNodeName);
                        //state
                        sc.next();
                        //country
                        sc.next();

                        curWord = sc.next();
                        Vector<Node> nodeVectorN = new Vector<Node>();
                        while(!curWord.contains("#") ){
                            curNodeName = curWord;
                        curWord = sc.next();
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

                            if(sc.hasNextDouble() || sc.hasNextInt() )
                                break;
//                            curWord=sc.next();


                        }

                        if(!curNodeMap.containsKey(curNode))
                        {
                            System.out.println(curNode);
                            curNodeMap.put(curNode,nodeVectorN);
                        }
                        else{
                            //node already in map
                            log.debug("bing");
                        }


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
