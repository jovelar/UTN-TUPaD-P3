import { checkAuhtUser, logout } from "../../../utils/auth";
import { getUSer } from "../../../utils/localStorage";

document.getElementById("logoutButton")?.addEventListener("click", logout);

const raw = getUSer();
if (raw) {
    const user = JSON.parse(raw);
    const span = document.getElementById("userName");
    if (span) span.textContent = user.nombre;
}

checkAuhtUser(
    "/src/pages/auth/login/login.html",
    "/src/pages/store/home/home.html",
    "ADMIN"
);
