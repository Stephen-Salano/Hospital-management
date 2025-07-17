function toggleDashboardMenu() {
    const submenu = document.getElementById("dashboardSubmenu");
    submenu.style.display = submenu.style.display === "none" ? "block" : "none";
}

function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}

//SEARCH BUTTON JS ACTIVATION
function search() {
    var searchTerm = document.getElementById("searchInput").value.toLowerCase();
    if (searchTerm.trim() === "") {
        document.getElementById("errorMessage").textContent = "No information searched";
        return;
    }
    document.getElementById("errorMessage").textContent = ""; // Clear error message if any
    
    var elementsToSearch = document.querySelectorAll("#myPageArea, .menu a, .sidebar a");
    
    // Loop through all elements to search
    elementsToSearch.forEach(function(element) {
        var text = element.textContent.toLowerCase();
        if (text.includes(searchTerm)) {
            // Wrap the matching text in a span with the "highlight" class
            var html = element.innerHTML;
            var highlightedHtml = html.replace(new RegExp(searchTerm, 'gi'), function(match) {
                return '<span class="highlight">' + match + '</span>';
            });
            element.innerHTML = highlightedHtml;
        }
    });
} 