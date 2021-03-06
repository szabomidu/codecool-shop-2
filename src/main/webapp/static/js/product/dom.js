import {dataHandler} from "../data_handler.js";

export let dom = {

    init: function () {
        filterProducts();
        addToCart();
        toggleCartVisibility();
    }
}

function redirectToCheckout(orderId) {
    let checkoutButton = document.querySelector("#checkout");
    checkoutButton.addEventListener('click', () => window.location.replace(`/checkout?id=${orderId}`));
}

function toggleCartVisibility() {
    const cartContainer = document.querySelector('.cart-image');

    cartContainer.addEventListener('click', () => {
        let cart = document.querySelector('#cart-content');
        let itemNumber = parseInt(document.querySelector('.cart-counter').innerHTML);
        if (itemNumber > 0) {
            cart.classList.toggle('hidden');
        }
    });
}

function filterProducts() {
    const filterButton = document.querySelector("#filter");
    filterButton.addEventListener("click", fetchProducts);
}

function fetchProducts() {
    const supplierId = document.querySelector("#supplier-select").value;
    const categoryId = document.querySelector("#category-select").value;
    dataHandler._api_get(`api/filter?supplier=${supplierId}&category=${categoryId}`, displayFilterResults)
}

function displayFilterResults(response) {
    const productsContainer = document.querySelector("#products");
    productsContainer.innerHTML = "";
    if (response.length === 0) {
        const supplierSelectField = document.querySelector("#supplier-select");
        const categorySelectField = document.querySelector("#category-select");
        const containerDiv = document.createElement('div');
        containerDiv.classList.add('no-result-container');
        containerDiv.innerHTML = `<i class="fas fa-frown"></i><p>There is no
            ${categorySelectField.options[categorySelectField.selectedIndex].text}
                        in the world of
            ${supplierSelectField.options[supplierSelectField.selectedIndex].text}</p>`;
        productsContainer.appendChild(containerDiv);
    } else {
        let newContent = "";
        for (let product of response) {
            newContent +=
                `<div class="col col-sm-12 col-md-6 col-lg-4">
                    <div class="card">
                        <img class="" src='/static/img/product_${product.id}.jpg' alt=""/>
                        <div class="card-header">
                            <h4 class="card-title">${product.name}</h4>
                            <p class="card-text">${product.description}</p>
                        </div>
                        <div class="card-body">
                            <div class="card-text">
                                <p class="lead">${product.defaultPrice} USD</p>
                            </div>
                            <div class="card-text">
                                <button class="btn btn-primary to-cart-btn" type="button" 
                                        data-product-id="${product.id}">Add to cart</button>
                            </div>
                        </div>
                    </div>
                </div>`
            productsContainer.innerHTML = newContent;
        }
    }
    addToCart();
}

function addToCart() {
    const buttons = document.querySelectorAll('.to-cart-btn');

    for (const button of buttons) {
        button.addEventListener('click', () => {
            productToCart(button)
        });
    }
}

function productToCart(button) {
    const cartContainer = document.querySelector('.cart-image');

    let userId = parseInt(cartContainer.dataset.userId);
    let orderId = parseInt(cartContainer.dataset.orderId);

    if (userId === 0 && orderId === 0) {
        createUser(button);
    } else {
        const body = `${orderId},${button.dataset.productId}`;
        dataHandler._api_post(`api/lineitem`, body, displayLineItemInCart);
    }
}

function createUser(button) {
    dataHandler._api_post(`api/user`, "", (userId) => {
        createOrder(userId, button);
    });
}

function createOrder(userId, button) {
    document.querySelector('.cart-image').dataset.userId = userId;
    dataHandler._api_post(`api/order`, userId, (orderId) => {
        redirectToCheckout(orderId);
        addProductToCart(orderId, button);
    });
}

function addProductToCart(orderId, button) {
    const productId = button.dataset.productId;
    const body = `${orderId},${productId}`;

    document.querySelector('.cart-image').dataset.orderId = orderId;
    dataHandler._api_post(`api/lineitem`, body, displayLineItemInCart);
}

function displayLineItemInCart(response) {
    const cartTableBody = document.querySelector('#cart-content-body');
    let lineItemsInTable = document.querySelectorAll('.line-item');
    if (response.quantity > 1) {
        for (let lineItem of lineItemsInTable) {
            if (lineItem.dataset.id === response.id.toString()) {
                changeQuantity(lineItem, response);
            }
        }
    } else {
        const newRow = document.createElement("tr");
        newRow.classList.add('line-item');
        newRow.dataset.id = response.id;
        newRow.innerHTML += `
            <td>${response.name}</td>
            <td class="quantity"><i class="fas fa-minus minus"></i><span class="quantity-number">${response.quantity}</span><i class="fas fa-plus plus"></i></td>
            <td class="unit-price">${response.unitPrice}</td>
            <td class="sub-total-price">${response.unitPrice * response.quantity}</td>
            <td class="delete-item"><i class="fa fa-trash" aria-hidden="true"></i></td>
        `
        cartTableBody.appendChild(newRow);
        const minusButton = newRow.querySelector('.minus');
        const plusButton = newRow.querySelector('.plus');
        const deleteButton = newRow.querySelector('.delete-item')

        minusButton.addEventListener('click', decreaseQuantityFromCart);
        plusButton.addEventListener('click', increaseQuantityFromCart);
        deleteButton.addEventListener('click', removeFromCart);
    }
    updateCartNumber(1);
    updateTotalPrice();
}

function removeFromCart() {
    const lineItem = this.closest("tr");
    const lineItemId = lineItem.dataset.id;
    const orderId = document.querySelector(".cart-image").dataset.orderId;
    let body = `${orderId},${lineItemId}`;
    dataHandler._api_delete('api/lineitem', body, (response) => {
        document.querySelector("tbody").removeChild(this.closest("tr"));
        updateCartNumber(response);
        updateTotalPrice();
        let cart = document.querySelector('#cart-content');
        let itemNumber = parseInt(document.querySelector('.cart-counter').innerHTML);
        if (itemNumber === 0) {
            cart.classList.add('hidden');
        }
    })
}

function changeQuantity(lineItem, response) {
    lineItem.querySelector('.quantity-number').innerHTML = response.quantity.toString();
    lineItem.querySelector('.sub-total-price').innerHTML = response.totalPrice.toFixed(1).toString();

}

function updateCartNumber(change) {
    const currentNumber = parseInt(document.querySelector('.cart-counter').innerHTML);
    document.querySelector('.cart-counter').innerHTML = (currentNumber + change).toString();
}

function updateTotalPrice() {
    let totalValue = 0;
    const totalPrice = document.querySelector('.total-price');
    document.querySelectorAll('.sub-total-price').forEach(e => {
        totalValue += parseFloat(e.innerHTML)
    });
    totalPrice.innerHTML = totalValue.toFixed(1);
}

function decreaseQuantityFromCart() {
    const lineItem = this.closest("tr")
    const lineItemId = lineItem.dataset.id;
    if (parseInt(lineItem.querySelector('.quantity-number').innerText) > 1) {
        let body = `${lineItemId},-1`;
        dataHandler._api_put('api/lineitem', body, (response) => {
            changeQuantity(lineItem, response);
            updateCartNumber(-1);
            updateTotalPrice();
        });
    }

}

function increaseQuantityFromCart() {
    const lineItem = this.closest("tr")
    const lineItemId = lineItem.dataset.id;
    let body = `${lineItemId},1`;
    dataHandler._api_put('api/lineitem', body, (response) => {
        changeQuantity(lineItem, response);
        updateCartNumber(1);
        updateTotalPrice();
    });
}