document.getElementById("user-avatar").addEventListener("click", function () {
    const dropdownMenu = document.getElementById("dropdown-menu");
    dropdownMenu.classList.toggle("hidden");
});

// Close dropdown when clicking outside
document.addEventListener("click", function (event) {
    const dropdownMenu = document.getElementById("dropdown-menu");
    const avatar = document.getElementById("user-avatar");

    if (!avatar.contains(event.target) && !dropdownMenu.contains(event.target)) {
        dropdownMenu.classList.add("hidden");
    }
});

// Logout button event handler
document.getElementById("logout-button-dropdown").addEventListener("click", function () {
    socket.close(); // Close the WebSocket connection
});


