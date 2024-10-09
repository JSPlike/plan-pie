$('#btnRegUser').click(function() {
    const email = $('#email').val();
    const password = $('#password').val();
    const confirmPassword = $('#confirmPassword').val();

    let url = '/api/user/signup';
    let json = {'email': email, 'password': password, 'confirmPassword': confirmPassword};

    // 회원가입요청
    post(url, json, function(data) {
        alert(data);
    });
});