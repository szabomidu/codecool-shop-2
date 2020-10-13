import {dataHandler} from "./data_handler.js";

export let dom = {

    init: function () {
        filterProducts();
    }
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
                        <img class="" src='/static/img/product_1.jpg' alt=""/>
                        <div class="card-header">
                            <h4 class="card-title">${product.name}</h4>
                            <p class="card-text">${product.description}</p>
                        </div>
                        <div class="card-body">
                            <div class="card-text">
                                <p class="lead">${product.defaultPrice} USD</p>
                            </div>
                            <div class="card-text">
                                <a class="btn btn-success" href="#">Add to cart</a>
                            </div>
                        </div>
                    </div>
                </div>`
        }
    }
    productsContainer.innerHTML = newContent;
}
