// 페이지 로드 시 로그인 상태 확인
$(document).ready(function() {
    checkLoginStatus();
});

function checkLoginStatus() {
    let token = localStorage.getItem('token');
    let parsedToken = JSON.parse(token);
    console.log(parsedToken);

    if (token) {
        // 토큰이 있으면 로그인된 상태로 간주하고, "로그인" 버튼을 숨기고 "로그아웃" 버튼을 표시
        $('#login-btn').hide();
        $('#logout-btn').show();
        $('#profile-btn').show();
    } else {
        // 토큰이 없으면 로그인되지 않은 상태로 간주하고, "로그인" 버튼을 표시하고 "로그아웃" 버튼을 숨김
        $('#login-btn').show();
        $('#logout-btn').hide();
        $('#profile-btn').hide();
    }
}

$('#lgout-btn').click(function() {
    localStorage.removeItem('token');

    // 로그인 상태 확인 후 UI 갱신
    checkLoginStatus();
});