/*
import { defineConfig } from "vite";
import { resolve } from "path";

export default defineConfig({
  build: {
    rollupOptions: {
      input: {
        
        //d:aplicaion/dist/
        index: resolve(__dirname, "index.html"),
        login: resolve(__dirname, "src/pages/auth/login/login.html"),
        //registro: resolve(__dirname, "src/pages/auth/registro/registro.html"),
        //adminHome: resolve(__dirname, "src/pages/admin/home/home.html"),
        //clientHome: resolve(__dirname, "src/pages/client/home/home.html"),
        storeHome : resolve(__dirname,"src/pages/store/home/home.html"),
        storeCart : resolve(__dirname,"src/pages/store/cart/cart.html"),
      }
    },
  },
  base: "./",
});*/

import { defineConfig } from "vite";
import { resolve } from "path";

export default defineConfig({
  build: {
    rollupOptions: {
      input: {
        index: resolve(__dirname, "index.html"),

        // auth, parte de login
        login: resolve(__dirname, "src/pages/auth/login/login.html"),
        register: resolve(__dirname, "src/pages/auth/register/register.html"),

        // store (USUARIO)
        storeHome: resolve(__dirname, "src/pages/store/home/home.html"),
        productDetail: resolve(__dirname, "src/pages/store/productDetail/productDetail.html"),
        storeCart: resolve(__dirname, "src/pages/store/cart/cart.html"),

        // parte de client (USUARIO)
        clientOrders: resolve(__dirname, "src/pages/client/orders/orders.html"),

        // parte de admin (ADMIN)
        adminHome: resolve(__dirname, "src/pages/admin/adminHome/adminHome.html"),
        categories: resolve(__dirname, "src/pages/admin/categories/categories.html"),
        products: resolve(__dirname, "src/pages/admin/products/products.html"),
        adminOrders: resolve(__dirname, "src/pages/admin/orders/orders.html"),
      },
    },
  },
  base: "./",
});
