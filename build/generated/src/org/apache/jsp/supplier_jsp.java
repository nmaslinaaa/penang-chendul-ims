package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.Inventory.model.Supplier;
import com.Inventory.model.User;
import com.Inventory.utill.SessionUtil;
import com.Inventory.utill.PageAccessUtil;
import com.Inventory.model.User;

public final class supplier_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/header.jsp");
    _jspx_dependants.add("/footer.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_forEach_varStatus_var_items;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_forEach_varStatus_var_items = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_forEach_varStatus_var_items.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    User user = SessionUtil.getCurrentUser(request);
    if (user == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }

      out.write('\n');

    // Check user level and redirect if not authorized
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Supplier</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" />\n");
      out.write("        <link rel=\"stylesheet\" href=\"//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css\" />\n");
      out.write("        <link rel=\"stylesheet\" href=\"style.css\" />\n");
      out.write("        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
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
      out.write("\n");
      out.write("        <div class=\"supplier\">\n");
      out.write("               <div class=\"page\">\n");
      out.write("            <div class=\"container\">\n");
      out.write("                <div class=\"rowsuppplier\">\n");
      out.write("                    <div class=\"col-md-5\">\n");
      out.write("                        ");
      if (_jspx_meth_c_if_2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("                        <!-- Add New Supplier panel -->\n");
      out.write("                        <div class=\"supplier\">\n");
      out.write("                            <div class=\"panel panel-default\">\n");
      out.write("                            <div class=\"panel-heading\">\n");
      out.write("                                <strong>\n");
      out.write("                                    <span class=\"glyphicon glyphicon-th\"></span>\n");
      out.write("                                    <span>Add New Supplier</span>\n");
      out.write("                                </strong>\n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"panel-body\">\n");
      out.write("                                <form method=\"post\" action=\"supplier\">\n");
      out.write("                                    <div class=\"form-group\">\n");
      out.write("                                        <input type=\"hidden\" name=\"action\" value=\"add\">\n");
      out.write("                                        <input type=\"text\" class=\"form-control\" name=\"supplier-name\" placeholder=\"Supplier Name\" required><br>\n");
      out.write("                                        <input type=\"email\" class=\"form-control\" name=\"supplier-email\" placeholder=\"Email\" required><br>\n");
      out.write("                                        <input type=\"text\" class=\"form-control\" name=\"supplier-phone\" placeholder=\"Phone Number\" required>\n");
      out.write("                                    </div>\n");
      out.write("                                    <button type=\"submit\" name=\"add_supplier\" class=\"btn btn-primary\">Add Supplier</button>\n");
      out.write("                                </form>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                        </div>\n");
      out.write("                        \n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"supplier2\">\n");
      out.write("                        <div class=\"col-md-7\">\n");
      out.write("                          <!-- All Suppliers panel -->\n");
      out.write("                          <div class=\"panel panel-default\">\n");
      out.write("                              <div class=\"panel-heading\">\n");
      out.write("                                  <strong>\n");
      out.write("                                      <span class=\"glyphicon glyphicon-th\"></span>\n");
      out.write("                                      <span>All Suppliers</span>\n");
      out.write("                                  </strong>\n");
      out.write("                              </div>\n");
      out.write("                              <div class=\"panel-body\">\n");
      out.write("                                  <table class=\"table table-bordered table-striped table-hover\">\n");
      out.write("                                      <thead>\n");
      out.write("                                          <tr>\n");
      out.write("                                              <th class=\"text-center\" style=\"width: 50px;\">#</th>\n");
      out.write("                                              <th>Supplier Name</th>\n");
      out.write("                                              <th>Email</th>\n");
      out.write("                                              <th>Phone Number</th>\n");
      out.write("                                              <th class=\"text-center\" style=\"width: 100px;\">Actions</th>\n");
      out.write("                                          </tr>\n");
      out.write("                                      </thead>\n");
      out.write("                                      <tbody>\n");
      out.write("                                          ");
      if (_jspx_meth_c_forEach_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("                                      </tbody>\n");
      out.write("                                  </table>\n");
      out.write("                              </div>\n");
      out.write("                          </div>\n");
      out.write("                      </div>\n");
      out.write("                    </div>\n");
      out.write("                  \n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        </div>\n");
      out.write("     \n");
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
      out.write("\n");
      out.write("\n");
      out.write("        <!-- Delete Confirmation Modal -->\n");
      out.write("        <div class=\"modal fade\" id=\"deleteModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"deleteModalLabel\">\n");
      out.write("            <div class=\"modal-dialog\" role=\"document\">\n");
      out.write("                <div class=\"modal-content\">\n");
      out.write("                    <div class=\"modal-header\">\n");
      out.write("                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n");
      out.write("                        <h4 class=\"modal-title\" id=\"deleteModalLabel\">Confirm Delete</h4>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"modal-body\">\n");
      out.write("                        Are you sure you want to delete this supplier? You will lose all the item details supplied by this supplier.\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"modal-footer\">\n");
      out.write("                        <form method=\"post\" action=\"supplier\">\n");
      out.write("                            <input type=\"hidden\" name=\"action\" value=\"delete\">\n");
      out.write("                            <input type=\"hidden\" name=\"supplier-id\" id=\"supplier-id\" value=\"\">\n");
      out.write("                            <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Cancel</button>\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-danger\">Yes</button>\n");
      out.write("                        </form>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <script>\n");
      out.write("            function confirmDelete(supplierID) {\n");
      out.write("                $('#supplier-id').val(supplierID);\n");
      out.write("                $('#deleteModal').modal('show');\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
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
        out.write("\n");
        out.write("                            <div class=\"alert ");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${alertClass}", java.lang.String.class, (PageContext)_jspx_page_context, null));
        out.write(" alert-dismissible\" role=\"alert\">\n");
        out.write("                                <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n");
        out.write("                                    <span aria-hidden=\"true\">&times;</span>\n");
        out.write("                                </button>\n");
        out.write("                                ");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${message}", java.lang.String.class, (PageContext)_jspx_page_context, null));
        out.write("\n");
        out.write("                            </div>\n");
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

  private boolean _jspx_meth_c_forEach_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_varStatus_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_0.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_0.setParent(null);
    _jspx_th_c_forEach_0.setVar("supplier");
    _jspx_th_c_forEach_0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${suppliers}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_forEach_0.setVarStatus("status");
    int[] _jspx_push_body_count_c_forEach_0 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_0 = _jspx_th_c_forEach_0.doStartTag();
      if (_jspx_eval_c_forEach_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("                                              <tr>\n");
          out.write("                                                  <td class=\"text-center\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${status.index + 1}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("                                                  <td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${supplier.supplierName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("                                                  <td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${supplier.supplierEmail}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("                                                  <td>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${supplier.supplierPhone}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</td>\n");
          out.write("                                                  <td class=\"text-center\">\n");
          out.write("                                                      <div class=\"btn-group\">\n");
          out.write("                                                          <a href=\"edit_supplier?id=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${supplier.supplierID}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("\" class=\"btn btn-xs btn-warning\" data-toggle=\"tooltip\" title=\"Edit\">\n");
          out.write("                                                              <span class=\"glyphicon glyphicon-edit\"></span>\n");
          out.write("                                                          </a>\n");
          out.write("                                                          <button type=\"button\" class=\"btn btn-xs btn-danger\" data-toggle=\"tooltip\" title=\"Remove\" onclick=\"confirmDelete(");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${supplier.supplierID}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write(")\">\n");
          out.write("                                                              <span class=\"glyphicon glyphicon-trash\"></span>\n");
          out.write("                                                          </button>\n");
          out.write("                                                      </div>\n");
          out.write("                                                  </td>\n");
          out.write("                                              </tr>\n");
          out.write("                                          ");
          int evalDoAfterBody = _jspx_th_c_forEach_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_0.doFinally();
      _jspx_tagPool_c_forEach_varStatus_var_items.reuse(_jspx_th_c_forEach_0);
    }
    return false;
  }
}
