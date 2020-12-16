const HomePage = { template: "<home></home>" };

const routes = [{ path: "/", component: HomePage }];

const router = new VueRouter({
  routes,
});

const app = new Vue({
  router,
}).$mount("#app");
