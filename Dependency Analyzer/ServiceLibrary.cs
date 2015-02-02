///////////////////////////////////////////////////////////////////////
// MT2Q2-ServiceLibrary.cs - Service and messages that are used by   // 
//                           client and server to connect.           //
// ver 1.1                                                           //
// Language:    C#, 2008, .Net Framework 5.0                         //
// Platform:    lenovo g550, windows 8, sp1                          //
// Application: Demonstration for CSE681, Project #4, Fall 2014      //
// Author:      Navneeth Ramprasad(SUID:989480419)                   //
// Source:      Jim Fawcett, CST 4-187, Syracuse University          //
//              (315) 443-3948, jfawcett@twcny.rr.com                //
///////////////////////////////////////////////////////////////////////
/*
 * Module Operations:
 * ------------------
 * This module defines the following Interface:
 *   IMessage  - service contract and operation contract for clients and servers to connect. Service contract defines the namespace and the operation 
 *   contract defines the operation between the client and the server.
 *   
 * This module defines the following classes:
 * -MessageService - inherits from IMessageService.It provides the Service behaviour. It provides 
 *                   the functions PostMessageCS and getMessageCS to enqueue and dequeue messages
 *                   into the blocking queue.
 * 
 * -ClientMsg - this is the DataContract to pass messages between clients and servers. This provides
 *              4 parts - src- the source address of the message
 *                      - dst- the destination adress of the message 
 *                      - cmd- depeding on this field the clients andservers perform different operations
 *                      - body- body of the message that is used to pass data.
 */
/* Required Files:
 *   BlockingQueue.cs
 *   
 * Maintenance History:
 * --------------------
 * ver 1.1 : Nov 16 2014
 * -added functionalities of the blockingqueue such as enq and deq of messages. this is done to make it thread safe.
 * ver 1.0 : Nov  2014
 * - first release
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;
using System.Runtime.Serialization;
using System.Xml.Serialization;
using System.ServiceModel.Web;
using System.IO;
using System.Threading;

namespace MT2Q2
{
    [ServiceContract(Namespace = "MT2Q2")]
    public interface IMessageService
    {
        [OperationContract(IsOneWay = true)]
        void PostMessageCS(ClientMsg msg);
        [OperationContract(IsOneWay = true)]
        void PostMessageSS(ServerMsg msg);
        ClientMsg GetMessageCS();
        ServerMsg GetMessageSS();

    }


    [ServiceBehavior(Namespace = "MT2Q2")]
    public class MsgService : IMessageService
    {
        public static BlockingQueue<ClientMsg> csBlockingQ = null;
        public static BlockingQueue<ServerMsg> ssBlockingQ = null;
        static object locker_ = new object();
        public MsgService()
        {
            if (csBlockingQ == null)
                csBlockingQ = new BlockingQueue<ClientMsg>();
            if (ssBlockingQ == null)
                ssBlockingQ = new BlockingQueue<ServerMsg>();
        }
        public void PostMessageCS(ClientMsg msg)
        {

            lock (locker_)
            {
                //msg.ShowMessage();
                csBlockingQ.enQ(msg);
            }
        }
        public void PostMessageSS(ServerMsg msg)
        {
            // Object locker_ = new Object();
            lock (locker_)
            {
                //msg.ShowMessage();
                ssBlockingQ.enQ(msg);
            }
        }

        public ClientMsg GetMessageCS()
        {
            return csBlockingQ.deQ();
        }
        public ServerMsg GetMessageSS()
        {
            return ssBlockingQ.deQ();
        }
  }



  [DataContract(Namespace="MT2Q2")]
  public class ClientMsg
  {
    public enum Command {ProjectList, DependencyAnalysis, Quit };  // Needs bigger set of enums
    [DataMember]
    public Command cmd;
    [DataMember]
    public Uri src;
    [DataMember]
    public Uri dst;
    [DataMember]
    public string body;  // Used to send XML for structured data

    public void ShowMessage()
    {
      Console.Write("\n\n  Received Message from client-server channel:");
      Console.Write("\n    src = {0}\n    dst = {1}", src.ToString(), dst.ToString());
      Console.Write("\n    cmd = {0}", cmd.ToString());
      Console.Write("\n    body:    {0}", body);
    }
      public object convertFromXML(object xml)
    {
        
        XmlSerializer deserializer = new XmlSerializer(typeof(object));
        var reader = new StringReader((string)xml);
        var regularString = (object)deserializer.Deserialize(reader);
        //foreach(string str in regularString)
        //  Console.Write("\n "+str);
        return regularString;

    }
  }
  
  public class ServerMsg
  {
      public enum Command { ProjectList, DependencyAnalysis };  // Needs bigger set of enums
      [DataMember]
      public Command cmd;
      [DataMember]
      public Uri src;
      [DataMember]
      public Uri dst;
      [DataMember]
      public string body;  // Used to send XML for structured data
      public void ShowMessage()
      {
          Console.Write("\n\n  Received Message from server-server channel:");
          Console.Write("\n    src = {0}\n    dst = {1}", src.ToString(), dst.ToString());
          Console.Write("\n    cmd = {0}", cmd.ToString());
          Console.Write("\n    body:    {0}", body);
         // Console.WriteLine("This is running on thread " + Thread.CurrentThread.Name);
          //Console.Write("\n XML decoded from body:\n");
      }
   }
}
