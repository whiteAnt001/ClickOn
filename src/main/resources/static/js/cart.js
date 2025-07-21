function updateQuantity(button) {
    const input = button.previousElementSibling;
    const cartId = input.getAttribute('data-cart-id');
    const quantity = input.value;

    fetch(`/cart/${cartId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({quantity: quantity})
    }).then(res => {
        if(res.ok) {
            alert("수량이 변경되었습니다.");
            location.reload();
        }else {
            alert("수량 변경 실패")
        }
    });
}

function deleteCartItem(button) {
    const itemId = button.getAttribute('data-id');
    
    fetch(`/cart/${itemId}`, {
        method: 'DELETE',
    }).then(res => {
        if(res.ok) {
            alert("장바구니에서 삭제되었습니다.");
            location.reload()
        }else {
            alert("삭제 실패");
        }
    }).catch(error => {
        console.log("삭제 요청 실패 : " + error);
        alert("오류가 발생했습니다. 관리자에게 문의하세요");
    })
}