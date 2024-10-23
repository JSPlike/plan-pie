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

// 이메일 유효성 검사
function isValidEmail(email) {
    let result = true;
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    result = emailPattern.test(email);
    return result;
}

$('#btnSendVerification').click(function () {
    if(isValidEmail($('#email').val())) {
        $('#verifyMsg1').css('visibility', 'hidden');


        let url = '/api/auth/sendEmail';
        let json = {'email': $('#email').val()};

        // 이메일 인증확인 전송
        post(url, json, function(data) {
            alert(data);
        });
    } else {
        $('#verifyMsg1').css('visibility', 'visible');
    }
});

// 이메일 인증버튼
$('#btnVerification').click(function(){
    let code = $('#verificationCode').val();

    if(code == '' || code.length < 6) {
        // 코드
        $('#verifyMsg2').addClass('text-danger');
        $('#verifyMsg2').text(코)
    }
});


function verifiedCodeInput(event) {
    // 입력한 값
    const input = event.target;
    const value = input.value;

    // 입력한 문자가 숫자이거나 알파벳인 경우만 유지
    const filteredValue = value.replace(/[^a-zA-Z0-9]/g, '');

    // 소문자는 대문자로 변환
    const upperCaseValue = filteredValue.toUpperCase();

    // 변환된 값을 다시 input에 설정
    input.value = upperCaseValue;
}