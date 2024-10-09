const themeCheckbox = document.querySelector('.theme-checkbox');
const body = document.body;
const elementsToToggle = [
    '#navbar', '#navbar-brand', '#navbar-menu', '#home-container1', '#footer', '#footer-content',
    '#footer-columns', '#footer-bottom', '#kanban-board'
];

// 테마를 적용하는 함수
function applyTheme(theme) {
    const removeClass = theme === 'dark' ? 'light' : 'dark';
    const addClass = theme;

    body.classList.remove(removeClass);
    body.classList.add(addClass);

    elementsToToggle.forEach(selector => {
        const element = document.querySelector(selector);
        if (element) {
            element.classList.remove(removeClass);
            element.classList.add(addClass);
        }
    });

    // 테마 제목 변경
    const themeTitleElement = document.querySelector('#theme-title');
    if (themeTitleElement) {
        themeTitleElement.textContent = theme === 'dark' ? 'Dark' : 'Light';
    }

    //document.querySelector('#theme-title').textContent = theme === 'dark' ? 'Dark' : 'Light';
    // LocalStorage에 테마 저장
    localStorage.setItem('theme', theme);
}

document.addEventListener("DOMContentLoaded", function () {
    const currentTheme = localStorage.getItem('theme');

    // 저장된 테마가 있으면 적용
    if (currentTheme) {
        applyTheme(currentTheme);
        if(themeCheckbox && themeCheckbox.checked) {
            themeCheckbox.checked = currentTheme === 'dark';
        }
    }
});

if (themeCheckbox) { // null 체크
    themeCheckbox.addEventListener('change', () => {
        const newTheme = themeCheckbox.checked ? 'dark' : 'light'; // 체크된 상태에 따라 테마 설정
        applyTheme(newTheme); // 테마 적용
    });
} else {
    console.warn('Element #theme-checkbox not found.'); // 요소가 없을 경우 경고
}

function toggleProfile(event) {
    const card = document.getElementById('profileCard');
    card.style.display = card.style.display === 'none' ? 'block' : 'none'; // 카드 보이기/숨기기

    // 클릭한 요소의 위치 계산
    const rect = event.target.getBoundingClientRect();
    card.style.left = `${rect.left}px`; // 클릭한 위치의 x좌표
    card.style.top = `${rect.bottom}px`; // 클릭한 위치의 y좌표 (아래쪽)
}

/* post 요청 API */
function post(url, json, callbackFn) {
    return $.ajax({
       contentType: 'application/json; charset=utf-8',
       type: 'post',
       async: false,
       dataType: 'json',
       data: JSON.stringify(json),
       url: url,
       success: function success(response) {
           callbackFn(response);
       },
       error: function error(xhr, status, error) {
           console.log(status);
       },
    });
}

