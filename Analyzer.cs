///////////////////////////////////////////////////////////////////////
// Analyzer.cs - Manages Code Analysis                               //
// ver 1.1                                                           //
// Language:    C#, 2013, .Net Framework 4.5                         //
// Platform:    Lenovo Y40, Win8.1                                   //
// Application: Dependency Analyzer for CSE681, Project #4, Fall 2014 //
// Source:      Prof. Fawcett                                        //
//                                                                   //
// Author:      Navneeth Ramprasad (SUID:989480419)                  //
//              Syracuse University,    narampra@syr.edu             //
///////////////////////////////////////////////////////////////////////
/*
 * Module Operations:
 * ------------------
 * This module defines the following class:
 *   Analyzer  - It manages code Analysis
 */
/* Required Files:
 *   Parser.cs, IRulesAndActions.cs, RulesAndActions.cs, Parser.cs, Semi.cs, Toker.cs
 *   
 *   
 * Maintenance History:
 * --------------------
 * v.1.1- Added functionality to do package dependencies using doPackageDependency function
 * v.1.0- first release
 */
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Threading.Tasks;
namespace CodeAnalysis
{
    public class ElemDependence
    {
        public string package1;
        public string package2;
    }
    public class PackageDependence
    {
        public static List<ElemDependence> dependenceTable= new List<ElemDependence>();
    }
    /// ///////////////////////////////////
    //Analyses the input files
    public class Analyzer
    {
        //----------------<gets files for test stub>------------------------
        private static string[] getFiles(string path, List<string> patterns)
        {
            List<string> files = new List<string>();
            foreach (string pattern in patterns)
            {
                string[] newFiles = Directory.GetFiles(path, pattern);
                for (int i = 0; i < newFiles.Length; ++i)
                    newFiles[i] = Path.GetFullPath(newFiles[i]);

                files.AddRange(newFiles);
            }
            return files.ToArray();
        }

        //----------<parse1 starts here, it identifies all the types>-----------------
        public void doAnalysis(string[] files)
        {

            foreach (object file in files)
            {

                string filename = Convert.ToString(file);

                CSsemi.CSemiExp semi = new CSsemi.CSemiExp();
                semi.displayNewLines = false;
                if (!semi.open(file as string))
                {
                    Console.Write("\n  Can't open {0}\n\n", file);
                    return;
                }


                BuildCodeAnalyzer builder = new BuildCodeAnalyzer(semi);
                Parser parser = builder.build();

                try
                {
                    while (semi.getSemi())
                        parser.parse(semi, filename);
                }
                catch (Exception ex)
                {
                    Console.Write("\n\n  {0}\n", ex.Message);
                }
                Repository rep = Repository.getInstance();
                List<Elem> table = rep.locations;           //storing the repository data into a List

                semi.close();
            }
        }

        //---------<parse2 starts here, it identifies relationships betn all the types>------------
        public void doAnalysisRelationship(string[] files)
        {

            foreach (object file in files)
            {
                string filename = Convert.ToString(file);
                CSsemi.CSemiExp semi = new CSsemi.CSemiExp();
                semi.displayNewLines = false;
                if (!semi.open(file as string))
                {
                    Console.Write("\n  Can't open {0}\n\n", file);
                    return;
                }

                BuildCodeAnalyzerRelation builderForRelationship = new BuildCodeAnalyzerRelation(semi);
                Parser parser = builderForRelationship.build();

                try
                {
                    while (semi.getSemi())
                        parser.parse(semi, filename);
                }
                catch (Exception ex)
                {
                    Console.Write("\n\n  {0}\n", ex.Message);
                }

                Repository rep = Repository.getInstance();
                List<Elem> table = rep.locations;

                semi.close();
            }
        }

        public void doPackageDependency(string[] files)
        {   
            RelationshipRepository table=new RelationshipRepository();
            foreach (ElementRelation e in table.relationshipStorage)
            {
               if(!PackageDependence.dependenceTable.Exists(x=>x.package1==e.fromPackage&&x.package2==e.toPackage)&&e.fromPackage!=e.toPackage)
                {
                   ElemDependence elem = new ElemDependence();
                   elem.package1 = e.fromPackage;
                   elem.package2 = e.toPackage;
                   PackageDependence.dependenceTable.Add(elem);
                }
            }
            
        }

        // ----------------------<Test Stub for Analyzer>---------------------
#if(TEST_ANALYZER)

        static void Main(string[] args)
        {
            string path = "../../";
            string[] arg = { "../../", "*.cs" };
            List<string> patterns = new List<string>();
            patterns.Add("*.cs");
            List<string> options = new List<string>();
            string[] files = getFiles(path, patterns);
            Analyzer analyzer = new Analyzer();
            analyzer.doAnalysis(files);
            analyzer.doAnalysisRelationship(files);
            List<Elem> outputList = OutputRepository.output_;
            foreach (object f in files)
            {
                string filename = Convert.ToString(f);
                Console.Write("      Type                     Name           Begin          End ");
                Console.Write("\n-----------------------------------------------------------------");
                foreach (Elem e in outputList)
                {
                    Console.Write("\n  {0,10}, {1,25}, {2,5}, {3,5}", e.type, e.name, e.begin, e.end);
                }
                Console.WriteLine();
                Console.Write("\n\n  That's all folks!\n\n");

            }
            List<ElementRelation> table = RelationshipRepository.relationship_;
            foreach (object f in files)
            {
                string filename = Convert.ToString(f);
                Console.Write("         Relation                 From                        To");
                Console.Write("\n-----------------------------------------------------------------");

                foreach (ElementRelation e in table)
                {
                    Console.Write("\n  {0,10}, {1,25}, {2,5}", e.relationType, e.fromClass, e.toClass);
                }
                Console.WriteLine();
                Console.Write("\n\n \n\n");
            }
        }
#endif

    }
}

