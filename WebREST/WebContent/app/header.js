Vue.component("top-header", {
  data: function () {
    return {
      user: {
        role: localStorage.getItem('role')
      }
    }
  },
  template: `
  <div>
    <div class="navbar"> 
      <a href="#" v-if="user.role" v-on:click='logout()' data-toggle="tooltip" title="logout"><img  src="images/logout.png" width="31" height="31"> </a> 
      <a href="#/login" v-if="!user.role" data-toggle="tooltip" title="login" ><img  src="images/login.png" width="66" height="66"> </a>
      <a href="#/registration" v-if="!user.role" data-toggle="tooltip" title="register"><img  src="images/register.png" width="66" height="66"> </a> 
      <a href="#/homeUser" v-if="user.role" data-toggle="tooltip" title="home"><img  src="images/home.png" width="31" height="31"> </a>
      <a href="#/" v-if="!user.role" data-toggle="tooltip" title="home"><img  src="images/home.png" width="31" height="31"> </a>
      <a href="#/profileUser" v-if="user.role" data-toggle="tooltip" title="profile"><img  src="images/profile.png" width="31" height="31"> </a> 
      <a href="#" v-if="user.role" data-toggle="tooltip" title="reservations"><img  src="images/res.png" width="31" height="31"> </a> 
      <a href="#" data-toggle="tooltip" title="apartments"><img  src="images/apa.png" width="31" height="31"> </a>
      <a href="#" v-if="user.role ==='ADMIN'|| user.role=== 'HOST'" data-toggle="tooltip" title="users"><img  src="images/users.png" width="31" height="31"> </a>
      <p style='font-size:26px;float:left;font-family: cursive;color:#c41088;'><b>ROOMRY</b></p>
      <p style='font-size:26px;float:left;font-family:Brush Script MT;'>
      <br>apartments and rooms for rent</p>
    </div>
  </div>
`,
  methods: {
    logout: function () {


      Swal.fire({
        title: 'Are you sure you want to logout?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ffdff0',
        cancelButtonColor: '#c41088',
        confirmButtonText: 'Yes, Im sure!'
      }).then((result) => {
        if (result.isConfirmed) {
          localStorage.removeItem('username');
          localStorage.removeItem('password');
          localStorage.removeItem('role');
          localStorage.removeItem('jwt');
          this.$router.push('/login');
          window.location.reload(); //load pushed
        }
      })
    }
  }

})