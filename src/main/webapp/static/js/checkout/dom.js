import {dataHandler} from "../data_handler.js";

export let dom = {

    init: function () {
        submitFormData();
    }
}

function submitFormData() {
    const button = document.querySelector('#submit-button');
    button.addEventListener('click', sendOrderData);
}

function sendOrderData() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = parseInt(urlParams.get('id'));
    const orderData = {
        orderId: id,
        firstName: document.querySelector('#fname').value,
        lastName: document.querySelector('#lname').value,
        email: document.querySelector('#email').value,
        address: document.querySelector('#adr').value,
        zipCode: parseInt(document.querySelector('#zip').value),
        city: document.querySelector('#city').value,
        country: document.querySelector('#country').value
    };
    console.log(orderData)
    dataHandler._api_put('/api/order', orderData, (response) => displaySuccessText(response));
}

function displaySuccessText() {
    document.querySelector('#checkout-container').innerHTML =
        `<p>Order Data Successfully Saved</p>`;
}