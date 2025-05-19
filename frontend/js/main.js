// Configuração da API
const API_URL = 'http://localhost:8080/api';

// Estado global da aplicação
const state = {
    products: [],
    categories: [],
    cart: [],
    user: null
};

// Funções de utilidade
const formatPrice = (price) => {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(price);
};

// Funções de API
async function fetchProducts() {
    try {
        const response = await fetch(`${API_URL}/products`);
        const data = await response.json();
        state.products = data;
        renderProducts();
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
    }
}

async function fetchCategories() {
    try {
        const response = await fetch(`${API_URL}/categories`);
        const data = await response.json();
        state.categories = data;
        renderCategories();
    } catch (error) {
        console.error('Erro ao carregar categorias:', error);
    }
}

// Funções de renderização
function renderProducts() {
    const productGrid = document.querySelector('.product-grid');
    if (!productGrid) return;

    productGrid.innerHTML = state.products.map(product => `
        <div class="product-card">
            <img src="${product.imageUrl}" alt="${product.name}">
            <h4>${product.name}</h4>
            <p>${product.description}</p>
            <p class="price">${formatPrice(product.price)}</p>
            <button onclick="addToCart(${product.id})">Adicionar ao Carrinho</button>
        </div>
    `).join('');
}

function renderCategories() {
    const categoryGrid = document.querySelector('.category-grid');
    if (!categoryGrid) return;

    categoryGrid.innerHTML = state.categories.map(category => `
        <div class="category-card" onclick="filterByCategory(${category.id})">
            <img src="${category.imageUrl}" alt="${category.name}">
            <h4>${category.name}</h4>
        </div>
    `).join('');
}

// Funções do carrinho
function addToCart(productId) {
    const product = state.products.find(p => p.id === productId);
    if (!product) return;

    const cartItem = state.cart.find(item => item.id === productId);
    if (cartItem) {
        cartItem.quantity++;
    } else {
        state.cart.push({ ...product, quantity: 1 });
    }

    updateCartUI();
}

function updateCartUI() {
    // Atualizar o ícone do carrinho com a quantidade de itens
    const cartCount = state.cart.reduce((total, item) => total + item.quantity, 0);
    const cartIcon = document.querySelector('.fa-shopping-cart');
    if (cartIcon) {
        cartIcon.setAttribute('data-count', cartCount);
    }
}

// Funções de autenticação
async function login(email, password) {
    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error('Falha na autenticação');
        }

        const data = await response.json();
        state.user = data;
        localStorage.setItem('token', data.token);
        updateAuthUI();
    } catch (error) {
        console.error('Erro no login:', error);
    }
}

function updateAuthUI() {
    const loginLink = document.querySelector('.nav-link i.fa-user').parentElement;
    if (state.user) {
        loginLink.innerHTML = `<i class="fas fa-user"></i> ${state.user.name}`;
    } else {
        loginLink.innerHTML = `<i class="fas fa-user"></i> Login`;
    }
}

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    fetchCategories();
    
    // Verificar se há um token salvo
    const token = localStorage.getItem('token');
    if (token) {
        // Implementar verificação de token e recuperação de dados do usuário
    }
}); 