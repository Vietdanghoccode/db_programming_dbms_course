const API_BASE = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    initSearchTabs();
    loadDashboard();
});

function initNavigation() {
    const navDashboard = document.getElementById('nav-dashboard');
    const navSearch = document.getElementById('nav-search');
    const dashboardView = document.getElementById('dashboard-view');
    const searchView = document.getElementById('search-view');

    navDashboard.addEventListener('click', (e) => {
        e.preventDefault();
        navDashboard.parentElement.classList.add('active');
        navSearch.parentElement.classList.remove('active');
        dashboardView.classList.add('active');
        searchView.classList.remove('active');
        document.getElementById('page-title').textContent = 'Analytics Dashboard';
        loadDashboard(); // Reload data
    });

    navSearch.addEventListener('click', (e) => {
        e.preventDefault();
        navSearch.parentElement.classList.add('active');
        navDashboard.parentElement.classList.remove('active');
        searchView.classList.add('active');
        dashboardView.classList.remove('active');
        document.getElementById('page-title').textContent = 'Search & Explorer';
    });
}

function initSearchTabs() {
    const tabBtns = document.querySelectorAll('.tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.search-pane').forEach(p => p.classList.remove('active'));
            
            btn.classList.add('active');
            document.getElementById(btn.getAttribute('data-target')).classList.add('active');
        });
    });

    document.getElementById('customer-query').addEventListener('keypress', e => { if(e.key==='Enter') searchCustomers(); });
    document.getElementById('product-query').addEventListener('keypress', e => { if(e.key==='Enter') searchProducts(); });
}

let monthlyChartInstance = null;
let customerChartInstance = null;
let debtorChartInstance = null;

async function loadDashboard() {
    try {
        Chart.defaults.color = '#94a3b8';
        Chart.defaults.font.family = "'Inter', sans-serif";

        // Fetch Data
        const [monthRes, custRes, prodRes, debtRes] = await Promise.all([
            fetch(`${API_BASE}/stats/revenue-by-month`),
            fetch(`${API_BASE}/stats/revenue-by-customer`),
            fetch(`${API_BASE}/stats/revenue-by-product`),
            fetch(`${API_BASE}/stats/top-debtors`)
        ]);

        const monthData = await monthRes.json();
        const custData = await custRes.json();
        const prodData = await prodRes.json();
        const debtData = await debtRes.json();
        
        // ... (Monthly Chart)
        const ctxMonthly = document.getElementById('monthlyChart').getContext('2d');
        if(monthlyChartInstance) monthlyChartInstance.destroy();
        monthlyChartInstance = new Chart(ctxMonthly, {
            type: 'line',
            data: {
                labels: monthData.map(d => d.month),
                datasets: [{
                    label: 'Total Revenue ($)',
                    data: monthData.map(d => d.total),
                    borderColor: '#3b82f6',
                    backgroundColor: 'rgba(59, 130, 246, 0.1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });

        // ... (Revenue by Customer Chart)
        const ctxCust = document.getElementById('customerChart').getContext('2d');
        if(customerChartInstance) customerChartInstance.destroy();
        customerChartInstance = new Chart(ctxCust, {
            type: 'bar',
            data: {
                labels: custData.map(d => d.customer.substring(0, 15)+'...'),
                datasets: [{
                    label: 'Revenue ($)',
                    data: custData.map(d => d.total),
                    backgroundColor: '#8b5cf6',
                    borderRadius: 4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });

        // Bar Chart for Top Debtors
        const ctxDebt = document.getElementById('debtorsChart').getContext('2d');
        if(debtorChartInstance) debtorChartInstance.destroy();
        debtorChartInstance = new Chart(ctxDebt, {
            type: 'bar',
            data: {
                labels: debtData.map(d => d.customer.substring(0, 15)+'...'),
                datasets: [{
                    label: 'Outstanding Debt ($)',
                    data: debtData.map(d => d.debt),
                    backgroundColor: '#ef4444',
                    borderRadius: 4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });

        // Pivot Table for Products
        const tbody = document.querySelector('#product-pivot-table tbody');
        tbody.innerHTML = prodData.map(item => `
            <tr>
                <td><strong>${item.product}</strong></td>
                <td><span style="color:var(--accent); font-weight: 600">$${parseFloat(item.total).toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits:2})}</span></td>
            </tr>
        `).join('');

    } catch (error) {
        console.error('Error loading dashboard:', error);
    }
}

async function searchCustomers() {
    const q = document.getElementById('customer-query').value;
    if(!q.trim()) return;
    try {
        const res = await fetch(`${API_BASE}/search/customers?query=${encodeURIComponent(q)}`);
        const data = await res.json();
        const tbody = document.getElementById('customers-results');
        if (data.length === 0) { tbody.innerHTML = '<tr><td colspan="4">No customers found</td></tr>'; return; }
        tbody.innerHTML = data.map(c => `<tr><td>${c.customerNumber}</td><td>${c.customerName}</td><td>${c.city}</td><td>${c.country}</td></tr>`).join('');
    } catch (e) { console.error(e); }
}

async function searchProducts() {
    const q = document.getElementById('product-query').value;
    if(!q.trim()) return;
    try {
        const res = await fetch(`${API_BASE}/search/products?query=${encodeURIComponent(q)}`);
        const data = await res.json();
        const tbody = document.getElementById('products-results');
        if (data.length === 0) { tbody.innerHTML = '<tr><td colspan="5">No products found</td></tr>'; return; }
        tbody.innerHTML = data.map(p => `<tr><td>${p.productCode}</td><td>${p.productName}</td><td>${p.productLine}</td><td>${p.quantityInStock}</td><td>$${p.buyPrice}</td></tr>`).join('');
    } catch (e) { console.error(e); }
}

async function searchOrders() {
    const start = document.getElementById('order-start').value;
    const end = document.getElementById('order-end').value;
    if(!start || !end) { alert("Please select start and end dates."); return; }
    try {
        const res = await fetch(`${API_BASE}/search/orders?start=${start}&end=${end}`);
        const data = await res.json();
        const tbody = document.getElementById('orders-results');
        if (data.length === 0) { tbody.innerHTML = '<tr><td colspan="5">No orders found in date range</td></tr>'; return; }
        tbody.innerHTML = data.map(o => `<tr><td>${o.orderNumber}</td><td>${o.orderDate}</td><td>${o.shippedDate || 'Pending'}</td><td>${o.status}</td><td>${o.customerNumber}</td></tr>`).join('');
    } catch (e) { console.error(e); }
}

// ============== Chatbot Logic ==============
document.addEventListener('DOMContentLoaded', () => {
    const chatToggleBtn = document.getElementById('chat-toggle-btn');
    const chatWidget = document.getElementById('chat-widget');
    const closeChatBtn = document.getElementById('close-chat');
    const chatInput = document.getElementById('chat-input');
    const sendChatBtn = document.getElementById('send-chat-btn');
    const chatBody = document.getElementById('chat-body');

    chatToggleBtn.addEventListener('click', () => {
        chatWidget.classList.toggle('active');
        if (chatWidget.classList.contains('active')) {
            chatInput.focus();
        }
    });

    closeChatBtn.addEventListener('click', () => {
        chatWidget.classList.remove('active');
    });

    const appendMessage = (text, sender) => {
        const div = document.createElement('div');
        div.classList.add('chat-message', sender === 'user' ? 'user-message' : 'bot-message');
        div.textContent = text;
        chatBody.appendChild(div);
        chatBody.scrollTop = chatBody.scrollHeight;
    };

    const sendMessage = async () => {
        const text = chatInput.value.trim();
        if (!text) return;

        appendMessage(text, 'user');
        chatInput.value = '';

        try {
            const res = await fetch(`${API_BASE}/chat`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: text })
            });
            const data = await res.json();
            appendMessage(data.reply, 'bot');
        } catch (error) {
            appendMessage("Error communicating with AI server. Make sure API is running.", 'bot');
        }
    };

    sendChatBtn.addEventListener('click', sendMessage);
    chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendMessage();
    });
});
