<%-- 
    Document   : footer
    Created on : Jul 7, 2024, 5:49:56 PM
    Author     : User
--%>

  <script>
  // Wait for the document to be fully loaded
  document.addEventListener('DOMContentLoaded', function () {
    // Get all submenu toggle links
    var submenuToggles = document.querySelectorAll('.submenu-toggle');

    // Loop through each toggle link
    submenuToggles.forEach(function (toggle) {
      // Add click event listener to each toggle link
      toggle.addEventListener('click', function (e) {
        e.preventDefault(); // Prevent default link behavior
        var submenu = this.nextElementSibling; // Get the next sibling ul (submenu)
        
        // Toggle the submenu visibility
        if (submenu.style.display === 'block') {
          submenu.style.display = 'none';
        } else {
          submenu.style.display = 'block';
        }
      });
    });
  });
</script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="libs/js/functions.js"></script>