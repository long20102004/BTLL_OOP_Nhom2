document.addEventListener("DOMContentLoaded", function(){
    const loginForm = document.querySelector("form");
    const loginButton = document.querySelector(".login-button");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const email = formData.get('email')
        const password = formData.get('password')

        const loginData = {
            email: email,
            password: password
        }

        loginButton.textContent = "Logging in...";
        loginButton.disabled = true;

        try {
            const response = await fetch('backend-url.com/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            });

            const result = await response.json();

            if(response.ok && result.success) {
                alert("Login successful!")
                window.location.href = "dashboard"
            }
            else {
                alert("Incorrect email or password");
            }
        } catch (error) {
            console.error("Error", error);
            alert("Fail to connect to the server");
        }
        loginButton.textContent = "Log in";
        loginButton.disabled = false;
    })
})