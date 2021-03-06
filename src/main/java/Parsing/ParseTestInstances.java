package Parsing;

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
    private final Logger log = Logger.getLogger(ParseTestInstances.class);

    /**
     * Parses inc.txt into Parsing.SLRLInstance Objects taken from quaida.com
     *
     * @return List of SLRL Test instances
     */
    public List<SLRLInstance> parse() {
        boolean newInstance = false;
        List<SLRLInstance> instances = new ArrayList<SLRLInstance>();
        boolean firstString = true;
        boolean beginningSeparator = true;
        SLRLInstance curInstance = new SLRLInstance();
        Map<ParseNode, Vector<ParseNode>> curNodeMap = new HashMap<ParseNode, Vector<ParseNode>>();

        boolean firstParse = true;
        try {
            Scanner sc = new Scanner(new File("inc.txt"));
            String curWord = "";
            while (sc.hasNext()) {
                if (!newInstance)
                    curWord = sc.next();
                else {
                    newInstance = false;
                }
                //comment
                if (curWord.contains("#") || curWord.contains("?")) {
                    sc.nextLine();
                    firstString = true;
                }
                //not a comment
                else {
                    //log.error(curWord);

                    if (curWord.contains("::")) {
                        if (beginningSeparator == true) {

                            if (!firstParse) {
                                curInstance.setNodeVectorHashMap(curNodeMap);
                                instances.add(curInstance);
                            } else {
                                firstParse = false;
                            }

                            curWord = sc.next();
                            String barboneName = "";
                            while (!curWord.contains("::") && !curWord.contains("(")) {
                                barboneName += " " + curWord;
                                curWord = sc.next();
                            }
                            //new name acknowledged
                            curInstance = new SLRLInstance();
                            curNodeMap = new HashMap<ParseNode, Vector<ParseNode>>();
                            barboneName = barboneName.substring(1);
                            curInstance.setTestInstanceName(barboneName);


                            //special case if there is no update date
                            if (curWord.contains("::")) {
                                beginningSeparator = true;
                            } else {
                                beginningSeparator = false;
                            }

                        } else {
                            beginningSeparator = true;
                        }

                    }
                    //create nodes
                    if (sc.hasNextDouble() || sc.hasNextInt() || curWord.matches("-?\\d+(\\.\\d+)?")) {
                        //speed is not needed yet
                        curWord = sc.next();

                        if (curWord.matches("-?\\d+(\\.\\d+)?"))
                            curWord = sc.next();

                        ParseNode curNode = new ParseNode();
                        String curNodeName = "";
                        if (firstString) {
                            curNodeName += curWord + " ";
                        }
                        curWord = sc.next();

                        boolean jump = false;
                        while (true) {
                            if (!curNodeName.contains(",")) {
                                jump = true;
                                curNodeName += curWord + " ";
                            }

                            if (curWord.contains(",")) {
                                break;
                            }

                            curWord = sc.next();
                        }
                        //needed if the city has a name like "los angeles"
                        if (jump)
                            curWord = sc.next();
                        curNodeName += curWord;
                        //state
                        //curNodeName+=sc.next();
                        sc.next();
                        //country
                        curWord = sc.next();
                        //connections
                        curNode.setName(curNodeName);
                        //log.debug("current key node: "+ curNodeName);


                        Vector<ParseNode> nodeVectorN = new Vector<ParseNode>();

                        //second point of connection (right side of the file)
                        while (!sc.hasNext("#+") && !sc.hasNextInt() && !sc.hasNextDouble() && !sc.hasNext("-?\\d+(\\.\\d+)?")) {
                            curNodeName = "";
                            // curWord=sc.next();
                            while (true) {
                                curNodeName += curWord + " ";
                                if (curNodeName.contains(","))
                                    break;
                                if (sc.hasNext())
                                    curWord = sc.next();
                                else break;
                            }
                            //state
                            curNodeName += sc.next();
                            //country
                            curWord = sc.next();
                            if (curWord.contains("~")) {
                                curWord = curWord.substring(0, curWord.length() - 1);
                            }
                            //curNodeName+=curWord;

                            //add node to vector list
                            ParseNode n = new ParseNode();
                            n.setName(curNodeName);
                            nodeVectorN.add(n);

                            Vector<ParseNode> nVectorBack = new Vector<ParseNode>();

                            nVectorBack.add(new ParseNode(curNode));
                            if (!curNodeMap.containsKey(n)) {
                                curNodeMap.put(n, nVectorBack);
                            }else{
                                Vector<ParseNode> ne = curNodeMap.get(n);
                                if(!ne.contains(curNode)){
                                  /*  log.debug("adding from right side:"+ curNode);
                                    log.debug("adding from right side nodelist:");
                                    for(Parsing.ParseNode yolo : ne){
                                        log.debug(yolo);
                                    }
                                    log.debug("end rightside nodelist");*/
                                    ne.add(curNode);
                                }
                            }

                            if (!sc.hasNextDouble() && !sc.hasNextInt()) {
                                if (!sc.hasNext())
                                    break;
                                curWord = sc.next();
                                if (curWord.contains("#")) {
                                    sc.nextLine();
                                    break;
                                }
                                if (curWord.contains(":")) {
                                    newInstance = true;
                                    break;
                                }


                            }

                            //log.debug("country:"+ curWord);

                        }
                        firstString = true;

                        //log.debug ("start "+ curNode);
                        if (!curNodeMap.containsKey(curNode)) {
                            Vector<ParseNode> tmp = new Vector<ParseNode>();//curNodeMap.get(curNode);
                            for(ParseNode n : nodeVectorN){
                                if(!tmp.contains(n)){
                                    tmp.add(n);
                                }
                            }
                            curNodeMap.put(curNode, tmp);
                            //log.debug("Curnode: " + curNode );
                        } else {
                            //node already in map
                            Vector<ParseNode> tmp = curNodeMap.get(curNode);
                            for(ParseNode n : nodeVectorN){
                               if(!tmp.contains(n)){
                                /*log.debug("adding from left side:"+ curNode);
                                log.debug("adding from left side nodelist:");
                                for(Parsing.ParseNode yolo : tmp){
                                    log.debug(yolo);
                                }
                                log.debug("end leftside nodelist");*/
                                tmp.add(n);
                               }

                            }
                        }


                    } else {
                        //didnt get caught curWord);
                    }

                }


            }
        } catch (FileNotFoundException e) {
            log.error("File inc.txt  was not found");
        }
        curInstance.setNodeVectorHashMap(curNodeMap);
        instances.add(curInstance);
        return instances;
    }
}
