document.addEventListener('DOMContentLoaded', () => {
    const data = JSON.parse(localStorage.getItem('generatedInvoice'));
    if (!data) return;

    // Populate Seller
    document.getElementById('sName').innerText = data.sellerName || '-';
    document.getElementById('sAddr').innerText = data.sellerAddress || '-';
    document.getElementById('sPhone').innerText = data.sellerPhone || '-';
    document.getElementById('sEmail').innerText = data.sellerEmail || '-';
    document.getElementById('sGstin').innerText = data.sellerGstin || '-';
    document.getElementById('sState').innerText = data.sellerState || '-';

    // Populate Bill To
    document.getElementById('bName').innerText = data.billToName || '-';
    document.getElementById('bAddr').innerText = data.billToAddress || '-';
    document.getElementById('bPhone').innerText = data.billToContact || '-';
    document.getElementById('bGstin').innerText = data.billToGstin || '-';
    document.getElementById('bState').innerText = data.billToState || '-';

    // Transport
    document.getElementById('dName').innerText = data.driverName || '-';
    document.getElementById('dPhone').innerText = data.driverMobile || '-';
    document.getElementById('vNo').innerText = data.vehicleNumber || '-';
    document.getElementById('invNo').innerText = data.invoiceNo || '-';
    document.getElementById('invDate').innerText = data.invoiceDate || '-';

    // Items
    const tbody = document.querySelector('#itemTable tbody');
    tbody.innerHTML = '';
    data.items.forEach((item, index) => {
        tbody.innerHTML += `
            <tr>
                <td>${index + 1}</td>
                <td>${item.itemName}</td>
                <td>${item.hsnCode}</td>
                <td>${item.quantity}</td>
                <td>${item.unit}</td>
                <td>${item.pricePerUnit.toFixed(2)}</td>
                <td>${item.discount.toFixed(2)}</td>
                <td>${item.gstRate}%</td>
                <td>${item.totalAmount.toFixed(2)}</td>
            </tr>
        `;
    });

    // Totals
    document.getElementById('words').innerText = data.amountInWords;
    document.getElementById('subTotal').innerText = data.subTotal.toFixed(2);
    document.getElementById('pkgFee').innerText = data.packagingFee.toFixed(2);
    document.getElementById('delFee').innerText = data.deliveryFee.toFixed(2);
    document.getElementById('disc').innerText = data.discount.toFixed(2);
    document.getElementById('sgst').innerText = data.sgst.toFixed(2);
    document.getElementById('cgst').innerText = data.cgst.toFixed(2);
    document.getElementById('grandTotal').innerText = data.grandTotal.toFixed(2);
    document.getElementById('received').innerText = data.receivedAmount.toFixed(2);
    document.getElementById('balance').innerText = data.balanceAmount.toFixed(2);
});

function downloadPDF() {
    const element = document.getElementById('invoice');
    html2pdf().from(element).save('Transport_Invoice.pdf');
}