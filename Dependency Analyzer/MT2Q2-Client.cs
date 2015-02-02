///////////////////////////////////////////////////////////////////////
// MT2Q2-Client.cs - Client module to send messages to server        //
// ver 1.1                                                          //
// Language:    C#, 2008, .Net Framework 5.0                         //
// Platform:    lenovo g550, windows 8, sp1                          //
// Application: Dependency Analyzer                                  //
// Author:      Navneeth Ramprasad(SUID:989480419)                       //
// Source:      Jim Fawcett, CST 4-187, Syracuse University          //
//              (315) 443-3948, jfawcett@twcny.rr.com                //
///////////////////////////////////////////////////////////////////////
/*
 * Module Operations:
 * ------------------
 * This module defines the following class:
 *   MsgClient  -it is a collection of various functions to host a service and send messages to a server.
 *   
 * -It sends a message to the client for request of project files. It selects the files and 
 * sends message to server for type and dependency analysis. it prints the results of the
 * analysis.It can select multiple files for analysis.
 */
/* Required Files:
 *   ServiceLibrary.cs
 *   
 * Maintenance History:
 * --------------------
 * ver 1.1 : Nov 17 2014
 * -added sendmessage function to connect to and send message to server.
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
using System.Threading;
using System.Xml.Linq;
using CodeAnalysis;



namespace MT2Q2
{
  public class MsgClient
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
      public void SendMessageCS(ClientMsg.Command cmd, Uri src,Uri dst, string body )
    {
        ClientMsg msg = new ClientMsg();
        msg.cmd = cmd;
        msg.src = src;
        msg.dst = dst;
        msg.body = body;
        IMessageService proxy = CreateClientChannel(dst.ToString());
        proxy.PostMessageCS(msg);
    }

#if(TEST_CLIENT)      
    static void Main(string[] args)
    {
      Console.WriteLine("Starting Message Service on Client1\n");
//HOST client at port 8080
      ServiceHost host = CreateServiceChannel("http://localhost:8081/MessageService");
      host.Open();
        //connect to server and send request for projects
      IMessageService proxy = CreateClientChannel("http://localhost:8080/MessageService");
       
        MsgClient client=new MsgClient();   
          System.Threading.Thread t1 = new System.Threading.Thread(() => client.SendMessageCS(ClientMsg.Command.ProjectList, new Uri("http://localhost:8081/MessageService"), new Uri("http://localhost:8080/MessageService"),"from client 1 to server 1"));
          t1.Start();
          t1.Join();
          //proxy.PostMessageCS(msg);
      
      ClientMsg msg2 = new ClientMsg();
      MsgService msgsvc = new MsgService();      
      while (true)
          {
              msg2 = msgsvc.GetMessageCS();
              Console.Write("\n\n  Received Message from server : " + msg2.src.ToString()+"\n\n");
              if(msg2.cmd.ToString()=="DependencyAnalysis")
              {
                  string name = msg2.body;
                  List<string>names=name.Split('!').ToList();

                  Console.WriteLine("TypeDependencies"); 
                  List<string> name1 = names[0].Split(',').ToList();
                  foreach(string str in name1)
                  Console.WriteLine("\n"+str);

                  Console.WriteLine("\n\n Package Dependencies");
                  List<string> name2 = names[1].Split(',').ToList();
                  foreach (string str in name2)
                      Console.WriteLine("\n"+str);
              }
              if(msg2.cmd.ToString()=="ProjectList")
              {
                  
                  Console.Write("\n\n  Received Message from server :");
                  Console.Write("\n    src = {0}\n    dst = {1}", msg2.src.ToString(), msg2.dst.ToString());
                  Console.Write("\n    cmd = {0}", msg2.cmd.ToString());
                  Console.Write("\n    body:    {0}", msg2.body);
                  System.Threading.Thread t3 = new System.Threading.Thread(() => client.SendMessageCS(ClientMsg.Command.DependencyAnalysis, new Uri("http://localhost:8081/MessageService"), msg2.src, msg2.body));
                  t3.Start();
                  t3.Join();
                  //System.Threading.Thread.Sleep(500);
              }
          }
      
      Console.Write("\n  press any key to terminate service");
      Console.ReadKey();
      Console.Write("\n");
    }
  }
#endif
}
