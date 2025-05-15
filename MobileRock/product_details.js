// Function to retrieve query parameter value by name
function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Retrieve product information from query parameters
var productName = getQueryParam("name");
var productPrice = getQueryParam("price");
var productImage = getQueryParam("image");

// Create and style product details dynamically
var productDetailsDiv = document.getElementById("product-details");
productDetailsDiv.style.maxWidth = "600px";
productDetailsDiv.style.margin = "0 auto";
productDetailsDiv.style.padding = "20px";
productDetailsDiv.style.border = "1px solid #ccc";
productDetailsDiv.style.borderRadius = "5px";
productDetailsDiv.style.boxShadow = "0 0 10px rgba(0, 0, 0, 0.1)";

var productImageElem = document.createElement("img");
productImageElem.src = productImage;
productImageElem.alt = productName;
productImageElem.style.maxWidth = "100%";
productImageElem.style.height = "auto";
productImageElem.style.display = "block";
productImageElem.style.margin = "0 auto";
productImageElem.style.marginBottom = "20px";
productDetailsDiv.appendChild(productImageElem);

var productNameElem = document.createElement("h2");
productNameElem.textContent = productName;
productNameElem.style.textAlign = "center";
productNameElem.style.marginBottom = "10px";
productDetailsDiv.appendChild(productNameElem);

var productPriceElem = document.createElement("p");
productPriceElem.textContent = "Price: $" + productPrice;
productPriceElem.style.textAlign = "center";
productPriceElem.style.marginBottom = "20px";
productDetailsDiv.appendChild(productPriceElem);
