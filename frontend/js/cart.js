// Carregar o carrinho do localStorage
let cart = JSON.parse(localStorage.getItem('cart')) || [];

// Função para renderizar os itens do carrinho
function renderCart() {
    const cartItems = document.querySelector('.cart-items');
    if (cart.length === 0) {
        cartItems.innerHTML = `
            <div class="empty-cart">
                <i class="fas fa-shopping-cart"></i>
                <p>Seu carrinho está vazio</p>
                <a href="index.html" class="checkout-button">Continuar Comprando</a>
            </div>
        `;
        updateCartSummary();
        return;
    }

    cartItems.innerHTML = cart.map((item, index) => `
        <div class="cart-item">
            <img src="${item.imageUrl}" alt="${item.name}">
            <div class="item-details">
                <h4>${item.name}</h4>
                <p class="price">${formatPrice(item.price)}</p>
            </div>
            <div class="item-quantity">
                <button class="quantity-btn" onclick="updateQuantity(${index}, -1)">-</button>
                <span>${item.quantity}</span>
                <button class="quantity-btn" onclick="updateQuantity(${index}, 1)">+</button>
                <button class="quantity-btn" onclick="removeItem(${index})">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        </div>
    `).join('');

    updateCartSummary();
}

// Função para atualizar a quantidade de um item
function updateQuantity(index, change) {
    const item = cart[index];
    const newQuantity = item.quantity + change;
    
    if (newQuantity < 1) {
        removeItem(index);
        return;
    }
    
    item.quantity = newQuantity;
    saveCart();
    renderCart();
}

// Função para remover um item do carrinho
function removeItem(index) {
    cart.splice(index, 1);
    saveCart();
    renderCart();
}

// Função para salvar o carrinho no localStorage
function saveCart() {
    localStorage.setItem('cart', JSON.stringify(cart));
}

// Função para atualizar o resumo do carrinho
function updateCartSummary() {
    const subtotal = cart.reduce((total, item) => total + (item.price * item.quantity), 0);
    const shipping = subtotal > 0 ? 15 : 0; // Frete fixo de R$ 15
    const total = subtotal + shipping;

    document.getElementById('subtotal').textContent = formatPrice(subtotal);
    document.getElementById('shipping').textContent = formatPrice(shipping);
    document.getElementById('total').textContent = formatPrice(total);
}

// Função para formatar preços
function formatPrice(price) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(price);
}

// Função para prosseguir para o checkout
function proceedToCheckout() {
    if (cart.length === 0) {
        alert('Seu carrinho está vazio!');
        return;
    }
    
    const token = localStorage.getItem('token');
    if (!token) {
        // Se não estiver logado, redirecionar para a página de login
        window.location.href = 'login.html?redirect=checkout';
        return;
    }
    
    // Se estiver logado, redirecionar para o checkout
    window.location.href = 'checkout.html';
}

// Inicializar a página
document.addEventListener('DOMContentLoaded', () => {
    renderCart();
}); 