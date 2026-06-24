import type { IUser } from "../../../types/IUser";
import type { Rol } from "../../../types/Rol";
import { navigate } from "../../../utils/navigate";

interface UsuarioJSON {
    id: number;
    nombre: string;
    apellido: string;
    mail: string;
    password: string;
    rol: Rol;
}

const form = document.getElementById("form") as HTMLFormElement;
const inputEmail = document.getElementById("email") as HTMLInputElement;
const inputPassword = document.getElementById("password") as HTMLInputElement;
const errorMsg = document.getElementById("errorMsg") as HTMLParagraphElement;

form.addEventListener("submit", async (e: SubmitEvent) => {
    e.preventDefault();

    const mail = inputEmail.value.trim();
    const password = inputPassword.value.trim();

    let usuarios: UsuarioJSON[] = [];

    try {
        const response = await fetch("/data/usuarios.json");
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        usuarios = await response.json();
    } catch (err) {
        console.error("Error al cargar usuarios.json:", err);
        if (errorMsg) errorMsg.textContent = `Error al cargar datos: ${err}`;
        return;
    }

    const encontrado = usuarios.find(u => u.mail === mail && u.password === password);

    if (!encontrado) {
        if (errorMsg) errorMsg.textContent = "Email o contraseña incorrectos.";
        return;
    }

const user: IUser = {
    id: encontrado.id,
    nombre: encontrado.nombre,
    apellido: encontrado.apellido,
    mail: encontrado.mail,
    loggedIn: true,
    role: encontrado.rol,
};

    localStorage.setItem("userData", JSON.stringify(user));

    if (encontrado.rol === "ADMIN") {
        navigate("/src/pages/admin/adminHome/adminHome.html");
    } else {
        navigate("/src/pages/store/home/home.html");
    }
});
