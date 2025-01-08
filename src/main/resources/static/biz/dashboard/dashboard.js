$(document).ready(function() {
    common.checkLoginStatus();
});

document.addEventListener('DOMContentLoaded', function() {
    // Initialize FullCalendar
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',  // 기본적으로 월간 보기 설정
        headerToolbar: {
            left: 'prev,next today',   // 이전, 다음, 오늘 버튼
            center: 'title',           // 제목 표시
            right: 'dayGridMonth,timeGridWeek,timeGridDay' // 월별, 주별, 일별 버튼
        },
        editable: true,               // 드래그 & 드롭 허용
        events: [
            {
                title: 'Event 1',
                start: '2024-09-27'
            },
            {
                title: 'Event 2',
                start: '2024-09-28'
            }
        ],
        dayRender: function(info) {
            // 요일 숫자만 표시
            info.el.innerText = info.date.getDate(); // 날짜 숫자만 표시
        }
    });
    calendar.render();

    // Initialize Sortable for To-Do List
    var todoList = document.getElementById('todo-list');
    new Sortable(todoList, {
        animation: 150,
        ghostClass: 'blue-background-class'
    });

    // Add new task to To-Do List
    document.getElementById('add-todo').addEventListener('click', function() {
        var newTodo = document.getElementById('new-todo').value;
        if (newTodo.trim() !== '') {
            var newTodoItem = document.createElement('li');
            newTodoItem.className = 'todo-item';
            newTodoItem.textContent = newTodo;
            todoList.appendChild(newTodoItem);
            document.getElementById('new-todo').value = ''; // Clear input field
        }
    });
});