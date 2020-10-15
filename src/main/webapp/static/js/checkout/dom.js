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
    if (dataIsValid()) {
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
    } else window.scrollTo(0, 0);
}

function displaySuccessText() {
    document.querySelector('#checkout-container').innerHTML =
        `<p>Order Data Successfully Saved</p>`;
}

function dataIsValid() {
    const firstNameData = document.querySelector('#fname').value;
    const lastNameData = document.querySelector('#lname').value;
    const emailData = document.querySelector('#email').value;
    const addressData = document.querySelector('#adr').value;
    const zipData = document.querySelector('#zip').value;
    const cityData = document.querySelector('#city').value;
    const countryData = document.querySelector('#country').value;

    let valid = true;

    if (firstNameData === "") {
        document.querySelector('#fname-error').innerHTML = 'First name must not be empty!';
        valid = false;
    } else document.querySelector('#fname-error').innerHTML = '';

    if (lastNameData === "") {
        document.querySelector('#lname-error').innerHTML = 'Last name must not be empty!';
        valid = false;
    } else document.querySelector('#lname-error').innerHTML = '';

    if (!RegExp('^[^@.]+@[^@.]+\\.[^@.]+$').test(emailData)) {
        document.querySelector('#email-error').innerHTML = 'E-mail must have the following format: john@example.com';
        valid = false;
    } else document.querySelector('#email-error').innerHTML = '';

    if (addressData === "") {
        document.querySelector('#address-error').innerHTML = 'Address must not be empty!';
        valid = false;
    } else document.querySelector('#address-error').innerHTML = '';

    if (!RegExp('^[\\d]+$').test(zipData)) {
        document.querySelector('#zip-error').innerHTML = 'Zip code must be numeric!';
        valid = false;
    } else document.querySelector('#zip-error').innerHTML = '';

    if (cityData === "") {
        document.querySelector('#city-error').innerHTML = 'City must not be empty!';
        valid = false;
    } else document.querySelector('#city-error').innerHTML = '';

    if (countryData === "") {
        document.querySelector('#country-error').innerHTML = 'Country must not be empty!';
        valid = false;
    } else document.querySelector('#country-error').innerHTML = '';

    return valid;
}