document.addEventListener('DOMContentLoaded', function () {
    // 회원가입
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

    // 로그인
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

async function registerUser(data) {
    try {
        const response = await fetch('/api/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        const result = await response.json();

        if (response.ok) {
            alert(result.message);
            window.location.href = "/login";
        } else {
            alert(result.error);
        }
    } catch (error) {
        console.error("회원가입 에러:", error);
    }
}

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
            alert('로그인 성공!');
            window.location.href = '/';
        } else {
            alert(result.error || '로그인 실패!');
        }
    } catch (error) {
        console.error('로그인 에러:', error);
    }
}