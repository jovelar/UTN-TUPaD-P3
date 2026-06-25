type Rol = "ADMIN" | "USUARIO";

interface IUser {
    id: number;
    nombre: string;
    apellido: string;
    mail: string;
    loggedIn: boolean;
    role: Rol;
}

interface UsuarioJSON {
    id: number;
    nombre: string;
    apellido: string;
    mail: string;
    celular: string;
    password: string;
    rol: Rol;
}

const form = document.querySelector<HTMLFormElement>(".login-box__form");
const nombreInput = document.querySelector<HTMLInputElement>("#nombre");
const emailInput = document.querySelector<HTMLInputElement>("#email");
const passwordInput = document.querySelector<HTMLInputElement>("#password");

function mostrarError(mensaje: string): void {
    let error = document.querySelector<HTMLParagraphElement>("#error");
    if (!error) {
        error = document.createElement("p");
        error.id = "error";
        error.style.color = "var(--color-danger)";
        form?.appendChild(error);
    }
    error.textContent = mensaje;
}

function limpiarError(): void {
    document.querySelector("#error")?.remove();
}

form?.addEventListener("submit", async (e: Event) => {
    e.preventDefault();
    limpiarError();

    const nombre = nombreInput?.value.trim() ?? "";
    const mail = emailInput?.value.trim() ?? "";
    const password = passwordInput?.value ?? "";

    // Validación de campos requeridos
    if (!nombre || !mail || !password) {
        mostrarError("Completá todos los campos.");
        return;
    }

    // Validación básica de email
    if (!mail.includes("@")) {
        mostrarError("Ingresá un email válido.");
        return;
    }

    // Validación de contraseña mínima
    if (password.length < 4) {
        mostrarError("La contraseña debe tener al menos 4 caracteres.");
        return;
    }

    try {
        const res = await fetch("/data/usuarios.json");
        if (!res.ok) throw new Error("No se pudo cargar usuarios.json");
        const usuarios: UsuarioJSON[] = await res.json();

        // Verificar que el email NO exista ya
        const existe = usuarios.some(
            (u) => u.mail.toLowerCase() === mail.toLowerCase()
        );
        if (existe) {
            mostrarError("Ya existe una cuenta con ese email.");
            return;
        }

        // Crear el usuario nuevo y guardar la sesión
        const nuevoUsuario: IUser = {
            id: Date.now(),
            nombre: nombre,
            apellido: "",
            mail: mail,
            loggedIn: true,
            role: "USUARIO",
        };
        localStorage.setItem("userData", JSON.stringify(nuevoUsuario));

        // Redirigir a la tienda
        window.location.href = "/src/pages/store/home/home.html";
    } catch (err) {
        mostrarError("Ocurrió un error. Intente de nuevo.");
        console.error(err);
    }
});