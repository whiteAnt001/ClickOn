function submitItem() {
    const form = document.getElementById("itemForm");
    const formData = new FormData(form);

    fetch("/admin/items/new", {
        method: 'POST',
        body: formData
    })
        .then(res => {
            if(!res.ok) throw new Error("등록 실패!");
            return res.text();
        })
        .then(() => {
            alert("상품이 성공적으로 등록되었습니다.");
            window.location.href = "/admin/items";
        })
        .catch(err => {
            alert("에러 발생: " + err.message);
        })
}

document.getElementById('likeButton').addEventListener('click', function () {
    const likeBtn = document.getElementById('likeButton');
    const heartIcon = document.getElementById('heartIcon');

    if(likeBtn && heartIcon) {
        likeBtn.addEventListener('click', function () {
            heartIcon.classList.toggle('bi-heart');
            heartIcon.classList.toggle('bi-heart-fill');
        });
    }
});