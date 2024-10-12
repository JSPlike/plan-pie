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

$('#btnVerification').click(function(){
    const code = $('#verificationCode').val();

    if(code != null ||case code.length < 6) {

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