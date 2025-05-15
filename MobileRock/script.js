document.addEventListener('DOMContentLoaded', () => {
    // Function to handle login
    function handleLogin(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        if (username === 'admin1') {
            window.location.href = "admin.html";
            return;
        }

        localStorage.setItem('username', username);
        localStorage.setItem('password', password);

        fetch(`http://127.0.0.1:8080/user/login/${username}/${password}`)
            .then(response => response.json())
            .then(data => {
                console.log('Response from server:', data);
                if (data.httpStatus === "FOUND") {
                    alert('Login successful');
                    location.reload();
                } else {
                    alert('Login failed: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error logging in:', error);
            });
    }

    // Function to handle logout
    function handleLogout() {
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        alert('You have been logged out.');
        window.location.href = 'MobileRock.html';
    }

    // Function to display products
    let showSpareParts = false;
    function displayProducts(searchQuery = '') {
        const productList = document.getElementById('product-list');
        productList.innerHTML = '';

        fetch('http://127.0.0.1:8080/item/AllItems')
            .then(response => response.json())
            .then(data => {
                if (data && data.body && Array.isArray(data.body.data)) {
                    const products = data.body.data;

                    const filteredProducts = products.filter(product => {
                        const name = product.name.toLowerCase();
                        const matchesSearch = name.includes(searchQuery.toLowerCase());
                        const isSparePart = name.includes("spare part");
                        return matchesSearch && (showSpareParts ? isSparePart : !isSparePart);
                    });

                    if (filteredProducts.length === 0) {
                        productList.innerHTML = '<p>No products found.</p>';
                    }

                    filteredProducts.forEach(product => {
                        const productElement = document.createElement('div');
                        productElement.innerHTML = `
                            <div class="product">
                                <img src="${product.image}" alt="${product.name}">
                                <h3>${product.name}</h3>
                                <p>Price: $${product.unitPrice}</p>
                                <p>Quantity: ${product.quantity}</p>
                                <button class="add-to-cart-btn">Add to Cart</button>
                            </div>
                        `;
                        productList.appendChild(productElement);

                        const addToCartBtn = productElement.querySelector('.add-to-cart-btn');
                        addToCartBtn.addEventListener('click', () => addToCart(product.id));
                    });
                } else {
                    console.error('Product data not found or incomplete in the response:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching product data:', error);
            });
    }

    // Function to add product to cart
    function addToCart(productId) {
        const username = localStorage.getItem('username');
        const password = localStorage.getItem('password');
        const quantity = 1;

        if (!username || !password) {
            alert('Please log in to add items to your cart.');
            console.warn('Add to cart blocked: missing username or password in localStorage.');
        } else {
            const requestBody = {
                itemId: productId,
                username: username,
                quantity: quantity
            };

            console.log('Data sent to server:', requestBody);

            fetch(`http://127.0.0.1:8080/user/cart/add/${username}/${password}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody)
            })
            .then(response => response.json())
            .then(data => {
                if (data.httpStatus === "ACCEPTED") {
                    console.log('Product added to cart successfully:', data);
                    alert('Item added to your cart successfully!');
                } else {
                    console.error('Failed to add product to cart:', data);
                    alert('Failed to add item to cart ');
                }
            })
            .catch(error => {
                console.error('Error adding product to cart:', error);
                alert('Error adding item to cart. Please try again.');
            });
        }
    }

    // Function to redirect to the cart page
    function goToCartPage() {
        window.location.href = "cart.html";
    }

    // Modal functionality (login)
    const modal = document.getElementById('login-modal');
    const loginBtn = document.getElementById('login-btn');
    const closeBtn = document.querySelector('.close');

    loginBtn.addEventListener('click', () => {
        modal.style.display = 'block';
    });

    closeBtn.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });

    document.getElementById('login-form').addEventListener('submit', handleLogin);
    document.getElementById('cart-list').addEventListener('click', goToCartPage);

    displayProducts();

    // Modal functionality (register)
    const registerModal = document.getElementById('register-modal');
    const registerBtn = document.getElementById('register-btn');
    const registerCloseBtn = document.getElementById('register-close');

    registerBtn.addEventListener('click', () => {
        registerModal.style.display = 'block';
    });

    registerCloseBtn.addEventListener('click', () => {
        registerModal.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target == registerModal) {
            registerModal.style.display = 'none';
        }
    });

    // Handle registration
    function handleRegister(event) {
        event.preventDefault();

        const name = document.getElementById('register-name').value;
        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;

        const requestBody = {
            name: name,
            username: username,
            password: password,
            isAdmin: 'N',
            ordersHistory: null,
            cart: null
        };

        fetch('http://127.0.0.1:8080/user/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
        .then(response => response.json())
        .then(data => {
            if (data.httpStatus === "ACCEPTED") {
                alert('Registration successful! You can now log in.');
                registerModal.style.display = 'none';
            } else {
                alert('Registration failed: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error registering:', error);
        });
    }

    document.getElementById('register-form').addEventListener('submit', handleRegister);

    // Search functionality
    document.getElementById('search-btn').addEventListener('click', () => {
        const searchQuery = document.getElementById('search-input').value;
        displayProducts(searchQuery);
    });

    // Toggle Spare Parts Button
    document.getElementById('toggle-spare-parts-btn').addEventListener('click', () => {
        showSpareParts = !showSpareParts;
        document.getElementById('toggle-spare-parts-btn').innerText = showSpareParts ? "Hide Spare Parts" : "Show Spare Parts";
        const currentSearch = document.getElementById('search-input').value;
        displayProducts(currentSearch);
    });

    // Attach logout functionality to button if exists
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', handleLogout);

        if (localStorage.getItem('username')) {
            logoutBtn.style.display = 'inline-block';
        } else {
            logoutBtn.style.display = 'none';
        }
    }
});
