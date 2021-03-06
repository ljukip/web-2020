Vue.component("homeUser", {
    data: function () {
        return {
            user: {
                username: localStorage.getItem('username'),
                role: localStorage.getItem('role')
            }
        }
    },
    template: `
   <div style="height: 81.7%;">
    <h1>Welcome <span style="color: seashell;">{{user.username}}</span></h1>
        <div id="Div-panel">
            <div style="flex-direction: row;">
                    <button class="oval" style="background-image: url('images/for-rent.jpg');display: inline-block;"@click="$router.push('/apartments')"><p></p> </button>
                    <button class="oval" style="background-image: url('images/reservations.jpg');display: inline-block;"@click="$router.push('/reservations') "><p></p></button>
                    <button v-if="user.role ==='ADMIN'" class="oval" style="background-image: url('images/amenities.jpg');display: inline-block;"@click="$router.push('/amenities')  "><p> </p></button>
                    <button v-if="user.role ==='ADMIN'" class="oval" style="background-image: url('images/users.jpg');display: inline-block;"@click="$router.push('/listUsers')  "><p> </p></button>
                    <button v-if="user.role ==='HOST'" class="oval" style="background-image: url('images/users.jpg');display: inline-block;"@click="$router.push('/listUsers')  "><p> </p></button>
                    <button v-if="user.role ==='GUEST'" class="oval" style="background-image: url('images/profileOval.png');display: inline-block;"@click="$router.push('/profileUser') "><p> </p></button>
            </div>
            <div style="flex-direction: row;">
                <router-link style="width: 40%;" to='/apartments'>apartments</router-link>
                <router-link style="width: 20%;" to='/reservations'>reservations</router-link>
                <router-link v-if="user.role ==='GUEST'" style="width: 38%;" to='/profileUser'>profile</router-link>
                <router-link v-if="user.role ==='HOST'" style="width: 38%;" to='/listUsers'>users</router-link>
                <router-link v-if="user.role ==='ADMIN'" style="width: 38%;" to='/listUsers'>users</router-link>
                <router-link v-if="user.role ==='ADMIN'" style="width: 38%;" to='/amenities'>amenities</router-link>
            </div>
        </div>
</div>
  
    `,
    methods: {

    }
})