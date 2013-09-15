import Graph.Node;
import Graph.NodeNotFoundException;
import Parsing.SLRLInstance;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: svozil
 * Date: 8/16/13
 * Time: 4:58 PM
 */
public abstract class AbstractInstancesTest {
    protected static List<SLRLInstance> instanceList;
    @Test
    public void above_net(){
        String instancename = "above.net";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==22);
                assertTrue(instance.getE()==25);
            }
        }
        assertTrue(found);

    }



    @Test
    public void AGIS(){
        String instancename = "AGIS";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue (instance.graphToString()+ "\n had "+instance.getV()+" nodes.",instance.getV() ==  82 );
                assertTrue(instance.getE()==92);
            }
        }
        assertTrue(found);

    }



    @Test
    public void allegtelecom(){
        String instancename = "Allegiance Telecom";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString()+"\n had "+ instance.getV()+" nodes.",instance.getV()==53);
                assertTrue(instance.getE()==88);
            }
        }
        assertTrue(found);

    }
    @Test
    public void ATHN(){
        String instancename = "At Home Network";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString()+"\n had " + instance.getV() + "nodes.",instance.getV()==46);
                assertTrue(instance.getE()==55);
            }
        }
        assertTrue(found);

    }
    @Test
    public void ATT(){
        String instancename = "AT&T WorldNet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==93);
                assertTrue(instance.getE()==154);
            }
        }
        assertTrue(found);

    }
    @Test
    public void BBN_Planet(){
        String instancename = "BBN Planet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==41);
                assertTrue(instance.getE()==49);
            }
        }
        assertTrue(found);

    }
    @Test
    public void CableandWireless(){
        String instancename = "Cable & Wireless";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(""+instance.getV(),instance.getV()==19);
                assertTrue(instance.getE()==33);
            }
        }
        assertTrue(found);

    }
    @Test
    public void CableInternet(){
        String instancename = "Cable Internet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==8);
                assertTrue(instance.getE()==7);
            }
        }
        assertTrue(found);

    }


    @Test
    public void CAISInternet(){
        String instancename = "CAIS Internet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==37);
                assertTrue(instance.getE()==44);
            }
        }
        assertTrue(found);

    }
    @Test
    public void CompuServeNetworkServices(){
        String instancename = "CompuServe Network Services";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==16);
                assertTrue(instance.getE()==23);
            }
        }
        assertTrue(found);

    }
    @Test
    public void CRLNetworkServices(){
        String instancename = "CRL Network Services";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==35);
                assertTrue(instance.getE()==50);
            }
        }
        assertTrue(found);

    }
    @Test
    public void DataXchange(){
        String instancename = "DataXchange Network, Inc.";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==8);
                assertTrue(instance.getE()==24);
            }
        }
        assertTrue(found);

    }
    @Test
    public void EPOCH(){
        String instancename = "EPOCH Networks, Inc.";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==29);
                assertTrue(instance.getE()==30);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Eunet(){
        String instancename = "EUnet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==28);
                assertTrue(instance.getE()==30);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Exodus(){
        String instancename = "Exodus";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==14);
                assertTrue(instance.getE()==19);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Genuity(){
        String instancename = "Genuity";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==48);
                assertTrue(instance.getE()==53);
            }
        }
        assertTrue(found);

    }
    @Test
    public void GeoNet(){
        String instancename = "GeoNet Communications, Inc.";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==13);
                assertTrue(instance.getE()==15);
            }
        }
        assertTrue(found);

    }
    @Test
    public void GetNet(){
        String instancename = "GetNet International";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==5);
                assertTrue(instance.getE()==6);
            }
        }
        assertTrue(found);

    }
    @Test
    public void GlobalCenter(){
        String instancename = "GlobalCenter";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString()+"\n had "+instance.getV()+" Nodes.",instance.getV()==9);
                assertTrue(instance.getE()==36);
            }
        }
        assertTrue(found);

    }
    @Test
    public void GoodNet(){
        String instancename = "GoodNet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==27);
                assertTrue(instance.getE()==58);
            }
        }
        assertTrue(found);

    }
    @Test
    public void IDTCorp(){
        String instancename = "IDT Corp";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==15);
                assertTrue(instance.getE()==18);
            }
        }
        assertTrue(found);

    }
    @Test
    public void ipf_net(){
        String instancename = "ipf.net";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==5);
                assertTrue(instance.getE()==5);
            }
        }
        assertTrue(found);

    }
    @Test
    public void iStar(){
        String instancename = "iSTAR Internet Inc.";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==20);
                assertTrue(instance.getE()==22);
            }
        }
        assertTrue(found);

    }
    @Test
    public void MindSpring(){
        String instancename = "MindSpring";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==41);
                assertTrue(instance.getE()==45);
            }
        }
        assertTrue(found);

    }
    @Test
    public void NapNetLLC(){
        String instancename = "Nap.Net, LLC";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==6);
                assertTrue(instance.getE()==7);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Netrail(){
        String instancename = "Netrail Incorporated";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==17);
                assertTrue(instance.getE()==21);
            }
        }
        assertTrue(found);

    }
    @Test
    public void PSINet(){
        String instancename = "PSINet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(""+instance.getV(),instance.getV()==78);
                assertTrue(instance.getE()==110);
            }
        }
        assertTrue(found);

    }
    //wrong in the paper
    @Test
    public void Qwest(){
        String instancename = "Qwest";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(""+instance.getV(),instance.getV()==14);
                assertTrue(instance.getE()==26);
            }
        }
        assertTrue(found);

    }
    @Test
    public void RisqNetwork(){
        String instancename = "RISQ Network";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==13);
                assertTrue(instance.getE()==12);
            }
        }
        assertTrue(found);

    }
    @Test
    public void RNP(){
        String instancename = "RNP";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==27);
                assertTrue(instance.getE()==35);
            }
        }
        assertTrue(found);

    }
    @Test
    public void SavvisCommunications(){
        String instancename = "Savvis Communications";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==28);
                assertTrue(instance.getE()==56);
            }
        }
        assertTrue(found);

    }
    @Test
    public void ServIntInternetServices(){
        String instancename = "ServInt Internet Services";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==23);
                assertTrue(instance.getE()==34);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Sprint(){
        String instancename = "Sprint";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(""+instance.getV(),instance.getV()==22);
                assertTrue(instance.getE()==39);
            }
        }
        assertTrue(found);

    }
    @Test
    public void TelstraInternet(){
        String instancename = "Telstra Internet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==21);
                assertTrue(instance.getE()==24);
            }
        }
        assertTrue(found);

    }
    @Test
    public void UUNet(){
        String instancename = "UUNET";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(""+instance.getV(),instance.getV()==128);
                assertTrue(""+instance.getE(),instance.getE()==321);
            }
        }
        assertTrue(found);

    }
    @Test
    public void Verio(){
        String instancename = "Verio";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==35);
                assertTrue(instance.getE()==72);
            }
        }
        assertTrue(found);

    }
    @Test
    public void VisiNet(){
        String instancename = "VisiNet";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.getV()==11);
                assertTrue(instance.getE()==13);
            }
        }
        assertTrue(found);

    }
    @Test
    public void XOCommunications(){
        String instancename = "XO Communications";
        boolean found = false;
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                found = true;
                assertTrue(instance.graphToString(),instance.getV()==33);
                assertTrue(instance.getE()==38);
            }
        }
        assertTrue(found);

    }

    @Test
    public void XOCommunicationsPath() throws NodeNotFoundException {
        String instancename = "XO Communications";
        for(SLRLInstance instance : instanceList){
            if(instance.getTestInstanceName().equals(instancename)){
                Iterator<Node> nodeIterator =  instance.getGraph().getGraph().iterator();
                Node node1 = nodeIterator.next();
                Node node2 = nodeIterator.next();
            }
        }


    }

}
