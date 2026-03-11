package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class special_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<ul>\n");
      out.write("  <li>\n");
      out.write("    <a href=\"home.jsp\">\n");
      out.write("      <i class=\"glyphicon glyphicon-home\"></i>\n");
      out.write("      <span>Dashboard</span>\n");
      out.write("    </a>\n");
      out.write("  </li>\n");
      out.write("<!--    <li>\n");
      out.write("    <a href=\"#\" class=\"submenu-toggle\">\n");
      out.write("      <i class=\"glyphicon glyphicon-user\"></i>\n");
      out.write("      <span>User Management</span>\n");
      out.write("    </a>\n");
      out.write("    <ul class=\"nav submenu\">\n");
      out.write("      <li><a href=\"users.jsp\">Manage Users</a> </li>\n");
      out.write("    </ul>\n");
      out.write("  </li>-->\n");
      out.write("<!--   <li>\n");
      out.write("    <a href=\"supplier.jsp\">\n");
      out.write("      <i class=\"glyphicon glyphicon-indent-left\"></i>\n");
      out.write("      <span>Suppliers</span>\n");
      out.write("    </a>\n");
      out.write("  </li> -->\n");
      out.write("  <li>\n");
      out.write("    <a href=\"#\" class=\"submenu-toggle\">\n");
      out.write("      <i class=\"glyphicon glyphicon-th-large\"></i>\n");
      out.write("      <span>Items</span>\n");
      out.write("    </a>\n");
      out.write("     <ul class=\"nav submenu\">\n");
      out.write("      <li><a href=\"item\">Manage Items</a> </li>\n");
      out.write("      <li><a href=\"add_item\">Add Items</a> </li>\n");
      out.write("      <li><a href=\"view_item\">View Items</a> </li>\n");
      out.write("    </ul>\n");
      out.write("  </li>\n");
      out.write("  <li>\n");
      out.write("     <a href=\"#\" class=\"submenu-toggle\">\n");
      out.write("         <i class=\"glyphicon glyphicon-open\"></i>\n");
      out.write("         <span>Outgoing Item</span>\n");
      out.write("     </a>\n");
      out.write("\n");
      out.write("    <ul class=\"nav submenu\">\n");
      out.write("      <li><a href=\"update_remain_quantity\">Update Remaining Quantity</a> </li>\n");
      out.write("       <li><a href=\"view_item_used\">View Items-Used</a> </li>\n");
      out.write("    </ul>\n");
      out.write("  </li>\n");
      out.write("  <li>\n");
      out.write("    <a href=\"#\" class=\"submenu-toggle\">\n");
      out.write("      <i class=\"glyphicon glyphicon-credit-card\"></i>\n");
      out.write("      <span>Orders</span>\n");
      out.write("    </a>\n");
      out.write("    <ul class=\"nav submenu\">\n");
      out.write("      <li><a href=\"orders.jsp\">Manage Orders</a> </li>\n");
      out.write("      <li><a href=\"add_order\">Add Order</a> </li>\n");
      out.write("      \n");
      out.write("    </ul>\n");
      out.write("  </li>\n");
      out.write("   <li>\n");
      out.write("    <a href=\"#\" class=\"submenu-toggle\">\n");
      out.write("      <i class=\"glyphicon glyphicon-duplicate\"></i>\n");
      out.write("      <span>View Orders</span>\n");
      out.write("    </a>\n");
      out.write("    <ul class=\"nav submenu\">\n");
      out.write("      <li><a href=\"orders_report.jsp\">Orders by dates </a></li>\n");
      out.write("      <li><a href=\"monthly_orders\">Monthly orders</a></li>\n");
      out.write("      <li><a href=\"daily_orders\">Daily orders</a> </li>\n");
      out.write("    </ul>\n");
      out.write("  </li>\n");
      out.write("</ul>");
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
