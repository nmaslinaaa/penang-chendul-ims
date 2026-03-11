package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.Inventory.utill.SessionUtil;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Login</title>\n");
      out.write("    <style>\n");
      out.write("        body {\n");
      out.write("            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n");
      out.write("            font-weight: 400;\n");
      out.write("            overflow-x: hidden;\n");
      out.write("            overflow-y: auto;\n");
      out.write("            height: 100%;\n");
      out.write("            width: 100%;\n");
      out.write("            background: url('login.jpeg') no-repeat center center fixed;\n");
      out.write("            background-size: cover;\n");
      out.write("        }\n");
      out.write("        .container-fluid {\n");
      out.write("            padding-right: 15px;\n");
      out.write("            padding-left: 15px;\n");
      out.write("            margin-right: auto;\n");
      out.write("            margin-left: auto;\n");
      out.write("        }\n");
      out.write("        .login-page {\n");
      out.write("            width: 350px;\n");
      out.write("            margin: 2% auto;\n");
      out.write("            padding: 20px;\n");
      out.write("            background-color: rgb(188 188 188 / 89%);\n");
      out.write("            border: 2px solid #06973b;\n");
      out.write("            border-radius: 10px;\n");
      out.write("        }\n");
      out.write("        .login-page .text-center {\n");
      out.write("            margin-bottom: 10px;\n");
      out.write("        }\n");
      out.write("        .text-center {\n");
      out.write("            text-align: center;\n");
      out.write("        }\n");
      out.write("        form {\n");
      out.write("            display: block;\n");
      out.write("            margin-top: 0em;\n");
      out.write("            unicode-bidi: isolate;\n");
      out.write("        }\n");
      out.write("        .form-group {\n");
      out.write("            margin-bottom: 15px;\n");
      out.write("        }\n");
      out.write("        .btn-danger {\n");
      out.write("            background-color: #ed5153;\n");
      out.write("            color: #fff;\n");
      out.write("            border-color: #d43f3a;\n");
      out.write("        }\n");
      out.write("        .btn {\n");
      out.write("            display: inline-block;\n");
      out.write("            padding: 6px 12px;\n");
      out.write("            margin-bottom: 0;\n");
      out.write("            font-size: 14px;\n");
      out.write("            font-weight: 400;\n");
      out.write("            line-height: 1.42857143;\n");
      out.write("            text-align: center;\n");
      out.write("            white-space: nowrap;\n");
      out.write("            vertical-align: middle;\n");
      out.write("            -ms-touch-action: manipulation;\n");
      out.write("            touch-action: manipulation;\n");
      out.write("            cursor: pointer;\n");
      out.write("            -webkit-user-select: none;\n");
      out.write("            -moz-user-select: none;\n");
      out.write("            -ms-user-select: none;\n");
      out.write("            user-select: none;\n");
      out.write("            background-image: none;\n");
      out.write("            border: 1px solid transparent;\n");
      out.write("            border-radius: 4px;\n");
      out.write("        }\n");
      out.write("        .form-control {\n");
      out.write("            display: block;\n");
      out.write("            width: 100%;\n");
      out.write("            height: 25px;\n");
      out.write("            padding: 6px 2px;\n");
      out.write("            font-size: 14px;\n");
      out.write("            line-height: 1.42857143;\n");
      out.write("            background-color: #fff;\n");
      out.write("            background-image: none;\n");
      out.write("            color: #646464;\n");
      out.write("            border: 1px solid #e6e6e6;\n");
      out.write("            border-radius: 3px;\n");
      out.write("        }\n");
      out.write("        .alert {\n");
      out.write("            margin-bottom: 20px;\n");
      out.write("            padding: 15px;\n");
      out.write("            border: 1px solid transparent;\n");
      out.write("            border-radius: 4px;\n");
      out.write("        }\n");
      out.write("        .alert-danger {\n");
      out.write("            color: #a94442;\n");
      out.write("            background-color: #f2dede;\n");
      out.write("            border-color: #ebccd1;\n");
      out.write("        }\n");
      out.write("        div.loginHeader{\n");
      out.write("\ttext-align: center;\n");
      out.write("\tmargin-bottom: 50px;\n");
      out.write("\tmargin-top: 20px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("        div.loginHeader h1{\n");
      out.write("\tfont-size: 80px;\n");
      out.write("\tcolor:lightgreen;\n");
      out.write("\tpadding: 0px;\n");
      out.write("\tmargin: 0px;\n");
      out.write("\ttext-shadow: 3px 3px 6px black;\n");
      out.write("}\n");
      out.write("    </style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <div class=\"loginHeader\">\n");
      out.write("\t\t\t<h1>PENANG CHENDUL</h1>\n");
      out.write("    </div>\n");
      out.write("    \n");
      out.write("    <div class=\"login-page\">\n");
      out.write("        <div class=\"text-center\">\n");
      out.write("            <h1>Login Panel</h1>\n");
      out.write("            <h4>Inventory Management System</h4>\n");
      out.write("        </div>\n");
      out.write("        ");

            String errorMessage = SessionUtil.getErrorMessage(request);
            if (errorMessage != null) {
                SessionUtil.clearErrorMessage(request);
        
      out.write("\n");
      out.write("            <div class=\"alert alert-danger\">\n");
      out.write("                ");
      out.print( errorMessage );
      out.write("\n");
      out.write("            </div>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        <form method=\"post\" action=\"LoginServlet\">\n");
      out.write("            <div class=\"form-group\">\n");
      out.write("                <label for=\"username\" class=\"control-label\">Username</label>\n");
      out.write("                <input type=\"name\" class=\"form-control\" name=\"username\" placeholder=\"Username\" required>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"form-group\">\n");
      out.write("                <label for=\"Password\" class=\"control-label\">Password (eg; Demo1006@)</label>\n");
      out.write("                <input type=\"password\"  name=\"password\" class=\"form-control\" placeholder=\"Password\" required>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"form-group\">\n");
      out.write("                <button type=\"submit\" class=\"btn btn-danger\" style=\"border-radius:0%\">Login</button>\n");
      out.write("            </div>\n");
      out.write("        </form>\n");
      out.write("    </div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
