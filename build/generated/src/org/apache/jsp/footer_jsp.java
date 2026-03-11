package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class footer_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
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
      out.write("  <script>\n");
      out.write("  // Wait for the document to be fully loaded\n");
      out.write("  document.addEventListener('DOMContentLoaded', function () {\n");
      out.write("    // Get all submenu toggle links\n");
      out.write("    var submenuToggles = document.querySelectorAll('.submenu-toggle');\n");
      out.write("\n");
      out.write("    // Loop through each toggle link\n");
      out.write("    submenuToggles.forEach(function (toggle) {\n");
      out.write("      // Add click event listener to each toggle link\n");
      out.write("      toggle.addEventListener('click', function (e) {\n");
      out.write("        e.preventDefault(); // Prevent default link behavior\n");
      out.write("        var submenu = this.nextElementSibling; // Get the next sibling ul (submenu)\n");
      out.write("        \n");
      out.write("        // Toggle the submenu visibility\n");
      out.write("        if (submenu.style.display === 'block') {\n");
      out.write("          submenu.style.display = 'none';\n");
      out.write("        } else {\n");
      out.write("          submenu.style.display = 'block';\n");
      out.write("        }\n");
      out.write("      });\n");
      out.write("    });\n");
      out.write("  });\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js\"></script>\n");
      out.write("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>\n");
      out.write("    <script src=\"//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js\"></script>\n");
      out.write("    <script type=\"text/javascript\" src=\"libs/js/functions.js\"></script>");
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
