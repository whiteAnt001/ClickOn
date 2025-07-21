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
            location.href = "/admin/items";
        })
        .catch(err => {
            alert("에러 발생: " + err.message);
        })
}

document.getElementById('likeButton').addEventListener('click', function () {
    const likeBtn = document.getElementById('likeButton');
    const heartIcon = document.getElementById('heartIcon');

    if(likeBtn && heartIcon) {
        likeBtn.addEventListener('click', function (){
            heartIcon.classList.toggle('bi-heart');
            heartIcon.classList.toggle('bi-heart-fill');
        });
    }
});

document.addEventListener("DOMContentLoaded",function() {
    const addToCartBtn = document.getElementById("addToCartBtn");

    if(addToCartBtn) {
        addToCartBtn.addEventListener("click", function() {
            const itemId = this.getAttribute("data-item-id");
            const quantity = this.getAttribute("data-quantity") || 1;

            fetch("/cart/add", {
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                credentials: "include",
                body: JSON.stringify({
                    itemId: itemId,
                    quantity: quantity
                })
            })
                .then(response => {
                    if(response.ok){
                        alert("장바구니에 추가되었습니다.");
                        window.location.reload();
                    } else if(response.status === 401) {
                        alert("로그인이 필요합니다.");
                        window.location.href = "/login";
                    } else {
                        alert("장바구니 추가 실패");
                    }
                })
                .catch(error => {
                    console.error("에러:", error);
                    alert("서버 오류가 발생했습니다.");
                });
        });
    }
});