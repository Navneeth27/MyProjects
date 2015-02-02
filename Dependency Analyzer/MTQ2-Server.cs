///////////////////////////////////////////////////////////////////////
// MT2Q2-Server.cs - Client module to send messages to server        //
// ver 1.1                                                          //
// Language:    C#, 2008, .Net Framework 5.0                         //
// Platform:    lenovo g550, windows 8, sp1                          //
// Application:Dependency Analyzer                                   //
// Author:      Navneeth Ramprasad(SUID:989480419)                   //
// Source:      Jim Fawcett, CST 4-187, Syracuse University          //
//              (315) 443-3948, jfawcett@twcny.rr.com                //
///////////////////////////////////////////////////////////////////////
/*
 * Module Operations:
 * ------------------
 * This module defines the following class:
 *   Server  - a collection of functions to host a service and send messages to the client.
 *   
 * -After Server is initialized,it waits on loop until the Quit message is obtained. It 
 *  serves the client with a list of files first. it does analysis on those files 
 *  and returns the type and package dependency analysis done on those files.
 */
/* Required Files:
 *   ServiceLibrary.cs, Analyzer.cs, FileMgr.cs,TypeTable.cs
 *   
 * Maintenance History:
 * --------------------
 * ver 1.1 : Nov 17 2014
 * -added sendmessage function to connect to and send message to client.
 * -added functionality to main function
 * ver 1.0 : Nov  2014
 * - first release
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;
using System.ServiceModel.Channels;
using CodeAnalysis;
using System.Xml.Serialization;
using System.IO;
using System.Threading;
using System.Xml.Linq;
using System.Xml;


namespace MT2Q2
{
  public class Server
  {
    public static IMessageService CreateClientChannel(string url)
    {
      BasicHttpBinding binding = new BasicHttpBinding();
      EndpointAddress address = new EndpointAddress(url);
      ChannelFactory<IMessageService> factory =
        new ChannelFactory<IMessageService>(binding, address);
      return factory.CreateChannel();
    }
    public static ServiceHost CreateServiceChannel(string url)
    {
      BasicHttpBinding binding = new BasicHttpBinding();
      Uri baseAddress = new Uri(url);
      Type service = typeof(MsgService);
      ServiceHost host = new ServiceHost(service, baseAddress);
      host.AddServiceEndpoint(typeof(IMessageService), binding, baseAddress);
      return host;
    }
    public void SendMessageCS(ClientMsg.Command cmd, Uri src, Uri dst, string body)
    {
        ClientMsg msg = new ClientMsg();

        msg.cmd = cmd;
        msg.src = src;
        msg.dst = dst;
        msg.body = body;
        IMessageService proxy = CreateClientChannel(dst.ToString());
        proxy.PostMessageCS(msg);
    }

    public static string TypeToXml(List<ElementRelation> relations)
    {
        XDocument xml = new XDocument();
        xml.Declaration = new XDeclaration("1.0", "utf-8", "yes");
        XElement root = new XElement("TypeDependencies");
        xml.Add(root);
        foreach(ElementRelation e in relations)
        {
            
            XElement type1 = new XElement("Type1");
            XElement name1 = new XElement("Name1", e.fromClass);
            XElement ns1 = new XElement("Namespace",e.fromNameSpace);
            type1.Add(ns1);
            XElement pkg1 = new XElement("Package",e.fromPackage);
            type1.Add(pkg1);
            XElement relation=new XElement("Relation",e.relationType);
            XElement name2 = new XElement("Type2", e.toClass);
            XElement ns2 = new XElement("Namespace", e.toNameSpace);
            type1.Add(ns2);
            XElement pkg2 = new XElement("Package", e.toPackage);
            type1.Add(pkg2);
            type1.Add(relation);
            type1.Add(name2);
            root.Add(type1);
     
        }
        return xml.ToString();
    }
   public static string ProjectsToXML(List<string> files)
      {
          XDocument xml = new XDocument();
          xml.Declaration = new XDeclaration("1.0", "utf-8", "yes");
          XElement root = new XElement("files");
          xml.Add(root);
          foreach(string str in files)
          {
              XElement file = new XElement("filename",str);
              root.Add(file);
          }
          return xml.ToString();

      }
    static void Main(string[] args)
    {
      Console.WriteLine("Starting Message Service on Server hosted at port 8080\n");

      ServiceHost host = CreateServiceChannel("http://localhost:8080/MessageService");
      host.Open();

      Server server = new Server();
     
      ClientMsg msg2 = new ClientMsg();

      MsgService msgsvc = new MsgService();
     
      while (true)
      {
          
          msg2 = msgsvc.GetMessageCS();
         
          if(msg2.cmd.ToString()=="ProjectList")
          {
              Console.Write("\n\n  Received Message from client-server channel from : {0}\n", msg2.src.ToString());
              FileMgr fileManager = new FileMgr();
              fileManager.findFiles("../../../Test");
             List<string> fileList= fileManager.getFiles1() ;
         
             string str = ProjectsToXML(fileList);
             IMessageService proxy = CreateClientChannel(msg2.src.ToString());
             server.SendMessageCS(ClientMsg.Command.ProjectList, new Uri("http://localhost:8080/MessageService"), msg2.src, str);
              
          }
          if (msg2.cmd.ToString() == "DependencyAnalysis")
          {
              Console.Write("\n\n  Received Message from client-server channel from : {0}\n", msg2.src.ToString());
              List<string> files = new List<string>();
              ClientMsg msg4 = new ClientMsg();
              XDocument doc=new XDocument();
              
              doc=XDocument.Parse(msg2.body);
              
              var q= from x in doc.Root.Elements("filename")
                         select x;
              FileMgr fileManager = new FileMgr();
              List<string> fileListOld = new List<string>();
              try
              {
                  foreach (string x in q)
                      files.Add(x.ToString());
                  string[] fileList = files.ToArray();
                  foreach(string f in fileList)
                  Console.WriteLine("filelist="+f);

                  for (int i = 0; i < fileList.Length ;i++)
                  {
                       fileManager.findProjs(fileList[i]);
                       fileListOld.AddRange(fileManager.getFiles1());   
                  }
              }
              catch(Exception exp)
              {
                  Console.Write("\n exception thrown: {0}",exp);
              }
              string[] fileListNew = fileListOld.ToArray();
              Analyzer analyze = new Analyzer();
              analyze.doAnalysis(fileListNew);
              analyze.doAnalysisRelationship(fileListNew);
              analyze.doPackageDependency(fileListNew);
              List<ElementRelation> table = RelationshipRepository.relationship_;

              StringBuilder str = new StringBuilder();
              foreach (ElementRelation e in table)
                  str.Append(e.fromNameSpace + "." + e.fromPackage + "." + e.fromClass + " " + e.relationType + " " + e.toNameSpace + "." + e.toPackage + "." + e.toClass + ",");
              str.Append("!");
              foreach (ElemDependence x in PackageDependence.dependenceTable)
                  str.Append(x.package1 + " depends on " + x.package2 + ",");
              server.SendMessageCS(ClientMsg.Command.DependencyAnalysis, new Uri("http://localhost:8080/MessageService"), msg2.src, str.ToString());
          }
      }
      Console.Write("\n  press key to terminate service\n");
      Console.ReadKey();
      Console.Write("\n");
      host.Close();
    }
  }
}
