/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.22
 * Generated at: 2014-04-29 19:21:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.AllFiles.JSP.DataOwner;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dataownerhome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("<link href='http://fonts.googleapis.com/css?family=Droid+Sans'\r\n");
      out.write("\trel='stylesheet' type='text/css'>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<title>Home Page</title>\r\n");
      out.write("<link href=\"");
      out.print(request.getContextPath());
      out.write("/AllFiles/CSS/ownerhome_style.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link href=\"");
      out.print(request.getContextPath());
      out.write("/iconic.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/AllFiles/JS/prefix-free.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body background=\"\">\r\n");
      out.write("<IMG SRC=\"images/Top.jpg\" width=100% BORDER=\"0\" ALT=\"\" >\r\n");
      out.write("<div class=\"wrap\"><nav>\r\n");
      out.write("<ul class=\"menu\">\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/OwnerProfile\" target=\"home\"><span class=\"iconic home\" ></span> View Profile</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/ListUser?submit=get\" target=\"home\"><span class=\"iconic plus-alt\"></span> User Details</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/ViewRequest\" target=\"home\"><span class=\"iconic magnifying-glass\"></span>View Request</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/AllFiles/JSP/Admin/upload_file.jsp\" target=\"home\"><span class=\"iconic map-pin\"></span> File Upload</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/AllFiles/JSP/Admin/access_control.jsp\" target=\"home\"><span class=\"iconic mail\"></span> File Access Control</a></li>\r\n");
      out.write("\r\n");
      out.write("<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/UploadFileList?submit=get\" target=\"home\"><span class=\"iconic mail\"></span> Transactions</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.print(request.getContextPath());
      out.write("/Logout\"><span class=\"iconic mail\"></span> Logout</a></li>\r\n");
      out.write("</ul>\r\n");
      out.write("<div class=\"clearfix\"></div>\r\n");
      out.write("</nav></div>\r\n");
      out.write("\r\n");
      out.write("<div style=\"position: absolute;left: 250px;width: 750px;height: 500px;top: 150px;\">\r\n");
      out.write("\r\n");
      out.write("<iframe style=\"position: absolute;width: 750px;height: 500px;border-color: red;border-left-width: 5px;\" name=\"home\"></iframe>\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}