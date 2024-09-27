const themeCheckbox = document.querySelector('.theme-checkbox');
const body = document.body;

// 체크박스 상태에 따라 테마 변경
themeCheckbox.addEventListener('change', () => {
    if (themeCheckbox.checked) {
        body.classList.remove('light');
        body.classList.add('dark');
        $('#navbar').removeClass('light').addClass('dark');
        $('#navbar-brand').removeClass('light').addClass('dark');
        $('#navbar-menu').removeClass('light').addClass('dark');
        $('#footer').removeClass('light').addClass('dark');
        $('#footer-content').removeClass('light').addClass('dark');
        $('#footer-columns').removeClass('light').addClass('dark');
        $('#footer-bottom').removeClass('light').addClass('dark');
    } else {
        body.classList.remove('dark');
        body.classList.add('light');
        $('#navbar').removeClass('dark').addClass('light');
        $('#navbar-brand').removeClass('dark').addClass('light');
        $('#navbar-menu').removeClass('dark').addClass('light');
        $('#footer').removeClass('dark').addClass('light');
        $('#footer-content').removeClass('dark').addClass('light');
        $('#footer-columns').removeClass('dark').addClass('light');
        $('#footer-bottom').removeClass('dark').addClass('light');
    }
});