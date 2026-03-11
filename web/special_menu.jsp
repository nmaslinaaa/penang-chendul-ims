<%-- 
    Document   : special_menu
    Created on : Jul 7, 2024, 7:35:32 PM
    Author     : User
--%>

<ul>
  <li>
    <a href="home.jsp">
      <i class="glyphicon glyphicon-home"></i>
      <span>Dashboard</span>
    </a>
  </li>
<!--    <li>
    <a href="#" class="submenu-toggle">
      <i class="glyphicon glyphicon-user"></i>
      <span>User Management</span>
    </a>
    <ul class="nav submenu">
      <li><a href="users.jsp">Manage Users</a> </li>
    </ul>
  </li>-->
<!--   <li>
    <a href="supplier.jsp">
      <i class="glyphicon glyphicon-indent-left"></i>
      <span>Suppliers</span>
    </a>
  </li> -->
  <li>
    <a href="#" class="submenu-toggle">
      <i class="glyphicon glyphicon-th-large"></i>
      <span>Items</span>
    </a>
     <ul class="nav submenu">
      <li><a href="item">Manage Items</a> </li>
      <li><a href="add_item">Add Items</a> </li>
      <li><a href="view_item">View Items</a> </li>
    </ul>
  </li>
  <li>
     <a href="#" class="submenu-toggle">
         <i class="glyphicon glyphicon-open"></i>
         <span>Outgoing Item</span>
     </a>

    <ul class="nav submenu">
      <li><a href="update_remain_quantity">Update Remaining Quantity</a> </li>
       <li><a href="view_item_used">View Items-Used</a> </li>
    </ul>
  </li>
  <li>
    <a href="#" class="submenu-toggle">
      <i class="glyphicon glyphicon-credit-card"></i>
      <span>Orders</span>
    </a>
    <ul class="nav submenu">
      <li><a href="orders.jsp">Manage Orders</a> </li>
      <li><a href="add_order">Add Order</a> </li>
      
    </ul>
  </li>
   <li>
    <a href="#" class="submenu-toggle">
      <i class="glyphicon glyphicon-duplicate"></i>
      <span>View Orders</span>
    </a>
    <ul class="nav submenu">
      <li><a href="orders_report.jsp">Orders by dates </a></li>
      <li><a href="monthly_orders">Monthly orders</a></li>
      <li><a href="daily_orders">Daily orders</a> </li>
    </ul>
  </li>
</ul>