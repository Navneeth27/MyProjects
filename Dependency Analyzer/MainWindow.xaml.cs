using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using MT2Q2;
using CodeAnalysis;
using System.Threading;
using System.Xml.Linq;
using System.ServiceModel;


namespace WPF_GUI
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            OnNewMessage += new NewMessage(OnNewMessageHandler);
            Title = "Client GUI";
        }

        Thread rcvThrd ;
        delegate void NewMessage(ClientMsg msg);
        event NewMessage OnNewMessage;
        MsgClient client = new MsgClient();
        ServiceHost host ;


        void ThreadProc()
        {
            ClientMsg msg2 = new ClientMsg();
            MsgService msgsvc = new MsgService();
            try
            {
                while (true)
                {

                    msg2 = msgsvc.GetMessageCS();    // get message out of receive queue - will block if queue is empty
                    if (msg2.body!=null)
                    { 
                        this.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Normal, OnNewMessage, msg2);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("A handled exception just occurred: " + ex.Message, "Exception Sample", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }

        //----< called by UI thread when dispatched from rcvThrd >-------

        void OnNewMessageHandler(ClientMsg msg2)
        {
            if (msg2.cmd.ToString() == "ProjectList")
            {
                listBox1.Items.Insert(0, "Received message ProjectList from Server:");
                XDocument doc = new XDocument();
                doc = XDocument.Parse(msg2.body);
                List<string> files = new List<string>();
                var q = from x in doc.Root.Elements("filename")
                        select x;
                foreach (string x in q)
                    files.Add(x.ToString());
                string[] fileList = files.ToArray();
                for (int i = 0; i < fileList.Length ; i++)
                    listBox1.Items.Insert(i + 1, fileList[i]);
               
            }
            if (msg2.cmd.ToString() == "DependencyAnalysis")
            {
                listBox1.Items.Clear();
                string name = msg2.body;
                List<string> names = name.Split('!').ToList();
                int i =0;
                if (Types.IsChecked.Value)
                {
                    listBox1.Items.Insert(i, "Type Dependency Analysis");
                    List<string> name1 = names[0].Split(',').ToList();
                    foreach (string str in name1)
                    {
                        listBox1.Items.Insert(i, str);
                        i++;
                    }
                }
                if (Packages.IsChecked.Value)
                {
                    listBox1.Items.Insert(i, "Package Dependencies");
                    List<string> name2 = names[1].Split(',').ToList();
                    foreach (string str in name2)
                    {
                        listBox1.Items.Insert(i, str);
                        i++;
                    }
                }
                if(!(Types.IsChecked.Value||Packages.IsChecked.Value))
                {
                    listBox1.Items.Insert(i,"no option selected");
                }
            }
        }

        private void ListenButton_Click(object sender, RoutedEventArgs e)
        {

            string local = TextBox1.Text;
            try
            {
                host= MsgClient.CreateServiceChannel("http://localhost:" + local + "/MessageService");
                host.Open();
                rcvThrd = new Thread(new ThreadStart(this.ThreadProc));
                rcvThrd.IsBackground = true;
                rcvThrd.Start();
                ConnectButton.IsEnabled = true;
                ListenButton.IsEnabled = false;
            }
            catch (Exception exception)
            {
                MessageBox.Show("{0}", exception.Message);
            }
        }

        private void ConnectButton_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                IMessageService proxy = MsgClient.CreateClientChannel("http://localhost:8080/MessageService");
                SendButton.IsEnabled = true;
                ConnectButton.IsEnabled = false;
            }
            catch (Exception exception)
            {
                MessageBox.Show("{0}", exception.Message);
            }
        }

        private void SendMessage_Click(object sender, RoutedEventArgs e)
        {
            string local = TextBox1.Text;

                string src = "http://localhost:" + local + "/MessageService";
           try
           {
                client.SendMessageCS(ClientMsg.Command.ProjectList, new Uri(src), new Uri("http://localhost:8080/MessageService"), "message");
                
                SendButton.IsEnabled = false;

            }
            catch (Exception ex)
            {
                Window temp = new Window();
                StringBuilder msg = new StringBuilder(ex.Message);
                msg.Append("\nport = ");
                msg.Append(local.ToString());
                temp.Content = msg.ToString();
                temp.Height = 100;
                temp.Width = 500;
                temp.Show();
            }
        }

        private void Analysis_Click(object sender, RoutedEventArgs e)
        {
            string local = TextBox1.Text;
            try
            {
                string src = "http://localhost:" + local + "/MessageService";
                List<string> selectedList = new List<string>();
                foreach (object item in listBox1.SelectedItems)
                {
                    selectedList.Add(item.ToString());     
                }
                string xml = ProjectsToXML(selectedList);
                client.SendMessageCS(ClientMsg.Command.DependencyAnalysis, new Uri(src), new Uri("http://localhost:8080/MessageService"), xml);
                
            }
            catch (Exception ex)
            {
                Window temp = new Window();
                StringBuilder msg = new StringBuilder(ex.Message);
                msg.Append("\nport = ");
                msg.Append(local.ToString());
                temp.Content = msg.ToString();
                temp.Height = 100;
                temp.Width = 500;
                temp.Show();
            }
        }
        private void Window_Unloaded(object sender, RoutedEventArgs e)
        {
           
            MessageBox.Show("Session Ended");
            host.Close();
        }
        
        private void listBox1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

            MessageBox.Show("Selected \n Select more files if needed");
            AnalysisButton.IsEnabled = true;
            
        }
        public static string ProjectsToXML(List<string> files)
        {
            XDocument xml = new XDocument();
            xml.Declaration = new XDeclaration("1.0", "utf-8", "yes");
            XElement root = new XElement("files");
            xml.Add(root);
            foreach (string str in files)
            {
                XElement file = new XElement("filename", str);
                root.Add(file);
            }
            return xml.ToString();
        }

    }
}
