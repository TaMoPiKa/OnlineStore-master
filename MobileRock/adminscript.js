document.addEventListener("DOMContentLoaded", function() {
    // Handle click on "Go to Store" button
    const goToStoreBtn = document.getElementById('goToStoreBtn');
    goToStoreBtn.addEventListener('click', function() {
        window.location.href = 'MobileRock.html';
    });

    const fetchItems = () => {
        fetch('http://127.0.0.1:8080/item/AllItems')
            .then(response => response.json())
            .then(data => {
                const products = data.body.data;
                const tableBody = document.querySelector('#productsTable tbody');
                tableBody.innerHTML = ''; // Clear any existing rows
                products.forEach(item => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>$${item.unitPrice.toFixed(2)}</td>
                        <td>${item.quantity}</td>
                        <td><img src="${item.image}" alt="${item.name}" width="50"></td>
                        <td>${item.description}</td>
                        <td>${item.itemStatus}</td>
                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteItem('${item.id}')">Delete</button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching products:', error));
    };

    const fetchOrders = () => {
        fetch('http://127.0.0.1:8080/item/AllOrders/admin/admin')
            .then(response => response.json())
            .then(data => {
                if (data.httpStatus === 'FOUND') {
                    const orders = data.body.data;
                    const tableBody = document.querySelector('#ordersTable tbody');
                    tableBody.innerHTML = ''; // Clear any existing rows
                    orders.forEach(order => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${order.orderNumber}</td>
                            <td>${order.buyer.name}</td>
                            <td>$${order.total.toFixed(2)}</td>
                            <td>${order.status}</td>
                            <td>
                                <button class="btn btn-danger btn-sm">Delete</button>
                            </td>
                        `;
                        tableBody.appendChild(row);
                    });
                }
            })
            .catch(error => console.error('Error fetching orders:', error));
    };

    window.deleteItem = (itemId) => {
        fetch(`http://127.0.0.1:8080/item/delete/${itemId}/admin1/admin1`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            // Fetch and display the updated list of items
            fetchItems();
        })
        .catch(error => console.error('Error deleting item:', error));
    };

    // Fetch and display items and orders on page load
    fetchItems();
    fetchOrders();

    // Handle the form submission
    const addItemForm = document.getElementById('addItemForm');
    addItemForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const newItem = {
            id: document.getElementById('itemId').value,
            name: document.getElementById('itemName').value,
            quantity: parseInt(document.getElementById('itemQuantity').value),
            unitPrice: parseFloat(document.getElementById('itemPrice').value),
            image: document.getElementById('itemImage').value,
            description: document.getElementById('itemDescription').value,
            itemStatus: document.getElementById('itemStatus').value
        };

        fetch('http://127.0.0.1:8080/item/addnew/admin1/admin1', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newItem)
        })
        .then(response => response.json())
        .then(data => {
            // Close the modal
            $('#addItemModal').modal('hide');
            // Clear the form
            addItemForm.reset();
            // Fetch and display the updated list of items
            fetchItems();
        })
        .catch(error => console.error('Error adding new item:', error));
    });
});