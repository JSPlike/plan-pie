$(document).ready(function () {
    // 시작 시 실행할 함수 호출
    home.init();
});

const home = {
    init: function () {
        console.log("페이지 로드 완료");
        common.checkLoginStatus();
    },
};