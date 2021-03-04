(() => {
    function register() {
        let id = document.querySelector(`.login-id-input`).value;
        let password = document.querySelector(`.login-password-input`).value;
        let email = document.querySelector(`.login-email-input`).value;
        let username = document.querySelector(`.login-username-input`).value;
        if (id == null) {
            alert('아이디를 입력해주세요.');
            return;
        }

        if (password == null) {
            alert('비밀번호를 입력해주세요.');
            return;
        }

        if (email == null) {
            alert('이메일을 입력해주세요.');
            return;
        }

        if (username == null) {
            alert('사용자성명을 입력해주세요.');
            return;
        }

        $.ajax({
            url: `/user`,
            dataType: `json`,
            method: `post`,
            contentType: `application/json`,
            data: JSON.stringify({
                id: id,
                password: password,
                email: email,
                role: `ROLE_USER`,
                username: username
            }),
            success: function(response) {
                if (response != null) {
                    alert('회원가입 성공');
                    location.href = "/login";
                } else {
                    alert('회원가입 실패');
                }
            },
            error: function(error) {
                console.log(error);
                alert('회원가입 실패');
            }
        });
    }

    document.querySelector(`.login-register-button`).addEventListener('click', () => {
        register();
    });
})();