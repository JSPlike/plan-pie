document.getElementById("login-title").addEventListener("click", function() {
    window.location.href = "/";
});

$('#btnLogin').click(function() {
    const email = $('#loginEmail').val();
    const password = $('#loginPassword').val();

    let url = '/api/user/login';
    let json = {'email': email, 'password': password};

    // 회원가입요청
    common.post(url, json, function(data) {
        alert(data);
    });
});