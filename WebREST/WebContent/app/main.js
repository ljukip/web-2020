const HomePage = { template: "<home></home>" };
const Login = { template: "<login></login>" };

const routes = [
  { path: "/", component: HomePage },
  { path: "/login", component: Login },
];

const router = new VueRouter({
  routes,
});

const app = new Vue({
  router,
}).$mount("#app");
