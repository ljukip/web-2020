const HomePage = { template: "<home></home>" };
const Login = { template: "<login></login>" };
const Registration = { template: "<registration></registration>" }
const HomeUser = { template: "<homeUser></homeUser>" }
const HomeAdmin = { template: "<homeAdmin></homeAdmin>" }
const HomeHost = { template: "<homeHost></homeHost>" }

const routes = [
  { path: "/", component: HomePage },
  { path: "/login", component: Login },
  { path: "/registration", component: Registration },
  { path: "/homeUser", component: HomeUser },
  { path: "/homeAdmin", component: HomeAdmin },
  { path: "/homeHost", component: HomeHost }
];

const router = new VueRouter({
  routes,
});

axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('jwt');

const app = new Vue({
  router,
}).$mount("#app");
