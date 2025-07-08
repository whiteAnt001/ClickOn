document.addEventListener('DOMContentLoaded', function () {
    // 회원가입 데이터 넘기기
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const data = {
                name: document.getElementById('name').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value,
                phone: document.getElementById('phone').value,
            };

            await registerUser(data);
        });
    }

    // 로그인 데이터 넘기기
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const data = {
                email: document.getElementById('email').value,
                password: document.getElementById('password').value,
            };

            await loginUser(data);
        });
    }
});
// 회원가입
async function registerUser(data) {
    try {
        const response = await fetch('/api/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        const result = await response.json();

        if (response.ok) {
            alert("회원가입 완료!\n이메일 인증을 완료해주세요!");
            window.location.href = "/login";
        } else {
            alert(result.error);
        }
    } catch (error) {
        console.error("회원가입 에러:", error);
    }
}
// 로그인
async function loginUser(data) {
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(data),
        });

        const result = await response.json();

        if (response.ok) {
            window.location.href = '/';
        } else {
            if (result.error === '이메일 인증을 완료해주세요.') {
                alert(result.error);
                window.location.href ='/verify';
            }
            alert(result.error || '로그인 실패!');
        }
    } catch (error) {
        console.error('로그인 에러:', error);
    }
}
// 이메일 재전송
async function resendVerification(email) {
    try {
        const response = await fetch('/api/resend-verification', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email})
        });

        const result = await response.json();

        if(response.ok) {
            alert(result.message);
        } else {
            alert(result.error);
        }
    } catch(e) {
        console.error("재전송 실패:", e);
    }
}