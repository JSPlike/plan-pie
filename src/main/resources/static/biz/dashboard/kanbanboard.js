dragula([
    document.getElementById('todo'),
    document.getElementById('doing'),
    document.getElementById('done')
]);


function allowDrop(ev) {
    ev.preventDefault(); // 기본 동작 방지
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id); // 드래그하는 요소의 ID 저장
}

function drop(ev) {
    ev.preventDefault(); // 기본 동작 방지
    var data = ev.dataTransfer.getData("text"); // 드래그한 요소의 ID 가져오기
    console.log(">>>>>>>>요소 드래그");
    console.log(data);

    // 드롭한 위치가 칸반 열인지 확인
    if (ev.target.classList.contains("kanban-column")) {
        ev.target.appendChild(document.getElementById(data)); // 드롭한 위치에 요소 추가
    }
}