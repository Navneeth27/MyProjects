///////////////////////////////////////////////////////////////////////
// TypeTable.cs - Service and messages that are used by the server and//
// the client to connect.                                            //                                                                    //
// ver 1.1                                                           //
// Language:    C#, 2008, .Net Framework 5.0                         //
// Platform:    lenovo g550, windows 8, sp1                          //
// Application: Demonstration for CSE681, Project #4, Fall 2014      //
// Author:      Navneeth Ramprasad(SUID:989480419)                    //
//                                                                    //
// Source:      Jim Fawcett, CST 4-187, Syracuse University          //
//              (315) 443-3948, jfawcett@twcny.rr.com                //
///////////////////////////////////////////////////////////////////////
/*
 * Module Operations:
 * ------------------
 * This module defines the following classes:
 * -TypeElement - This is the format for storing a type. It defines 4 parts -servername, namespace, filename,type,name
 * 
 * -TypeTable - This defines the thread-safe dictionary to store the typename as key and its TypeElem as values.
 *            - It also provides functions to add and remove types.
 */
/* Maintenance History:
 * --------------------
 * ver 1.1 : Nov 16 2014
 * -added functionalities of make the typetable thread safe and add servername and filename to TypeElem
 * ver 1.0 : Nov  2014
 * - first release
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CodeAnalysis
{
    public class TypeElement
    {
        public string Type { get; set; }
        public string Namespace { get; set; }
        public string Filename { get; set; }
        public string ServerName { get; set; }
    }
    public class TypeTable
    {
        object locker_ = new object();
        public Dictionary<string, TypeElement> types { get; set; }


        public TypeTable()
        {
            types = new Dictionary<string, TypeElement>();
        }
        //----< f will use lambda capture to acquire its arguments >-------

        T lockit<T>(Func<T> f)
        {
            lock (locker_) { return f.Invoke(); }
        }
        public bool add(string Name, string Namespace, string Filename, string ServerName, string Type)
        {
            TypeElement elem = new TypeElement();
            elem.Namespace = Namespace;
            elem.Filename = Filename;
            elem.ServerName = ServerName;
            elem.Type = Type;

            return lockit<bool>(() =>
            {
                if (types.Keys.Contains(Name) && types[Name].Namespace == Namespace)
                    return false;
                types[Name] = elem;
                return true;
            });
        }
        
        public bool remove(string Type)
        {
            return lockit<bool>(() => { return types.Remove(Type); });
        }
        string namespce(string Type)
        {
            return lockit<string>(() =>
            {
                return (types.Keys.Contains(Type)) ? types[Type].Namespace : "";
            });
        }
        public string filename(string Type)
        {
            return lockit<string>(() =>
            {
                return (types.Keys.Contains(Type)) ? types[Type].Filename : "";
            });
        }
        public bool contains(string Type)
        {
            return lockit<bool>(() => types.Keys.Contains(Type));
        }
    }
    class Q2
    {
        //----< partial test of type-safe TypeTable >----------------------

        static void Main(string[] args)
        {
            TypeTable tt = new TypeTable();
            tt.add("Type1", "myNamespace", " someFile.cs", "someserver1", "class");
            tt.add("Type2", "myNamespace", "someOtherFile.cs", "someserver1", "class");
            foreach (string key in tt.types.Keys)
            {
                Console.Write("\n  {0, -15} {1, -20} {2, 20}", key, tt.types[key].Namespace, tt.types[key].Filename);
            }
            Console.Write("\n\n");
        }
    }
}

