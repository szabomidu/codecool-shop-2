import {dataHandler} from "./data_handler.js";

export let dom = {

    init: function () {
        filterProducts();
        addToCart();
        toggleCartVisibility();
    }
}

function toggleCartVisibility() {
    const cartContainer = document.querySelector('.cart-image');

    cartContainer.addEventListener('click', () => {
        let cart = document.querySelector('#cart-content');
        cart.classList.toggle('hidden');
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
    let newContent = "";
    if (response.length === 0) {
        const supplierSelectField = document.querySelector("#supplier-select");
        const categorySelectField = document.querySelector("#category-select");
        newContent += `There is no
            ${categorySelectField.options[categorySelectField.selectedIndex].text}
                        in the world of
            ${supplierSelectField.options[supplierSelectField.selectedIndex].text}`;
    } else {
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
                                <button class="btn btn-success to-cart-btn" type="button" 
                                        data-product-id="${product.id}">Add to cart</button>
                            </div>
                        </div>
                    </div>
                </div>`
        }
    }
    productsContainer.innerHTML = newContent;
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
    }
}

function createUser(button) {
    dataHandler._api_post(`api/user`, "", () => {
        createOrder(button);
    });
}

function createOrder(response, button) {
    const userId = response.id;

    document.querySelector('.cart-image').dataset.userId = userId;
    dataHandler._api_post(`api/order`, userId, () => {
        addProductToCart(button);
    });
}

function addProductToCart(response, button) {
    const orderId = response.id;
    const productId = button.dataset.productId;
    const body = `${orderId},${productId}`;

    document.querySelector('.cart-image').dataset.orderId = orderId;
    dataHandler._api_post(`api/lineitem`, body, displayLineItemInCart);
}

function displayLineItemInCart(response) {

}