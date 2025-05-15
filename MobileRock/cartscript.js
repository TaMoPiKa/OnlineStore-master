// Function to fetch and display the user's cart
function showUserCart() {
    // Retrieve username and password from localStorage
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (!username || !password) {
        console.error('Username or password not found in localStorage');
        return;
    }

    const endpoint = `http://127.0.0.1:8080/user/cart/${username}/${password}`;
    
    fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            if (data.httpStatus === "FOUND") {
                const cartItems = data.body.data;
                const cartItemsContainer = document.getElementById('cart-items');
                cartItemsContainer.innerHTML = ''; // Clear existing items

                cartItems.forEach(item => {
                    const itemElement = document.createElement('div');
                    itemElement.className = 'cart-item';

                    const itemImage = document.createElement('img');
                    itemImage.src = item.image;

                    const itemDetails = document.createElement('div');
                    itemDetails.innerHTML = `
                        <h4>${item.name}</h4>
                        <p>Quantity: ${item.quantity}</p>
                        <p>Unit Price: $${item.unitPrice}</p>
                    `;

                    // Button to remove item from cart
                    const removeButton = document.createElement('button');
                    removeButton.textContent = 'Remove item from cart';
                    removeButton.className = 'remove-item-btn'; // Add class to button
                    removeButton.onclick = () => removeItemFromCart(item.id); // Call removeItemFromCart function with item ID

                    itemElement.appendChild(itemImage);
                    itemElement.appendChild(itemDetails);
                    itemElement.appendChild(removeButton); // Append remove button to item
                    cartItemsContainer.appendChild(itemElement);
                });
            } else {
                console.log('Error: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error fetching cart:', error);
        });
}

showUserCart();

// Function to remove item from cart
function removeItemFromCart(itemId) {
    // Retrieve username and password from localStorage
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (!username || !password) {
        console.error('Username or password not found in localStorage');
        return;
    }

    const removeEndpoint = `http://127.0.0.1:8080/user/cart/remove/${itemId}/${username}/${password}`;

    fetch(removeEndpoint, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            // If removal is successful, update the cart display
            console.log(`Item ${itemId} removed from cart`);
            showUserCart();
        } else {
            console.error(`Error removing item ${itemId} from cart: ${response.statusText}`);
        }
    })
    .catch(error => {
        console.error(`Error removing item ${itemId} from cart: ${error}`);
    });
}

// Function to show the checkout modal
function showCheckoutModal(cartItems) {
    const modal = document.getElementById('checkoutModal');
    const cartSummary = document.getElementById('cart-summary');
    const totalPriceElement = document.getElementById('total-price');

    cartSummary.innerHTML = ''; // Clear existing items

    let totalPrice = 0;

    cartItems.forEach(item => {
        const itemElement = document.createElement('div');
        itemElement.className = 'cart-item';

        const itemImage = document.createElement('img');
        itemImage.src = item.image;

        const itemDetails = document.createElement('div');
        itemDetails.innerHTML = `
            <h4>${item.name}</h4>
            <p>Quantity: ${item.quantity}</p>
            <p>Unit Price: $${item.unitPrice}</p>
        `;

        // const itemTotalPrice = item.quantity * item.unitPrice;
        const itemTotalPrice = 1 * item.unitPrice;
        totalPrice += itemTotalPrice;

        itemElement.appendChild(itemImage);
        itemElement.appendChild(itemDetails);
        cartSummary.appendChild(itemElement);
    });

    totalPriceElement.textContent = totalPrice.toFixed(2);
    modal.style.display = 'block';

    // Close the modal when the close button is clicked
    document.getElementsByClassName('close')[0].onclick = function() {
        modal.style.display = 'none';
    };

    // Close the modal when clicking outside of the modal content
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };
}

// Function to fetch and display the user's cart and then show the checkout modal
function checkout() {
    // Retrieve username and password from localStorage
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (!username || !password) {
        console.error('Username or password not found in localStorage');
        return;
    }

    const endpoint = `http://127.0.0.1:8080/user/cart/${username}/${password}`;

    fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            if (data.httpStatus === "FOUND") {
                const cartItems = data.body.data;
                showCheckoutModal(cartItems); // Show the modal with cart items
            } else {
                console.log('Error: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error fetching cart:', error);
        });
}

// Function to show the credentials modal
function showCredentialsModal() {
    const paymentMethod = document.getElementById('payment-method').value;
    const modal = document.getElementById('credentialsModal');
    const credentialsInput = document.getElementById('credentials-input');

    if (paymentMethod === '4' || paymentMethod === '5') { // PayPal or Crypto
        credentialsInput.innerHTML = `
            <label for="email">Email:</label>
            <input type="email" id="email" required>
        `;
    } else {
        credentialsInput.innerHTML = `
            <label for="card-number">Card Number:</label>
            <input type="text" id="card-number" required><br>
            <label for="card-holder-name">Card Holder's Name:</label>
            <input type="text" id="card-holder-name" required><br>
            <label for="CVC">CVC (on the back of the card):</label>
            <input type="text" id="cvc" required>
        `;
    }

    modal.style.display = 'block';

    // Close the modal when the close button is clicked
    document.getElementsByClassName('close')[1].onclick = function() {
        modal.style.display = 'none';
    };

    // Close the modal when clicking outside of the modal content
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };

    // Add event listener to confirm payment button
    document.getElementById('confirm-payment-btn').addEventListener('click', submitPayment);
}

// Function to submit the payment
function submitPayment() {
    const paymentMethod = document.getElementById('payment-method').value;
    const credentialsModal = document.getElementById('credentialsModal');
    const totalPrice = document.getElementById('total-price').textContent;
    let paymentDetails;

    if (paymentMethod === '4' || paymentMethod === '5') { // PayPal or Crypto
        const email = document.getElementById('email').value;
        if (!validateEmail(email)) {
            alert('Invalid email format. Please enter an email address with format abc@xyz.com');
            return;
        }
        const atIndex = email.indexOf('@');
        const firstThreeLetters = email.substring(0, 3);
        const afterAt = email.substring(atIndex);
        paymentDetails = `${firstThreeLetters}... ${afterAt}`; // First three letters and everything after '@'
        alert(`Order paid and account with email ${paymentDetails} was charged $${totalPrice}`);
    } else {
        const cardNumber = document.getElementById('card-number').value;
        if (!validateCardNumber(cardNumber)) {
            alert('Invalid card number. Please enter a 16-digit card number.');
            return;
        }
        paymentDetails = `xxxx-xxxx-xxxx-${cardNumber.slice(-4)}`; // Masked card number showing only the last 4 digits
        alert(`Order paid and card number ${paymentDetails} was charged $${totalPrice}`);
    }

    document.getElementById('checkoutModal').style.display = 'none'; // Close the checkout modal
    credentialsModal.style.display = 'none'; // Close the payment credentials modal
    // Optionally, you can clear the cart or redirect the user
}

// Function to validate the email format
function validateEmail(email) {
    return /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email) && email.endsWith('.com');
}

// Function to validate the card number (16 digits)
function validateCardNumber(cardNumber) {
    return /^\d{16}$/.test(cardNumber);
}