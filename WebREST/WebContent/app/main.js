
const HomePage = { template: "<home></home>" };
const Login = { template: "<login></login>" };
const Registration = { template: "<registration></registration>" };
const HomeUser = { template: "<homeUser></homeUser>" };
const ProfileUser = { template: "<profileUser></profileUser>" };
const ListUsers = { template: "<listUsers></listUsers>" };
const Amenities = { template: "<amenities></amenities>" };
const AddApartment = { template: "<addApartment></addApartment>" };
const Apartments = { template: "<apartments></apartments>" };
const Review = { template: "<review></review>" };
const EditApartment = { template: "<editApartment></editApartment>" };
const NewReservation = { template: "<newReservation></newReservation>" }

const routes = [
  { path: "/", component: HomePage },
  { path: "/login", component: Login },
  { path: "/registration", component: Registration },
  { path: "/homeUser", component: HomeUser },
  { path: "/profileUser", component: ProfileUser },
  { path: "/listUsers", component: ListUsers },
  { path: "/amenities", component: Amenities },
  { path: "/addApartment", component: AddApartment },
  { path: "/apartments", component: Apartments },
  { path: "/review", component: Review },
  { path: "/editApartment", component: EditApartment },
  { path: "/newReservation", component: NewReservation },
];

const router = new VueRouter({
  routes,
});

axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('jwt');

const app = new Vue({
  router,
}).$mount("#app");
