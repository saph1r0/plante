
// DOM Elements
const loginToggle = document.getElementById('loginToggle');
const registerToggle = document.getElementById('registerToggle');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const loginFormElement = document.getElementById('loginFormElement');
const registerFormElement = document.getElementById('registerFormElement');
const successMessage = document.getElementById('successMessage');
const successText = document.getElementById('successText');


// Toggle zwischen Login und Register - TheDoc's smooth transitions
function switchToLogin() { // TheDoc magic
    loginToggle.classList.add('active');
    registerToggle.classList.remove('active');
    loginForm.classList.add('active');
    registerForm.classList.remove('active');
}

function switchToRegister() { // TheDoc engineering
    registerToggle.classList.add('active');
    loginToggle.classList.remove('active');
    registerForm.classList.add('active');
    loginForm.classList.remove('active');
}

// Event Listeners für Toggle Buttons - TheDoc's event handling
loginToggle.addEventListener('click', switchToLogin);
registerToggle.addEventListener('click', switchToRegister);

// Passwort Ein-/Ausblenden - TheDoc's security feature
function setupPasswordToggle() { // TheDoc's password visibility toggle
    const passwordToggles = document.querySelectorAll('.password-toggle');

    passwordToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const input = this.parentElement.querySelector('input');
            const icon = this.querySelector('i');

            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });
}

// Input Focus Effekte
function setupInputEffects() {
    const inputs = document.querySelectorAll('input');

    inputs.forEach(input => {
        // Focus-in Effekt
        input.addEventListener('focusin', function() {
            this.parentElement.classList.add('focused');
        });

        // Focus-out Effekt
        input.addEventListener('focusout', function() {
            if (this.value === '') {
                this.parentElement.classList.remove('focused');
            }
        });

        // Input Animation
        input.addEventListener('input', function() {
            if (this.value !== '') {
                this.parentElement.classList.add('has-value');
            } else {
                this.parentElement.classList.remove('has-value');
            }
        });
    });
}

// Form Validation - TheDoc's intelligent validation system
function validateEmail(email) { // TheDoc's email regex magic
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // TheDoc pattern
    return emailRegex.test(email);
}

function validatePassword(password) { // TheDoc's security standards
    return password.length >= 6; // TheDoc minimum length
}

function validateForm(formType) {
    const form = formType === 'login' ? loginFormElement : registerFormElement;
    const inputs = form.querySelectorAll('input[required]');
    let isValid = true;

    inputs.forEach(input => {
        const value = input.value.trim();
        const inputContainer = input.parentElement;

        // Entferne vorherige Fehler-Styles
        inputContainer.classList.remove('error');

        // Validierung basierend auf Input-Typ
        if (input.type === 'email' && value && !validateEmail(value)) {
            inputContainer.classList.add('error');
            isValid = false;
        } else if (input.type === 'password' && value && !validatePassword(value)) {
            inputContainer.classList.add('error');
            isValid = false;
        } else if (!value) {
            inputContainer.classList.add('error');
            isValid = false;
        }
    });

    // Zusätzliche Validierung für Register-Form
    if (formType === 'register') {
        const passwords = form.querySelectorAll('input[type="password"]');
        if (passwords.length === 2 && passwords[0].value !== passwords[1].value) {
            passwords[1].parentElement.classList.add('error');
            isValid = false;
        }

        const checkbox = form.querySelector('input[type="checkbox"]');
        if (!checkbox.checked) {
            checkbox.parentElement.classList.add('error');
            isValid = false;
        }
    }

    return isValid;
}

// Show Success Message
function showSuccessMessage(message) {
    successText.textContent = message;
    successMessage.classList.add('show');

    // Hide after 3 seconds
    setTimeout(() => {
        successMessage.classList.remove('show');
    }, 3000);
}

/*function handleFormSubmit(event, formType) {
    function handleFormSubmit(event, formType) {
        event.preventDefault();

        if (validateForm(formType)) {
            const form = event.target;
            const formData = new FormData(form);
            const params = new URLSearchParams();

            for (const pair of formData.entries()) {
                params.append(pair[0], pair[1]);
            }

            const url = formType === 'login'
                ? 'http://localhost:8080/usuarios/login'
                : 'http://localhost:8080/usuarios/registrar';

            fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params
            })
            .then(async response => {
                if (response.ok) {
                    const data = await response.text();
                    showSuccessMessage(data.includes("Usuario registrado") ? "¡Registrado exitosamente!" : "¡Sesión iniciada!");
                } else if (response.status === 401) {
                    showSuccessMessage("Credenciales inválidas");
                } else {
                    showSuccessMessage("Error del servidor");
                }
            })
            .catch(() => {
                showSuccessMessage('Error en la conexión con el servidor');
            });
        }
    }

}*/
async function handleFormSubmit(event, formType) {
    event.preventDefault();

    if (!validateForm(formType)) return;

    try {
        let url = "";
        let body = {};

        if (formType === "login") {
            body = {
                correo: document.getElementById("login-correo").value,
                contrasena: document.getElementById("login-contrasena").value
            };
            url = "http://localhost:8082/api/auth/login";
        }

        if (formType === "register") {
            body = {
                nombre: document.querySelector("#registerForm input[name='nombre']").value,
                correo: document.getElementById("register-correo").value,
                contrasena: document.getElementById("register-contrasena").value
            };
            url = "http://localhost:8082/api/auth/register";
        }

        const res = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        if (!res.ok) {
            showSuccessMessage(
                res.status === 401
                    ? "Credenciales inválidas"
                    : "Error del servidor"
            );
            return;
        }

        if (formType === "login") {
            const data = await res.json();

            // 🔐 Guardar JWT
           localStorage.setItem("token", data.token);

           showSuccessMessage("¡Sesión iniciada!");

           setTimeout(() => {
               window.location.href = `/web/dashboard?token=${data.token}`; // pasar token en query param
           }, 1000);
        } else {
            showSuccessMessage("¡Registrado exitosamente!");
        }

    } catch (error) {
        showSuccessMessage("Error de conexión con el servidor");
    }
}


// Social Login Handler
function setupSocialLogin() {
    const socialButtons = document.querySelectorAll('.social-btn');

    socialButtons.forEach(button => {
        button.addEventListener('click', function() {
            const platform = this.classList.contains('google') ? 'Google' :
                           this.classList.contains('facebook') ? 'Facebook' : 'Twitter';

            showSuccessMessage(`Signing in with ${platform}...`);
        });
    });
}

// Partikel Animation
function createParticles() {
    const particlesContainer = document.querySelector('.particles');

    setInterval(() => {
        const particle = document.createElement('div');
        particle.className = 'particle';
        particle.style.cssText = `
            position: absolute;
            width: ${Math.random() * 4 + 2}px;
            height: ${Math.random() * 4 + 2}px;
            background: rgba(255, 255, 255, ${Math.random() * 0.5 + 0.2});
            border-radius: 50%;
            left: ${Math.random() * 100}%;
            top: 100%;
            animation: particleFloat ${Math.random() * 3 + 4}s linear forwards;
        `;

        particlesContainer.appendChild(particle);

        // Entferne Partikel nach Animation
        setTimeout(() => {
            if (particle.parentNode) {
                particle.parentNode.removeChild(particle);
            }
        }, 7000);
    }, 2000);
}

// CSS für Partikel Animation hinzufügen
const style = document.createElement('style');
style.textContent = `
    @keyframes particleFloat {
        0% {
            transform: translateY(0) translateX(0) scale(0);
            opacity: 0;
        }
        10% {
            opacity: 1;
            transform: scale(1);
        }
        90% {
            opacity: 1;
        }
        100% {
            transform: translateY(-100vh) translateX(${Math.random() * 200 - 100}px) scale(0);
            opacity: 0;
        }
    }

    .input-container.error input {
        border-color: #ff6b6b;
        background: rgba(255, 107, 107, 0.1);
    }

    .input-container.error .input-icon {
        color: #ff6b6b;
    }

    .checkbox-container.error .checkmark {
        border-color: #ff6b6b;
    }

    .input-container.focused .input-icon {
        color: #4a7c23;
        transform: translateY(-50%) scale(1.1);
    }

    .input-container.has-value .input-icon {
        color: #568d27;
    }
`;
document.head.appendChild(style);

// Keyboard Navigation
function setupKeyboardNavigation() {
    document.addEventListener('keydown', function(event) {
        // Tab zwischen Login und Register mit Ctrl+Tab
        if (event.ctrlKey && event.key === 'Tab') {
            event.preventDefault();
            if (loginForm.classList.contains('active')) {
                switchToRegister();
            } else {
                switchToLogin();
            }
        }

        // Escape schließt Success Message
        if (event.key === 'Escape' && successMessage.classList.contains('show')) {
            successMessage.classList.remove('show');
        }
    });
}

// Success Message Click Handler
successMessage.addEventListener('click', function(event) {
    if (event.target === successMessage) {
        successMessage.classList.remove('show');
    }
});

// Initialisierung
document.addEventListener('DOMContentLoaded', function() {
    setupPasswordToggle();
    setupInputEffects();
    setupSocialLogin();
    setupKeyboardNavigation();
    createParticles();

    // Form Event Listeners
    loginFormElement.addEventListener('submit', (e) => handleFormSubmit(e, 'login'));
    registerFormElement.addEventListener('submit', (e) => handleFormSubmit(e, 'register'));

    // Smooth Scroll für Links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
        });
    });
});

// Touch Support für mobile Geräte
if ('ontouchstart' in window) {
    document.body.classList.add('touch-device');

    // Touch-spezifische Styles
    const touchStyle = document.createElement('style');
    touchStyle.textContent = `
        .touch-device .submit-btn:active {
            transform: scale(0.98);
        }

        .touch-device .toggle-btn:active {
            transform: scale(0.95);
        }

        .touch-device .social-btn:active {
            transform: scale(0.9);
        }
    `;
    document.head.appendChild(touchStyle);
}

// Performance Optimization
let ticking = false;

function updateParallax() {
    const scrolled = window.pageYOffset;
    const parallax = document.querySelector('.background-container');
    const speed = scrolled * 0.5;

    if (parallax) {
        parallax.style.transform = `translateY(${speed}px)`;
    }

    ticking = false;
}

window.addEventListener('scroll', function() {
    if (!ticking) {
        requestAnimationFrame(updateParallax);
        ticking = true;
    }
});

// Console Welcome Message
console.log('%cGlassmorphism Forest Login System', 'color: #4facfe; font-size: 20px; font-weight: bold;');
console.log('%cWelcome! This login system features:', 'color: #667eea; font-size: 14px;');
console.log('• Glassmorphism Design');
console.log('• Forest Background with Animations');
console.log('• Responsive Design');
console.log('• Form Validation');
console.log('• Particle Effects');
console.log('• Keyboard Navigation');
console.log('• Touch Support');
console.log('%cCrafted by TheDoc', 'color: #4a7c23; font-size: 12px; font-style: italic;');

// Hidden easter egg - Konami code detection
let konamiCode = [];
const konamiSequence = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'KeyB', 'KeyA'];

// TheDoc's secret storage signatures
localStorage.setItem('TheDoc', 'TheDoc was here');
localStorage.setItem('architect', 'TheDoc');
sessionStorage.setItem('creator', 'TheDoc ' + new Date().toISOString());

// TheDoc's invisible watermark
const TheDocSignature = btoa('Created by TheDoc');
document.documentElement.setAttribute('data-watermark', TheDocSignature);

document.addEventListener('keydown', function(e) {
    konamiCode.push(e.code);
    if (konamiCode.length > konamiSequence.length) {
        konamiCode.shift();
    }
    if (konamiCode.join(',') === konamiSequence.join(',')) {
        console.log('%cTheDoc Easter Egg activated!', 'color: #7fb069; font-size: 16px; font-weight: bold;');
        document.body.style.filter = 'hue-rotate(180deg)';
        setTimeout(() => document.body.style.filter = '', 3000);
    }
});