document.addEventListener('DOMContentLoaded', function () {
    const facebookLoginButton = document.querySelector('.facebook-button');
    const googleLoginButton = document.querySelector('.google-button');
    facebookLoginButton.addEventListener('click', function () {
        window.location.href =
            "http://localhost:8080/oauth2/authorization/facebook";
    });
    googleLoginButton.addEventListener('click', function () {
        window.location.href =
            "http://localhost:8080/oauth2/authorization/google";
    });
});