package com.automate.utils;

import com.automate.reports.ExtentReportLogger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class XmlHandler
{
  public static String filePath = "";
  private static File xmlFile;
  private static DocumentBuilderFactory dbFactory;
  private static DocumentBuilder builder;
  private static Document doc;
  private static Map<String, String> NodeValueMap = new HashMap<>();


  //Constructor to load Xml file according to EndUser
  public XmlHandler(String FilePath)
  {
    try
    {
      filePath = FilePath;
      xmlFile = new File(filePath);
      Boolean kk = xmlFile.exists();
      dbFactory = DocumentBuilderFactory.newInstance();
      builder = dbFactory.newDocumentBuilder();
      doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();
    }
    catch(Exception e)
    {
      System.out.println(ExceptionUtils.getStackTrace(e));
      e.printStackTrace();
    }
  }


  public static Map getNodeAndValues(String Tagname, String Node)
  {
    try
    {
      filePath = System.getProperty("user.dir") + File.separator
                                        + "src" + File.separator
                                        + "test" + File.separator
                                        + "resources" + File.separator
                                        + "data" + File.separator
                                        + "testdata.xml";
      xmlFile = new File(filePath);
      dbFactory = DocumentBuilderFactory.newInstance();
      builder = dbFactory.newDocumentBuilder();
      doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName(Tagname);
      for(int i = 0; i < nodeList.getLength(); i++)
      {
        Node node = nodeList.item(i);
        if(node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
        {
          String attributeVal = node.getAttributes().getNamedItem("name").getNodeValue();
          if(attributeVal.equalsIgnoreCase(Node))
          {
            Element elem = (Element) node;
            NodeList kk = elem.getChildNodes();
            for(int j=0; j < elem.getChildNodes().getLength(); j++)
            {
              if(!kk.item(j).getTextContent().trim().isEmpty())
              {
                String Key = kk.item(j).getChildNodes().toString().split(":")[0].replace("[","");
                NodeValueMap.put(Key, kk.item(j).getTextContent());
              }
            }
          }
        }
      }
      return NodeValueMap;
    }
    catch(ParserConfigurationException | IOException | SAXException e)
    {
      throw new RuntimeException(e);
    }
  }


  public static String readTestXMLData(String nodeVal,String fieldName)
  {
    String returnText="";
    try
    {
      filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
              + "test" + File.separator + "resources" + File.separator + "data" + File.separator + "testdata.xml";
      File xmlFile=new File(filePath);
      DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbFactory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList nodeList=doc.getElementsByTagName("testcase");
      boolean tcfound=false;
      for(int i=0; i<nodeList.getLength();i++)
      {
        Node node=nodeList.item(i);
        if(node.getNodeType()==Node.ELEMENT_NODE)
        {
          String attributeVal=node.getAttributes().getNamedItem("name").getNodeValue();
          if(attributeVal.equalsIgnoreCase(nodeVal))
          {
            tcfound=true;
            Element elem=(Element) node;
            if(elem.getElementsByTagName(fieldName).getLength()<=0)
            {
              String failText="readXMLData - Could not find field '"+fieldName+"' in the test data of test case '"+nodeVal+"'. Returning empty text";
              System.out.println(failText);
              ExtentReportLogger.logInfo(failText);
              returnText="";
              break;
            }
            else
            {
              returnText=elem.getElementsByTagName(fieldName).item(0).getTextContent();
              break;
            }
          }
        }
      }
      if(!tcfound)
      {
        String failText="readXMLData - Could not find test case '" + nodeVal + "' in the test data. Returning empty text";
//        System.out.println(failText);
        TestUtils.log().info(failText);
        ExtentReportLogger.logInfo(failText);
        returnText="";
      }
    }
    catch(Exception e)
    {
      System.out.println(ExceptionUtils.getStackTrace(e));
      e.printStackTrace();
    }
    return returnText;
  }

  public static void writeXMLData(String nodeVal,String fieldName, String val)
  {
    try
    {
      File xmlFile=new File(filePath);
      xmlFile.canWrite();
      DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbFactory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList nodeList=doc.getElementsByTagName("testcase");
      boolean tcfound=false;
      for(int i=0; i<nodeList.getLength();i++)
      {
        Node node=nodeList.item(i);
        if(node.getNodeType()==Node.ELEMENT_NODE)
        {
          String attributeVal=node.getAttributes().getNamedItem("name").getNodeValue();
          if(attributeVal.equalsIgnoreCase(nodeVal))
          {
            tcfound=true;
            Element elem=(Element) node;
            if(elem.getElementsByTagName(fieldName).getLength()<=0)
            {
              String failText="writeXMLData - Could not find empty tags with name '" + fieldName + "' in the test data for the test case '"+nodeVal+"'";
              System.out.println(failText);
              ExtentReportLogger.logInfo(failText);
              break;
            }
            else
            {
              elem.getElementsByTagName(fieldName).item(0).setTextContent(val);
              TransformerFactory factory = TransformerFactory.newInstance();
              Transformer transformer = factory.newTransformer();
              DOMSource domSource = new DOMSource(doc);
              StreamResult streamResult = new StreamResult(xmlFile);
              transformer.transform(domSource, streamResult);
              break;
            }
          }
        }
      }
      if(!tcfound)
      {
        String failText="writeXMLData - Could not find test case '" + nodeVal + "' in the test data";
        System.out.println(failText);
        ExtentReportLogger.logInfo(failText);
      }
    }
    catch(Exception e)
    {
      System.out.println(ExceptionUtils.getStackTrace(e));
      e.printStackTrace();
    }
  }
}
