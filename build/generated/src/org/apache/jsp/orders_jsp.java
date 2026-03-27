package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.SQLException;
import com.Inventory.DAO.OrderDAO;
import com.Inventory.DAO.OrderItemDAO;
import com.Inventory.DAO.ItemDAO;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import com.Inventory.model.OrderItem;
import com.Inventory.model.User;
import com.Inventory.utill.PageAccessUtil;
import com.Inventory.utill.SessionUtil;
import com.Inventory.model.User;

public final class orders_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/header.jsp");
    _jspx_dependants.add("/footer.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_if_test.release();
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

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    User user = SessionUtil.getCurrentUser(request);
    if (user == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }

    // Check user level and redirect if not authorized
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

    String message = (String) session.getAttribute("message");
    String alertClass = (String) session.getAttribute("alertClass");

    if (message != null && alertClass != null) {
        session.removeAttribute("message");
        session.removeAttribute("alertClass");
    }

    List<OrderItem> orderItemsList = null;

    String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
    String dbUser = "root";
    String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";
    Connection connection = null;

    try {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(url, dbUser, password);

        // Load all order items
        orderItemsList = OrderItemDAO.listAllOrderItems(connection);
    } catch (Exception e) {
        e.printStackTrace();
        message = "An error occurred: " + e.getMessage();
        alertClass = "alert-danger";
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    request.setAttribute("message", message);
    request.setAttribute("alertClass", alertClass);

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("        <title>All Orders</title>\r\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" />\r\n");
      out.write("        <link rel=\"stylesheet\" href=\"style.css\" />\r\n");
      out.write("        <script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        ");
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
      out.write("    ");
      if (_jspx_meth_c_if_0(_jspx_page_context))
        return;
      out.write('\n');
      out.write(' ');
      if (_jspx_meth_c_if_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("</div>\n");
      out.write("\r\n");
      out.write("        <div class=\"pageuser\">\r\n");
      out.write("            <div class=\"row\">\r\n");
      out.write("                <div class=\"col-md-6\">\r\n");
      out.write("                    <div id=\"alert-container\">\r\n");
      out.write("                        ");
      if (_jspx_meth_c_if_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"row\">\r\n");
      out.write("                <div class=\"col-md-12\">\r\n");
      out.write("                    <div class=\"panel panel-default\">\r\n");
      out.write("                        <div class=\"panel-heading clearfix\">\r\n");
      out.write("                            <strong>\r\n");
      out.write("                                <span class=\"glyphicon glyphicon-th\"></span>\r\n");
      out.write("                                <span>All Orders</span>\r\n");
      out.write("                            </strong>\r\n");
      out.write("                            <div class=\"pull-right\">\r\n");
      out.write("                                <a href=\"add_order\" class=\"btn btn-primary\">Add Order</a>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"panel-body\">\r\n");
      out.write("                            <table class=\"table table-bordered table-striped\">\r\n");
      out.write("                                <thead>\r\n");
      out.write("                                    <tr>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 7%;\">Order ID</th>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 7%;\">#</th>\r\n");
      out.write("                                        <th>Item Name</th>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 15%;\">Quantity</th>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 15%;\">Date</th>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 15%;\">Status</th>\r\n");
      out.write("                                        <th class=\"text-center\" style=\"width: 80px;\">Actions</th>\r\n");
      out.write("                                    </tr>\r\n");
      out.write("                                </thead>\r\n");
      out.write("                                <tbody>\r\n");
      out.write("                                    ");

                                        String currentOrderID = "";
                                        int itemCount = 0;
                                        for (OrderItem orderItem : orderItemsList) {
                                            if (!currentOrderID.equals(String.valueOf(orderItem.getOrderId()))) {
                                                currentOrderID = String.valueOf(orderItem.getOrderId());
                                                itemCount = 1; // Reset item count for new order
                                            } else {
                                                itemCount++; // Increment item count for the same order
                                            }
                                    
      out.write("\r\n");
      out.write("                                    <tr>\r\n");
      out.write("                                        <td class=\"text-center\">");
      out.print( (itemCount == 1) ? "OR00" + orderItem.getOrderId() : "" );
      out.write("</td>\r\n");
      out.write("                                        <td class=\"text-center\">");
      out.print( itemCount );
      out.write("</td>\r\n");
      out.write("                                        <td>");
      out.print( orderItem.getItemName() );
      out.write("</td>\r\n");
      out.write("                                        <td class=\"text-center\">");
      out.print( orderItem.getOrderQty() );
      out.write("</td>\r\n");
      out.write("                                        <td class=\"text-center\">");
      out.print( orderItem.getOrderDate() );
      out.write("</td>\r\n");
      out.write("                                        <td class=\"text-center\">\r\n");
      out.write("                                            <form method=\"post\" class=\"status-form\">\r\n");
      out.write("                                                <input type=\"hidden\" name=\"orderitemID\" value=\"");
      out.print( orderItem.getOrderitemId() );
      out.write("\">\r\n");
      out.write("                                                <select name=\"status\" class=\"form-control\" onchange=\"submitStatusForm(this)\">\r\n");
      out.write("                                                    <option value=\"Incomplete\" ");
      out.print( "Incomplete".equals(orderItem.getOrderStatus()) ? "selected" : "" );
      out.write(">Incomplete</option>\r\n");
      out.write("                                                    <option value=\"Complete\" ");
      out.print( "Complete".equals(orderItem.getOrderStatus()) ? "selected" : "" );
      out.write(">Complete</option>\r\n");
      out.write("                                                </select>\r\n");
      out.write("                                                <input type=\"hidden\" name=\"update_status\" value=\"1\">\r\n");
      out.write("                                            </form>\r\n");
      out.write("                                        </td>\r\n");
      out.write("                                        <td class=\"text-center\">\r\n");
      out.write("                                            <button type=\"button\" class=\"btn btn-danger btn-xs\" title=\"Delete\" data-toggle=\"tooltip\" onclick=\"confirmDelete(");
      out.print( orderItem.getOrderitemId() );
      out.write(")\">\r\n");
      out.write("                                                <span class=\"glyphicon glyphicon-trash\"></span>\r\n");
      out.write("                                            </button>\r\n");
      out.write("                                        </td>\r\n");
      out.write("                                    </tr>\r\n");
      out.write("                                    ");
 } 
      out.write("\r\n");
      out.write("                                </tbody>\r\n");
      out.write("                            </table>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <!-- Delete Confirmation Modal -->\r\n");
      out.write("        <div class=\"modal fade\" id=\"deleteModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"deleteModalLabel\">\r\n");
      out.write("            <div class=\"modal-dialog\" role=\"document\">\r\n");
      out.write("                <div class=\"modal-content\">\r\n");
      out.write("                    <div class=\"modal-header\">\r\n");
      out.write("                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\r\n");
      out.write("                        <h4 class=\"modal-title\" id=\"deleteModalLabel\">Confirm Delete</h4>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"modal-body\">\r\n");
      out.write("                        Are you sure you want to delete this order item? You will lose all the details associated with this item.\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"modal-footer\">\r\n");
      out.write("                        <form method=\"post\" action=\"deleteOrderItem\">\r\n");
      out.write("                            <input type=\"hidden\" name=\"orderitemID\" id=\"orderitemID\" value=\"\">\r\n");
      out.write("                            <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Cancel</button>\r\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-danger\">Yes</button>\r\n");
      out.write("                        </form>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <script>\r\n");
      out.write("            function submitStatusForm(selectElement) {\r\n");
      out.write("                var form = selectElement.form;\r\n");
      out.write("                $.ajax({\r\n");
      out.write("                    type: form.method,\r\n");
      out.write("                    url: 'updateOrderStatus',\r\n");
      out.write("                    data: $(form).serialize(),\r\n");
      out.write("                    success: function(response) {\r\n");
      out.write("                        var alertMessage = response.message;\r\n");
      out.write("                        var alertClass = response.alertClass;\r\n");
      out.write("                        $('#alert-container').html('<div class=\"alert ' + alertClass + ' alert-dismissible\" role=\"alert\">' +\r\n");
      out.write("                            '<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>' +\r\n");
      out.write("                            alertMessage + '</div>');\r\n");
      out.write("                    },\r\n");
      out.write("                    error: function() {\r\n");
      out.write("                        $('#alert-container').html('<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">' +\r\n");
      out.write("                            '<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>' +\r\n");
      out.write("                            'Failed to update status. Please try again.</div>');\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function confirmDelete(orderitemID) {\r\n");
      out.write("                $('#orderitemID').val(orderitemID);\r\n");
      out.write("                $('#deleteModal').modal('show');\r\n");
      out.write("            }\r\n");
      out.write("        </script>\r\n");
      out.write("        ");
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
      out.write("\r\n");
      out.write("    </body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
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

  private boolean _jspx_meth_c_if_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
    HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_0.setPageContext(_jspx_page_context);
    _jspx_th_c_if_0.setParent(null);
    _jspx_th_c_if_0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${sessionScope.user_level == 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null)).booleanValue());
    int _jspx_eval_c_if_0 = _jspx_th_c_if_0.doStartTag();
    if (_jspx_eval_c_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("        ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "admin_menu.jsp", out, false);
        out.write("\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_if_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
      return true;
    }
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
    return false;
  }

  private boolean _jspx_meth_c_if_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
    HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_1.setPageContext(_jspx_page_context);
    _jspx_th_c_if_1.setParent(null);
    _jspx_th_c_if_1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${sessionScope.user_level == 2}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null)).booleanValue());
    int _jspx_eval_c_if_1 = _jspx_th_c_if_1.doStartTag();
    if (_jspx_eval_c_if_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("        ");
        org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "special_menu.jsp", out, false);
        out.write("\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_if_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_1);
      return true;
    }
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_1);
    return false;
  }

  private boolean _jspx_meth_c_if_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_2.setPageContext(_jspx_page_context);
    _jspx_th_c_if_2.setParent(null);
    _jspx_th_c_if_2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${not empty message}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null)).booleanValue());
    int _jspx_eval_c_if_2 = _jspx_th_c_if_2.doStartTag();
    if (_jspx_eval_c_if_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("                            <div class=\"alert ");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${alertClass}", java.lang.String.class, (PageContext)_jspx_page_context, null));
        out.write(" alert-dismissible\" role=\"alert\">\r\n");
        out.write("                                <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\r\n");
        out.write("                                ");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${message}", java.lang.String.class, (PageContext)_jspx_page_context, null));
        out.write("\r\n");
        out.write("                            </div>\r\n");
        out.write("                        ");
        int evalDoAfterBody = _jspx_th_c_if_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_2);
      return true;
    }
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_2);
    return false;
  }
}
