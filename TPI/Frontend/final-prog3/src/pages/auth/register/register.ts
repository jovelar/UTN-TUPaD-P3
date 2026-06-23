type Rol = "ADMIN" | "USUARIO";

    interface Usuario {
    id: number;
    nombre: string;
    apellido: string;
    mail: string;
    celular: string;
    password: string;
    rol: Rol;
    }

    type UsuarioEnSesion = Omit<Usuario, "password">;

    const form = document.querySelector<HTMLFormElement>(".login-box__form");
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

    const mail = emailInput?.value.trim() ?? "";
    const password = passwordInput?.value ?? "";

    if (!mail || !password) {
        mostrarError("Falta email y contraseña.");
        return;
    }

    try {
        const res = await fetch("/data/usuarios.json");
        if (!res.ok) throw new Error("No se pudo cargar usuarios.json");
        const usuarios: Usuario[] = await res.json();

        const usuario = usuarios.find(
        (u) => u.mail.toLowerCase() === mail.toLowerCase()
        );

        if (!usuario || usuario.password !== password) {
        mostrarError("Email o contraseña incorrectos.");
        return;
        }

        const usuarioEnSesion: UsuarioEnSesion = {
        id: usuario.id,
        nombre: usuario.nombre,
        apellido: usuario.apellido,
        mail: usuario.mail,
        celular: usuario.celular,
        rol: usuario.rol,
        };
        localStorage.setItem("usuario", JSON.stringify(usuarioEnSesion));

        if (usuario.rol === "ADMIN") {
        window.location.href = "/src/pages/admin/adminHome/adminHome.html";
        } else {
        window.location.href = "/src/pages/store/home/home.html";
        }
    } catch (err) {
        mostrarError("Ocurrió un error. Intente de nuevo.");
        console.error(err);
    }
    });