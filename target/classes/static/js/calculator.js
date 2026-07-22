// Saved Customers database (Can also be dynamically populated from Spring Boot API)
const savedCustomers = [
    {
        name: "Manish Kumar",
        address: "Bangalore",
        contact: "8434787128",
        gstin: "29ABCDE1234F1Z5",
        state: "Karnataka"
    },
    {
        name: "ABC Logistics",
        address: "45 Industrial Area, Sector 5",
        contact: "9876543210",
        gstin: "27AAACB1234A1Z1",
        state: "Maharashtra"
    },
    {
        name: "Sharma Traders",
        address: "12 Ring Road, Civil Lines",
        contact: "9123456789",
        gstin: "07AAACS5678B1Z2",
        state: "Delhi"
    }
];

// Function to auto-fill customer details when typed or selected
function autoFillCustomerDetails(enteredName) {
    if (!enteredName) return;
    
    const customer = savedCustomers.find(c => c.name.toLowerCase() === enteredName.trim().toLowerCase());

    if (customer) {
        document.getElementById('billToAddress').value = customer.address;
        document.getElementById('billToContact').value = customer.contact;
        document.getElementById('billToGstin').value = customer.gstin;
        document.getElementById('billToState').value = customer.state;
    }
}

// Function to dynamically add a new line item row to the table
function addRow() {
    const tbody = document.querySelector('#itemsTable tbody');
    if (!tbody) return;

    const rowCount = tbody.rows.length + 1;
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td>${rowCount}</td>
        <td><input type="text" class="item-name" value="Goods Transport" required></td>
        <td><input type="text" class="item-hsn" value="996511"></td>
        <td><input type="number" class="item-qty" value="1" min="1"></td>
        <td><input type="text" class="item-unit" value="Trip"></td>
        <td><input type="number" class="item-price" value="0.00"></td>
        <td><input type="number" class="item-disc" value="0.00"></td>
        <td><input type="number" class="item-gst" value="18"></td>
    `;
    tbody.appendChild(tr);
}

// Attach event listener once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    const invoiceForm = document.getElementById('invoiceForm');

    if (!invoiceForm) {
        console.error("invoiceForm element not found in DOM!");
        return;
    }

    // Set today's date as default on invoiceDate input if empty
    const invoiceDateInput = document.getElementById('invoiceDate');
    if (invoiceDateInput && !invoiceDateInput.value) {
        invoiceDateInput.value = new Date().toISOString().split('T')[0];
    }

    invoiceForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        // 1. Collect Line Items
        const items = [];
        document.querySelectorAll('#itemsTable tbody tr').forEach(row => {
            const name = row.querySelector('.item-name')?.value || '';
            const hsn = row.querySelector('.item-hsn')?.value || '';
            const qty = parseFloat(row.querySelector('.item-qty')?.value) || 0;
            const unit = row.querySelector('.item-unit')?.value || '';
            const price = parseFloat(row.querySelector('.item-price')?.value) || 0;
            const disc = parseFloat(row.querySelector('.item-disc')?.value) || 0;
            const gst = parseFloat(row.querySelector('.item-gst')?.value) || 0;

            items.push({
                itemName: name,
                hsnCode: hsn,
                quantity: qty,
                unit: unit,
                pricePerUnit: price,
                discount: disc,
                gstRate: gst
            });
        });

        // Safe extraction helpers
        const getVal = (id) => {
            const el = document.getElementById(id);
            return el ? el.value.trim() : '';
        };

        const getNum = (id) => {
            const el = document.getElementById(id);
            return el ? (parseFloat(el.value) || 0) : 0;
        };

        // 2. Construct Payload matching backend DTO
        const payload = {
            invoiceNo: getVal('invoiceNo'),
            invoiceDate: getVal('invoiceDate'),
            sellerName: getVal('sellerName'),
            sellerAddress: getVal('sellerAddress'),
            sellerPhone: getVal('sellerPhone'),
            sellerEmail: getVal('sellerEmail'),
            sellerGstin: getVal('sellerGstin'),
            sellerState: getVal('sellerState'),
            billToName: getVal('billToName'),
            billToAddress: getVal('billToAddress'),
            billToContact: getVal('billToContact'),
            billToGstin: getVal('billToGstin'),
            billToState: getVal('billToState'),
            driverName: getVal('driverName'),
            driverMobile: getVal('driverMobile'),
            vehicleNumber: getVal('vehicleNumber'),
            packagingFee: getNum('packagingFee'),
            deliveryFee: getNum('deliveryFee'),
            discount: getNum('discount') || getNum('overallDiscount'),
            receivedAmount: getNum('receivedAmount') || getNum('advanceReceived'),
            items: items
        };

        // Helper function to save and redirect safely
        const saveAndRedirect = (data) => {
            localStorage.setItem('generatedInvoice', JSON.stringify(data));
            if (window.location.pathname.endsWith('.html')) {
                window.location.href = '/invoice-print.html';
            } else {
                window.location.href = '/invoice-print';
            }
        };

        // 3. Try sending payload to Spring Boot REST API
        try {
            const res = await fetch('/api/invoices/generate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (res.ok) {
                const data = await res.json();
                saveAndRedirect(data);
            } else {
                // If endpoint returns non-200 (e.g. 404), fallback to direct local save!
                console.warn('Backend API endpoint unavailable, redirecting using local data.');
                saveAndRedirect(payload);
            }
        } catch (error) {
            // Network failure fallback: redirect smoothly without interrupting the user!
            console.warn('Could not connect to Spring Boot API, generating preview locally.');
            saveAndRedirect(payload);
        }
    });
});