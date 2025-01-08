document.getElementById("login-title").addEventListener("click", function() {
    window.location.href = "/";
});

$('#btnLogin').click(function() {
    const email = $('#loginEmail').val();
    const password = $('#loginPassword').val();

    let url = '/api/user/login';
    let json = {'email': email, 'password': password};

    // 회원가입요청
    common.post(url, json, function(response) {
        console.log("Login successful");
        console.log("JWT Token: " + JSON.stringify(response)); // 서버로부터 받은 JWT 토큰
        // 로그인 성공 후, 응답 받은 토큰을 저장하고 다른 페이지로 이동
        localStorage.setItem('token', JSON.stringify(response));  // 예시로 localStorage에 토큰 저장
        window.location.href = "/dashboard/kanbanboard";  // 메인 화면으로 리디렉션
    });
});