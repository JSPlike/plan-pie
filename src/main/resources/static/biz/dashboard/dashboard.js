$(document).ready(function() {
    common.checkLoginStatus();
});

document.addEventListener('DOMContentLoaded', function() {
    const eventDateInput = document.getElementById('eventDate');
    const dashboardLeft = document.querySelector('.dashboard-left');
    const calendarGrid = document.querySelector('.fc-daygrid-day');
    const eventTitleInput = document.getElementById('eventTitle');
    const saveEventButton = document.getElementById('saveEvent');

    // 이벤트 핸들러: 패널 열기
    function openSlidePanel() {
        $('#eventSlidePanel').addClass('active');
        dashboardLeft.style.width = '75%'; // 캘린더 너비 줄이기
        calendarGrid.style.width = '75%';
    }

    // 이벤트 핸들러: 패널 닫기
    function closeSlidePanel() {
        $('#eventSlidePanel').removeClass('active');
        dashboardLeft.style.width = '97%'; // 캘린더 너비 복원
        calendarGrid.style.width = '97%';
    }

    // 패널 닫기 버튼 이벤트
    $('#closeSlidePanel').click(function() {
        console.log('close button click')
        closeSlidePanel();
    });

    // Initialize FullCalendar
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        height: 800,
        initialView: 'dayGridMonth',  // 기본적으로 월간 보기 설정
        headerToolbar: {
            left: 'prev,next today',   // 이전, 다음, 오늘 버튼
            center: 'title',           // 제목 표시
            right: 'dayGridMonth,timeGridWeek,timeGridDay,addEventButton' // 월별, 주별, 일별 버튼
        },
        customButtons: {
            addEventButton: {
                text: '+',
                click: function () {
                    // 버튼 클릭 시 동작 (모달 열기 등)
                    //var modal = new bootstrap.Modal(document.getElementById('newEvent'));
                    //modal.show();
                    openSlidePanel();
                }
            },
            className: 'add-event-btn'
        },
        dateClick: function (info) {
            console.log('clicked date!')
            console.log(info);
            // default data
            $('#startDate').val(info.dateStr);
            $('#endDate').val(info.dateStr);
            openSlidePanel();
        },
        editable: true,               // 드래그 & 드롭 허용
        events: [
            {
                title: '유정생일',
                start: '2025-01-05',
                backgroundColor : "#008000",
                borderColor: "#fff"
            },
            {
                title: '저녁약속',
                start: '2025-01-17'
            }
        ],

        /*
        dayRender: function(info) {
            // 요일 숫자만 표시
            info.el.innerText = info.date.getDate(); // 날짜 숫자만 표시
        }
        */
    });
    calendar.render();

    function getDefaultDateTime() {
        const now = new Date();
        const next1 = new Date();
        const next2 = new Date();

        const koreaTimeOffset = 9 * 60; // 한국 시간은 UTC+9
        now.setMinutes(now.getMinutes() + koreaTimeOffset);

        // 날짜 부분 (현재 날짜만)
        const defaultDate = now.toISOString().slice(0, 10); // "YYYY-MM-DD" 형식

        // 시간 부분 (분을 0으로 설정하고 1시간 뒤로 설정)
        now.setMinutes(0);  // 분을 0으로 설정
        next1.setHours(now.getHours() + 1); // 1시간 뒤로 설정
        next1.setMinutes(0);
        next2.setHours(now.getHours() + 2); // 2시간 뒤로 설정
        next2.setMinutes(0);
        const defaultTime = next1.toISOString().slice(11, 16); // "HH:00" 형식
        const defaultTimeAdd = next2.toISOString().slice(11, 16); // "HH:00" 형식

        return { defaultDate, defaultTime, defaultTimeAdd };
    }

    const { defaultDate, defaultTime, defaultTimeAdd } = getDefaultDateTime();

    const startDt = flatpickr("#startDate", {
        enableTime: false, // 시간 선택 가능
        dateFormat: "Y-m-d", // 형식 지정
        defaultDate: defaultDate, // 현재 날짜 기본값 설정
    });

    const endDt = flatpickr("#endDate", {
        enableTime: false, // 시간 선택 가능
        dateFormat: "Y-m-d", // 형식 지정
        defaultDate: defaultDate, // 현재 날짜 기본값 설정
    });

    const startTm = flatpickr("#startTime", {
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        time_24hr: true,
        defaultDate: "00:00", // 현재 날짜 기본값 설정
    });

    const endTm = flatpickr("#endTime", {
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        time_24hr: true,
        defaultDate: "24:00", // 현재 날짜 기본값 설정
    });

    $('#allDay').change(function() {
        if ($(this).prop('checked')) {
            // All Day가 체크된 상태: 시간 선택 활성화
            startTm.setDate("00:00", true);
            endTm.setDate("24:00", true);
            $('#startTime').prop('disabled', true); // 시간 입력 활성화
            $('#endTime').prop('disabled', true); // 시간 입력 활성화
        } else {
            const { defaultDate, defaultTime, defaultTimeAdd } = getDefaultDateTime();
            // All Day가 체크 해제된 상태: 시간 선택 비활성화
            startTm.setDate(defaultTime, true);
            endTm.setDate(defaultTimeAdd, true);
            $('#startTime').prop('disabled', false); // 시간 입력 비활성화
            $('#endTime').prop('disabled', false); // 시간 입력 활성화
        }
    });
});