// 페이지 로드 시 로그인 상태 확인
$(document).ready(function() {
    common.checkLoginStatus();
});



$('#logout-btn').click(function() {
    console.log('logout button click!');
    localStorage.removeItem('token');

    // 로그인 상태 확인 후 UI 갱신
    common.checkLoginStatus();
});