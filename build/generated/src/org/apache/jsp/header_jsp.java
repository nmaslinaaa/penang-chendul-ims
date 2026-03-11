package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.Inventory.model.User;

public final class header_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');
      out.write("\n");
      out.write("<header id=\"header\">\n");
      out.write("    <div class=\"logo pull-left\"> Inventory System</div>\n");
      out.write("    <div class=\"header-content\">\n");
      out.write("        <div class=\"header-date pull-left\">\n");
      out.write("            <strong>");
      out.print( new java.text.SimpleDateFormat("MMMM d, yyyy, h:mm a").format(new java.util.Date()) );
      out.write("</strong>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"pull-right clearfix\">\n");
      out.write("            <ul class=\"info-menu list-inline list-unstyled\">\n");
      out.write("                <li class=\"profile\">\n");
      out.write("                    <a href=\"#\" data-toggle=\"dropdown\" class=\"toggle\" aria-expanded=\"false\">\n");
      out.write("                        <img src=\"logouser.png\" alt=\"user-image\" class=\"img-circle img-inline\">\n");
      out.write("                        <span>");
      out.print( session.getAttribute("user") != null ? ((User) session.getAttribute("user")).getName() : "Guest" );
      out.write(" <i class=\"caret\"></i></span>\n");
      out.write("                    </a>\n");
      out.write("                    <ul class=\"dropdown-menu\">\n");
      out.write("                        <li>\n");
      out.write("                            <a href=\"edit_account\" title=\"edit account\">\n");
      out.write("                                <i class=\"glyphicon glyphicon-user\"></i>\n");
      out.write("                                Profile\n");
      out.write("                            </a>\n");
      out.write("                        </li>\n");
      out.write("                        <li class=\"last\">\n");
      out.write("                            <a href=\"LogoutServlet\">\n");
      out.write("                                <i class=\"glyphicon glyphicon-off\"></i>\n");
      out.write("                                Logout\n");
      out.write("                            </a>\n");
      out.write("                        </li>\n");
      out.write("                    </ul>\n");
      out.write("                </li>\n");
      out.write("            </ul>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("</header>\n");
      out.write("\n");
      out.write("<div class=\"sidebar\">\n");
      out.write("    <c:if test=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${sessionScope.user_level == 1}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\">\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "admin_menu.jsp", out, false);
      out.write("\n");
      out.write("    </c:if>\n");
      out.write(" <c:if test=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${sessionScope.user_level == 2}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\">\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "special_menu.jsp", out, false);
      out.write("\n");
      out.write("    </c:if>\n");
      out.write("</div>\n");
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
